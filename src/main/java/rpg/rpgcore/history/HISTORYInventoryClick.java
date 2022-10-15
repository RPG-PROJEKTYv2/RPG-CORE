package rpg.rpgcore.history;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class HISTORYInventoryClick implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void historyInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getInventory();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();


        if (clickedInventoryTitle.contains("Historia kar gracza ")) {
            e.setCancelled(true);
        }
    }
}
