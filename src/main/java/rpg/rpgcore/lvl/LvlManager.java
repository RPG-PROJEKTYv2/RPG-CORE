package rpg.rpgcore.lvl;

import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.lvl.enums.Levels;
import rpg.rpgcore.lvl.enums.mobs.Dungeons;
import rpg.rpgcore.lvl.enums.mobs.Events;
import rpg.rpgcore.lvl.enums.mobs.Maps;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class LvlManager {

    private final RPGCORE rpgcore;

    public LvlManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }



    public double getExpForLvl(final int lvl) {
        return Levels.getByLevel(lvl).getExp();
    }

    public double getExp(final String mob) {
        if (Maps.isMob(mob)) {
            return Maps.getByName(mob).getExp();
        } else if (Dungeons.isDungeonMob(mob)) {
            return Dungeons.getByName(mob).getExp();
        } else if (Events.isEventMob(mob)) {
            return Events.getByName(mob).getExp();
        } else {
            return 0;
        }
    }

    public double getKasa(final String mob, final UUID uuid) {
        double mnozik = 1;
        final RankTypePlayer rank = rpgcore.getUserManager().find(uuid).getRankPlayerUser().getRankType();
        if (rank == RankTypePlayer.VIP) mnozik = 1.25;
        if (rank == RankTypePlayer.TWORCA) mnozik = 1.35;
        if (rank == RankTypePlayer.ELITA) mnozik = 1.5;
        if (Maps.isMob(mob)) {
            return DoubleUtils.round(Maps.getByName(mob).getKasa() * mnozik, 2);
        } else if (Dungeons.isDungeonMob(mob)) {
            return DoubleUtils.round(Dungeons.getByName(mob).getKasa() * mnozik, 2);
        } else if (Events.isEventMob(mob)) {
            return DoubleUtils.round(Events.getByName(mob).getKasa() * mnozik, 2);
        } else {
            return 0;
        }
    }

    public boolean checkPlayer(final String mob, final int lvl) {
        if (Maps.isMob(mob)) {
            return Maps.getByName(mob).getMinLvl() <= lvl && lvl < Maps.getByName(mob).getReqLvl();
        } else if (Dungeons.isDungeonMob(mob)) {
            return Dungeons.getByName(mob).getMinLvl() <= lvl && lvl < Dungeons.getByName(mob).getReqLvl();
        } else if (Events.isEventMob(mob)) {
            return Events.getByName(mob).getMinLvl() <= lvl && lvl < Events.getByName(mob).getReqLvl();
        } else {
            return false;
        }
    }

    private double getDodatkowyExp(final UUID uuid) {
        double dodatkowyExp = 0;
        if (rpgcore.getServerManager().isServerUser("dodatkowyExp") && rpgcore.getServerManager().find("dodatkowyExp").getServer().isAktywny()) {
            dodatkowyExp += rpgcore.getServerManager().find("dodatkowyExp").getServer().getDodatkowyExp();
        }
        final User user = rpgcore.getUserManager().find(uuid);
        if (user.getRankPlayerUser().getRankType() == RankTypePlayer.VIP) dodatkowyExp += 25;
        if (user.getRankPlayerUser().getRankType() == RankTypePlayer.TWORCA) dodatkowyExp += 35;
        if (user.getRankPlayerUser().getRankType() == RankTypePlayer.ELITA) dodatkowyExp += 50;
        dodatkowyExp += rpgcore.getBonusesManager().find(uuid).getBonusesUser().getDodatkowyExp();

        return dodatkowyExp;
    }

    public void updateExp(final Player killer, final String mob) {
        final UUID killerUUID = killer.getUniqueId();
        final User user = rpgcore.getUserManager().find(killerUUID);
        final double dodatkowyExp = this.getDodatkowyExp(killerUUID);
        final double kasa = this.getKasa(mob, killerUUID);
        double expDoDodania = this.getExp(mob);
        expDoDodania += DoubleUtils.round(expDoDodania * dodatkowyExp / 100, 2);

        if (rpgcore.getPetyManager().findActivePet(killerUUID).getPet().getItem() != null) {
            rpgcore.getPetyManager().increasePetExp(killer, rpgcore.getPetyManager().findActivePet(killer.getUniqueId()).getPet().getItem(), DoubleUtils.round(expDoDodania / 100, 2));
        }

        if (user.getLvl() == 130) {
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendActionBar(killer, "&8[&6EXP&8]&7(&6+ " + dodatkowyExp + "%&7) &f+0 exp &8(&4MAX LVL&8) &8| &2+ " + Utils.spaceNumber(String.format("%.2f", kasa)) + "$ &8[&6EXP&8]"));
            return;
        }


        final int lvlGracza = user.getLvl();
        final int nextLvlGracza = lvlGracza + 1;

        double aktualnyExpGracza = user.getExp();
        final double expNaNextLvlGracza = this.getExpForLvl(nextLvlGracza);

        final boolean check = checkPlayer(mob, user.getLvl());
        if (check) aktualnyExpGracza += expDoDodania;

        final double expDoDodaniaL = expDoDodania;
        final double aktualnyExpGraczaL = aktualnyExpGracza;

        if (!check) {
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendActionBar(killer, "&8[&6EXP&8] &7(&6+ " + dodatkowyExp + "%&7) &f+0.0 exp &8(&e" + DoubleUtils.round((aktualnyExpGraczaL / expNaNextLvlGracza) * 100, 2) + "%&8) &8| &2+ " + Utils.spaceNumber(String.format("%.2f", kasa)) + "$ &8&8[&6EXP&8]"));
            return;
        }

        user.setExp(aktualnyExpGracza);
        if (aktualnyExpGracza >= expNaNextLvlGracza) {
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> this.updateLVL(killer));
            return;
        }
        killer.setLevel(lvlGracza);
        killer.setExp((float) (aktualnyExpGraczaL / expNaNextLvlGracza));
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendActionBar(killer, "&8[&6EXP&8] &7(&6+ " + dodatkowyExp + "%&7) &f+" + String.format("%.2f", expDoDodaniaL) + " exp &8(&e" + String.format("%.2f", (aktualnyExpGraczaL / expNaNextLvlGracza) * 100) + "%&8) &8| &2+ " + Utils.spaceNumber(String.format("%.2f", kasa)) + "$ &8&8[&6EXP&8]"));
    }

    public void updateLVL(final Player killer) {
        final UUID killerUUID = killer.getUniqueId();
        final User user = rpgcore.getUserManager().find(killerUUID);
        int aktualnyLvlGracza = user.getLvl();
        int nextLvlGracza = aktualnyLvlGracza + 1;
        final double kasaZaLvl = Levels.getByLevel(nextLvlGracza).getKasa();

        if (aktualnyLvlGracza <= 50) {
            if (nextLvlGracza == 2) {
                rpgcore.getWyszkolenieManager().find(killerUUID).setPunkty(2);
                rpgcore.getWyszkolenieManager().find(killerUUID).setTotalPoints(2);
            } else {
                rpgcore.getWyszkolenieManager().find(killerUUID).addPoint();
            }
        }
        user.setLvl(nextLvlGracza);
        user.setExp(0);
        user.setKasa(user.getKasa() + kasaZaLvl);
        killer.sendMessage(Utils.format("&2+ &a" + Utils.spaceNumber(kasaZaLvl) + "&2$"));
        killer.setExp(0);
        killer.setLevel(nextLvlGracza);
        if (nextLvlGracza >= 10 && nextLvlGracza % 5 == 0) {
            rpgcore.getServer().broadcastMessage(" ");
            rpgcore.getServer().broadcastMessage(Utils.format(Utils.LVLPREFIX + "&fGracz &3" + killer.getName() + " &fosiagnal &3" + nextLvlGracza + " &fpoziom. Gratulacje!"));
            rpgcore.getServer().broadcastMessage(" ");
        }
        PacketPlayOutTitle title = rpgcore.getNmsManager().makeTitle("&b&lLVL UP!", 5, 25, 5);
        PacketPlayOutTitle subtitle = rpgcore.getNmsManager().makeSubTitle("&fAwansowales na &3" + nextLvlGracza + " &fpoziom", 5, 25, 5);
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
            rpgcore.getNmsManager().sendTitleAndSubTitle(killer, title, subtitle);
            rpgcore.getMongoManager().saveDataUser(killerUUID, user);
            rpgcore.getMongoManager().saveDataWyszkolenie(killerUUID, rpgcore.getWyszkolenieManager().find(killerUUID));
        });
        for (Player p : rpgcore.getServer().getOnlinePlayers()) {
            this.updateLvlBelowName(p, killer.getName(), nextLvlGracza);
        }
        final PustelnikUser pustelnikUser = rpgcore.getPustelnikNPC().find(killerUUID);
        if (pustelnikUser.getMissionId() == 4) {
            pustelnikUser.setProgress(pustelnikUser.getProgress() + 1);
            rpgcore.getPustelnikNPC().save(pustelnikUser);
        }
        rpgcore.getArtefaktyZaLvlManager().checkArteZaLvl(killer, nextLvlGracza);
    }

    public void getPlayerLvl(final Player sender, final UUID uuid) {
        final User user = rpgcore.getUserManager().find(uuid);
        final String playerName = user.getName();
        final int aktualnyLvlGracza = user.getLvl();
        final double aktualnyExpGracza = user.getExp();
        sender.sendMessage(Utils.format("&8------------ &f( &b&lStatystyki &f) &8------------ "));
        sender.sendMessage(Utils.format("&7Nick: &c" + playerName));
        sender.sendMessage(Utils.format("&7Poziom doswiadczenia: &c" + aktualnyLvlGracza));
        if (aktualnyLvlGracza < Utils.MAXLVL) {
            final double expNaNextLvl = rpgcore.getLvlManager().getExpForLvl(aktualnyLvlGracza + 1);
            sender.sendMessage(Utils.format("&7Doswiadczenie: &c" + Utils.spaceNumber(DoubleUtils.round(aktualnyExpGracza, 2)) + " &7exp / &c" + Utils.spaceNumber(DoubleUtils.round(expNaNextLvl, 2)) + " &7exp (&c" + DoubleUtils.round((aktualnyExpGracza / expNaNextLvl) * 100, 2) + "%&7)"));
            sender.sendMessage(Utils.format("&7Exp potrzebny do nastepnego poziomu: &c" + Utils.spaceNumber(DoubleUtils.round(expNaNextLvl - aktualnyExpGracza, 2)) + " &7exp"));
        } else {
            sender.sendMessage(Utils.format("&7Doswiadczenie: &4&lMAX &7exp / &4&lMAX &7exp (&4&lMAX&7)"));
            sender.sendMessage(Utils.format("&7Exp potrzebny do nastepnego poziomu: &4&lMAX"));
        }
        if (user.getRankPlayerUser().getRankType() == RankTypePlayer.GRACZ) {
            sender.sendMessage(Utils.format("&7Ranga: &7Gracz"));
        } else {
            sender.sendMessage(Utils.format("&7Ranga: " + user.getRankPlayerUser().getRankType().getPrefix()));
        }
        sender.sendMessage(Utils.format("&8------------  &f( &b&lStatystyki &f) &8------------ "));
    }

    public void updateLvlBelowName(final Player p, final String name1, final int lvl) {
        final Scoreboard sb = p.getScoreboard();
        Objective ob = sb.getObjective(Utils.format("&7Lvl"));
        if (ob == null) {
            ob = sb.registerNewObjective(Utils.format("&7Lvl"), "dummy");
            ob.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }
        final Score score = ob.getScore(name1);
        score.setScore(lvl);
    }
}
