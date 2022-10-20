package rpg.rpgcore.chat;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
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

    public String formatujChat(final Player player, String format, String message) {
        final int playerLVL = rpgcore.getUserManager().find(player.getUniqueId()).getLvl();
        String lvlInString = String.valueOf(playerLVL);
        final User user = rpgcore.getUserManager().find(player.getUniqueId());
        final String playerName = player.getName();
        String playerRank = user.getRankPlayerUser().getRankType().getPrefix();
        final String playerGuild = rpgcore.getGuildManager().getGuildTag(player.getUniqueId());

        if (playerLVL == Utils.MAXLVL) {
            lvlInString = "&4&lMAX";
        }

        if (!user.getRankUser().isHighStaff()) {
            message = Utils.removeColor(message);
        }

        if (user.getRankUser().isStaff()) {
            playerRank = user.getRankUser().getRankType().getPrefix();
        }

        if (playerGuild.equals("Brak Klanu")) {
            format = format.replace("<player-klan>", "");
        } else {
            format = format.replace("<player-klan>", Utils.format("&8[&e" + playerGuild + "&8] "));
        }

        return format.replace("<player-group>", Utils.format(playerRank)).replace("<player-lvl>", Utils.format(lvlInString)).replace("<player-name>", playerName).replace("<message>", Utils.format(message));
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

        return eqGUI;
    }

    public void openChatPanel(Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lChat Panel"));

        if (user.isItemDropEnabled()) {
            gui.setItem(2, new ItemBuilder(Material.STICK).setName("&a&lWiadomosci o dropie").setLore(Arrays.asList("&a&lWlaczono!")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(2, new ItemBuilder(Material.STICK).setName("&c&lWiadomosci o dropie").setLore(Arrays.asList("&c&lWylaczono!")).toItemStack().clone());
        }
        if (user.isPingsEnabled()) {
            gui.setItem(3, new ItemBuilder(Material.ANVIL).setName("&a&lWiadomosci o zaczepkach").setLore(Arrays.asList("&a&lWlaczono!")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(3, new ItemBuilder(Material.ANVIL).setName("&c&lWiadomosci o zaczepkach").setLore(Arrays.asList("&c&lWylaczono!")).toItemStack().clone());
        }
        if (user.isNiesDropEnabled()) {
            gui.setItem(4, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&a&lWiadomosci o Niesamowitych Przedmiotach").setLore(Arrays.asList("&a&lWlaczono!")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(4, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&c&lWiadomosci o Niesamowitych Przedmiotach").setLore(Arrays.asList("&c&lWylaczono!")).toItemStack().clone());
        }
        if (user.isChestDropEnabled()) {
            gui.setItem(5, new ItemBuilder(Material.CHEST).setName("&a&lWiadomosci o dropie ze skrzynek").setLore(Arrays.asList("&a&lWlaczono!")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(5, new ItemBuilder(Material.TRAPPED_CHEST).setName("&c&lWiadomosci o dropie ze skrzynek").setLore(Arrays.asList("&c&lWylaczono!")).toItemStack().clone());
        }
        gui.setItem(6, new ItemBuilder(Material.BARRIER).setName("&c&lCos tu kiedys jeszcze bedzie").addGlowing().toItemStack().clone());


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
                final Player p = Bukkit.getPlayer(nick);
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

    public String getEnchantemntLvlForEQ(final Player player) {
        return " &8[&3&lP: &b" + Utils.getProtectionLevel(player.getInventory().getHelmet()) +
                "&b/" + Utils.getProtectionLevel(player.getInventory().getChestplate()) +
                "&b/" + Utils.getProtectionLevel(player.getInventory().getLeggings()) +
                "&b/" + Utils.getProtectionLevel(player.getInventory().getBoots()) +
                " &3&lT: &b " + Utils.getThornsLevel(player.getInventory().getHelmet()) +
                "&b/" + Utils.getThornsLevel(player.getInventory().getChestplate()) +
                "&b/" + Utils.getThornsLevel(player.getInventory().getLeggings()) +
                "&b/" + Utils.getThornsLevel(player.getInventory().getBoots()) +
                "&8]&7";
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

    public ImmutableSet<ChatUser> getChatUsersObjects() {
        return ImmutableSet.copyOf(this.chatUsers.values());
    }
}
