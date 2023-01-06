package rpg.rpgcore.guilds;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.NameTagUtil;
import rpg.rpgcore.utils.Utils;

import java.util.*;
import java.util.List;
import java.util.stream.Stream;

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
        final Guild guild = this.find(tag).getGuild();
        if (guild == null) {
            player.sendMessage(Utils.format("&cKlan o podanym tagie nie istnieje!"));
            return;
        }
        player.sendMessage(Utils.format("&8&m-_-_-_-_-_-_-_-_-&8{&6" + tag + "&8}&8&m-_-_-_-_-_-_-_-_-"));
        player.sendMessage(Utils.format("&6Opis: &7" + guild.getDescription()));
        player.sendMessage(Utils.format("&6Zalozyciel: &7" + rpgcore.getUserManager().find(guild.getOwner()).getName()));
        if (guild.getCoOwner().isEmpty()) {
            player.sendMessage(Utils.format("&6Zastepca: &7Brak Zastepcy"));
        } else {
            player.sendMessage(Utils.format("&6Zastepca: &7" + rpgcore.getUserManager().find(UUID.fromString(guild.getCoOwner())).getName()));
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

        if (!this.getGuildCoOwner(tag).isEmpty() && this.getGuildCoOwner(tag).equals(uuid.toString())) {
            this.setGuildCoOwner(tag, "");
        }

        final Player player = Bukkit.getPlayer(uuid);
        rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&cGracz &6" + player.getName() + " &czostal wyrzucony z klanu &6" + tag));
        if (player.isOnline()) {
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataGuild(guildObject.getTag(), guildObject);
                NameTagUtil.setPlayerNameTag(player, "updatePrefix");
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
            } else if (this.getGuildCoOwner(tag) != null && this.getGuildCoOwner(tag).equals(uuid.toString())) {
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

            if (!this.getGuildCoOwner(tag).isEmpty() && (this.getGuildOwner(tag).equals(player.getUniqueId()) || this.getGuildCoOwner(tag).equals(player.getUniqueId().toString()))) {
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
        if (!this.getGuildCoOwner(tag).isEmpty()) {
            members.add(UUID.fromString(this.getGuildCoOwner(tag)));
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
            lore.add("&8- &c&l100 &4&lH&6&lS");
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
        final net.md_5.bungee.api.chat.TextComponent inviteMessage = new TextComponent(Utils.format(Utils.GUILDSPREFIX + "&aOtrzymano zaproszenie do klanu &6" + tag + " &8(kliknij, aby zaakceptowac)"));
        inviteMessage.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/klan dolacz " + tag));
        player.spigot().sendMessage(inviteMessage);
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
        } else if (this.getGuildCoOwner(tag) != null && this.getGuildCoOwner(tag).equals(sender.getUniqueId().toString())) {
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

    public void showGuildStats(final String tag, final Player player) {
        if (tag.equals("Brak Klanu")) {
            player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
            return;
        }
        final Inventory gui = Bukkit.createInventory(null, 45, Utils.format("&6&lStatystyki Klanu " + tag));
        for (int i = 0; i < 45; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 3).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 9).setName(" ").toItemStack());
            }
        }

        gui.setItem(10, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());
        gui.setItem(12, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());
        gui.setItem(14, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());
        gui.setItem(16, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());

        gui.setItem(18, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());
        gui.setItem(20, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());
        gui.setItem(22, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());
        gui.setItem(24, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());
        gui.setItem(26, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());

        gui.setItem(28, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());
        gui.setItem(30, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());
        gui.setItem(32, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());
        gui.setItem(34, new ItemBuilder(Material.IRON_FENCE).setName("").toItemStack());

        gui.setItem(19, getKillsItem(tag));
        gui.setItem(21, getDeathsItem(tag));
        gui.setItem(23, getExpEarnedItem(tag));
        gui.setItem(25, getTimeSpent(tag));


        player.openInventory(gui);
    }

    private ItemStack getKillsItem(final String tag) {
        final Multimap<UUID, Integer> killsMap = ArrayListMultimap.create();
        final Guild guild = this.find(tag).getGuild();
        for (UUID uuid : guild.getMembers()) {
            killsMap.put(uuid, guild.getKills().get(uuid));
        }
        Stream<Map.Entry<UUID, Integer>> sorted = killsMap.entries().stream().sorted(Map.Entry.comparingByValue());

        final List<String> topka = new ArrayList<>();
        sorted.limit(10).forEach(entry -> {
            final OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());
            topka.add(player.getName() + " &8(&7" + entry.getValue() + " zabojstw&8)");
        });

        if (topka.size() < 10) {
            for (int i = topka.size(); i < 10; i++) {
                topka.add("Brak");
            }
        }

        return new ItemBuilder(Material.DIAMOND_SWORD).setName("&6&lZabojstwa").setLore(Arrays.asList(
                "&7Zabojstwa: &6" + guild.getKills().values().stream().mapToInt(Integer::intValue).sum(),
                "&7Top 10 Graczy:",
                "&41. " + topka.get(0),
                "&62. " + topka.get(1),
                "&63. " + topka.get(2),
                "&e4. " + topka.get(3),
                "&e5. " + topka.get(4),
                "&76. " + topka.get(5),
                "&77. " + topka.get(6),
                "&78. " + topka.get(7),
                "&79. " + topka.get(8),
                "&710. " + topka.get(9)
        )).toItemStack().clone();
    }

    private ItemStack getDeathsItem(final String tag) {
        final Multimap<UUID, Integer> deathsMap = ArrayListMultimap.create();
        final Guild guild = this.find(tag).getGuild();
        for (UUID uuid : guild.getMembers()) {
            deathsMap.put(uuid, guild.getDeaths().get(uuid));
        }
        Stream<Map.Entry<UUID, Integer>> sorted = deathsMap.entries().stream().sorted(Map.Entry.comparingByValue());

        final List<String> topka = new ArrayList<>(10);
        sorted.limit(10).forEach(entry -> {
            final OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());
            topka.add(player.getName() + " &8(&7" + entry.getValue() + " smierci&8)");
        });

        if (topka.size() < 10) {
            for (int i = topka.size(); i < 10; i++) {
                topka.add("Brak");
            }
        }

        return new ItemBuilder(Material.SKULL_ITEM).setName("&6&lSmierci").setLore(Arrays.asList(
                "&7Smierci: &6" + guild.getDeaths().values().stream().mapToInt(Integer::intValue).sum(),
                "&7Top 10 Graczy:",
                "&41. " + topka.get(0),
                "&62. " + topka.get(1),
                "&63. " + topka.get(2),
                "&e4. " + topka.get(3),
                "&e5. " + topka.get(4),
                "&76. " + topka.get(5),
                "&77. " + topka.get(6),
                "&78. " + topka.get(7),
                "&79. " + topka.get(8),
                "&710. " + topka.get(9)
        )).toItemStack().clone();
    }

    private ItemStack getExpEarnedItem(final String tag) {
        final Multimap<UUID, Double> expMap = ArrayListMultimap.create();
        final Guild guild = this.find(tag).getGuild();
        for (UUID uuid : guild.getMembers()) {
            expMap.put(uuid, guild.getExpEarned().get(uuid));
        }
        Stream<Map.Entry<UUID, Double>> sorted = expMap.entries().stream().sorted(Map.Entry.comparingByValue());

        final List<String> topka = new ArrayList<>(10);
        sorted.limit(10).forEach(entry -> {
            final OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());
            topka.add(player.getName() + " &8(&7" + DoubleUtils.round(entry.getValue(), 2) + " exp&8)");
        });

        if (topka.size() < 10) {
            for (int i = topka.size(); i < 10; i++) {
                topka.add("Brak");
            }
        }

        return new ItemBuilder(Material.EXP_BOTTLE).setName("&6&lExp").setLore(Arrays.asList(
                "&7Exp: &6" + DoubleUtils.round(guild.getExpEarned().values().stream().mapToDouble(Double::doubleValue).sum(), 2),
                "&7Top 10 Graczy:",
                "&41. " + topka.get(0),
                "&62. " + topka.get(1),
                "&63. " + topka.get(2),
                "&e4. " + topka.get(3),
                "&e5. " + topka.get(4),
                "&76. " + topka.get(5),
                "&77. " + topka.get(6),
                "&78. " + topka.get(7),
                "&79. " + topka.get(8),
                "&710. " + topka.get(9)
        )).toItemStack().clone();
    }

    private ItemStack getTimeSpent(final String tag) {
        final Multimap<UUID, Long> timeMap = ArrayListMultimap.create();
        final Guild guild = this.find(tag).getGuild();
        for (UUID uuid : guild.getMembers()) {
            timeMap.put(uuid, rpgcore.getOsManager().find(uuid).getCzasProgress());
        }
        Stream<Map.Entry<UUID, Long>> sorted = timeMap.entries().stream().sorted(Map.Entry.comparingByValue());
        final List<String> topka = new ArrayList<>(10);
        sorted.limit(10).forEach(entry -> {
            final OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());
            topka.add(player.getName() + " &8(&7" + Utils.durationToString(entry.getValue(), true) + "&8)");
        });

        if (topka.size() < 10) {
            for (int i = topka.size(); i < 10; i++) {
                topka.add("Brak");
            }
        }

        return new ItemBuilder(Material.WATCH).setName("&6&lCzas Spedzony").setLore(Arrays.asList(
                "&7Czas Spedzony: &6" + Utils.durationToString(timeMap.entries().stream().mapToLong(Map.Entry::getValue).sum(), true),
                "&7Top 10 Graczy:",
                "&41. " + topka.get(0),
                "&62. " + topka.get(1),
                "&63. " + topka.get(2),
                "&e4. " + topka.get(3),
                "&e5. " + topka.get(4),
                "&76. " + topka.get(5),
                "&77. " + topka.get(6),
                "&78. " + topka.get(7),
                "&79. " + topka.get(8),
                "&710. " + topka.get(9)
        )).toItemStack().clone();
    }


    public UUID getGuildOwner(final String tag) {
        return this.find(tag).getOwner();
    }

    public String getGuildCoOwner(final String tag) {
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
        return DoubleUtils.round(this.find(tag).getGuild().getExp(), 2);
    }

    public int getGuildBalance(final String tag) {
        return this.find(tag).getGuild().getBalance();
    }

    public double getGuildDodatkowyExp(final String tag) {
        return DoubleUtils.round(this.find(tag).getGuild().getDodatkowyExp(), 2);
    }

    public double getGuildSredniDmg(final String tag) {
        return DoubleUtils.round(this.find(tag).getGuild().getSredniDmg(), 2);
    }

    public double getGuildSredniDef(final String tag) {
        return DoubleUtils.round(this.find(tag).getGuild().getSredniDef(), 2);
    }

    public double getGuildSilnyNaLudzi(final String tag) {
        return DoubleUtils.round(this.find(tag).getGuild().getSilnyNaLudzi(), 2);
    }

    public double getGuildDefNaLudzi(final String tag) {
        return DoubleUtils.round(this.find(tag).getGuild().getDefNaLudzi(), 2);
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

    public void setGuildCoOwner(final String tag, final String coOwner) {
        this.find(tag).getGuild().setCoOwner(coOwner);
    }

    public void setGuildPvPStatus(final String tag, final boolean pvp) {
        this.find(tag).getGuild().setPvp(pvp);
    }

    public void setGuildExp(final String tag, final double exp) {
        this.find(tag).getGuild().setExp(exp);
    }

    public void setGuildBalance(final String tag, final int balance) {
        this.find(tag).getGuild().setBalance(balance);
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
        return this.guildMap.getOrDefault(tag, null);
    }
    public void add(final GuildObject guildObject) {
        this.guildMap.put(guildObject.getTag(), guildObject);
    }

    public ImmutableSet<GuildObject> getGuilds() {
        return ImmutableSet.copyOf(this.guildMap.values());
    }
}
