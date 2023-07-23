package rpg.rpgcore.utils.globalitems.akcesoriumBlok;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Akce110_120 {
    I110_120_1("110-120-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&3&lSzkarlatna Tarcza").toItemStack()),
    I110_120_2("110-120-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&3&lSzkarlatny Pierscien").toItemStack()),
    I110_120_3("110-120-3", 11, new ItemBuilder(Material.WATCH).setName("&3&lSzkarlatny Diadem").toItemStack()),
    I110_120_4("110-120-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&3&lSzkarlatny Naszyjnik").toItemStack()),
    I110_120_5("110-120-5", 9, new ItemBuilder(Material.HOPPER_MINECART).setName("&3&lSzkarlatne Kolczyki").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Akce110_120(String name, double dropChance, ItemStack item) {
        this.name = name;
        this.dropChance = dropChance;
        this.item = item;
    }
    public String getName() { return name; }
    public double getDropChance() { return dropChance; }
    public ItemStack getItem() { return item; }
    public static Akce110_120 getByName(String name) {
        for (Akce110_120 item : Akce110_120.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce110_120 item : Akce110_120.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(1890, 4090),
                            ChanceHelper.getRandInt(20, 33), ChanceHelper.getRandInt(25, 27), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(25, 36),
                            ChanceHelper.getRandInt(30, 62), ChanceHelper.getRandInt(7, 12), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(35, 68),
                            ChanceHelper.getRandInt(37, 60), ChanceHelper.getRandInt(30, 45), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(20, 32),
                            ChanceHelper.getRandInt(30, 44), ChanceHelper.getRandInt(-110, -90), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatne Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(20, 37), ChanceHelper.getRandInt(24, 36), ChanceHelper.getRandInt(38, 115),
                            ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatny Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player,szczescie);
    }
}
