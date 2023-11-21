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

public enum Akce10_20 {
    I10_20_3("10-20-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&2&lZielona Tarcza").toItemStack(), 9, 16, 6, 11, 3, 6),
    I10_20_2("10-20-2", 12, new ItemBuilder(Material.WATCH).setName("&2&lZielony Diadem").toItemStack(), 9, 10, 11, 15, 1, 5),
    I10_20_4("10-20-3", 11, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&2&lZielony Pierscien").toItemStack(), 8, 10, 10, 12, 25, 35),
    I10_20_1("10-20-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&2&lZielony Naszyjnik").toItemStack(), 10, 17, 10, 17, 4, 7);
    private final String name;
    private final double dropChance;
    private final ItemStack item;
    @Getter
    private final int min1,max1,min2,max2,min3,max3;

    Akce10_20(String name, double dropChance, ItemStack item, int min1, int max1, int min2, int max2, int min3, int max3) {
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
    public static Akce10_20 getByName(String name) {
        for (Akce10_20 item : Akce10_20.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static Akce10_20 getByAkceName(final String name) {
        for (final Akce10_20 akce : values()) {
            if (akce.getItem().getItemMeta().getDisplayName().equals(name)) return akce;
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce10_20 item : Akce10_20.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(10, 17),
                            ChanceHelper.getRandInt(10, 17), ChanceHelper.getRandInt(4, 7), ChanceHelper.getRandInt(10, 20), "&2&lZielony Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(9, 10), ChanceHelper.getRandInt(11, 15), ChanceHelper.getRandInt(1, 5),
                            ChanceHelper.getRandInt(10, 20), "&2&lZielony Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(9, 16),
                            ChanceHelper.getRandInt(6, 11), ChanceHelper.getRandInt(3, 6), ChanceHelper.getRandInt(10, 20), "&2&lZielona Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(8, 10), ChanceHelper.getRandInt(10, 12), ChanceHelper.getRandInt(25, 35),
                            ChanceHelper.getRandInt(10, 20), "&2&lZielony Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player,szczescie);
    }
}
