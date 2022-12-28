package rpg.rpgcore.utils.globalitems.niesy;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Map40_50 {
    I40_50_1("40-50-1", 10, ItemHelper.createArmor("&8Pradawny Helm", Material.IRON_HELMET, 35, 2,true)),
    I40_50_2("40-50-2", 10, ItemHelper.createArmor("&8Pradawna Zbroja", Material.DIAMOND_CHESTPLATE, 35, 2,true)),
    I40_50_3("40-50-3", 10, ItemHelper.createArmor("&8Pradawne Spodnie", Material.IRON_LEGGINGS, 35, 2,true)),
    I40_50_4("40-50-4", 10, ItemHelper.createArmor("&8Pradawne Buty", Material.IRON_BOOTS, 35, 2,true)),
    I40_50_5("40-50-5", 10, ItemHelper.createArmor("&8Pradawny Miecz", Material.DIAMOND_SWORD, 30, 12,true)),
    I40_50_6("40-50-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&8Pradawny Naszyjnik").toItemStack()),
    I40_50_7("40-50-7", 10, new ItemBuilder(Material.WATCH).setName("&8Pradawny Diadem").toItemStack()),
    I40_50_8("40-50-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&8Pradawna Tarcza").toItemStack()),

    I40_50_9("40-50-9", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&8Pradawny Pierscien").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map40_50(String name, double dropChance, ItemStack item) {
        this.name = name;
        this.dropChance = dropChance;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public double getDropChance() {
        return dropChance;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public static Map40_50 getByName(String name) {
        for (Map40_50 item : Map40_50.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        for (Map40_50 item : Map40_50.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(19, 41),
                            ChanceHelper.getRandDouble(10, 16), ChanceHelper.getRandDouble(8, 17), ChanceHelper.getRandInt(40, 50), "&8Pradawny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandDouble(4, 12),
                            ChanceHelper.getRandDouble(4, 10), ChanceHelper.getRandDouble(1, 3), ChanceHelper.getRandInt(40, 50), "&8Pradawny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandDouble(9, 24),
                            ChanceHelper.getRandDouble(6, 17), ChanceHelper.getRandInt(4, 6), ChanceHelper.getRandInt(40, 50), "&8Pradawna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandDouble(0.1, 0.2),
                            ChanceHelper.getRandDouble(4, 6), 1, ChanceHelper.getRandInt(40, 50), "&8Pradawny Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
