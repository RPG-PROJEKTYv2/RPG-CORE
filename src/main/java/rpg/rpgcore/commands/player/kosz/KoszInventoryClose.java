package rpg.rpgcore.commands.player.kosz;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.Utils;

public class KoszInventoryClose implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void koszInventoryClose(final InventoryCloseEvent e) {

        if (e.getInventory() == null) {
            return;
        }

        final Inventory closedInventory = e.getInventory();
        final String closedInventoryTitle = closedInventory.getTitle();

        if (Utils.removeColor(closedInventoryTitle).equals("Kosz")) {
            e.getInventory().clear();
        }
    }
}
