package rpg.rpgcore.npc.lesnik;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;
import rpg.rpgcore.utils.Utils;

public class LesnikInventoryClose implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (Utils.removeColor(e.getInventory().getTitle()).equals("Lesnik")) {
            if (e.getInventory().getItem(22) != null && e.getInventory().getItem(22).equals(LesnikItems.POTION.getItem())) {
                e.getPlayer().getInventory().addItem(LesnikItems.POTION.getItem());
            }
        }
    }
}
