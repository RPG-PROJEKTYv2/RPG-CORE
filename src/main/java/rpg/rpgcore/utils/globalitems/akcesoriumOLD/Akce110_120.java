package rpg.rpgcore.utils.globalitems.akcesoriumOLD;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Akce110_120 {
    I110_120_1("110-120-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&3&lSzkarlatna Tarcza").toItemStack(), 40, 75, 37, 57, 25, 50),
    I110_120_2("110-120-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&3&lSzkarlatny Pierscien").toItemStack(), 28, 48, 29, 42, 72, 110),
    I110_120_3("110-120-3", 11, new ItemBuilder(Material.WATCH).setName("&3&lSzkarlatny Diadem").toItemStack(), 25, 46, 30, 65, 6, 12),
    I110_120_4("110-120-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&3&lSzkarlatny Naszyjnik").toItemStack(), 2100, 4300, 25, 45, 25, 38),
    I110_120_5("110-120-5", 9, new ItemBuilder(Material.HOPPER_MINECART).setName("&3&lSzkarlatne Kolczyki").toItemStack(), 25, 39, 25, 48, -130, -70);
    private final String name;
    private final double dropChance;
    private final ItemStack item;
    @Getter
    private final int min1,max1,min2,max2,min3,max3;

    Akce110_120(String name, double dropChance, ItemStack item, int min1, int max1, int min2, int max2, int min3, int max3) {
        this.name = name;
        this.dropChance = dropChance;
        this.item = item;
        this.min1 = min1;
        this.max1 = max1;
        this.min2 = min2;
        this.max2 = max2;
        this.min3 = min3;
        this.max3 = max3;
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

    public static Akce110_120 getByAkceName(final String name) {
        for (final Akce110_120 akce : values()) {
            if (akce.getItem().getItemMeta().getDisplayName().equals(name)) return akce;
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
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(2100, 4300),
                            ChanceHelper.getRandInt(25, 45), ChanceHelper.getRandInt(25, 38), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(25, 46),
                            ChanceHelper.getRandInt(30, 65), ChanceHelper.getRandInt(6, 12), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(40, 75),
                            ChanceHelper.getRandInt(37, 57), ChanceHelper.getRandInt(25, 50), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(25, 39),
                            ChanceHelper.getRandInt(25, 48), ChanceHelper.getRandInt(-130, -70), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatne Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(28, 48), ChanceHelper.getRandInt(29, 42), ChanceHelper.getRandInt(72, 110),
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
