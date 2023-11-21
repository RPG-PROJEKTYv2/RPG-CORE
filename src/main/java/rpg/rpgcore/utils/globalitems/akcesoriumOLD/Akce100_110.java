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

public enum Akce100_110 {
    I100_110_1("100-110-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&b&lMityczna Tarcza").toItemStack(), 30, 40, 25, 35, 10, 15),
    I100_110_2("100-110-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&b&lMityczny Pierscien").toItemStack(), 20, 25, 20, 30, 50, 90),
    I100_110_3("100-110-3", 11, new ItemBuilder(Material.WATCH).setName("&b&lMityczny Diadem").toItemStack(), 25, 30, 25, 35, 3, 7),
    I100_110_4("100-110-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&b&lMityczny Naszyjnik").toItemStack(), 60, 100, 20, 30, 10, 20),
    I100_110_5("100-110-5", 9, new ItemBuilder(Material.HOPPER_MINECART).setName("&b&lMityczne Kolczyki").toItemStack(), 20, 30, 20, 30, -77, -60);
    private final String name;
    private final double dropChance;
    private final ItemStack item;
    @Getter
    private final int min1,max1,min2,max2,min3,max3;

    Akce100_110(String name, double dropChance, ItemStack item, int min1, int max1, int min2, int max2, int min3, int max3) {
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
    public static Akce100_110 getByName(String name) {
        for (Akce100_110 item : Akce100_110.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static Akce100_110 getByAkceName(final String name) {
        for (final Akce100_110 akce : values()) {
            if (akce.getItem().getItemMeta().getDisplayName().equals(name)) return akce;
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce100_110 item : Akce100_110.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(1600, 3750),
                            ChanceHelper.getRandInt(20, 35), ChanceHelper.getRandInt(20, 30), ChanceHelper.getRandInt(100, 110), "&b&lMityczny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(25, 38),
                            ChanceHelper.getRandInt(30, 60), ChanceHelper.getRandInt(6, 12), ChanceHelper.getRandInt(100, 110), "&b&lMityczny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(30, 65),
                            ChanceHelper.getRandInt(37, 57), ChanceHelper.getRandInt(25, 40), ChanceHelper.getRandInt(100, 110), "&b&lMityczna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(19, 35),
                            ChanceHelper.getRandInt(25, 45), ChanceHelper.getRandInt(-110, -70), ChanceHelper.getRandInt(100, 110), "&b&lMityczne Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(20, 38), ChanceHelper.getRandInt(20, 35), ChanceHelper.getRandInt(63, 105),
                            ChanceHelper.getRandInt(100, 110), "&b&lMityczny Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player,szczescie);
    }
}
