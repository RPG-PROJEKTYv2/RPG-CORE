package rpg.rpgcore.npc.kupiec;

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

public class KupiecInventoryClose implements Listener {

    private final RPGCORE rpgcore;

    public KupiecInventoryClose(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void magazynierInventoryClose (final InventoryCloseEvent e) {

        if (e.getInventory() == null) {
            return;
        }

        final Inventory closedInventory = e.getInventory();
        final String closedInventoryTitle = closedInventory.getTitle();
        final Player player = (Player) e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (Utils.removeColor(closedInventoryTitle).equals("Kupiec")) {

            for (ItemStack is : rpgcore.getKupiecNPC().getPlayerItemStackList(uuid).keys()) {
                for (int i = 0; i < is.getAmount(); i++) {
                    player.getInventory().addItem(is);
                }
            }

            rpgcore.getKupiecNPC().resetPlayerSellValueItems(uuid);
            rpgcore.getKupiecNPC().resetPlayerItemStack(uuid);
        }
    }
}
