package rpg.rpgcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryItemDragListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void tradeInventoryDragEvent(final InventoryDragEvent e) {
        if (e.getWhoClicked().getOpenInventory().getTopInventory().getTitle().contains("container.")) {
            return;
        }
        e.setCancelled(true);
    }
}
