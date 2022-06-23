package rpg.rpgcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryItemDragListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void tradeInventoryDragEvent(final InventoryDragEvent e) {
        if (!e.getInventory().getType().equals(InventoryType.PLAYER)) {
            e.setCancelled(true);
        }
    }
}
