package rpg.rpgcore.dungeons.zamekNieskonczonosci.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ZamekNieskonczonosciEntityDamgeListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(final EntityDamageEvent e) {
        if (e.getEntity().getWorld().getName().equalsIgnoreCase("zamekNieskonczonosci")) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) e.setCancelled(true);
        }
    }
}
