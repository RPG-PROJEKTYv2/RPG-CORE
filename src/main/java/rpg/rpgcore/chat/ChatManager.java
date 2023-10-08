package rpg.rpgcore.chat;

import com.google.common.collect.ImmutableSet;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.guilds.objects.Guild;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class ChatManager {

    private final RPGCORE rpgcore;
    private final Map<UUID, ChatUser> chatUsers;
    private final HashMap<UUID, String> messageWithEQ = new HashMap<>();
    private RankType rankReqForChat = RankType.getByName("GRACZ");
    private int lvlReqForChat = 1;
    private boolean chatEnabled = true;

    public ChatManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.chatUsers = rpgcore.getMongoManager().loadAllChatUsers();
    }

    public String getMessageWithEQ(final UUID uuid) {
        return this.messageWithEQ.get(uuid);
    }

    public void updateMessageWithEQ(final UUID uuid, final String message) {
        if (this.messageWithEQ.containsKey(uuid)) {
            this.messageWithEQ.replace(uuid, message);
            return;
        }
        this.messageWithEQ.put(uuid, message);
    }

    public TextComponent formmatPrzed(final Player player) {
        final TextComponent main = new TextComponent("");
        if (rpgcore.getGuildManager().hasGuild(player.getUniqueId())) {
            final String tag = rpgcore.getGuildManager().getGuildTag(player.getUniqueId());
            final Guild guild = rpgcore.getGuildManager().find(tag).getGuild();
            final TextComponent guildM = new TextComponent(Utils.format("&8[&e" + tag + "&8] "));
            guildM.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                    new TextComponent(Utils.format("&7Tag gildii: &6" + tag +
                            "\n&7Opis gildi: &6" + guild.getDescription() +
                            "\n&7Lider Gildii: &6" + (Bukkit.getPlayer(guild.getOwner()) != null ? "&a" + Bukkit.getPlayer(guild.getOwner()).getName() : "&c" + Bukkit.getOfflinePlayer(guild.getOwner()).getName()) +
                            "\n&7Ilosc czlonkow: &6" + guild.getMembers().size() + "&7/&615" +
                            "\n&7Poziom: &6" + guild.getLevel() +
                            (guild.getLevel() >= 50 ? "\n&7Doswiadczenie: &4&lMAX&7/&4&lMAX" :
            "\n&7Doswiadczenie: &6" + String.format("%.2f", Utils.convertDoublesToPercentage(guild.getExp(), rpgcore.getGuildManager().getGuildNextLvlExp(tag))) + "%")))}));
            guildM.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/klan info " + tag));
            main.addExtra(guildM);
        }
        final User user = rpgcore.getUserManager().find(player.getUniqueId());
        final TextComponent lvl = (user.getLvl() == 130 ? new TextComponent(Utils.format("&8[&bLvl. &4&lMAX&8] ")) : new TextComponent(Utils.format("&8[&bLvl. &f" + user.getLvl() + "&8] ")));
        lvl.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                new TextComponent(Utils.format("&7Poziom: &6" + user.getLvl() +
                        "\n&7Postep do nastepnego poziomu: &6" + String.format("%.2f", Utils.convertDoublesToPercentage(user.getExp(), rpgcore.getLvlManager().getExpForLvl(user.getLvl()))) + "%"))}));
        main.addExtra(lvl);

        TextComponent rank;
        if (user.getRankUser().isStaff() && user.isAdminCodeLogin()) {
            rank = new TextComponent(Utils.format(user.getRankUser().getRankType().getPrefix() + player.getName() + "&f: "));
        } else {
            rank = new TextComponent(Utils.format(user.getRankPlayerUser().getRankType().getPrefix() + player.getName() + "&f: "));
        }
        rank.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(Utils.format("&7Kliknij, zeby napisac wiadomosc"))}));
        rank.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + player.getName() + " "));
        main.addExtra(rank);
        return main;
    }


    public void formatujChatEQ(final Player player) {
        final Inventory eqQUI = this.createEQGUI();
        player.openInventory(eqQUI);
    }

    public Inventory createEQGUI() {
        final Inventory eqGUI = Bukkit.createInventory(null, 9, Utils.format("&4&lGui Interakcji z Chatem"));

        ItemStack item;
        ItemMeta itemMeta;
        List<String> itemLore = new ArrayList<>();

        //          POKAZ ITEM          \\
        item = new ItemStack(Material.DIAMOND_SWORD);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6&lPokaz Item"));
        itemLore.add(Utils.format("&7Pokaz swoj item na chacie"));
        itemMeta.setLore(itemLore);
        itemLore.clear();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(itemMeta);
        eqGUI.addItem(item);

        //          POKAZ SET          \\
        item = new ItemStack(Material.DIAMOND_CHESTPLATE);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6&lPokaz Seta"));
        itemLore.add(Utils.format("&7Pokaz seta na chacie"));
        itemMeta.setLore(itemLore);
        itemLore.clear();
        item.setItemMeta(itemMeta);
        eqGUI.addItem(item);

        //          POKAZ BAO          \\
        item = new ItemStack(Material.ENCHANTMENT_TABLE);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6&lPokaz BAO"));
        itemLore.add(Utils.format("&7Pokaz na chacie swoje bonusy stolu magii"));
        itemMeta.setLore(itemLore);
        itemLore.clear();
        item.setItemMeta(itemMeta);
        eqGUI.addItem(item);

        //          POKAZ DOSWIADCZENIE          \\
        item = new ItemStack(Material.MONSTER_EGG);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6&lPokaz EXP"));
        itemLore.add(Utils.format("&7Pokaz swoj postep do nastepnego poziomu na chacie"));
        itemMeta.setLore(itemLore);
        itemLore.clear();
        item.setItemMeta(itemMeta);
        eqGUI.addItem(item);

        //          POKAZ KASE          \\
        item = new ItemStack(Material.BOOK);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6&lPokaz KASE"));
        itemLore.add(Utils.format("&7Pokaz swoj stan konta na chacie"));
        itemMeta.setLore(itemLore);
        itemLore.clear();
        item.setItemMeta(itemMeta);
        eqGUI.addItem(item);

        //          POKAZ SPEDZONY CZAS          \\
        item = new ItemStack(Material.WATCH);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6&lPokaz SPEDZONY CZAS"));
        itemLore.add(Utils.format("&7Pokaz swoj czas spedzony online na serwerze"));
        itemMeta.setLore(itemLore);
        itemLore.clear();
        item.setItemMeta(itemMeta);
        eqGUI.addItem(item);

        //          POKAZ KRYTYK          \\
        eqGUI.addItem(new ItemBuilder(Material.IRON_SWORD).setName("&6&lPokaz Krytyk").setLore(Arrays.asList(
                "&7Pokaz swoj cios krytyczny na chacie"
        )).hideFlag().toItemStack());

        //          POKAZ PRZEKLETA MOC          \\
        eqGUI.addItem(new ItemBuilder(Material.NETHER_BRICK).setName("&6&lPokaz Przekleta Moc").setLore(Arrays.asList(
                "&7Pokaz swoja przekleta moc na chacie"
        )).hideFlag().toItemStack());

        return eqGUI;
    }

    public void openChatPanel(Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, 36, Utils.format("&6&lChat Panel"));
        for (int i = 0; i < 36; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 9).setName(" ").toItemStack());
        }

        if (user.isItemDropEnabled()) {
            gui.setItem(10, new ItemBuilder(Material.INK_SACK, 1, (short)14).setName("&cWiadomosc o dropie z mobow").setLore(Arrays.asList(" ", "&8* &9Status: &a&LWLACZONE")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(10, new ItemBuilder(Material.INK_SACK, 1, (short)14).setName("&cWiadomosc o dropie z mobow").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
        }
        if (user.isChestDropEnabled()) {
            gui.setItem(11, new ItemBuilder(Material.CHEST).setName("&cWiadomosc o dropek ze skrzynek").setLore(Arrays.asList(" ", "&8* &9Status: &a&LWLACZONE")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(11, new ItemBuilder(Material.TRAPPED_CHEST).setName("&cWiadomosc o dropie ze skrzynek").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
        }
        if (user.isNiesDropEnabled()) {
            gui.setItem(12, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&cWiadomosc o &bNiesamowitych Przedmiotach").setLore(Arrays.asList(" ", "&8* &9Status: &a&LWLACZONE")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(12, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&cWiadomosc o &bNiesamowitych Przedmiotach").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
        }
        if (user.isPingsEnabled()) {
            gui.setItem(13, new ItemBuilder(Material.ANVIL).setName("&cWiadomosc o &ezaczepkach").setLore(Arrays.asList(" ", "&8* &9Status: &a&LWLACZONE")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(13, new ItemBuilder(Material.ANVIL).setName("&cWiadomosc o &ezaczepkach").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
        }
        if (user.isMsgEnabled()) {
            gui.setItem(14, new ItemBuilder(Material.BOOK).setName("&cPrywatne wiadomosci").setLore(Arrays.asList(" ", "&8* &9Status: &a&LWLACZONE")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(14, new ItemBuilder(Material.BOOK).setName("&cPrywatne wiadomosci").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
        }
        if (user.isJoinMessageEnabled()) {
            gui.setItem(15, new ItemBuilder(Material.PAPER).setName("&cWiadomosc dolaczenia na serwer").setLore(Arrays.asList(" ", "&8* &9Status: &a&LWLACZONE")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(15, new ItemBuilder(Material.PAPER).setName("&cWiadomosc dolaczenia na serwer").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
        }
        if (user.isQuitMessageEnabled()) {
            gui.setItem(16, new ItemBuilder(Material.PAPER).setName("&cWiadomosc wyjscia z serwera").setLore(Arrays.asList(" ", "&8* &9Status: &a&LWLACZONE")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(16, new ItemBuilder(Material.PAPER).setName("&cWiadomosc wyjscia z serwera").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
        }

        if (user.isDmgHologramsVisable()) {
            gui.setItem(19, new ItemBuilder(Material.ARMOR_STAND).setName("&cPokazywanie zadawanych obrazen").setLore(Arrays.asList(" ", "&8* &9Status: &a&LWSZYSCY")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(19, new ItemBuilder(Material.ARMOR_STAND).setName("&cPokazywanie zadawanych obrazen").setLore(Arrays.asList(" ", "&8* &9Status: &c&lGRACZ")).toItemStack().clone());
        }
        if (user.isBoss1_10()) {
            gui.setItem(20, new ItemBuilder(Material.FIREBALL).setName("&cInformacje o Bossie 1-10").setLore(Arrays.asList(" ", "&8* &9Status: &a&lWLACZONE")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(20, new ItemBuilder(Material.FIREBALL).setName("&cInformacje o Bossie  1-10").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
        }
        if (user.isBoss10_20()) {
            gui.setItem(21, new ItemBuilder(Material.FIREBALL).setName("&cInformacje o Bossie 10-20").setLore(Arrays.asList(" ", "&8* &9Status: &a&lWLACZONE")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(21, new ItemBuilder(Material.FIREBALL).setName("&cInformacje o Bossie 10-20").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
        }
        if (user.isBoss20_30()) {
            gui.setItem(22, new ItemBuilder(Material.FIREBALL).setName("&cInformacje o Bossie 20-30").setLore(Arrays.asList(" ", "&8* &9Status: &a&lWLACZONE")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(22, new ItemBuilder(Material.FIREBALL).setName("&cInformacje o Bossie 20-30").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
        }
        if (user.isBoss30_40()) {
            gui.setItem(23, new ItemBuilder(Material.FIREBALL).setName("&cInformacje o Bossie 30-40").setLore(Arrays.asList(" ", "&8* &9Status: &a&lWLACZONE")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(23, new ItemBuilder(Material.FIREBALL).setName("&cInformacje  o Bossie 30-40").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
        }
        if (user.isBoss40_50()) {
            gui.setItem(24, new ItemBuilder(Material.FIREBALL).setName("&cInformacje o Bossie 40-50").setLore(Arrays.asList(" ", "&8* &9Status: &a&lWLACZONE")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(24, new ItemBuilder(Material.FIREBALL).setName("&cInformacje o Bossie 40-50").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
        }

        final User mainUser = rpgcore.getUserManager().find(player.getUniqueId());

        if (mainUser.getRankUser().isHighStaff() && mainUser.isAdminCodeLogin()) {
            if (user.isDatabaseMessageEnabled()) {
                gui.setItem(35, new ItemBuilder(Material.BOOK).setName("&cWiadomosc z bazy danych").setLore(Arrays.asList(" ", "&8* &9Status: &a&LWLACZONE")).addGlowing().toItemStack().clone());
            } else {
                gui.setItem(35, new ItemBuilder(Material.BOOK).setName("&cWiadomosc z bazy danych").setLore(Arrays.asList(" ", "&8* &9Status: &c&lWYLACZONE")).toItemStack().clone());
            }
        }


        player.openInventory(gui);
    }

    public void pingPlayer(final Player player, final String message) {
        final String[] args = message.split(" ");
        for (String s : args) {
            if (s.contains("@")) {
                final String nick = s.replace("@", "");
                if (nick.isEmpty()) {
                    return;
                }
                final Player p = Bukkit.getPlayerExact(nick);
                if (p == null) {
                    return;
                }
                final ChatUser atUser = rpgcore.getChatManager().find(p.getUniqueId());
                if (!atUser.isPingsEnabled()) {
                    return;
                }
                rpgcore.getNmsManager().sendTitleAndSubTitle(p, rpgcore.getNmsManager().makeTitle("&cHej " + p.getName() + "!", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&3" + player.getName() + " &7zaczepia Cie!", 5, 20, 5));
                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 100, 100);
            }
        }
    }

    public TextComponent getPlayerArmor(final Player player) {
        final TextComponent helm = new TextComponent(Utils.format("&8[&bHelm&8]"));
        helm.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(CraftItemStack.asNMSCopy(player.getInventory().getHelmet()).save(new NBTTagCompound()).toString()).create()));
        final TextComponent zbroja = new TextComponent(Utils.format("&8[&cZbroja&8]"));
        zbroja.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(CraftItemStack.asNMSCopy(player.getInventory().getChestplate()).save(new NBTTagCompound()).toString()).create()));
        final TextComponent spodnie = new TextComponent(Utils.format("&8[&aSpodnie&8]"));
        spodnie.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(CraftItemStack.asNMSCopy(player.getInventory().getLeggings()).save(new NBTTagCompound()).toString()).create()));
        final TextComponent buty = new TextComponent(Utils.format("&8[&6Buty&8]"));
        buty.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(CraftItemStack.asNMSCopy(player.getInventory().getBoots()).save(new NBTTagCompound()).toString()).create()));
        helm.addExtra(" ");
        helm.addExtra(zbroja);
        helm.addExtra(" ");
        helm.addExtra(spodnie);
        helm.addExtra(" ");
        helm.addExtra(buty);
        System.out.println(helm.toPlainText());
        return helm;
    }

    public RankType getRankReqForChat() {
        return this.rankReqForChat;
    }

    public void setRankReqForChat(final RankType rankReqForChat) {
        this.rankReqForChat = rankReqForChat;
    }

    public void resetRankReqForChat() {
        this.rankReqForChat = RankType.getByName("GRACZ");
    }

    public int getLvlReqForChat() {
        return lvlReqForChat;
    }

    public void setLvlReqForChat(final int lvlReqForChat) {
        this.lvlReqForChat = lvlReqForChat;
    }

    public void resetLvlReqForChat() {
        this.lvlReqForChat = 1;
    }

    public boolean isChatEnabled() {
        return this.chatEnabled;
    }

    public void setChatEnabled(final boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
    }

    public void add(final ChatUser chatUser) {
        this.chatUsers.put(chatUser.getUuid(), chatUser);
    }

    public ChatUser find(final UUID uuid) {
        chatUsers.computeIfAbsent(uuid, k -> new ChatUser(uuid));
        return this.chatUsers.get(uuid);
    }

    public void set(final UUID uuid, final ChatUser chatUser) {
        this.chatUsers.replace(uuid, chatUser);
    }

    public ImmutableSet<ChatUser> getChatUsersObjects() {
        return ImmutableSet.copyOf(this.chatUsers.values());
    }
}
