package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.globalitems.niesy.Map1_10;
import rpg.rpgcore.utils.Utils;

public class PlayerItemPickUpListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemPickUp(final PlayerPickupItemEvent e) {
        if (e.getItem().getItemStack().getType().equals(Material.DIAMOND_BLOCK) && e.getItem().getItemStack().getItemMeta().hasDisplayName()) {
            e.getItem().remove();
            e.setCancelled(true);
            final String itemName = Utils.removeColor(e.getItem().getItemStack().getItemMeta().getDisplayName());
            final String expowisko = itemName.substring(itemName.lastIndexOf(" ") + 1).trim();
            e.getPlayer().sendMessage(expowisko);
            Bukkit.broadcastMessage(Utils.format("&6&lDROP " + expowisko +" &8>> &fGracz &b" + e.getPlayer().getName() + " &fznalazl &b&lNiesamowity Przedmiot"));

            int amount = e.getItem().getItemStack().getAmount();

            if (e.getPlayer().getInventory().containsAtLeast(e.getItem().getItemStack(), 1)) {
                for (ItemStack is : e.getPlayer().getInventory().getContents()) {
                    if (is != null && is.isSimilar(e.getItem().getItemStack())) {
                        amount += is.getAmount();
                    }
                }
            }

            for (int i = 0; i < amount; i++) {
                switch (expowisko) {
                    case "1-10":
                        Map1_10.getDrop(e.getPlayer(), (double) RPGCORE.getInstance().getBonusesManager().find(e.getPlayer().getUniqueId()).getBonusesUser().getSzczescie() / 100);
                        e.getPlayer().sendMessage("" + ((double) RPGCORE.getInstance().getBonusesManager().find(e.getPlayer().getUniqueId()).getBonusesUser().getSzczescie() / 100));
                        break;
                    case "10-20":
                        break;
                }
                e.getPlayer().getInventory().removeItem(e.getItem().getItemStack());
            }
        }
    }
}
