package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

public class EntityDamageEntityListener implements Listener {

    private final RPGCORE rpgcore;

    public EntityDamageEntityListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(final EntityDamageByEntityEvent e) {

        final Entity entity = e.getEntity();

        if (entity instanceof Player) {
            final Player target = (Player) entity;
            final UUID uuid = target.getUniqueId();

            if (rpgcore.getGodManager().containsPlayer(uuid)) {
                e.setCancelled(true);
                return;
            }
            return;
        }

        final Entity damager = e.getDamager();

        if (damager instanceof Player) {
            final Player p = (Player) damager;
            final double dmg = e.getDamage();

            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket(dmg, entity.getLocation(), p));

        }

    }


}
