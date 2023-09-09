package rpg.rpgcore.npc.zlotnik.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class ZlotnikInventoryCloseListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (Utils.removeColor(e.getInventory().getTitle()).equals("Zlotnik")) {
            if (e.getInventory().getItem(2).isSimilar(RPGCORE.getInstance().getZlotnikNPC().getMiejsceItem())) return;
            e.getPlayer().getInventory().addItem(e.getInventory().getItem(2));
            e.getInventory().setItem(2, RPGCORE.getInstance().getZlotnikNPC().getMiejsceItem());
        }
    }
}
