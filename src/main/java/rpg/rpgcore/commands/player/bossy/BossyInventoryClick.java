package rpg.rpgcore.commands.player.bossy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.Utils;


public class BossyInventoryClick implements Listener {


    public BossyInventoryClick() {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void duszologInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        final String clickedInventoryTitle = clickedInventory.getTitle();

        if (Utils.removeColor(clickedInventoryTitle).equals("Lista bossów")) {
            e.setCancelled(true);
        }
    }
}
