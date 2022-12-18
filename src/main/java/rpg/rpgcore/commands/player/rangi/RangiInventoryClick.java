package rpg.rpgcore.commands.player.rangi;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.Utils;

public class RangiInventoryClick implements Listener {
    public RangiInventoryClick() {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void rangiInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        final String clickedInventoryTitle = clickedInventory.getTitle();

        if (Utils.removeColor(clickedInventoryTitle).equals("Rangi")) {
            e.setCancelled(true);
        }
    }
}
