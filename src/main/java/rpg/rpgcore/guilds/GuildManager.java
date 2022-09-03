package rpg.rpgcore.guilds;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.NameTagUtil;
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
    private final Map<String, Integer> guildBalance = new HashMap<>();
    private final Map<String, Boolean> guildPvPStatus = new HashMap<>();

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

    // INVITES
    private final Map<UUID, Map<String, Integer>> guildInvites = new HashMap<>();

    // ITEMY DO GUI
    private final List<String> lore = new ArrayList<>();
    private final ItemBuilder members = new ItemBuilder(Material.DIAMOND_CHESTPLATE);
    private final ItemBuilder memberItem = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3);
    private final ItemBuilder upgrades = new ItemBuilder(Material.ANVIL);
    private final ItemBuilder info = new ItemBuilder(Material.GOLD_BLOCK);
    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ItemBuilder sredniDmgItem = new ItemBuilder(Material.DIAMOND_SWORD);
    private final ItemBuilder sredniDefItem = new ItemBuilder(Material.DIAMOND_CHESTPLATE);
    private final ItemBuilder dmgLudzieItem = new ItemBuilder(Material.IRON_SWORD);
    private final ItemBuilder defLudzieItem = new ItemBuilder(Material.IRON_CHESTPLATE);
    private final ItemBuilder expItem = new ItemBuilder(Material.EXP_BOTTLE);

    // MAPA OD LVLI
    private final Map<Integer, Double> guildLvlMap = new HashMap<>(48);


    public void loadGuildLvlReq() {
        double next = 5000;
        for (int i = 2; i <= 50; i++) {
            this.guildLvlMap.put(i, next);
            next+=5000;
        }
    }

    public void listAllCommands(final Player player) {
        player.sendMessage(Utils.format("&8&m-_-_-_-_-_-_-_-_-&8{&6KLANY&8}&8&m-_-_-_-_-_-_-_-_-"));
        player.sendMessage(Utils.format("&6/klan zaloz <TAG> <Opis> &8- &7zaklada klan z podanym tagiem i opisem"));
        player.sendMessage(Utils.format("&6/klan usun &8- &7usuwa klan &8(&7musisz byc &4Zalozycielem &7klanu&8)"));
        player.sendMessage(Utils.format("&6/klan zapros <nick> &8- &7zaprasza podanego gracza do twojego klanu"));
        player.sendMessage(Utils.format("&6/klan wyrzuc <nick> &8- &7wyrzuca podanego gracza z klanu"));
        player.sendMessage(Utils.format("&6/klan lider <nick> &8- &7mianuje nowego lidera klanu"));
        player.sendMessage(Utils.format("&6/klan zastepca <nick> &8- &7mianuje nowego zastepce klanu"));
        player.sendMessage(Utils.format("&6/klan pvp &8- &7zmienia status pvp w klanie"));
        player.sendMessage(Utils.format("&6/klan usunzastepce &8- &7usuwa zastepce klanu"));
        player.sendMessage(Utils.format("&6/klan dolacz <TAG> &8- &7dolacza do podanego klanu, jesli masz zaproszenie"));
        player.sendMessage(Utils.format("&6/klan wplac <ilosc> &8- &7wplaca do skarbca klanowego podana ilosc"));
        player.sendMessage(Utils.format("&6/klan opusc &8- &7opuszcza klan"));
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
        player.sendMessage(Utils.format("&6Zalozyciel: &7" + rpgcore.getUserManager().find(guildOwner.get(tag)).getName()));
        if (guildCoOwner.get(tag) == null) {
            player.sendMessage(Utils.format("&6Zastepca: &7Brak Zastepcy"));
        } else {
            player.sendMessage(Utils.format("&6Zastepca: &7" + rpgcore.getUserManager().find(guildCoOwner.get(tag)).getName()));
        }
        if (guildPvPStatus.get(tag)) {
            player.sendMessage(Utils.format("&6PvP: &a&lON"));
        } else {
            player.sendMessage(Utils.format("&6PvP: &c&lOFF"));
        }
        player.sendMessage(Utils.format("&6Punkty: &7" + guildPoints.get(tag)));
        player.sendMessage(Utils.format("&6Lvl: &7" + guildLvl.get(tag)));
        if (this.getGuildLvl(tag) == 50) {
            player.sendMessage(Utils.format("&6Exp: &4&lMAX &7/ &4&lMAX"));
        } else  {
            player.sendMessage(Utils.format("&6Exp: &7" + guildExp.get(tag) + " &6/&7 " + this.getGuildNextLvlExp(tag)));
        }
        player.sendMessage(Utils.format("&6Czlonkowie: "));
        for (UUID uuid : guildMembers.get(tag)) {
            if (Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline()) {
                player.sendMessage(Utils.format("&8- &a" + rpgcore.getUserManager().find(uuid).getName()));
            } else {
                player.sendMessage(Utils.format("&8- &c" + rpgcore.getUserManager().find(uuid).getName()));
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
        guildPvPStatus.put(tag, false);
        guildPoints.put(tag, 0);
        guildLvl.put(tag, 1);
        guildExp.put(tag, 0.0);
        guildBalance.put(tag, 0);
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

    public void loadGuild(final String tag, final String description,  final UUID owner, final String coOwner, final List<UUID> members, final boolean pvp, final int points, final int lvl,
                          final double exp, final int balance, final double dodatkowyExp, final double sredniDmg, final double sredniDef,
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
        guildPvPStatus.put(tag, pvp);
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

    public void addPlayerToGuild(final String tag, final UUID uuid) {
        guildMembers.get(tag).add(uuid);
        guildKills.get(tag).put(uuid, 0);
        guildDeaths.get(tag).put(uuid, 0);
        guildExpEarned.get(tag).put(uuid, 0.0);
    }

    public void removePlayerFromGuild(final String tag, final UUID uuid) {
        guildMembers.get(tag).remove(uuid);
        guildKills.get(tag).remove(uuid);
        guildDeaths.get(tag).remove(uuid);
        guildExpEarned.get(tag).remove(uuid);

        if (this.getGuildCoOwner(tag) != null && this.getGuildCoOwner(tag).equals(uuid)) {
            this.setGuildCoOwner(tag, null);
        }

        final Player player = Bukkit.getPlayer(uuid);
        rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&cGracz &6" + player.getName() + " &czostal wyrzucony z klanu &6" + tag));
        final String group = rpgcore.getUserManager().getPlayerGroup(player);
        if (player.isOnline()) {
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> NameTagUtil.setPlayerDisplayNameNoGuild(player, group));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                TabManager.removePlayer(player);
                TabManager.addPlayer(player);
                for (Player restOfServer : Bukkit.getOnlinePlayers()) {
                    TabManager.update(restOfServer.getUniqueId());
                }
            });
        }

    }

    public void deleteGuild(final String tag) {
        guildList.remove(tag);
        guildDescription.remove(tag);
        guildOwner.remove(tag);
        guildCoOwner.remove(tag);
        guildMembers.get(tag).clear();
        guildPvPStatus.remove(tag);
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

    public String getGuildTag(final UUID uuid) {
        for (String tag : guildList) {
            if (guildMembers.get(tag).contains(uuid)) {
                return tag;
            }
        }
        return "Brak Klanu";
    }

    public void showPanel(final String tag, final Player player) {
        final Inventory panel = Bukkit.createInventory(null, 27, Utils.format("&6&lPanel Klanu " + tag));

        fill.setName(" ");
        for (int i = 0; i < panel.getSize(); i++) {
            panel.setItem(i, fill.toItemStack());
        }

        lore.clear();
        lore.add("&8&oKliknij&8, zeby sprawdzic informacje");
        lore.add("&8dotyczace czlonkow swojego klanu");

        panel.setItem(10, members.setName("&6&lCzlonkowie").setLore(lore).toItemStack());

        lore.clear();
        lore.add("&6Punkty: &7" + guildPoints.get(tag));
        lore.add("&6Lvl: &7" + guildLvl.get(tag));
        if (this.getGuildLvl(tag) == 50) {
            lore.add("&6Exp: &4&lMAX &6/ &4&lMAX");
        } else {
            lore.add("&6Exp: &7" + guildExp.get(tag) + "&6/&7 " + this.getGuildNextLvlExp(tag));
        }
        lore.add("&6Stan Konta: &7" + guildBalance.get(tag) + " &6kredytow");

        panel.setItem(13, info.setName("&6&lStatystyki").setLore(lore).toItemStack());

        lore.clear();
        lore.add("&8&oKliknij&8, zeby sprawdzic poziom ulepszenia swojego klanu");
        lore.add("&8&o(tylko dla &4&oLidera&8&o/&c&oZastepcy&8&o)");

        panel.setItem(16, upgrades.setName("&6&lUlepszenia").setLore(lore).toItemStack());

        player.openInventory(panel);
    }

    public void showMembers(final String tag, final Player player) {
        final Inventory membersPanel = Bukkit.createInventory(null, 27, Utils.format("&6&lCzlonkowie Klanu " + tag));

        final List<UUID> members = sortMemebers(tag);

        for (int i = 0; i < members.size(); i++) {
            final UUID uuid = members.get(i);
            final User user = rpgcore.getUserManager().find(uuid);

            if (this.getGuildOwner(tag).equals(uuid)) {
                memberItem.setName("&4&l" + user.getName());
            } else if (this.getGuildCoOwner(tag) != null && this.getGuildCoOwner(tag).equals(uuid)) {
                memberItem.setName("&c&l" + user.getName());
            } else {
                memberItem.setName("&6&l" + user.getName());
            }

            lore.clear();
            lore.add("&6Lvl: &7" + user.getLvl());
            if (user.getLvl() == 130) {
                lore.add("&6Exp: &4&lMAX &6/ &4&lMAX &8(&4&lMAX &7%&8)");
            } else {
                lore.add("&6Exp: &7" + user.getExp() + " &6/ &7" + rpgcore.getLvlManager().getExpForLvl(user.getLvl() + 1)
                        + " &8(&6" + Utils.procentFormat.format((user.getExp() / rpgcore.getLvlManager().getExpForLvl(user.getLvl() + 1)) * 100) + "&7%&8)");
            }
            lore.add("&6Zabojstwa: &7" + this.getGuildKills(tag).get(uuid));
            lore.add("&6Zgony: &7" + this.getGuildDeaths(tag).get(uuid));
            lore.add("&6Zdobyte doswiadczenie: &7" + this.getGuildExpEarned(tag).get(uuid));
            lore.add(" ");
            if (Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline()) {
                lore.add("&a&lOnline");
            } else {
                lore.add("&c&lOffline");
                lore.add("&7Ostatnio widziany: &c" + this.getLastSeenDateInString(tag, uuid));
            }

            if (this.getGuildCoOwner(tag) != null && (this.getGuildOwner(tag).equals(player.getUniqueId()) || this.getGuildCoOwner(tag).equals(player.getUniqueId()))) {
                lore.add(" ");
                lore.add("&8&o(Kliknij, zeby wyrzucic tego gracza z klanu)");
            }

            membersPanel.setItem(i, memberItem.setSkullOwner(user.getName()).setLore(lore).toItemStack().clone());

        }

        player.openInventory(membersPanel);
    }

    private List<UUID> sortMemebers(final String tag) {
        final List<UUID> members = new ArrayList<>();

        members.add(this.getGuildOwner(tag));
        if (this.getGuildCoOwner(tag) != null) {
            members.add(this.getGuildCoOwner(tag));
        }

        for (UUID uuid : this.getGuildMembers(tag)) {
            if (!members.contains(uuid)) {
                members.add(uuid);
            }
        }

        return members;
    }

    public void showUpgrades(final String tag, final Player player) {
        final Inventory upgradesPanel = Bukkit.createInventory(null, 9, Utils.format("&6&lUlepszenia Klanu " + tag));

        fill.setName(" ");
        for (int i = 0; i < upgradesPanel.getSize(); i++) {
            upgradesPanel.setItem(i, fill.toItemStack());
        }

        upgradesPanel.setItem(0, getSredniDmgItem(tag));
        upgradesPanel.setItem(2, getSredniDefItem(tag));
        upgradesPanel.setItem(4, getDodatkowyExpItem(tag));
        upgradesPanel.setItem(6, getDefLudzieItem(tag));
        upgradesPanel.setItem(8, getDmgLudzieItem(tag));

        player.openInventory(upgradesPanel);
    }

    public ItemStack getSredniDmgItem(final String tag) {
        final List<String> lore = new ArrayList<>();

        if (String.valueOf(this.getGuildSredniDmg(tag)).contains(".0")) {
            lore.add("&7Postep: &c" + String.format("%.0f", this.getGuildSredniDmg(tag)) + "% &7/ &c50%");
        } else  {
            lore.add("&7Postep: &c" + this.getGuildSredniDmg(tag) + "% &7/ &c50%");
        }
        if (this.getGuildSredniDmg(tag) != 50) {
            lore.add(" ");
            lore.add("&6Koszt ulepszenia:");
            lore.add("&8- &71 &6kredyt");
            return sredniDmgItem.setName("&c&lSrednie Obrazenia").setLore(lore).hideFlag().toItemStack().clone();
        } else {
            lore.add(" ");
            lore.add("&4&lMAX");
            return sredniDmgItem.setName("&c&lSrednie Obrazenia").setLore(lore).addGlowing().toItemStack().clone();
        }
    }

    public ItemStack getSredniDefItem(final String tag) {
        final List<String> lore = new ArrayList<>();

        if (String.valueOf(this.getGuildSredniDef(tag)).contains(".0")) {
            lore.add("&7Postep: &c" + String.format("%.0f", this.getGuildSredniDef(tag)) + "% &7/ &c50%");
        } else  {
            lore.add("&7Postep: &c" + this.getGuildSredniDef(tag) + "% &7/ &c50%");
        }
        if (this.getGuildSredniDef(tag) != 50) {
            lore.add(" ");
            lore.add("&6Koszt ulepszenia:");
            lore.add("&8- &71 &6kredyt");
            return sredniDefItem.setName("&c&lSrednia Odpornosc").setLore(lore).hideFlag().toItemStack().clone();
        } else {
            lore.add(" ");
            lore.add("&4&lMAX");
            return sredniDefItem.setName("&c&lSrednia Odpornosc").setLore(lore).addGlowing().toItemStack().clone();
        }
    }

    public ItemStack getDmgLudzieItem(final String tag) {
        final List<String> lore = new ArrayList<>();

        if (String.valueOf(this.getGuildSilnyNaLudzi(tag)).contains(".0")) {
            lore.add("&7Postep: &c" + String.format("%.0f", this.getGuildSilnyNaLudzi(tag)) + "% &7/ &c50%");
        } else  {
            lore.add("&7Postep: &c" + this.getGuildSilnyNaLudzi(tag) + "% &7/ &c50%");
        }
        if (this.getGuildSilnyNaLudzi(tag) != 50) {
            lore.add(" ");
            lore.add("&6Koszt ulepszenia:");
            lore.add("&8- &71 &6kredyt");
            return dmgLudzieItem.setName("&c&lSilny Przeciwko Ludziom").setLore(lore).hideFlag().toItemStack().clone();
        } else {
            lore.add(" ");
            lore.add("&4&lMAX");
            return dmgLudzieItem.setName("&c&lSilny Przeciwko Ludziom").setLore(lore).addGlowing().toItemStack().clone();
        }
    }

    public ItemStack getDefLudzieItem(final String tag) {
        final List<String> lore = new ArrayList<>();

        if (String.valueOf(this.getGuildDefNaLudzi(tag)).contains(".0")) {
            lore.add("&7Postep: &c" + String.format("%.0f", this.getGuildDefNaLudzi(tag)) + "% &7/ &c50%");
        } else  {
            lore.add("&7Postep: &c" + this.getGuildDefNaLudzi(tag) + "% &7/ &c50%");
        }
        if (this.getGuildDefNaLudzi(tag) != 50) {
            lore.add(" ");
            lore.add("&6Koszt ulepszenia:");
            lore.add("&8- &71 &6kredyt");
            return defLudzieItem.setName("&c&lOdpornosc Przeciwko Ludziom").setLore(lore).hideFlag().toItemStack().clone();
        } else {
            lore.add(" ");
            lore.add("&4&lMAX");
            return defLudzieItem.setName("&c&lOdpornosc Przeciwko Ludziom").setLore(lore).addGlowing().toItemStack().clone();
        }
    }

    public ItemStack getDodatkowyExpItem(final String tag) {
        final List<String> lore = new ArrayList<>();

        if (String.valueOf(this.getGuildDodatkowyExp(tag)).contains(".0")) {
            lore.add("&7Postep: &c" + String.format("%.0f", this.getGuildDodatkowyExp(tag)) + "% &7/ &c25%");
        } else  {
            lore.add("&7Postep: &c" + this.getGuildDodatkowyExp(tag) + "% &7/ &c25%");
        }
        if (this.getGuildDodatkowyExp(tag) != 25) {
            lore.add(" ");
            lore.add("&6Koszt ulepszenia:");
            lore.add("&8- &72 &6kredyt");
            lore.add("&8- &c&l100 &4&lH&8&lC");
            return expItem.setName("&c&lDodatkowy EXP").setLore(lore).hideFlag().toItemStack().clone();
        } else {
            lore.add(" ");
            lore.add("&4&lMAX");
            return expItem.setName("&c&lDodatkowy EXP").setLore(lore).addGlowing().toItemStack().clone();
        }
    }

    public void invitePlayer(final String tag, final UUID uuid, final Player player) {
        guildInvites.put(uuid, new HashMap<>());
        guildInvites.get(uuid).put(tag, 0);
        int i = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.removeFromGuildInvites(uuid, tag, player), 600L).getTaskId();
        this.updateInviteTaskId(uuid, tag, i);
        player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&aOtrzymano zaproszenie do klanu &6" + tag));
    }

    public void acceptInvite(final String tag, final UUID uuid, final Player player) {
        if (this.getGuildInviteTag(uuid) != null && this.getGuildInviteTag(uuid).contains(tag)) {
            this.addPlayerToGuild(tag, uuid);
            final int taskID = this.guildInvites.get(uuid).get(tag);
            guildInvites.get(uuid).remove(tag);
            this.removeFromGuildInvites(uuid, tag, player, taskID);
            player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&aZaakceptowano zaproszenie do Klanu &6" + tag));
        }
    }

    public void sendMessageToGuild(final String tag, final String message, final Player sender) {
        String prefix = "&6";
        String messagePrefix = "&f";

        if (this.getGuildOwner(tag).equals(sender.getUniqueId())) {
            prefix = "&4";
            messagePrefix = "&c";
        } else if (this.getGuildCoOwner(tag) != null && this.getGuildCoOwner(tag).equals(sender.getUniqueId())) {
            prefix = "&c";
            messagePrefix = "&c";
        }
        for (final UUID uuid : this.getGuildMembers(tag)) {
            if (Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline()) {
                final Player player = Bukkit.getPlayer(uuid);
                player.sendMessage(Utils.format("&8[&4KlanChat&8] " + prefix + sender.getName() + ">> " + messagePrefix + message));
            }
        }
    }


    public void setAll(final String tag, final UUID owner, final UUID coOwner, final List<UUID> members, final boolean pvp, final int points, final int lvl, final double exp,
                       final int balance, final double dodatkowyExp, final double sredniDmg, final double sredniDef, final double silnyNaLudzi, final double defNaLudzi,
                       final Map<UUID, Integer> kills, final Map<UUID, Integer> deaths, final Map<UUID, Double> expEarned) {
        setGuildOwner(tag, owner);
        setGuildCoOwner(tag, coOwner);
        setGuildMembers(tag, members);
        setGuildPoints(tag, points);
        setGuildPvPStatus(tag, pvp);
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

    public int getGuildBalance(final String tag) {
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

    public void setGuildPvPStatus(final String tag, final boolean pvp) {
        guildPvPStatus.put(tag, pvp);
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

    public void setGuildBalance(final String tag, final int balance) {
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
        if (this.getGuildExp(tag) >= this.getGuildNextLvlExp(tag)) {
            this.updateGuildLvl(tag, 1);
            this.setGuildExp(tag, 0);
            this.updateGuildBalance(tag, 1);
            rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&7Klan &6" + tag + " &7osiagnal &6" + this.getGuildLvl(tag) + " &7poziom!"));

        }
    }

    public void updateGuildBalance(final String tag, final int balance) {
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
        guildKills.get(tag).replace(player, this.getGuildKills(tag).get(player) + kills);
    }

    public void updateGuildDeaths(final String tag, final UUID player, final int deaths) {
        guildDeaths.get(tag).replace(player, this.getGuildDeaths(tag).get(player) + deaths);
    }

    public void updateGuildExpEarned(final String tag, final UUID player, final double expEarned) {
        guildExpEarned.get(tag).replace(player, this.getGuildExpEarned(tag).get(player) + expEarned);
    }

    public void updateGuildLastOnline(final String tag, final UUID player, final Date lastOnline) {
        guildLastOnline.get(tag).replace(player, lastOnline);
    }

    public void putGuildLastOnline(final String tag, final UUID player, final Date lastOnline) {
        guildLastOnline.get(tag).put(player, lastOnline);
    }

    public String getLastSeenDateInString(final String tag, final UUID uuid) {
        return Utils.dateFormat.format(guildLastOnline.get(tag).get(uuid));
    }

    public boolean hasGuild(final UUID uuid) {
        for (String tag : guildList) {
            if (guildMembers.get(tag).contains(uuid)) {
                return true;
            }
        }
        return false;
    }

    public void addToGuildInvites(final UUID uuid, final String tag, final int taskId) {
        guildInvites.put(uuid, new HashMap<>());
        guildInvites.get(uuid).put(tag, taskId);
    }

    public void updateInviteTaskId(final UUID uuid, final String tag, final int taskId) {
        guildInvites.get(uuid).replace(tag, taskId);
    }

    public List<String> getGuildInviteTag(final UUID uuid) {
        List<String> tags = new ArrayList<>();
        for (String tag : guildList) {
            if (guildInvites.get(uuid) == null) {
                guildInvites.computeIfAbsent(uuid, k -> new HashMap<>());
                return new ArrayList<>();
            }
            if (guildInvites.get(uuid).containsKey(tag)) {
                tags.add(tag);
            }
        }
        return tags;
    }

    public void removeFromGuildInvites(final UUID uuid, final String tag, final Player player, final int taskId) {
        Bukkit.getScheduler().cancelTask(taskId);
        if (guildInvites.get(uuid).containsKey(tag)) {
            player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cZaproszenie od klanu &6" + tag + "&c wygaslo."));
        }
    }

    public void removeFromGuildInvites(final UUID uuid, final String tag, final Player player) {
        final int i = guildInvites.get(uuid).get(tag);
        Bukkit.getScheduler().cancelTask(i);
        if (guildInvites.get(uuid).containsKey(tag)) {
            player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cZaproszenie od klanu &6" + tag + "&c wygaslo."));
        }
    }

    public void removeFromGuildInvitesAccepted(final UUID uuid, final String tag) {
        final int i = guildInvites.get(uuid).get(tag);
        Bukkit.getScheduler().cancelTask(i);
        guildInvites.get(uuid).remove(tag);
    }

    public List<String> getListOfGuilds() {
        return guildList;
    }

    public boolean getGuildPvPStatus(final String tag) {
        return guildPvPStatus.get(tag);
    }

    public int getGuildKillsAll(final String tag) {
        int kills = 0;
        for (UUID uuid : guildMembers.get(tag)) {
            kills += guildKills.get(tag).get(uuid);
        }
        return kills;
    }

    public int getGuildDeathsAll(final String tag) {
        int deaths = 0;
        for (UUID uuid : guildMembers.get(tag)) {
            deaths += guildDeaths.get(tag).get(uuid);
        }
        return deaths;
    }

    public double getGuildNextLvlExp(final String tag) {
        return this.guildLvlMap.get(this.getGuildLvl(tag) + 1);
    }
}
