package rpg.rpgcore.pets.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.pets.Pet;
import rpg.rpgcore.pets.UserPets;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.List;
import java.util.UUID;

public class PetInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public PetInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        final String title = Utils.removeColor(e.getClickedInventory().getTitle());
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final ItemStack item = e.getCurrentItem();

        if (title.equals("Menu Petow")) {
            e.setCancelled(true);
            if (!e.getClickedInventory().getHolder().equals(player)) {
                return;
            }

            if (item == null || item.getType().equals(Material.STAINED_GLASS_PANE) || item.getType().equals(Material.AIR) || !item.getItemMeta().hasDisplayName()) {
                return;
            }

            if (item.getType().equals(Material.ARMOR_STAND)) {
                if (item.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)) {
                    e.getClickedInventory().setItem(40, new ItemBuilder(e.getClickedInventory().getItem(40)).removeGlowing().toItemStack());
                    return;
                }
                e.getClickedInventory().setItem(40, new ItemBuilder(e.getClickedInventory().getItem(40)).addGlowing().toItemStack());
                return;
            }

            final Pet pet = rpgcore.getPetyManager().findActivePet(uuid).getPet();

            if (e.getClickedInventory().getItem(40).getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)) {

                if (item.isSimilar(pet.getItem())) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz najpierw zdespawnowac swojego zwierzaka!"));
                    return;
                }

                final UserPets userPets = rpgcore.getPetyManager().findUserPets(uuid);
                player.getInventory().addItem(addExpToLore(item.clone()));
                userPets.removePet(item);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUserPets(userPets.getUuid(), userPets));
                e.getClickedInventory().setItem(40, new ItemBuilder(e.getClickedInventory().getItem(40)).removeGlowing().toItemStack());
                rpgcore.getPetyManager().openPetyGUI(player);
                return;
            }


            if (pet.getItem() != null) {
                final ItemStack prevPet = pet.getItem();
                if (pet.getRarity().equals("Mityczny")) {
                    Utils.removeBonuses(uuid, prevPet.getItemMeta().getLore().get(1));
                    Utils.removeBonuses(uuid, prevPet.getItemMeta().getLore().get(2));
                    Utils.removeBonuses(uuid, prevPet.getItemMeta().getLore().get(3));
                } else {
                    Utils.removeBonuses(uuid, prevPet.getItemMeta().getLore().get(1));
                    Utils.removeBonuses(uuid, prevPet.getItemMeta().getLore().get(2));
                }
                if (new ItemBuilder(item).removeGlowing().toItemStack().equals(prevPet)) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPomyslnie zdespawnowales " + prevPet.getItemMeta().getDisplayName() + "&c!"));
                    player.closeInventory();
                    pet.despawn();
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataActivePets(uuid, rpgcore.getPetyManager().findActivePet(uuid)));
                    return;
                }
            }
            pet.setItem(item);
            pet.setName(Utils.removeColor(item.getItemMeta().getDisplayName()));
            pet.setRarity(getRarity(item));
            if (pet.getRarity().equals("Mityczny")) {
                pet.setValue1(Utils.addBonuses(uuid, item.getItemMeta().getLore().get(1)));
                pet.setValue2(Utils.addBonuses(uuid, item.getItemMeta().getLore().get(2)));
                pet.setValue3(Utils.addBonuses(uuid, item.getItemMeta().getLore().get(3)));
            } else {
                pet.setValue1(Utils.addBonuses(uuid, item.getItemMeta().getLore().get(1)));
                pet.setValue2(Utils.addBonuses(uuid, item.getItemMeta().getLore().get(2)));
            }

            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie przywolales " + item.getItemMeta().getDisplayName() + "&a!"));
            player.closeInventory();

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataActivePets(uuid, rpgcore.getPetyManager().findActivePet(uuid)));
        }
    }

    private ItemStack addExpToLore(final ItemStack is) {
        final ItemMeta im = is.getItemMeta();
        final List<String> lore = im.getLore();
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        lore.add(Utils.format("&8Exp: &6" + nmsStack.getTag().getDouble("PetExp") + "&8/&6" + nmsStack.getTag().getDouble("ReqPetExp")));
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    private String getRarity(final ItemStack is) {
        for (String s : is.getItemMeta().getLore()) {
            if (s.contains(" Zwierzak")) {
                return Utils.removeColor(s).replace("Zwierzak", "").replaceAll(" ", "");
            }
        }
        return "";
    }
}
