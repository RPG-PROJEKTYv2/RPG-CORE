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
    I30_40_1("30-40-1", 10, ItemHelper.createArmor("&8Przeklety Helm", Material.GOLD_HELMET, 28, 1,true)),
    I30_40_2("30-40-2", 10, ItemHelper.createArmor("&8Przekleta Zbroja", Material.GOLD_CHESTPLATE, 28, 1,true)),
    I30_40_3("30-40-3", 10, ItemHelper.createArmor("&8Przeklete Spodnie", Material.GOLD_LEGGINGS, 28, 1,true)),
    I30_40_4("30-40-4", 10, ItemHelper.createArmor("&8Przeklete Buty", Material.GOLD_BOOTS, 28, 1,true)),
    I30_40_5("30-40-5", 10, ItemHelper.createArmor("&8Przekleta Kosa", Material.IRON_SWORD, 18, 8,true)),
    I30_40_6("30-40-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&8Przeklety Naszyjnik").toItemStack()),
    I30_40_7("30-40-7", 10, new ItemBuilder(Material.WATCH).setName("&8Przeklety Diadem").toItemStack()),
    I30_40_8("30-40-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&8Przekleta Tarcza").toItemStack()),

    I30_40_9("30-40-9", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&8Przeklety Pierscien").toItemStack());
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
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(15, 34),
                            ChanceHelper.getRandDouble(8, 13), ChanceHelper.getRandDouble(6, 9), ChanceHelper.getRandInt(30, 40), "&8Przeklety Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandDouble(4, 11),
                            ChanceHelper.getRandDouble(4, 10), ChanceHelper.getRandDouble(1, 2), ChanceHelper.getRandInt(30, 40), "&8Przeklety Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandDouble(6, 19),
                            ChanceHelper.getRandDouble(4, 14), ChanceHelper.getRandInt(2, 5), ChanceHelper.getRandInt(30, 40), "&8Przekleta Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandDouble(0.08, 0.2),
                            ChanceHelper.getRandDouble(3, 5), 1, ChanceHelper.getRandInt(30, 40), "&8Przeklety Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
