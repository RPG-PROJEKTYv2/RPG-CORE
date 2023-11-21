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

public enum Akce120_130 {
    I120_130_1("120-130-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&9&lStarozytna Tarcza").toItemStack(), 40, 78, 40, 65, 40, 60),
    I120_130_2("120-130-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&9&lStarozytny Pierscien").toItemStack(), 30, 40, 25, 40, 100, 125),
    I120_130_3("120-130-3", 11, new ItemBuilder(Material.WATCH).setName("&6&lStarozytny Diadem").toItemStack(), 30, 40, 40, 75, 8, 15),
    I120_130_4("120-130-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&6&lStarozytny Naszyjnik").toItemStack(), 3500, 6000, 20, 36, 20, 30),
    I120_130_5("120-130-5", 9, new ItemBuilder(Material.HOPPER_MINECART).setName("&9&lStarozytne Kolczyki").toItemStack(), 20, 35, 30, 50, -145, -120);
    private final String name;
    private final double dropChance;
    private final ItemStack item;
    @Getter
    private final int min1,max1,min2,max2,min3,max3;

    Akce120_130(String name, double dropChance, ItemStack item, int min1, int max1, int min2, int max2, int min3, int max3) {
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
    public static Akce120_130 getByName(String name) {
        for (Akce120_130 item : Akce120_130.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static Akce120_130 getByAkceName(final String name) {
        for (final Akce120_130 item : values()) {
            if (item.getItem().getItemMeta().getDisplayName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce120_130 item : Akce120_130.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(3500, 6000),
                            ChanceHelper.getRandInt(20, 36), ChanceHelper.getRandInt(20, 30), ChanceHelper.getRandInt(120, 130), "&9&lStarozytny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(30, 40),
                            ChanceHelper.getRandInt(40, 75), ChanceHelper.getRandInt(8, 15), ChanceHelper.getRandInt(120, 130), "&9&lStarozytny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(40, 78),
                            ChanceHelper.getRandInt(40, 65), ChanceHelper.getRandInt(40, 60), ChanceHelper.getRandInt(120, 130), "&9&lStarozytna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(20, 35),
                            ChanceHelper.getRandInt(30, 50), ChanceHelper.getRandInt(-145, -120), ChanceHelper.getRandInt(120, 130), "&9&lStarozytne Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(30, 40), ChanceHelper.getRandInt(25, 40), ChanceHelper.getRandInt(100, 125),
                            ChanceHelper.getRandInt(120, 130), "&9&lStarozytny Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player,szczescie);
    }
}
