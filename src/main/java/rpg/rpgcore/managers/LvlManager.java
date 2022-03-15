package rpg.rpgcore.managers;

import io.lumine.xikage.mythicmobs.MythicMobs;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
        PacketPlayOutTitle title = rpgcore.getNmsManager().makeTitle("&b&lLVL UP!", 5, 25, 5);
        PacketPlayOutTitle subtitle = rpgcore.getNmsManager().makeSubTitle("&fAwansowales na &b" + nextLvlGracza + " &fpoziom", 5, 25, 5);
        Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(killer, title, subtitle));
    }
}
