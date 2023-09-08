package rpg.rpgcore.guilds.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class GuildsPlayerDamage implements Listener {

    private final RPGCORE rpgcore;

    public GuildsPlayerDamage(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
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

            if (!tagPlayer.equals("Brak Klanu") && !tagTarget.equals("Brak Klanu")) {
                if (tagPlayer.equals(tagTarget)) {
                    if (!rpgcore.getGuildManager().getGuildPvPStatus(tagPlayer)) {
                        damagerPlayer.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&7PvP w twoim klanie jest &cwylaczone&7!"));
                        e.setCancelled(true);
                        e.setDamage(EntityDamageEvent.DamageModifier.BASE, 0);
                    }
                }
            }
        }

    }
}
