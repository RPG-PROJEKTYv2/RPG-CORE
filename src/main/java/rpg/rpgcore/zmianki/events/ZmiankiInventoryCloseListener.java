package rpg.rpgcore.zmianki.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class ZmiankiInventoryCloseListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClose(final InventoryCloseEvent e) {
        if (Utils.removeColor(e.getInventory().getTitle()).equals("Zmiana Bonusow")) {
            if (e.getInventory().getItem(13).getType() != Material.IRON_FENCE) {
                final ItemStack toReturn = e.getInventory().getItem(13).clone();
                RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> e.getPlayer().getInventory().addItem(toReturn), 1L);
            }
        }
    }
}
