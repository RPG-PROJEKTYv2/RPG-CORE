package rpg.rpgcore.trade;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;

public class TRADEInventoryDrag implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void tradeInventoryDragEvent(final InventoryDragEvent e) {
        if (e.getInventory().getType().equals(InventoryType.PLAYER)) {
            return;
        }
        if (e.getInventory().getTitle().contains("Wymiana")) {
            e.setCancelled(true);
        }
    }

}
