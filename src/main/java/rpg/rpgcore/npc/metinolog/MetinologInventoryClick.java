package rpg.rpgcore.npc.metinolog;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class MetinologInventoryClick implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void metinologInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (Utils.removeColor(clickedInventoryTitle).equals("Metinolog")) {
            e.setCancelled(true);
        }
    }
}
