package rpg.rpgcore.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

public class EntityDamageListener implements Listener {

    private final RPGCORE rpgcore;

    public EntityDamageListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(final EntityDamageEvent e) {


        final Entity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        final Player p = (Player) entity;
        final UUID uuid = p.getUniqueId();

        if (rpgcore.getGodManager().containsPlayer(uuid)) {
            e.setCancelled(true);
        }

    }

}
