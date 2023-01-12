package rpg.rpgcore.commands.player.administracja;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.Utils;

public class AdministracjaInventoryClick implements Listener{

        public AdministracjaInventoryClick() {
        }
        @EventHandler(priority = EventPriority.LOWEST)
        public void administracjaInventoryClick(final InventoryClickEvent e) {

            if (e.getClickedInventory() == null || e.getInventory() == null) {
                return;
            }

            final Inventory clickedInventory = e.getClickedInventory();
            final String clickedInventoryTitle = clickedInventory.getTitle();

            if (Utils.removeColor(clickedInventoryTitle).equals("Administracja")) {
                e.setCancelled(true);
            }
        }
}
