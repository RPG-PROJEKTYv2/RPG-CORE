package rpg.rpgcore.newTarg;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class NewTargInventoryClose implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (Utils.removeColor(e.getInventory().getName()).equals("Wystaw przedmiot")) {
            if (e.getInventory().getItem(4).getType() != Material.AIR) {
                RPGCORE.getInstance().getNewTargManager().returnPlayerItem((Player) e.getPlayer(), e.getInventory().getItem(4));
                RPGCORE.getInstance().getNewTargManager().removeFromWystawia(e.getPlayer().getUniqueId());
            }
        }
    }
}
