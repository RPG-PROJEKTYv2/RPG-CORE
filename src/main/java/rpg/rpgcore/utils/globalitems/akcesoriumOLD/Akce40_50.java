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

public enum Akce40_50 {
    I40_50_1("40-50-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&b&lPradawna Tarcza").toItemStack(), 21, 27, 15, 24, 7, 10),
    I40_50_2("40-50-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&b&lPradawny Pierscien").toItemStack(), 13, 14, 14, 17, 10, 45),
    I40_50_3("40-50-3", 11, new ItemBuilder(Material.WATCH).setName("&b&lPradawny Diadem").toItemStack(), 18, 20, 20, 25, 2, 5),
    I40_50_4("40-50-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&b&lPradawny Naszyjnik").toItemStack(), 40, 81, 10, 16, 8, 14);
    private final String name;
    private final double dropChance;
    private final ItemStack item;
    @Getter
    private final int min1,max1,min2,max2,min3,max3;

    Akce40_50(String name, double dropChance, ItemStack item, int min1, int max1, int min2, int max2, int min3, int max3) {
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
    public static Akce40_50 getByName(String name) {
        for (Akce40_50 item : Akce40_50.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static Akce40_50 getByAkceName(final String name) {
        for (final Akce40_50 akce : values()) {
            if (akce.getItem().getItemMeta().getDisplayName().equals(name)) return akce;
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce40_50 item : Akce40_50.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(40, 81),
                            ChanceHelper.getRandInt(10, 16), ChanceHelper.getRandInt(8, 14), ChanceHelper.getRandInt(40, 50), "&b&lPradawny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(18, 20),
                            ChanceHelper.getRandInt(20, 25), ChanceHelper.getRandInt(2, 5), ChanceHelper.getRandInt(40, 50), "&b&lPradawny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(21, 27),
                            ChanceHelper.getRandInt(15, 24), ChanceHelper.getRandInt(7, 10), ChanceHelper.getRandInt(40, 50), "&b&lPradawna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(6 ,10),
                            ChanceHelper.getRandInt(8, 12), ChanceHelper.getRandInt(-55, -40), ChanceHelper.getRandInt(40, 50), "&b&lPradawne Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(13, 14), ChanceHelper.getRandInt(14, 17), ChanceHelper.getRandInt(10, 45),
                            ChanceHelper.getRandInt(40, 50), "&b&lPradawny Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player,szczescie);
    }
}
