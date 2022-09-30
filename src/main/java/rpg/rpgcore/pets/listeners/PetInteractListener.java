package rpg.rpgcore.pets.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.pets.UserPets;
import rpg.rpgcore.utils.Utils;

import java.util.List;
import java.util.UUID;

public class PetInteractListener implements Listener {
    private final RPGCORE rpgcore;

    public PetInteractListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private ItemStack removeExpFromLore(final ItemStack is) {
        final ItemMeta im = is.getItemMeta();
        final List<String> lore = im.getLore();
        lore.remove(lore.size() - 1);
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final PlayerInteractEvent e) {
        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (eventItem == null) {
            return;
        }

        if (eventItem.getType().equals(Material.SKULL_ITEM) && eventItem.getDurability() == 3) {
            e.setCancelled(true);
            if (!Utils.checkIfLoreContainsString(eventItem.getItemMeta().getLore(), "Zwierzak")) {
                return;
            }

            if (eventItem.getAmount() > 1) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz miec wiecej niz 1 przedmiotu!"));
                return;
            }

            final UserPets userPets = rpgcore.getPetyManager().findUserPets(uuid);
            if (!userPets.getPety().isEmpty()) {
                for (final ItemStack is : userPets.getPety()) {
                    if (is == null || is.getType().equals(Material.AIR) || !is.hasItemMeta() || !is.getItemMeta().hasLore()) {
                        continue;
                    }
                    // TODO DOOKONCZYC SPRAWDZANIE CZY JEST TAKI SAM
                    System.out.println("Lore - " + Utils.checkIfLoreContainsString(is.getItemMeta().getLore(), "Przywolany"));
                    if (Utils.checkIfLoreContainsString(is.getItemMeta().getLore(), "Przywolany")) {
                        System.out.println("Taki sam item - " + removeFromLore(is).isSimilar(removeExpFromLore(eventItem.clone())));
                    }
                    System.out.println("Suma - " + (Utils.checkIfLoreContainsString(is.getItemMeta().getLore(), "Przywolany") && removeFromLore(is).isSimilar(removeExpFromLore(eventItem.clone())))); // TU COS NIE DZIALA
                    System.out.println(is.equals(removeExpFromLore(eventItem.clone()))); // TA SEKCJA DZIALA
                    if ((Utils.checkIfLoreContainsString(is.getItemMeta().getLore(), "Przywolany") && removeFromLore(is).isSimilar(removeExpFromLore(eventItem.clone()))) || is.isSimilar(removeExpFromLore(eventItem.clone()))) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPosiadasz juz tego zwierzaka w swojej kolekcji!"));
                        return;
                    }
                }
            }

            player.getInventory().removeItem(eventItem);
            userPets.addPet(removeExpFromLore(eventItem));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie dodales " + eventItem.getItemMeta().getDisplayName() + " &ado swojej kolekcji zwierzakow!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUserPets(userPets.getUuid(), userPets));
        }
    }
}
