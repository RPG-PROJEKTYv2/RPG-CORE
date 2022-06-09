package rpg.rpgcore.guilds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class GuildManager {

    private final RPGCORE rpgcore;

    public GuildManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    // LISTA GILDII
    private final List<String> guildList = new ArrayList<>();
    private final Map<String, String> guildDescription = new HashMap<>();
    private final Map<String, UUID> guildOwner = new HashMap<>();
    private final Map<String, UUID> guildCoOwner = new HashMap<>();
    private final Map<String, List<UUID>> guildMembers = new HashMap<>();

    // GŁÓWNE ZMIENNE
    private final Map<String, Integer> guildPoints = new HashMap<>();
    private final Map<String, Integer> guildLvl = new HashMap<>();
    private final Map<String, Double> guildExp = new HashMap<>();
    private final Map<String, Double> guildBalance = new HashMap<>();

    // BONUSY
    private final Map<String, Double> guildDodatkowyExp = new HashMap<>();
    private final Map<String, Double> guildSredniDmg = new HashMap<>();
    private final Map<String, Double> guildSredniDef = new HashMap<>();
    private final Map<String, Double> guildSilnyNaLudzi = new HashMap<>();
    private final Map<String, Double> guildDefNaLudzi = new HashMap<>();

    // STATYSTYKI
    private final Map<String, Map<UUID, Integer>> guildKills = new HashMap<>();
    private final Map<String, Map<UUID, Integer>> guildDeaths = new HashMap<>();
    private final Map<String, Map<UUID, Double>> guildExpEarned = new HashMap<>();
    private final Map<String, Map<UUID, Date>> guildLastOnline = new HashMap<>();


    public void listAllCommands(final Player player) {
        player.sendMessage(Utils.format("&8&m-_-_-_-_-_-_-_-_-&8{&6KLANY&8}&8&m-_-_-_-_-_-_-_-_-"));
        player.sendMessage(Utils.format("&6/klan zaloz <TAG> <Opis> &8- &7zaklada klan z podanym tagiem i opisem"));
        player.sendMessage(Utils.format("&6/klan usun &8- &7usuwa klan &8(&7musisz byc &4Zalozycielem &7klanu&8)"));
        player.sendMessage(Utils.format("&6/klan zapros <nick> &8- &7zaprasza podanego gracza do twojego klanu"));
        player.sendMessage(Utils.format("&6/klan wyrzuc <nick> &8- &7wyrzuca podanego gracza z klanu"));
        player.sendMessage(Utils.format("&6/klan panel &8- &7otwiera panel klanowy"));
        player.sendMessage(Utils.format("&6/klan info <TAG> &8- &7wyswietla informacje o podanym klanie"));
        player.sendMessage(Utils.format("&6/klan stats &8- &7wyswietla statystyki twojego klanu"));
        player.sendMessage(Utils.format("&6/klan chat <wiadomosc> &8- &7wysyla wiadomosc na chacie klanowym"));
        player.sendMessage(Utils.format("&8&m-_-_-_-_-_-_-_-_-&8{&6KLANY&8}&8&m-_-_-_-_-_-_-_-_-"));
    }

    public void showInfo(final String tag, final Player player) {
        if (!guildList.contains(tag)) {
            player.sendMessage(Utils.format("&cKlan o podanym tagie nie istnieje!"));
            return;
        }
        player.sendMessage(Utils.format("&8&m-_-_-_-_-_-_-_-_-&8{&6" + tag + "&8}&8&m-_-_-_-_-_-_-_-_-"));
        player.sendMessage(Utils.format("&6Opis: &7" + guildDescription.get(tag)));
        player.sendMessage(Utils.format("&6Zalozyciel: &7" + rpgcore.getPlayerManager().getPlayerName(guildOwner.get(tag))));
        if (guildCoOwner.get(tag) == null) {
            player.sendMessage(Utils.format("&6Zastepca: &7Brak Zastepcy"));
        } else {
            player.sendMessage(Utils.format("&6Zastepca: &7" + rpgcore.getPlayerManager().getPlayerName(guildCoOwner.get(tag))));
        }
        player.sendMessage(Utils.format("&6Punkty: &7" + guildPoints.get(tag)));
        player.sendMessage(Utils.format("&6Lvl: &7" + guildLvl.get(tag)));
        player.sendMessage(Utils.format("&6Exp: &7" + guildExp.get(tag)));
        player.sendMessage(Utils.format("&6Czlonkowie: "));
        for (UUID uuid : guildMembers.get(tag)) {
            if (Bukkit.getPlayer(uuid).isOnline()) {
                player.sendMessage(Utils.format("&8- &a" + rpgcore.getPlayerManager().getPlayerName(uuid)));
            } else {
                player.sendMessage(Utils.format("&8- &c" + rpgcore.getPlayerManager().getPlayerName(uuid)));
            }
        }
        player.sendMessage(Utils.format("&8&m-_-_-_-_-_-_-_-_-&8{&6" + tag + "&8}&8&m-_-_-_-_-_-_-_-_-"));
    }

    public void createGuild(final String tag, final String description, final UUID owner) {
        guildList.add(tag);
        guildDescription.put(tag, description);
        guildOwner.put(tag, owner);
        guildCoOwner.put(tag, null);
        guildMembers.put(tag, new ArrayList<>());
        guildPoints.put(tag, 0);
        guildLvl.put(tag, 1);
        guildExp.put(tag, 0.0);
        guildBalance.put(tag, 0.0);
        guildDodatkowyExp.put(tag, 0.0);
        guildSredniDmg.put(tag, 0.0);
        guildSredniDef.put(tag, 0.0);
        guildSilnyNaLudzi.put(tag, 0.0);
        guildDefNaLudzi.put(tag, 0.0);
        guildKills.put(tag, new HashMap<>());
        guildDeaths.put(tag, new HashMap<>());
        guildExpEarned.put(tag, new HashMap<>());
        guildLastOnline.put(tag, new HashMap<>());

        guildMembers.get(tag).add(owner);
        guildKills.get(tag).put(owner, 0);
        guildDeaths.get(tag).put(owner, 0);
        guildExpEarned.get(tag).put(owner, 0.0);


        rpgcore.getMongoManager().saveGuildFirst(tag);
    }

    public void loadGuild(final String tag, final String description,  final UUID owner, final String coOwner, final List<UUID> members, final int points, final int lvl,
                          final double exp, final double balance, final double dodatkowyExp, final double sredniDmg, final double sredniDef,
                          final double silnyNaLudzi, final double defNaLudzi, final Map<UUID, Integer> kills, final Map<UUID, Integer> deaths,
                          final Map<UUID, Double> expEarned, final Map<UUID, Date> lastOnline) {
        guildList.add(tag);
        guildDescription.put(tag, description);
        guildOwner.put(tag, owner);
        if (coOwner == null) {
            guildCoOwner.put(tag, null);
        } else {
            guildCoOwner.put(tag, UUID.fromString(coOwner));
        }
        guildMembers.put(tag, members);
        guildPoints.put(tag, points);
        guildLvl.put(tag, lvl);
        guildExp.put(tag, exp);
        guildBalance.put(tag, balance);
        guildDodatkowyExp.put(tag, dodatkowyExp);
        guildSredniDmg.put(tag, sredniDmg);
        guildSredniDef.put(tag, sredniDef);
        guildSilnyNaLudzi.put(tag, silnyNaLudzi);
        guildDefNaLudzi.put(tag, defNaLudzi);
        guildKills.put(tag, kills);
        guildDeaths.put(tag, deaths);
        guildExpEarned.put(tag, expEarned);
        guildLastOnline.put(tag, lastOnline);
    }

    public void deleteGuild(final String tag) {
        guildList.remove(tag);
        guildDescription.remove(tag);
        guildOwner.remove(tag);
        guildCoOwner.remove(tag);
        guildMembers.get(tag).clear();
        guildMembers.remove(tag);
        guildPoints.remove(tag);
        guildLvl.remove(tag);
        guildExp.remove(tag);
        guildBalance.remove(tag);
        guildDodatkowyExp.remove(tag);
        guildSredniDmg.remove(tag);
        guildSredniDef.remove(tag);
        guildSilnyNaLudzi.remove(tag);
        guildDefNaLudzi.remove(tag);
        guildKills.get(tag).clear();
        guildKills.remove(tag);
        guildDeaths.get(tag).clear();
        guildDeaths.remove(tag);
        guildExpEarned.get(tag).clear();
        guildExpEarned.remove(tag);
        guildLastOnline.get(tag).clear();
        guildLastOnline.remove(tag);
    }

    public void setAll(final String tag, final UUID owner, final UUID coOwner, final List<UUID> members, final int points, final int lvl, final double exp,
                       final double balance, final double dodatkowyExp, final double sredniDmg, final double sredniDef, final double silnyNaLudzi, final double defNaLudzi,
                       final Map<UUID, Integer> kills, final Map<UUID, Integer> deaths, final Map<UUID, Double> expEarned) {
        setGuildOwner(tag, owner);
        setGuildCoOwner(tag, coOwner);
        setGuildMembers(tag, members);
        setGuildPoints(tag, points);
        setGuildLvl(tag, lvl);
        setGuildExp(tag, exp);
        setGuildBalance(tag, balance);
        setGuildDodatkowyExp(tag, dodatkowyExp);
        setGuildSredniDmg(tag, sredniDmg);
        setGuildSredniDef(tag, sredniDef);
        setGuildSilnyNaLudzi(tag, silnyNaLudzi);
        setGuildDefNaLudzi(tag, defNaLudzi);
        setGuildKills(tag, kills);
        setGuildDeaths(tag, deaths);
        setGuildExpEarned(tag, expEarned);
    }


    public String getGuildDescription(final String tag) {
        return guildDescription.get(tag);
    }

    public UUID getGuildOwner(final String tag) {
        return guildOwner.get(tag);
    }

    public UUID getGuildCoOwner(final String tag) {
        return guildCoOwner.get(tag);
    }

    public List<UUID> getGuildMembers(final String tag) {
        return guildMembers.get(tag);
    }

    public int getGuildPoints(final String tag) {
        return guildPoints.get(tag);
    }

    public int getGuildLvl(final String tag) {
        return guildLvl.get(tag);
    }

    public double getGuildExp(final String tag) {
        return guildExp.get(tag);
    }

    public double getGuildBalance(final String tag) {
        return guildBalance.get(tag);
    }

    public double getGuildDodatkowyExp(final String tag) {
        return guildDodatkowyExp.get(tag);
    }

    public double getGuildSredniDmg(final String tag) {
        return guildSredniDmg.get(tag);
    }

    public double getGuildSredniDef(final String tag) {
        return guildSredniDef.get(tag);
    }

    public double getGuildSilnyNaLudzi(final String tag) {
        return guildSilnyNaLudzi.get(tag);
    }

    public double getGuildDefNaLudzi(final String tag) {
        return guildDefNaLudzi.get(tag);
    }

    public Map<UUID, Integer> getGuildKills(final String tag) {
        return guildKills.get(tag);
    }

    public Map<UUID, Integer> getGuildDeaths(final String tag) {
        return guildDeaths.get(tag);
    }

    public Map<UUID, Double> getGuildExpEarned(final String tag) {
        return guildExpEarned.get(tag);
    }

    public Map<UUID, Date> getGuildLastOnline(final String tag) {
        return guildLastOnline.get(tag);
    }

    public void setGuildDescription(final String tag, final String description) {
        guildDescription.put(tag, description);
    }

    public void setGuildOwner(final String tag, final UUID owner) {
        guildOwner.put(tag, owner);
    }

    public void setGuildCoOwner(final String tag, final UUID coOwner) {
        guildCoOwner.put(tag, coOwner);
    }

    public void setGuildMembers(final String tag, final List<UUID> members) {
        guildMembers.put(tag, members);
    }

    public void setGuildPoints(final String tag, final int points) {
        guildPoints.put(tag, points);
    }

    public void setGuildLvl(final String tag, final int lvl) {
        guildLvl.put(tag, lvl);
    }

    public void setGuildExp(final String tag, final double exp) {
        guildExp.put(tag, exp);
    }

    public void setGuildBalance(final String tag, final double balance) {
        guildBalance.put(tag, balance);
    }

    public void setGuildDodatkowyExp(final String tag, final double dodatkowyExp) {
        guildDodatkowyExp.put(tag, dodatkowyExp);
    }

    public void setGuildSredniDmg(final String tag, final double sredniDmg) {
        guildSredniDmg.put(tag, sredniDmg);
    }

    public void setGuildSredniDef(final String tag, final double sredniDef) {
        guildSredniDef.put(tag, sredniDef);
    }

    public void setGuildSilnyNaLudzi(final String tag, final double silnyNaLudzi) {
        guildSilnyNaLudzi.put(tag, silnyNaLudzi);
    }

    public void setGuildDefNaLudzi(final String tag, final double defNaLudzi) {
        guildDefNaLudzi.put(tag, defNaLudzi);
    }

    public void setGuildKills(final String tag, final Map<UUID, Integer> kills) {
        guildKills.put(tag, kills);
    }

    public void setGuildDeaths(final String tag, final Map<UUID, Integer> deaths) {
        guildDeaths.put(tag, deaths);
    }

    public void setGuildExpEarned(final String tag, final Map<UUID, Double> expEarned) {
        guildExpEarned.put(tag, expEarned);
    }

    public void setGuildLastOnline(final String tag, final Map<UUID, Date> lastOnline) {
        guildLastOnline.put(tag, lastOnline);
    }

    public void addGuildMember(final String tag, final UUID member) {
        guildMembers.get(tag).add(member);
    }

    public void removeGuildMember(final String tag, final UUID member) {
        guildMembers.get(tag).remove(member);
    }

    public void addGuildKill(final String tag, final UUID player, final int kills) {
        guildKills.get(tag).put(player, kills);
    }

    public void addGuildDeath(final String tag, final UUID player, final int deaths) {
        guildDeaths.get(tag).put(player, deaths);
    }

    public void addGuildExpEarned(final String tag, final UUID player, final double expEarned) {
        guildExpEarned.get(tag).put(player, expEarned);
    }

    public void updateGuildPoints(final String tag, final int points) {
        guildPoints.put(tag, guildPoints.get(tag) + points);
    }

    public void updateGuildLvl(final String tag, final int lvl) {
        guildLvl.replace(tag, guildLvl.get(tag) + lvl);
    }

    public void updateGuildExp(final String tag, final double exp) {
        guildExp.replace(tag, guildExp.get(tag) + exp);
    }

    public void updateGuildBalance(final String tag, final double balance) {
        guildBalance.replace(tag, guildBalance.get(tag) + balance);
    }

    public void updateGuildDodatkowyExp(final String tag, final double dodatkowyExp) {
        guildDodatkowyExp.replace(tag, guildDodatkowyExp.get(tag) + dodatkowyExp);
    }

    public void updateGuildSredniDmg(final String tag, final double sredniDmg) {
        guildSredniDmg.replace(tag, guildSredniDmg.get(tag) + sredniDmg);
    }

    public void updateGuildSredniDef(final String tag, final double sredniDef) {
        guildSredniDef.replace(tag, guildSredniDef.get(tag) + sredniDef);
    }

    public void updateGuildSilnyNaLudzi(final String tag, final double silnyNaLudzi) {
        guildSilnyNaLudzi.replace(tag, guildSilnyNaLudzi.get(tag) + silnyNaLudzi);
    }

    public void updateGuildDefNaLudzi(final String tag, final double defNaLudzi) {
        guildDefNaLudzi.replace(tag, guildDefNaLudzi.get(tag) + defNaLudzi);
    }

    public void updateGuildKills(final String tag, final UUID player, final int kills) {
        guildKills.get(tag).replace(player, kills);
    }

    public void updateGuildDeaths(final String tag, final UUID player, final int deaths) {
        guildDeaths.get(tag).replace(player, deaths);
    }

    public void updateGuildExpEarned(final String tag, final UUID player, final double expEarned) {
        guildExpEarned.get(tag).replace(player, expEarned);
    }

    public void updateGuildLastOnline(final String tag, final UUID player, final Date lastOnline) {
        guildLastOnline.get(tag).replace(player, lastOnline);
    }
}
