package rpg.rpgcore.commands.kosz;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class KoszInventoryClick implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void koszInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }
    }

}
