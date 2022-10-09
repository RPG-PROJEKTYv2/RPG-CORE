package rpg.rpgcore.listeners;

import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.MobDropHelper;
import rpg.rpgcore.utils.Utils;

public class EntityDeathListener implements Listener {

    private final RPGCORE rpgcore;

    public EntityDeathListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeathPlayer(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        e.setKeepInventory(true);

        PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
        ((CraftPlayer) e.getEntity()).getHandle().playerConnection.a(packet);

        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> e.getEntity().teleport(rpgcore.getSpawnManager().getSpawn()), 1L);

        final EntityDamageByEntityEvent e2 = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(e.getEntity(),
                rpgcore.getNmsManager().makeTitle("&4&lZGINALES!", 5, 20, 5),
                rpgcore.getNmsManager().makeSubTitle("&7Zostales zabity przez &c" + e2.getDamager().getName() + " &8(&c" +
                        String.format("%.2f", ((LivingEntity) e2.getDamager()).getHealth()) + "♥&8)", 5, 20, 5)));
        if (e2.getDamager() == null && !(e2.getDamager() instanceof Player)) {
            return;
        }

        final Player victim = e.getEntity();
        final Player killer = (Player) e2.getDamager();

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(killer,
                rpgcore.getNmsManager().makeTitle("&a&lZABOJSTWO!", 5, 20, 5),
                rpgcore.getNmsManager().makeSubTitle("&7Zabijasz &c" + victim.getName(), 5, 20, 5)));

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
            } else {
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

        final int killerLvl = rpgcore.getUserManager().find(killer.getUniqueId()).getLvl();
        final int victimLvl = rpgcore.getUserManager().find(victim.getUniqueId()).getLvl();

        if ((killerLvl - victimLvl >= 0 && killerLvl - victimLvl < 10) || (victimLvl - killerLvl >= 0 && victimLvl - killerLvl < 10)) {
            rpgcore.getGuildManager().updateGuildPoints(killerGuild, 30);
            rpgcore.getGuildManager().updateGuildPoints(victimGuild, -30);
            rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&8[&3" + killerGuild + "&8] &a" + killer.getName() + " [+30] &7zabija &8[&3" + victimGuild + "&8] &c" + victim.getName() + " [-30]"));
        } else if ((killerLvl - victimLvl > 10 && killerLvl - victimLvl < 20) || (victimLvl - killerLvl > 10 && victimLvl - killerLvl < 20)) {
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

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeathEntity(final EntityDeathEvent e) {
        Entity entity = e.getEntity();
        Player player = e.getEntity().getKiller();
        e.setDroppedExp(0);
        e.getDrops().clear();
        if (entity.getName() == null) {
            return;
        }
        if (player != null) {
            MobDropHelper.dropFromMob(player, entity);
        }
    }
}