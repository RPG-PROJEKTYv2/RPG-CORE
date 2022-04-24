package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

public class InventoryDragListener implements Listener {

    private final RPGCORE rpgcore;

    public InventoryDragListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }
    

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryDrag(final InventoryDragEvent e) {
        if (e.getInventory().getType().equals(InventoryType.PLAYER)) {
            return;
        }
        if (e.getInventory().getTitle().contains("Wymiana ")) {
            e.setCancelled(true);
        }
    }

}
