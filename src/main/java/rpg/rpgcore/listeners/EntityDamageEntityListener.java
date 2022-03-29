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
    public void onEntityDamage(EntityDamageByEntityEvent e) {

        final Entity player = e.getDamager();
        final Entity entity = e.getEntity();
        final double dmg = e.getDamage();

        if (!(entity instanceof Player)) {
            System.out.println(2);
            return;
        }
        System.out.println(3);
        final Player p = (Player) entity;
        final UUID uuid = p.getUniqueId();

        if (rpgcore.getGodManager().containsPlayer(uuid)) {
            System.out.println(4);
            e.setCancelled(true);
            return;
        }

        if (!(player instanceof Player)) {
            return;
        }

        final Player dmgPlayer = (Player) player;

        Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket(dmg, entity.getLocation(), dmgPlayer));

    }


}
