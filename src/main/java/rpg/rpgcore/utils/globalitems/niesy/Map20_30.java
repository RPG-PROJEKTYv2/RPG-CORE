package rpg.rpgcore.utils.globalitems.niesy;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Map20_30 {
    I20_30_1("20-30-1", 10, ItemHelper.createArmor("&8Tropikalny Helm", Material.GOLD_HELMET, 21, 0,true)),
    I20_30_2("20-30-2", 10, ItemHelper.createArmor("&8Tropikalna Zbroja", Material.CHAINMAIL_CHESTPLATE, 21, 0,true)),
    I20_30_3("20-30-3", 10, ItemHelper.createArmor("&8Tropikalne Spodnie", Material.GOLD_LEGGINGS, 21, 0,true)),
    I20_30_4("20-30-4", 10, ItemHelper.createArmor("&8Tropikalne Buty", Material.GOLD_BOOTS, 21, 0,true)),
    I20_30_5("20-30-5", 10, ItemHelper.createSword("&8Tropikalna Maczeta", Material.STONE_SWORD, 12, 5,true)),
    I20_30_6("20-30-6", 10, AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(8, 19),
            ChanceHelper.getRandDouble(6, 9), ChanceHelper.getRandDouble(4, 6), ChanceHelper.getRandInt(20, 30), "&8Tropikalny Naszyjnik")),
    I20_30_7("20-30-7", 10, AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandDouble(1, 7),
            ChanceHelper.getRandDouble(1, 8), 1, ChanceHelper.getRandInt(20, 30), "&8Tropikalny Diadem")),
    I20_30_8("20-30-8", 10, AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandDouble(4, 14),
            ChanceHelper.getRandDouble(3, 10), ChanceHelper.getRandInt(2, 4), ChanceHelper.getRandInt(20, 30), "&8Tropikalna Tarcza")),

    I20_30_9("20-30-9", 10, AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandDouble(0.05, 0.1),
            ChanceHelper.getRandDouble(3, 4), 1, ChanceHelper.getRandInt(1, 10), "&8Tropikalny Pierscien")),

    I99("null", 0, null);
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map20_30(String name, double dropChance, ItemStack item) {
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

    public static Map20_30 getByName(String name) {
        for (Map20_30 item : Map20_30.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return I99;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        for (Map20_30 item : Map20_30.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
