package rpg.rpgcore.managers;

import io.lumine.xikage.mythicmobs.MythicMobs;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class LvlManager {

    private final RPGCORE rpgcore;

    public LvlManager(RPGCORE rpgcore) {this.rpgcore = rpgcore;}


    public HashMap<Integer, Double> getWymaganyExpDlaLvli() {
        return wymaganyExpDlaLvli;
    }

    private final HashMap<Integer, Double> wymaganyExpDlaLvli = new HashMap<>();
    private final HashMap<String, Double> expZaMoby = new HashMap<>();



    public HashMap<String, Double> getExpZaMoby() {return expZaMoby;}

    public void loadAllReqExp() {
        try {
            int sciezkoLvl = rpgcore.getConfig().getConfigurationSection("wymaganyexp_na_lvl").getKeys(false).size();
            for (int i = 1; i <= sciezkoLvl; i++) {
                this.wymaganyExpDlaLvli.put(i, rpgcore.getConfig().getConfigurationSection("wymaganyexp_na_lvl").getDouble("wymaganyexp_lvl_" + i));
            }
            System.out.println("[rpg.core]" + ChatColor.GREEN + "Pomyslnie wczytano wymagane doswiadczenia dla kazdego poziomu");
        }catch (final Exception e){
            e.printStackTrace();
            System.out.println(Utils.format("&8[&crpg.core&8] &cCos poszlo nie tak podczas wczytywania wymaganego doswiadczenia dla kazdego poziomu"));
        }
    }

    public void loadExpForAllMobs() {
        try {
            List<String> allMobsToArray = new ArrayList<>();
            for (String s : MythicMobs.inst().getMobManager().getMobNames()) {
                allMobsToArray.add(s);
            }
            for (String s : allMobsToArray) {
                String mob = Utils.removeColor(MythicMobs.inst().getMobManager().getMythicMob(s).getDisplayName().get());
                double exp = 0;
                if (rpgcore.getConfig().getConfigurationSection("exp_za_moby").contains(mob)) {
                    exp = rpgcore.getConfig().getConfigurationSection("exp_za_moby").getDouble(mob);
                }
                expZaMoby.put(mob, exp);
            }
            allMobsToArray.clear();
            System.out.println("[rpg.core] Pomyslnie zaladowano exp za moby");
        } catch (final Exception e){
            e.printStackTrace();
            System.out.println(Utils.format("&8[&crpg.core&8] &cCos poszlo nie tak podczas wczytywania expa za moby"));
        }
    }

    public void unLoadAll() {
        this.wymaganyExpDlaLvli.clear();
        this.expZaMoby.clear();
        System.out.println("[rpg.core] Pomyslnie wyczyszczono wymagany exp i exp za moby!");
    }

    public double getExpForLvl(final int lvl) {
        double exp = 0;

        if (this.wymaganyExpDlaLvli.containsKey(lvl)){
            exp = this.wymaganyExpDlaLvli.get(lvl);
        }

        return exp;
    }

    public double getExp(final String mob){
        double exp = 0;

        if (this.expZaMoby.containsKey(mob)){
            exp = this.expZaMoby.get(mob);
        }

        return exp;
    }

    public void updateExp(final Player killer, final String mob) {
        if (!(this.getExpZaMoby().containsKey(mob))){
            killer.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono expa dla tego moba"));
            return;
        }

        final UUID killerUUID = killer.getUniqueId();

        if (rpgcore.getPlayerManager().getPlayerLvl(killerUUID) == 130){
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendActionBar(killer, rpgcore.getNmsManager().makeActionBar("&b[EXP] &f+0 exp &b(&4&lMAX LVL%&b) [EXP]")));
            return;
        }

        final int lvlGracza = rpgcore.getPlayerManager().getPlayerLvl(killerUUID);
        final int nextLvlGracza = lvlGracza + 1;

        double aktualnyExpGracza = rpgcore.getPlayerManager().getPlayerExp(killerUUID);
        final double expNaNextLvlGracza = this.getExpForLvl(nextLvlGracza);
        final double expDoDodania = this.getExp(mob);

        killer.sendMessage("Aktualny lvl gracza - " + lvlGracza);
        killer.sendMessage("Next lvl gracza - " + nextLvlGracza);
        killer.sendMessage("Aktualny exp gracza - " + aktualnyExpGracza);
        killer.sendMessage("Exp na next lvl - " + expNaNextLvlGracza);
        killer.sendMessage("Exp za moba - " + expDoDodania);

        if (aktualnyExpGracza >= expNaNextLvlGracza){
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> this.updateLVL(killer, killerUUID));
            return;
        }
        aktualnyExpGracza = aktualnyExpGracza + expDoDodania;
        killer.sendMessage("Exp po dodaniu wszystkiego - " + aktualnyExpGracza);
        killer.setExp((float) (aktualnyExpGracza / expNaNextLvlGracza));
        rpgcore.getPlayerManager().updatePlayerExp(killerUUID, aktualnyExpGracza);
        killer.sendMessage("Ustawiono exp gracza - " + rpgcore.getPlayerManager().getPlayerExp(killerUUID));
        final double expForLambda = aktualnyExpGracza;
        Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendActionBar(killer, rpgcore.getNmsManager().makeActionBar("&b[EXP] &f+" + expDoDodania + " exp &b(&f" + Utils.procentFormat.format((expForLambda / expNaNextLvlGracza)*100) + "%&b) [EXP]")));
    }

    public void updateLVL(final Player killer, final UUID killerUUID) {
        int aktualnyLvlGracza = rpgcore.getPlayerManager().getPlayerLvl(killerUUID);
        int nextLvlGracza = aktualnyLvlGracza + 1;

        rpgcore.getPlayerManager().updatePlayerLvl(killerUUID, nextLvlGracza);
        rpgcore.getPlayerManager().updatePlayerExp(killerUUID, 0);
        killer.setExp(0);
        killer.setLevel(nextLvlGracza);
        if (nextLvlGracza >= 10 && nextLvlGracza % 5 == 0) {
            Bukkit.broadcastMessage(Utils.format(Utils.LVLPREFIX + "&fGracz &3" + killer.getName() + " &fosiagnal &3" + nextLvlGracza + " &fpoziom. Gratulacje!"));
        }
        PacketPlayOutTitle title = rpgcore.getNmsManager().makeTitle("&b&lLVL UP!", 5, 25, 5);
        PacketPlayOutTitle subtitle = rpgcore.getNmsManager().makeSubTitle("&fAwansowales na &3" + nextLvlGracza + " &fpoziom", 5, 25, 5);
        Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(killer, title, subtitle));
        for (Player p : Bukkit.getOnlinePlayers()){
            this.updateLvlBelowName(p, killer.getName(), nextLvlGracza);
        }
    }

    public void getPlayerLvl(final Player sender, final UUID uuid) {
        final String playerName = rpgcore.getPlayerManager().getPlayerName(uuid);
        final int aktualnyLvlGracza = rpgcore.getPlayerManager().getPlayerLvl(uuid);
        final double aktualnyExpGracza = rpgcore.getPlayerManager().getPlayerExp(uuid);
        sender.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-{&b&lLVL&8}-_-_-_-_-_-_-_-_-"));
        sender.sendMessage(Utils.format("&bInformacje odnoscie gracza &f" + playerName));
        sender.sendMessage(Utils.format("&bPoziom: &f" + aktualnyLvlGracza));
        if (aktualnyLvlGracza < Utils.MAXLVL) {
            final double expNaNextLvl = rpgcore.getLvlManager().getExpForLvl(aktualnyLvlGracza + 1);
            sender.sendMessage(Utils.format("&bDoswiadczenie: &f" + aktualnyExpGracza + " &bexp / &f" + expNaNextLvl + " &bexp (&f" + Utils.procentFormat.format((aktualnyExpGracza / expNaNextLvl) * 100) + "%&b)"));
            sender.sendMessage(Utils.format("&bExp potrzebny do nastepnego poziomu: &f" + (expNaNextLvl - aktualnyExpGracza) + "&bexp"));
        } else {
            sender.sendMessage(Utils.format("&bDoswiadczenie: &f" + aktualnyExpGracza + " &bexp / &4&lMAX LVL (&4&lMAX LVL&b)"));
            sender.sendMessage(Utils.format("&bExp potrzebny do nastepnego poziomu: &4&lMAX LVL"));
        }
        sender.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-{&b&lLVL&8}-_-_-_-_-_-_-_-_-"));
    }

    public void setPlayerLvl(final String adminName, final UUID uuid, int nowyLvl) {
        if (nowyLvl > Utils.MAXLVL){
            nowyLvl = Utils.MAXLVL;
        }
        rpgcore.getPlayerManager().getPlayerLvl().replace(uuid, nowyLvl);
        rpgcore.getPlayerManager().getPlayerExp().replace(uuid, 0.0);
        final Player playerToKick = Bukkit.getPlayer(uuid);
        if (playerToKick != null){
            playerToKick.kickPlayer(Utils.kickMessage(adminName, "Zmiana lvla"));
        }
        Bukkit.broadcastMessage(Utils.normalKickBroadcast(playerToKick.getName(), adminName, "Zmiana lvla"));
    }

    public void setPlayerExp(final String adminName, final UUID uuid, double nowyExp) {
        if(nowyExp > rpgcore.getLvlManager().getExpForLvl(rpgcore.getPlayerManager().getPlayerLvl(uuid) +1)) {
            nowyExp = rpgcore.getLvlManager().getExpForLvl(rpgcore.getPlayerManager().getPlayerLvl(uuid) +1);
        }
        rpgcore.getPlayerManager().getPlayerExp().replace(uuid, nowyExp);
        final Player playerToKick = Bukkit.getPlayer(uuid);
        if (playerToKick != null){
            playerToKick.kickPlayer(Utils.kickMessage(adminName, "Zmiana ilosci expa"));
        }
        Bukkit.broadcastMessage(Utils.normalKickBroadcast(playerToKick.getName(), adminName, "Zmiana ilosci expa"));
    }

    public void setPlayerProcent(final String adminName, final UUID uuid, final double procent) {
        final int aktualnyLvlGracza = rpgcore.getPlayerManager().getPlayerLvl(uuid);
        final double expNaNextLvl = rpgcore.getLvlManager().getExpForLvl(aktualnyLvlGracza + 1);
        final double nowyExpGracza = (procent * expNaNextLvl) / 100;

        rpgcore.getPlayerManager().getPlayerExp().replace(uuid, nowyExpGracza);
        final Player playerToKick = Bukkit.getPlayer(uuid);
        if (playerToKick != null){
            playerToKick.kickPlayer(Utils.kickMessage(adminName, "Zmiana postepu do nastepego lvla"));
        }
        Bukkit.broadcastMessage(Utils.normalKickBroadcast(playerToKick.getName(), adminName, "Zmiana postepu do nastepnego lvla"));
    }

    public void updateLvlBelowName(final Player p, final String name1, final int lvl) {
        final Scoreboard sb = p.getScoreboard();
        Objective ob = sb.getObjective(Utils.format("&7Lvl &6"));
        if (ob == null) {
            ob = sb.registerNewObjective(Utils.format("&7Lvl &6"), "dummy");
            ob.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }
        final Score score = ob.getScore(name1);
        score.setScore(lvl);
    }
}
