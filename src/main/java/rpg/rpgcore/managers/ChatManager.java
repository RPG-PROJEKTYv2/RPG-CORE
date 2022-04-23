package rpg.rpgcore.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class ChatManager implements Listener {

    private final RPGCORE rpgcore;
    private final HashMap<UUID, String> messageWithEQ = new HashMap<>();

    public ChatManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
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

    public String formatujChat(final Player player, final String format, String message) {
        final int playerLVL = rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId());
        String playerName = player.getName();
        String playerRank = rpgcore.getPlayerManager().getPlayerGroup(player);

        switch (playerRank) {
            case "H@":
                playerName = Utils.format("&c" + playerName);
                break;
            case "Admin":
                playerName = Utils.format("&c" + playerName);
                break;
            case "GM":
                playerName = Utils.format("&a" + playerName);
                break;
            case "Mod":
                message = Utils.removeColor(message);
                playerName = Utils.format("&a" + playerName);
                break;
            case "KidMod":
                message = Utils.removeColor(message);
                playerName = Utils.format("&a" + playerName);
                break;
            case "Helper":
                message = Utils.removeColor(message);
                playerName = Utils.format("&b" + playerName);
                break;
            case "JuniorHelper":
                message = Utils.removeColor(message);
                playerName = Utils.format("&b" + playerName);
                break;
            default:
                message = Utils.removeColor(message);
                playerName = Utils.format("&7" + playerName);
                break;
        }
        if (playerRank.equals("Gracz")) {
            return PlaceholderAPI.setPlaceholders(player, format.replace("<player-lvl>", String.valueOf(playerLVL)).replace("<player-group>", "").replace("<player-name>", playerName).replace("<message>", message));
        }
        return PlaceholderAPI.setPlaceholders(player, format.replace("<player-lvl>", String.valueOf(playerLVL)).replace("<player-group>", " %uperms_prefixes%").replace("<player-name>", playerName).replace("<message>", message));
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
        return " &8[&3&lP: &b" + player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) +
                "&b/" + player.getInventory().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) +
                "&b/" + player.getInventory().getLeggings().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) +
                "&b/" + player.getInventory().getBoots().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) +
                " &3&lT: &b " + player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS) +
                "&b/" + player.getInventory().getChestplate().getEnchantmentLevel(Enchantment.THORNS) +
                "&b/" + player.getInventory().getLeggings().getEnchantmentLevel(Enchantment.THORNS) +
                "&b/" + player.getInventory().getBoots().getEnchantmentLevel(Enchantment.THORNS) +
                "&8]&7";
    }
}
