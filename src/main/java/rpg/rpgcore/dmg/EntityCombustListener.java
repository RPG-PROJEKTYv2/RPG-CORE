package rpg.rpgcore.dmg;

import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class EntityCombustListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onIgnite(EntityCombustEvent e) {
        if (e.getEntity() instanceof Monster) {
            e.setCancelled(true);
        }
    }
}
