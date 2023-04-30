package rpg.rpgcore.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InvseeInventoryClickListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();

        if (gui == null || e.getInventory() == null) return;

        if (!gui.getName().contains("Ekwipunek gracza")) return;
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getType().name().contains("AIR")) return;
        if (e.getSlot() > 39) e.setCancelled(true);
    }
}
