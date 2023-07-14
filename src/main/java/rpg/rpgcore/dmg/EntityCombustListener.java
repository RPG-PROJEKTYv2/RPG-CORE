package rpg.rpgcore.dmg;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityCombustListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onIgnite(EntityCombustEvent e) {
        if (e.getEntity() instanceof Monster) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExplode(final EntityExplodeEvent e) {
        if (e.getEntity() instanceof EnderCrystal) e.setCancelled(true);
        if (e.getEntity() instanceof Creeper) e.setCancelled(true);
        if (e.getEntity() instanceof Fireball) e.setCancelled(true);
    }
}
