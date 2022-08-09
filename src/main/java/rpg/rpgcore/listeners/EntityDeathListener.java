package rpg.rpgcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class EntityDeathListener implements Listener {

    private final RPGCORE rpgcore;

    public EntityDeathListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent e) {
        e.setDroppedExp(0);
        e.getDrops().clear();

        if (e.getEntity() instanceof Player) {
            final Player victim = (Player) e.getEntity();
            final Player killer = victim.getKiller();

            if (rpgcore.getGuildManager().getGuildTag(killer.getUniqueId()).equals("Brak Klanu") || rpgcore.getGuildManager().getGuildTag(victim.getUniqueId()).equals("Brak Klanu")) {

                String killerGuild;
                String victimGuild;

                if (rpgcore.getGuildManager().getGuildTag(victim.getUniqueId()).equals("Brak Klanu")) {
                    victimGuild = "";
                } else {
                    victimGuild = "&8[&3" + rpgcore.getGuildManager().getGuildTag(victim.getUniqueId()) + "&8] &c";
                    rpgcore.getGuildManager().updateGuildDeaths(rpgcore.getGuildManager().getGuildTag(victim.getUniqueId()), victim.getUniqueId(), 1);
                }

                if (rpgcore.getGuildManager().getGuildTag(killer.getUniqueId()).equals("Brak Klanu")) {
                    killerGuild = "";
                } else  {
                    killerGuild = "&8[&3" + rpgcore.getGuildManager().getGuildTag(killer.getUniqueId()) + "&8] &a";
                    rpgcore.getGuildManager().updateGuildKills(rpgcore.getGuildManager().getGuildTag(killer.getUniqueId()), killer.getUniqueId(), 1);
                    rpgcore.getGuildManager().updateGuildExpEarned(rpgcore.getGuildManager().getGuildTag(killer.getUniqueId()), killer.getUniqueId(), 10);
                    rpgcore.getGuildManager().updateGuildExp(rpgcore.getGuildManager().getGuildTag(killer.getUniqueId()), 10);
                }
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + killerGuild + killer.getName() + " &7zabija " + victimGuild + victim.getName()));
                return;
            }

            final String killerGuild = rpgcore.getGuildManager().getGuildTag(killer.getUniqueId());
            final String victimGuild = rpgcore.getGuildManager().getGuildTag(victim.getUniqueId());

            if (killerGuild.equals(victimGuild)) {
                rpgcore.getGuildManager().updateGuildPoints(killerGuild, -50);
                rpgcore.getGuildManager().updateGuildKills(killerGuild, killer.getUniqueId(), 1);
                rpgcore.getGuildManager().updateGuildDeaths(victimGuild, victim.getUniqueId(), 1);
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&8[&3" + killerGuild + "&8] &a" + killer.getName() + " [-25] &7zabija &8[&3" + victimGuild + "&8] &c" + victim.getName() + " [-25]"));
                return;
            }

            final int killerLvl = rpgcore.getPlayerManager().getPlayerLvl(killer.getUniqueId());
            final int victimLvl = rpgcore.getPlayerManager().getPlayerLvl(victim.getUniqueId());

            if (killerLvl - victimLvl < 10 || victimLvl - killerLvl < 10) {
                rpgcore.getGuildManager().updateGuildPoints(killerGuild, 30);
                rpgcore.getGuildManager().updateGuildPoints(victimGuild, -30);
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&8[&3" + killerGuild + "&8] &a" + killer.getName() + " [+30] &7zabija &8[&3" + victimGuild + "&8] &c" + victim.getName() + " [-30]"));
            } else if (killerLvl - victimLvl < 20 || victimLvl - killerLvl < 20) {
                rpgcore.getGuildManager().updateGuildPoints(killerGuild, 15);
                rpgcore.getGuildManager().updateGuildPoints(victimGuild, -15);
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&8[&3" + killerGuild + "&8] &a" + killer.getName() + " [+15] &7zabija &8[&3" + victimGuild + "&8] &c" + victim.getName() + " [-15]"));
            } else {
                rpgcore.getGuildManager().updateGuildPoints(killerGuild, 5);
                rpgcore.getGuildManager().updateGuildPoints(victimGuild, -5);
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&8[&3" + killerGuild + "&8] &a" + killer.getName() + " [+5] &7zabija &8[&3" + victimGuild + "&8] &c" + victim.getName() + " [-5]"));
            }
            rpgcore.getGuildManager().updateGuildExp(killerGuild, 10);
            rpgcore.getGuildManager().updateGuildExpEarned(killerGuild, killer.getUniqueId(), 10);
            rpgcore.getGuildManager().updateGuildKills(killerGuild, killer.getUniqueId(), 1);
            rpgcore.getGuildManager().updateGuildDeaths(victimGuild, victim.getUniqueId(), 1);
        } else  {

            final Player killer = e.getEntity().getKiller();
            final UUID uuid = killer.getUniqueId();
            final String mobName = Utils.removeColor(e.getEntity().getCustomName());

            rpgcore.getLvlManager().updateExp(killer, mobName);

        }


    }
}