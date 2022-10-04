package rpg.rpgcore.guilds;

import com.google.common.collect.ImmutableSet;
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
    private final Map<String, GuildObject> guildMap;

    public GuildManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.guildMap = rpgcore.getMongoManager().loadAllGuilds();
    }

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
        for (GuildObject guildObject : this.getGuilds()){
            if (!guildObject.getTag().equals(tag)) {
                player.sendMessage(Utils.format("&cKlan o podanym tagie nie istnieje!"));
                return;
            }
        }
        final Guild guild = this.find(tag).getGuild();
        player.sendMessage(Utils.format("&8&m-_-_-_-_-_-_-_-_-&8{&6" + tag + "&8}&8&m-_-_-_-_-_-_-_-_-"));
        player.sendMessage(Utils.format("&6Opis: &7" + guild.getDescription()));
        player.sendMessage(Utils.format("&6Zalozyciel: &7" + rpgcore.getUserManager().find(guild.getOwner()).getName()));
        if (guild.getCoOwner() == null) {
            player.sendMessage(Utils.format("&6Zastepca: &7Brak Zastepcy"));
        } else {
            player.sendMessage(Utils.format("&6Zastepca: &7" + rpgcore.getUserManager().find(guild.getCoOwner()).getName()));
        }
        if (guild.isPvp()) {
            player.sendMessage(Utils.format("&6PvP: &a&lON"));
        } else {
            player.sendMessage(Utils.format("&6PvP: &c&lOFF"));
        }
        player.sendMessage(Utils.format("&6Punkty: &7" + guild.getPoints()));
        player.sendMessage(Utils.format("&6Lvl: &7" + guild.getLevel()));
        if (this.getGuildLvl(tag) == 50) {
            player.sendMessage(Utils.format("&6Exp: &4&lMAX &7/ &4&lMAX"));
        } else  {
            player.sendMessage(Utils.format("&6Exp: &7" + guild.getExp() + " &6/&7 " + this.getGuildNextLvlExp(tag)));
        }
        player.sendMessage(Utils.format("&6Czlonkowie: "));
        for (UUID uuid : guild.getMembers()) {
            if (Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline()) {
                player.sendMessage(Utils.format("&8- &a" + rpgcore.getUserManager().find(uuid).getName()));
            } else {
                player.sendMessage(Utils.format("&8- &c" + rpgcore.getUserManager().find(uuid).getName()));
            }
        }
        player.sendMessage(Utils.format("&8&m-_-_-_-_-_-_-_-_-&8{&6" + tag + "&8}&8&m-_-_-_-_-_-_-_-_-"));
    }

    public void createGuild(final String tag, final String description, final UUID owner) {
        final GuildObject guildObject = new GuildObject(tag, description, owner);
        this.add(guildObject);
        rpgcore.getMongoManager().addDataGuild(guildObject);
    }


    public void addPlayerToGuild(final String tag, final UUID uuid) {
        final GuildObject guildObject = this.find(tag);
        guildObject.getGuild().getMembers().add(uuid);
        guildObject.getGuild().getKills().put(uuid, 0);
        guildObject.getGuild().getDeaths().put(uuid, 0);
        guildObject.getGuild().getExpEarned().put(uuid, 0.0);
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataGuild(guildObject.getTag(), guildObject));
    }

    public void removePlayerFromGuild(final String tag, final UUID uuid) {
        final GuildObject guildObject = this.find(tag);
        guildObject.getGuild().getMembers().remove(uuid);
        guildObject.getGuild().getKills().remove(uuid);
        guildObject.getGuild().getDeaths().remove(uuid);
        guildObject.getGuild().getExpEarned().remove(uuid);

        if (this.getGuildCoOwner(tag) != null && this.getGuildCoOwner(tag).equals(uuid)) {
            this.setGuildCoOwner(tag, null);
        }

        final Player player = Bukkit.getPlayer(uuid);
        rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&cGracz &6" + player.getName() + " &czostal wyrzucony z klanu &6" + tag));
        final String group = rpgcore.getUserManager().getPlayerGroup(player);
        if (player.isOnline()) {
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataGuild(guildObject.getTag(), guildObject);
                NameTagUtil.setPlayerDisplayNameNoGuild(player, group);
                TabManager.removePlayer(player);
                TabManager.addPlayer(player);
                for (Player restOfServer : Bukkit.getOnlinePlayers()) {
                    TabManager.update(restOfServer.getUniqueId());
                }
            });
        }

    }

    public void deleteGuild(final String tag) {
        this.guildMap.remove(tag);
        rpgcore.getMongoManager().removeDataGuild(tag);
    }

    public String getGuildTag(final UUID uuid) {
        for (final GuildObject guildObject : this.getGuilds()) {
            if (guildObject.getGuild().getMembers().contains(uuid)) {
                return guildObject.getTag();
            }
        }
        return "Brak Klanu";
    }

    public void showPanel(final String tag, final Player player) {
        final Inventory panel = Bukkit.createInventory(null, 27, Utils.format("&6&lPanel Klanu " + tag));
        final Guild guild = this.find(tag).getGuild();
        fill.setName(" ");
        for (int i = 0; i < panel.getSize(); i++) {
            panel.setItem(i, fill.toItemStack());
        }

        lore.clear();
        lore.add("&8&oKliknij&8, zeby sprawdzic informacje");
        lore.add("&8dotyczace czlonkow swojego klanu");

        panel.setItem(10, members.setName("&6&lCzlonkowie").setLore(lore).toItemStack());

        lore.clear();
        lore.add("&6Punkty: &7" + guild.getPoints());
        lore.add("&6Lvl: &7" + guild.getLevel());
        if (this.getGuildLvl(tag) == 50) {
            lore.add("&6Exp: &4&lMAX &6/ &4&lMAX");
        } else {
            lore.add("&6Exp: &7" + guild.getExp() + "&6/&7 " + this.getGuildNextLvlExp(tag));
        }
        lore.add("&6Stan Konta: &7" + guild.getBalance() + " &6kredytow");

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

    public String getGuildDescription(final String tag) {
        return this.find(tag).getDescription();
    }

    public UUID getGuildOwner(final String tag) {
        return this.find(tag).getOwner();
    }

    public UUID getGuildCoOwner(final String tag) {
        return this.find(tag).getGuild().getCoOwner();
    }

    public List<UUID> getGuildMembers(final String tag) {
        return this.find(tag).getGuild().getMembers();
    }

    public int getGuildPoints(final String tag) {
        return this.find(tag).getGuild().getPoints();
    }

    public int getGuildLvl(final String tag) {
        return this.find(tag).getGuild().getLevel();
    }

    public double getGuildExp(final String tag) {
        return this.find(tag).getGuild().getExp();
    }

    public int getGuildBalance(final String tag) {
        return this.find(tag).getGuild().getBalance();
    }

    public double getGuildDodatkowyExp(final String tag) {
        return this.find(tag).getGuild().getDodatkowyExp();
    }

    public double getGuildSredniDmg(final String tag) {
        return this.find(tag).getGuild().getSredniDmg();
    }

    public double getGuildSredniDef(final String tag) {
        return this.find(tag).getGuild().getSredniDef();
    }

    public double getGuildSilnyNaLudzi(final String tag) {
        return this.find(tag).getGuild().getSilnyNaLudzi();
    }

    public double getGuildDefNaLudzi(final String tag) {
        return this.find(tag).getGuild().getDefNaLudzi();
    }

    public Map<UUID, Integer> getGuildKills(final String tag) {
        return this.find(tag).getGuild().getKills();
    }

    public Map<UUID, Integer> getGuildDeaths(final String tag) {
        return this.find(tag).getGuild().getDeaths();
    }

    public Map<UUID, Double> getGuildExpEarned(final String tag) {
        return this.find(tag).getGuild().getExpEarned();
    }

    public Map<UUID, Date> getGuildLastOnline(final String tag) {
        return this.find(tag).getGuild().getLastOnline();
    }

    public void setGuildDescription(final String tag, final String description) {
        this.find(tag).setDescription(description);
        this.find(tag).getGuild().setDescription(description);
    }

    public void setGuildOwner(final String tag, final UUID owner) {
        //this.find(tag).setOwner(owner);
        this.find(tag).getGuild().setOwner(owner);
    }

    public void setGuildCoOwner(final String tag, final UUID coOwner) {
        this.find(tag).getGuild().setCoOwner(coOwner);
    }

    public void setGuildPvPStatus(final String tag, final boolean pvp) {
        this.find(tag).getGuild().setPvp(pvp);
    }

    public void setGuildPoints(final String tag, final int points) {
        this.find(tag).getGuild().setPoints(points);
    }

    public void setGuildLvl(final String tag, final int lvl) {
        this.find(tag).getGuild().setLevel(lvl);
    }

    public void setGuildExp(final String tag, final double exp) {
        this.find(tag).getGuild().setExp(exp);
    }

    public void setGuildBalance(final String tag, final int balance) {
        this.find(tag).getGuild().setBalance(balance);
    }

    public void setGuildDodatkowyExp(final String tag, final double dodatkowyExp) {
        this.find(tag).getGuild().setDodatkowyExp(dodatkowyExp);
    }

    public void setGuildSredniDmg(final String tag, final double sredniDmg) {
        this.find(tag).getGuild().setSredniDmg(sredniDmg);
    }

    public void setGuildSredniDef(final String tag, final double sredniDef) {
        this.find(tag).getGuild().setSredniDef(sredniDef);
    }

    public void setGuildSilnyNaLudzi(final String tag, final double silnyNaLudzi) {
        this.find(tag).getGuild().setSilnyNaLudzi(silnyNaLudzi);
    }

    public void setGuildDefNaLudzi(final String tag, final double defNaLudzi) {
        this.find(tag).getGuild().setDefNaLudzi(defNaLudzi);
    }

    public void setGuildKills(final String tag, final Map<UUID, Integer> kills) {
        this.find(tag).getGuild().setKills(kills);
    }

    public void setGuildDeaths(final String tag, final Map<UUID, Integer> deaths) {
        this.find(tag).getGuild().setDeaths(deaths);
    }

    public void setGuildExpEarned(final String tag, final Map<UUID, Double> expEarned) {
        this.find(tag).getGuild().setExpEarned(expEarned);
    }

    public void setGuildLastOnline(final String tag, final Map<UUID, Date> lastOnline) {
        this.find(tag).getGuild().setLastOnline(lastOnline);
    }

    public void addGuildMember(final String tag, final UUID member) {
        this.find(tag).getGuild().getMembers().add(member);
    }

    public void removeGuildMember(final String tag, final UUID member) {
        this.find(tag).getGuild().getMembers().remove(member);
    }

    public void addGuildKill(final String tag, final UUID player, final int kills) {
        this.find(tag).getGuild().getKills().put(player, kills);
    }

    public void addGuildDeath(final String tag, final UUID player, final int deaths) {
        this.find(tag).getGuild().getDeaths().put(player, deaths);
    }

    public void addGuildExpEarned(final String tag, final UUID player, final double expEarned) {
        this.find(tag).getGuild().getExpEarned().put(player, expEarned);
    }

    public void updateGuildPoints(final String tag, final int points) {
        this.find(tag).getGuild().setPoints(this.find(tag).getGuild().getPoints() + points);
    }

    public void updateGuildLvl(final String tag, final int lvl) {
        this.find(tag).getGuild().setLevel(this.find(tag).getGuild().getLevel() + lvl);
    }

    public void updateGuildExp(final String tag, final double exp) {
        this.find(tag).getGuild().setExp(this.find(tag).getGuild().getExp() + exp);
        if (this.getGuildExp(tag) >= this.getGuildNextLvlExp(tag)) {
            this.updateGuildLvl(tag, 1);
            this.setGuildExp(tag, 0);
            this.updateGuildBalance(tag, 1);
            rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&7Klan &6" + tag + " &7osiagnal &6" + this.getGuildLvl(tag) + " &7poziom!"));

        }
    }

    public void updateGuildBalance(final String tag, final int balance) {
        this.find(tag).getGuild().setBalance(this.find(tag).getGuild().getBalance() + balance);
    }

    public void updateGuildDodatkowyExp(final String tag, final double dodatkowyExp) {
        this.find(tag).getGuild().setDodatkowyExp(this.find(tag).getGuild().getDodatkowyExp() + dodatkowyExp);
    }

    public void updateGuildSredniDmg(final String tag, final double sredniDmg) {
        this.find(tag).getGuild().setSredniDmg(this.find(tag).getGuild().getSredniDmg() + sredniDmg);
    }

    public void updateGuildSredniDef(final String tag, final double sredniDef) {
        this.find(tag).getGuild().setSredniDef(this.find(tag).getGuild().getSredniDef() + sredniDef);
    }

    public void updateGuildSilnyNaLudzi(final String tag, final double silnyNaLudzi) {
        this.find(tag).getGuild().setSilnyNaLudzi(this.find(tag).getGuild().getSilnyNaLudzi() + silnyNaLudzi);
    }

    public void updateGuildDefNaLudzi(final String tag, final double defNaLudzi) {
        this.find(tag).getGuild().setDefNaLudzi(this.find(tag).getGuild().getDefNaLudzi() + defNaLudzi);
    }

    public void updateGuildKills(final String tag, final UUID player, final int kills) {
        this.find(tag).getGuild().getKills().replace(player, this.find(tag).getGuild().getKills().get(player) + kills);
    }

    public void updateGuildDeaths(final String tag, final UUID player, final int deaths) {
        this.find(tag).getGuild().getDeaths().replace(player, this.find(tag).getGuild().getDeaths().get(player) + deaths);
    }

    public void updateGuildExpEarned(final String tag, final UUID player, final double expEarned) {
        this.find(tag).getGuild().getExpEarned().replace(player, this.find(tag).getGuild().getExpEarned().get(player) + expEarned);
    }

    public void updateGuildLastOnline(final String tag, final UUID player, final Date lastOnline) {
        this.find(tag).getGuild().getLastOnline().replace(player, lastOnline);
    }

    public void putGuildLastOnline(final String tag, final UUID player, final Date lastOnline) {
        this.find(tag).getGuild().getLastOnline().put(player, lastOnline);
    }

    public String getLastSeenDateInString(final String tag, final UUID uuid) {
        return Utils.dateFormat.format(this.find(tag).getGuild().getLastOnline().get(uuid));
    }

    public boolean hasGuild(final UUID uuid) {
        for (final GuildObject guildObject : this.getGuilds()) {
            if (guildObject.getGuild().getMembers().contains(uuid)) {
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
        for (final GuildObject guildObject : this.getGuilds()) {
            if (guildInvites.get(uuid) == null) {
                guildInvites.computeIfAbsent(uuid, k -> new HashMap<>());
                return new ArrayList<>();
            }
            if (guildInvites.get(uuid).containsKey(guildObject.getTag())) {
                tags.add(guildObject.getTag());
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
        List<String> tags = new ArrayList<>();
        for (final GuildObject guildObject : this.getGuilds()) {
            tags.add(guildObject.getTag());
        }
        return tags;
    }

    public boolean getGuildPvPStatus(final String tag) {
        return this.find(tag).getGuild().isPvp();
    }

    public int getGuildKillsAll(final String tag) {
        int kills = 0;
        for (UUID uuid : this.find(tag).getGuild().getMembers()) {
            kills += this.find(tag).getGuild().getKills().get(uuid);
        }
        return kills;
    }

    public int getGuildDeathsAll(final String tag) {
        int deaths = 0;
        for (UUID uuid : this.find(tag).getGuild().getMembers()) {
            deaths += this.find(tag).getGuild().getDeaths().get(uuid);
        }
        return deaths;
    }

    public double getGuildNextLvlExp(final String tag) {
        return this.guildLvlMap.get(this.getGuildLvl(tag) + 1);
    }

    public GuildObject find(final String tag) {
        if (this.guildMap.containsKey(tag)) {
            return this.guildMap.get(tag);
        } else {
            return null;
        }
    }
    public void add(final GuildObject guildObject) {
        this.guildMap.put(guildObject.getTag(), guildObject);
    }

    public ImmutableSet<GuildObject> getGuilds() {
        return ImmutableSet.copyOf(this.guildMap.values());
    }
}
