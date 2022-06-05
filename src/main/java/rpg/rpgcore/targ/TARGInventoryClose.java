package rpg.rpgcore.targ;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class TARGInventoryClose implements Listener {

    private final RPGCORE rpgcore;

    public TARGInventoryClose(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void targInventoryClose(final InventoryCloseEvent e) {

        if (e.getInventory() == null) {
            return;
        }

        final Inventory closedInventory = e.getInventory();
        final String closedInventoryTitle = closedInventory.getTitle();
        final Player player = (Player) e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (Utils.removeColor(closedInventoryTitle).equals("Wystaw przedmiot")) {
            final ItemStack itemToGiveBack = closedInventory.getItem(4);
            if (!(rpgcore.getTargManager().isInWystawia(uuid))) {
                return;
            }
            rpgcore.getTargManager().returnPlayerItem(player, itemToGiveBack);
            rpgcore.getTargManager().removeFromWystawia(uuid);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, player::closeInventory, 1L);
            return;
        }

        if (closedInventoryTitle.contains("Targ gracza ")) {
            if (rpgcore.getTargManager().isInTaskMap(uuid)) {
                rpgcore.getTargManager().removePlayerFromTaskMap(uuid);
            }
        }
    }

}
