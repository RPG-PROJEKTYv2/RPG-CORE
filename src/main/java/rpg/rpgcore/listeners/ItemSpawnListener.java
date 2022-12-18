package rpg.rpgcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemSpawnListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemSpawn(final ItemSpawnEvent e) {
        e.getEntity().setCustomName(e.getEntity().getItemStack().getItemMeta().getDisplayName());
        e.getEntity().setCustomNameVisible(true);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemDrop(final PlayerDropItemEvent e) {
        e.setCancelled(true);
    }
}
