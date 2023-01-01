package rpg.rpgcore.artefakty.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import rpg.rpgcore.utils.Utils;


public class ArtefaktyInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        if (Utils.removeColor(e.getClickedInventory().getTitle()).equals("Artefakty")) {
            e.setCancelled(true);
        }
    }
}
