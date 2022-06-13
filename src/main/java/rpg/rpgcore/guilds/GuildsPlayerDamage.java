package rpg.rpgcore.guilds;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

public class GuildsPlayerDamage implements Listener {

    private final RPGCORE rpgcore;

    public GuildsPlayerDamage(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void guildPlayerDamage(final EntityDamageByEntityEvent e) {

        final Entity entity = e.getEntity();
        final Entity damager = e.getDamager();

        if (entity instanceof Player && damager instanceof Player) {
            final Player damagerPlayer = (Player) damager;
            final Player target = (Player) entity;
            final UUID uuidPlayer = damagerPlayer.getUniqueId();
            final UUID targetUUID = target.getUniqueId();

            final String tagPlayer = rpgcore.getGuildManager().getGuildTag(uuidPlayer);
            final String tagTarget = rpgcore.getGuildManager().getGuildTag(targetUUID);

            if (tagPlayer != null && tagTarget != null) {
                if (tagPlayer.equals(tagTarget)) {
                    if (!rpgcore.getGuildManager().getGuildPvPStatus(tagPlayer)) {
                        e.setCancelled(true);
                    }
                }
            }
        }

    }
}
