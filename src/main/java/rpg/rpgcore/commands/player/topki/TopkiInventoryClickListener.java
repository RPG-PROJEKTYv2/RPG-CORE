package rpg.rpgcore.commands.player.topki;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import rpg.rpgcore.utils.Utils;

public class TopkiInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        if (Utils.removeColor(e.getClickedInventory().getTitle()).equals("Topki")) {
            e.setCancelled(true);
        }
    }
}
