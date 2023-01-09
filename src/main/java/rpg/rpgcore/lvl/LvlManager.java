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
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.user.User;
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

    public double getKasa(final String mob) {
        if (Maps.isMob(mob)) {
            return Maps.getByName(mob).getKasa();
        } else if (Dungeons.isDungeonMob(mob)) {
            return Dungeons.getByName(mob).getKasa();
        } else if (Events.isEventMob(mob)) {
            return Events.getByName(mob).getKasa();
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
        if (user.getRankPlayerUser().getRankType() == RankTypePlayer.VIP) {
            dodatkowyExp += 5;
        }
        if (user.getRankPlayerUser().getRankType() == RankTypePlayer.SVIP) {
            dodatkowyExp += 10;
        }
        if (user.getRankPlayerUser().getRankType() == RankTypePlayer.ELITA) {
            dodatkowyExp += 15;
        }
        dodatkowyExp += rpgcore.getBonusesManager().find(uuid).getBonusesUser().getDodatkowyExp();

        return dodatkowyExp;
    }

    public void updateExp(final Player killer, final String mob) {
        final UUID killerUUID = killer.getUniqueId();
        final User user = rpgcore.getUserManager().find(killerUUID);
        final double dodatkowyExp = this.getDodatkowyExp(killerUUID);
        final double kasa = this.getKasa(mob);
        double expDoDodania = this.getExp(mob);
        expDoDodania += expDoDodania * dodatkowyExp / 100;

        if (rpgcore.getPetyManager().findActivePet(killerUUID).getPet().getItem() != null) {
            rpgcore.getPetyManager().increasePetExp(killer, rpgcore.getPetyManager().findActivePet(killer.getUniqueId()).getPet().getItem(), expDoDodania / 100);
        }

        if (user.getLvl() == 130) {
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendActionBar(killer, "&8[&6EXP&8]&7(&6+ " + dodatkowyExp + "%&7) &f+0 exp &8(&4MAX LVL&8) &8| &2+ " + Utils.spaceNumber(String.format("%.2f", kasa)) + "$ &8[&6EXP&8]"));
            return;
        }


        final int lvlGracza = user.getLvl();
        final int nextLvlGracza = lvlGracza + 1;

        double aktualnyExpGracza = user.getExp();
        final double expNaNextLvlGracza = this.getExpForLvl(nextLvlGracza);


        aktualnyExpGracza += expDoDodania;

        final double expDoDodaniaL = expDoDodania;
        final double aktualnyExpGraczaL = aktualnyExpGracza;

        if (!checkPlayer(mob, user.getLvl())) {
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendActionBar(killer, "&8[&6EXP&8] &7(&6+ " + dodatkowyExp + "%&7) &f+0.0 exp &8(&e" + String.format("%.2f", (aktualnyExpGraczaL / expNaNextLvlGracza) * 100) + "%&8) &8| &2+ " + Utils.spaceNumber(String.format("%.2f", kasa)) + "$ &8&8[&6EXP&8]"));
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

        if (nextLvlGracza == 2) {
            rpgcore.getTrenerNPC().find(killerUUID).getTrenerUser().setPoints(2);
        } else {
            rpgcore.getTrenerNPC().find(killerUUID).getTrenerUser().setPoints(rpgcore.getTrenerNPC().find(killerUUID).getTrenerUser().getPoints() + 1);
        }
        user.setLvl(nextLvlGracza);
        user.setExp(0);
        killer.setExp(0);
        killer.setLevel(nextLvlGracza);
        if (nextLvlGracza >= 10 && nextLvlGracza % 5 == 0) {
            rpgcore.getServer().broadcastMessage(Utils.format(Utils.LVLPREFIX + "&fGracz &3" + killer.getName() + " &fosiagnal &3" + nextLvlGracza + " &fpoziom. Gratulacje!"));
        }
        PacketPlayOutTitle title = rpgcore.getNmsManager().makeTitle("&b&lLVL UP!", 5, 25, 5);
        PacketPlayOutTitle subtitle = rpgcore.getNmsManager().makeSubTitle("&fAwansowales na &3" + nextLvlGracza + " &fpoziom", 5, 25, 5);
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
            rpgcore.getNmsManager().sendTitleAndSubTitle(killer, title, subtitle);
            rpgcore.getMongoManager().saveDataUser(killerUUID, user);
        });
        for (Player p : rpgcore.getServer().getOnlinePlayers()) {
            this.updateLvlBelowName(p, killer.getName(), nextLvlGracza);
        }
        rpgcore.getArtefaktyZaLvlManager().checkArteZaLvl(killer, nextLvlGracza);
    }

    public void getPlayerLvl(final Player sender, final UUID uuid) {
        final User user = rpgcore.getUserManager().find(uuid);
        final String playerName = user.getName();
        final int aktualnyLvlGracza = user.getLvl();
        final double aktualnyExpGracza = user.getExp();
        sender.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-{&c&lLVL&8}-_-_-_-_-_-_-_-_-"));
        sender.sendMessage(Utils.format("&7Informacje odnoscie gracza &c" + playerName));
        sender.sendMessage(Utils.format("&7Poziom: &c" + aktualnyLvlGracza));
        if (aktualnyLvlGracza < Utils.MAXLVL) {
            final double expNaNextLvl = rpgcore.getLvlManager().getExpForLvl(aktualnyLvlGracza + 1);
            sender.sendMessage(Utils.format("&7Doswiadczenie: &c" + Utils.spaceNumber(String.format("%.2f", aktualnyExpGracza)) + " &7exp / &c" + Utils.spaceNumber(String.format("%.2f", expNaNextLvl)) + " &7exp (&c" + Utils.procentFormat.format((aktualnyExpGracza / expNaNextLvl) * 100) + "%&7)"));
            sender.sendMessage(Utils.format("&7Exp potrzebny do nastepnego poziomu: &c" + Utils.spaceNumber(String.format("%.2f", expNaNextLvl - aktualnyExpGracza)) + " &7exp"));
        } else {
            sender.sendMessage(Utils.format("&7Doswiadczenie: &c" + aktualnyExpGracza + " &7exp / &4&lMAX LVL (&4&lMAX LVL&7)"));
            sender.sendMessage(Utils.format("&7Exp potrzebny do nastepnego poziomu: &4&lMAX LVL"));
        }
        sender.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-{&c&lLVL&8}-_-_-_-_-_-_-_-_-"));
    }

    public void setPlayerLvl(final String adminName, final UUID uuid, int nowyLvl) {
        final User user = rpgcore.getUserManager().find(uuid);
        if (nowyLvl > Utils.MAXLVL) {
            nowyLvl = Utils.MAXLVL;
        }
        user.setLvl(nowyLvl);
        user.setExp(0);
        final Player playerToKick = Bukkit.getPlayer(uuid);
        final String playerToKickName = user.getName();
        if (playerToKick != null) {
            playerToKick.kickPlayer(Utils.kickMessage(adminName, "Zmiana lvla"));
        }
        rpgcore.getServer().broadcastMessage(Utils.normalKickBroadcast(playerToKickName, adminName, "Zmiana lvla"));
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
    }

    public void setPlayerExp(final String adminName, final UUID uuid, double nowyExp) {
        final User user = rpgcore.getUserManager().find(uuid);
        if (nowyExp > rpgcore.getLvlManager().getExpForLvl(user.getLvl() + 1)) {
            nowyExp = rpgcore.getLvlManager().getExpForLvl(user.getLvl() + 1);
        }
        user.setExp(nowyExp);
        final Player playerToKick = Bukkit.getPlayer(uuid);
        final String playerToKickName = user.getName();
        if (playerToKick != null) {
            playerToKick.kickPlayer(Utils.kickMessage(adminName, "Zmiana ilosci expa"));
        }
        rpgcore.getServer().broadcastMessage(Utils.normalKickBroadcast(playerToKickName, adminName, "Zmiana ilosci expa"));
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
    }

    public void setPlayerProcent(final String adminName, final UUID uuid, final double procent) {
        final User user = rpgcore.getUserManager().find(uuid);
        final int aktualnyLvlGracza = user.getLvl();
        final double expNaNextLvl = rpgcore.getLvlManager().getExpForLvl(aktualnyLvlGracza + 1);
        final double nowyExpGracza = (procent * expNaNextLvl) / 100;

        user.setExp(nowyExpGracza);
        final Player playerToKick = Bukkit.getPlayer(uuid);
        final String playerToKickName = user.getName();
        if (playerToKick != null) {
            playerToKick.kickPlayer(Utils.kickMessage(adminName, "Zmiana postepu do nastepego lvla"));
        }
        rpgcore.getServer().broadcastMessage(Utils.normalKickBroadcast(playerToKickName, adminName, "Zmiana postepu do nastepnego lvla"));
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
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
