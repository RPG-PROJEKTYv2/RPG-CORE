package rpg.rpgcore.chat;

import com.google.common.collect.ImmutableSet;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.user.User;
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
        final User user = rpgcore.getUserManager().find(player.getUniqueId());
        final String playerName = player.getName();
        String playerRank = user.getRankPlayerUser().getRankType().getPrefix();
        final String playerGuild = rpgcore.getGuildManager().getGuildTag(player.getUniqueId());

        if (!user.getRankUser().isHighStaff()) {
            message = Utils.removeColor(message);
        }

        if (user.getRankUser().isStaff()) {
            playerRank = user.getRankUser().getRankType().getPrefix();
        }

        if (playerGuild.equals("Brak Klanu")) {
            format = format.replace("<player-klan>", "");
        } else {
            format = format.replace("<player-klan>", Utils.format("&8[&e" + playerGuild + "&8]"));
        }

        return format.replace("<player-group>", Utils.format(playerRank)).replace("<player-lvl>", String.valueOf(playerLVL)).replace("<player-name>", playerName).replace("<message>", message);
    }


    public void formatujChatEQ(final Player player) {
        final Inventory eqQUI = this.createEQGUI();
        player.openInventory(eqQUI);
    }

    public Inventory createEQGUI() {
        final Inventory eqGUI = Bukkit.createInventory(null, 9, Utils.format("&4&lEQ GUI"));

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
