package rpg.rpgcore.guilds.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class GuildEntityDeath implements Listener {

    private final RPGCORE rpgcore;

    public GuildEntityDeath(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void guildEntityDeath(final EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) {
            return;
        }

        if (!(e.getEntity() instanceof Player)) {
            final Player killer = e.getEntity().getKiller();

            if (rpgcore.getGuildManager().getGuildTag(killer.getUniqueId()).equals("Brak Klanu")) {
                return;
            }

            final String killerGuild = rpgcore.getGuildManager().getGuildTag(killer.getUniqueId());

            if (rpgcore.getGuildManager().getGuildLvl(killerGuild) == 50) {
                return;
            }

            rpgcore.getGuildManager().updateGuildExp(killerGuild, 1);
            rpgcore.getGuildManager().updateGuildExpEarned(killerGuild, killer.getUniqueId(), 1);
        }

    }
}
