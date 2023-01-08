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

public enum Map30_40 {
    I30_40_1("30-40-1", 10, ItemHelper.createArmor("&c&lPrzeklety Helm", Material.GOLD_HELMET, 23, 5)),
    I30_40_2("30-40-2", 10, ItemHelper.createArmor("&c&lPrzekleta Zbroja", Material.GOLD_CHESTPLATE, 21, 5)),
    I30_40_3("30-40-3", 10, ItemHelper.createArmor("&c&lPrzeklete Spodnie", Material.GOLD_LEGGINGS, 20, 5)),
    I30_40_4("30-40-4", 10, ItemHelper.createArmor("&c&lPrzeklete Trepy", Material.GOLD_BOOTS, 20, 5)),
    I30_40_5("30-40-5", 10, ItemHelper.createSword("&c&lPrzekleta Kosa", Material.GOLD_SWORD, 18, 8, true)),

    I30_40_6("30-40-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&c&lPrzeklety Naszyjnik").toItemStack()),
    I30_40_7("30-40-7", 10, new ItemBuilder(Material.WATCH).setName("&c&lPrzeklety Diadem").toItemStack()),
    I30_40_8("30-40-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&c&lPrzekleta Tarcza").toItemStack()),
    I30_40_9("30-40-9", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&c&lPrzeklety Pierscien").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map30_40(String name, double dropChance, ItemStack item) {
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

    public static Map30_40 getByName(String name) {
        for (Map30_40 item : Map30_40.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        for (Map30_40 item : Map30_40.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(15, 42),
                            ChanceHelper.getRandInt(8, 13), ChanceHelper.getRandInt(6, 12), ChanceHelper.getRandInt(30, 40), "&c&lPrzeklety Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(5, 14),
                            ChanceHelper.getRandInt(6, 18), ChanceHelper.getRandInt(2, 5), ChanceHelper.getRandInt(30, 40), "&c&lPrzeklety Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(14, 21),
                            ChanceHelper.getRandInt(20, 36), ChanceHelper.getRandInt(6, 9), ChanceHelper.getRandInt(30, 40), "&c&lPrzekleta Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(1, 11), ChanceHelper.getRandInt(3, 14), ChanceHelper.getRandInt(10, 35),
                            ChanceHelper.getRandInt(30, 40), "&c&lPrzeklety Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        player.sendMessage(Utils.format("&c&lNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
