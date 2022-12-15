package rpg.rpgcore.zmianki.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class ZmiankiInventoryCloseListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClose(final InventoryCloseEvent e) {
        if (Utils.removeColor(e.getInventory().getTitle()).equals("Zmiana Bonusow")) {
            if (e.getInventory().getItem(13) != null && e.getInventory().getItem(13).getType() != Material.AIR) {
                RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getZmiankiManager().openGUI((Player) e.getPlayer(), e.getInventory().getItem(13)), 1L);
                e.getPlayer().sendMessage(Utils.format("&cMusisz najpier wyjac swoj przedmiot!"));
            }
        }
    }
}
