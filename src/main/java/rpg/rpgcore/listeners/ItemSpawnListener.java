package rpg.rpgcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemSpawnListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemSpawn(final ItemSpawnEvent e) {
        e.getEntity().setCustomName(e.getEntity().getItemStack().getItemMeta().getDisplayName());
        e.getEntity().setCustomNameVisible(true);
    }
}
