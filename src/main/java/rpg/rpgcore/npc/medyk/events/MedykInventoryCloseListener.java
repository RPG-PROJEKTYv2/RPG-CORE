package rpg.rpgcore.npc.medyk.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import rpg.rpgcore.utils.Utils;

public class MedykInventoryCloseListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (Utils.removeColor(e.getInventory().getTitle()).contains("Medyk - Bonus")) {
            if (e.getInventory().getItem(1) != null && !e.getInventory().getItem(1).getType().equals(Material.AIR)
                    && e.getInventory().getItem(1).hasItemMeta() && e.getInventory().getItem(1).getItemMeta().hasDisplayName() && !e.getInventory().getItem(1).getItemMeta().getDisplayName().contains("Mikstura Medyka")) {
                e.getPlayer().getInventory().addItem(e.getInventory().getItem(1));
            }
            if (e.getInventory().getItem(3) != null && !e.getInventory().getItem(3).getType().equals(Material.AIR)) {
                e.getPlayer().getInventory().addItem(e.getInventory().getItem(3));
            }
        }
    }
}
