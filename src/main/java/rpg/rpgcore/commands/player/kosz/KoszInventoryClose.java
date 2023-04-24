package rpg.rpgcore.commands.player.kosz;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.discord.EmbedUtil;
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
            final ItemStack[] items = closedInventory.getContents();
            e.getInventory().clear();
            if (items.length == 0) return;
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () ->
                    RPGCORE.getDiscordBot().sendChannelMessage("kosz-logs", EmbedUtil.createKoszLogs((Player) e.getPlayer(), items))
            );
        }
    }
}
