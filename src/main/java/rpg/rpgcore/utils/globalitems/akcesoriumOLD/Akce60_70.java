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

public enum Akce60_70 {
    I60_70_1("60-70-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&4&lOgnista Tarcza").toItemStack(), 26, 37, 22, 31, 12, 16),
    I60_70_2("60-70-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&4&lOgnisty Pierscien").toItemStack(), 15, 22, 15, 23, 20, 80),
    I60_70_3("60-70-3", 11, new ItemBuilder(Material.WATCH).setName("&4&lOgnisty Diadem").toItemStack(), 20, 23, 20, 31, 3, 7),
    I60_70_4("60-70-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&4&lOgnisty Naszyjnik").toItemStack(), 145, 200, 16, 22, 12, 18),
    I60_70_5("60-70-5", 9, new ItemBuilder(Material.HOPPER_MINECART).setName("&4&lOgniste Kolczyki").toItemStack(), 14, 17, 18, 23, -65, -50);
    private final String name;
    private final double dropChance;
    private final ItemStack item;
    @Getter
    private final int min1,max1,min2,max2,min3,max3;

    Akce60_70(String name, double dropChance, ItemStack item, int min1, int max1, int min2, int max2, int min3, int max3) {
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

    public String getName() {
        return name;
    }

    public double getDropChance() {
        return dropChance;
    }

    public ItemStack getItem() {
        return item;
    }

    public static Akce60_70 getByName(String name) {
        for (Akce60_70 item : Akce60_70.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static Akce60_70 getByAkceName(final String name) {
        for (final Akce60_70 akce : values()) {
            if (akce.getItem().getItemMeta().getDisplayName().equals(name)) return akce;
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce60_70 item : Akce60_70.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(145, 200),
                            ChanceHelper.getRandInt(16, 22), ChanceHelper.getRandInt(12, 18), ChanceHelper.getRandInt(60, 70), "&4&lOgnisty Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(20, 23),
                            ChanceHelper.getRandInt(20, 31), ChanceHelper.getRandInt(3, 7), ChanceHelper.getRandInt(60, 70), "&4&lOgnisty Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(26, 37),
                            ChanceHelper.getRandInt(22, 31), ChanceHelper.getRandInt(12, 16), ChanceHelper.getRandInt(60, 70), "&4&lOgnista Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(14, 17),
                            ChanceHelper.getRandInt(18, 23), ChanceHelper.getRandInt(-65, -50), ChanceHelper.getRandInt(60, 70), "&4&lOgniste Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(15, 22), ChanceHelper.getRandInt(15, 23), ChanceHelper.getRandInt(20, 80),
                            ChanceHelper.getRandInt(60, 70), "&4&lOgnisty Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player, szczescie);
    }
}