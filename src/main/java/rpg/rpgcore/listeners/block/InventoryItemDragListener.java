package rpg.rpgcore.listeners.block;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryItemDragListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void tradeInventoryDragEvent(final InventoryDragEvent e) {
        if (e.getWhoClicked().getOpenInventory().getTopInventory().getTitle().contains("container.") || e.getWhoClicked().getOpenInventory().getTopInventory().getTitle().contains("Magazyn")) {
            return;
        }
        e.setCancelled(true);
        e.setResult(Event.Result.DENY);
    }
}
