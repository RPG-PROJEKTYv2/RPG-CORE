package rpg.rpgcore.dmg;

import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.dungeons.maps.icetower.IceTowerManager;
import rpg.rpgcore.dungeons.maps.icetower.ResetType;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.MobDropHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Objects;

public class EntityDeathListener implements Listener {

    private final RPGCORE rpgcore;

    public EntityDeathListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeathPlayer(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        e.setKeepInventory(true);
        e.setKeepLevel(true);
        e.getEntity().closeInventory();

        if (e.getEntity().getWorld().getName().equals("demontower")) {
            if (Bukkit.getWorld("demontower").getPlayers().size() == 0) {
                IceTowerManager.resetIceTower(ResetType.BYPASS);
            }
        }

        if (e.getEntity().getWorld().getName().equals("Dungeon60-70")) {
            if (e.getEntity().getWorld().getPlayers().isEmpty()) {
                final String playerName = Utils.removeColor(Objects.requireNonNull(((TextHologramLine) rpgcore.getPrzedsionekManager().getDungeonHologram().getLines().get(3)).getText())).replace("Przechodzi: ", "");
                Bukkit.getServer().broadcastMessage(Utils.format("&4&lPiekielny Przedsionek &8>> &cGrupa gracza &4" + playerName + " &cpolegla podczas przechodzenia dungeonu!"));
                rpgcore.getPrzedsionekManager().resetDungeon();
            }
        }

        PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
        ((CraftPlayer) e.getEntity()).getHandle().playerConnection.a(packet);

        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> e.getEntity().teleport(rpgcore.getSpawnManager().getSpawn()), 1L);
        if (e.getEntity().getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && e.getEntity().getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.THORNS) {
            return;
        }

        final EntityDamageByEntityEvent e2 = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();

        if (e2.getDamager() == null) return;
        if (!(e2.getDamager() instanceof Player)) {
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(e.getEntity(),
                    rpgcore.getNmsManager().makeTitle("&4&lZGINALES!", 5, 20, 5),
                    rpgcore.getNmsManager().makeSubTitle("&7Zostales zabity przez &c" + e2.getDamager().getName(), 5, 20, 5)));
            return;
        } else {
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(e.getEntity(),
                    rpgcore.getNmsManager().makeTitle("&4&lZGINALES!", 5, 20, 5),
                    rpgcore.getNmsManager().makeSubTitle("&7Zostales zabity przez &c" + e2.getDamager().getName() + " &8(&c" +
                            DoubleUtils.round(((LivingEntity) e2.getDamager()).getHealth(), 0) + "♥&8)", 5, 20, 5)));
        }

        final Player victim = e.getEntity();
        final Player killer = (Player) e2.getDamager();

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(killer,
                rpgcore.getNmsManager().makeTitle("&a&lZABOJSTWO!", 5, 20, 5),
                rpgcore.getNmsManager().makeSubTitle("&7Zabijasz &c" + victim.getName(), 5, 20, 5)));
        if (RPGCORE.getInstance().getMagazynierNPC().find(killer.getUniqueId()).getMissions().getSelectedMission() == 11) {
            RPGCORE.getInstance().getMagazynierNPC().find(killer.getUniqueId()).getMissions().setProgress(RPGCORE.getInstance().getMagazynierNPC().find(killer.getUniqueId()).getMissions().getProgress() + 1);
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMagazynier(killer.getUniqueId(), RPGCORE.getInstance().getMagazynierNPC().find(killer.getUniqueId())));
        }

        final User victimUser = rpgcore.getUserManager().find(victim.getUniqueId());
        final User killerUser = rpgcore.getUserManager().find(killer.getUniqueId());
        final PustelnikUser pustelnikUser = rpgcore.getPustelnikNPC().find(killer.getUniqueId());
        if (pustelnikUser.getMissionId() == 3) {
            if (victimUser.getLvl() >= killerUser.getLvl()) {
                pustelnikUser.setProgress(pustelnikUser.getProgress() + 1);
                rpgcore.getPustelnikNPC().save(pustelnikUser);
            }
        }

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
        if (entity.getCustomName() != null && entity.getCustomName().contains("Ksiaze Mroku")) {
            if (rpgcore.getZamekNieskonczonosciManager().phase != DungeonStatus.ETAP_1_BOSS) {
                return;
            }
            rpgcore.getZamekNieskonczonosciManager().bossMap.remove(rpgcore.getZamekNieskonczonosciManager().party);
            for (Integer i : rpgcore.getZamekNieskonczonosciManager().ksiazeTasks) {
                if (rpgcore.getServer().getScheduler().isCurrentlyRunning(i) || rpgcore.getServer().getScheduler().isQueued(i)) {
                    rpgcore.getServer().getScheduler().cancelTask(i);
                }
            }
            rpgcore.getZamekNieskonczonosciManager().ksiazeTasks.clear();
            Bukkit.broadcastMessage(Utils.format("&4&lZamek Nieskonczonosci &8>> &7Grupa gracza &c" + rpgcore.getZamekNieskonczonosciManager().partyLeader.getName() + " &7pokonala &c&lKsiecia Mroku&7!"));
            for (Player p : rpgcore.getZamekNieskonczonosciManager().players) {
                if (ChanceHelper.getChance(0.001 + rpgcore.getBonusesManager().find(p.getUniqueId()).getBonusesUser().getSzczescie() / 100f)) {
                    p.sendMessage("artefakt jakis do dodania");
                    Bukkit.broadcastMessage(Utils.format("&4&lZamek Nieskonczonosci &8>> &c&lKsiaze Mroku &7postanowil obdarowac gracza &c" + p.getName() + " &5niesamowitym &6skarbem&7!"));
                }
            }
            rpgcore.getZamekNieskonczonosciManager().startPhase2();
        }

        if (player != null) {
            MobDropHelper.dropFromMob(player, entity);
        }
    }
}