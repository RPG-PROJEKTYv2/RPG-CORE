package rpg.rpgcore.pets.listeners;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.entities.PetArmorStand.PetArmorStand;
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

                if (Utils.checkIfLoreContainsString(item.clone().getItemMeta().getLore(), "Przywolany") && removeFromLore(item.clone()).equals(pet.getItem().clone())) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz najpierw zdespawnowac swojego zwierzaka!"));
                    return;
                }

                final UserPets userPets = rpgcore.getPetyManager().findUserPets(uuid);
                player.getInventory().addItem(rpgcore.getPetyManager().addExpToLore(item.clone()));
                userPets.removePet(item);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUserPets(userPets.getUuid(), userPets));
                e.getClickedInventory().setItem(40, new ItemBuilder(e.getClickedInventory().getItem(40)).removeGlowing().toItemStack());
                rpgcore.getPetyManager().openPetyGUI(player);
                return;
            }


            if (pet.getItem() != null) {
                final ItemStack prevPet = pet.getItem();
                if (removeFromLore(item.clone()).equals(prevPet)) {
                    pet.despawn(prevPet, uuid);
                    EntityTypes.despawnPet(uuid);
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataActivePets(uuid, rpgcore.getPetyManager().findActivePet(uuid)));
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPomyslnie zdespawnowales " + prevPet.getItemMeta().getDisplayName() + "&c!"));
                    player.closeInventory();
                    return;
                }
                EntityTypes.despawnPet(uuid);
                pet.spawn(item.clone(), uuid);
                EntityTypes.spawnEntity(new PetArmorStand(((CraftWorld) player.getLocation().getWorld()).getHandle(), player), player.getUniqueId(), player.getLocation(), item.clone().getItemMeta().getDisplayName() + " " + pet.getItem().clone().getItemMeta().getDisplayName().substring(0, 2) + player.getName()); //.substring(0, item.clone().getItemMeta().getDisplayName().indexOf(" "))
                EntityTypes.addEquipment(EntityTypes.getEntity(player.getUniqueId()), item.clone());

                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie przywolales " + item.getItemMeta().getDisplayName() + "&a!"));
                player.closeInventory();

                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataActivePets(uuid, rpgcore.getPetyManager().findActivePet(uuid)));
            } else {
                pet.spawn(item.clone(), uuid);
                EntityTypes.spawnEntity(new PetArmorStand(((CraftWorld) player.getLocation().getWorld()).getHandle(), player), player.getUniqueId(), player.getLocation(), item.clone().getItemMeta().getDisplayName() + " " + pet.getItem().clone().getItemMeta().getDisplayName().substring(0, 2) + player.getName());
                EntityTypes.addEquipment(EntityTypes.getEntity(player.getUniqueId()), item.clone());


                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie przywolales " + item.getItemMeta().getDisplayName() + "&a!"));
                player.closeInventory();

                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataActivePets(uuid, rpgcore.getPetyManager().findActivePet(uuid)));
            }
        }
    }

    private ItemStack removeFromLore(final ItemStack is) {
        final ItemMeta im = is.getItemMeta();
        final List<String> lore = im.getLore();
        lore.remove(lore.size() - 1);
        lore.remove(lore.size() - 1);
        im.setLore(lore);
        is.setItemMeta(im);
        System.out.println(lore);
        return is;
    }
}
