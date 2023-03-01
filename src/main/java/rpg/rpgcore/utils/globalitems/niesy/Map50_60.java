package rpg.rpgcore.utils.globalitems.niesy;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaD.helpers.AkcesoriaDodatHelper;
import rpg.rpgcore.dodatki.akcesoriaD.objects.AkcesoriaDodatUser;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Map50_60 {
    I50_60_1("50-60-1", 10, ItemHelper.createArmor("&f&lSniezny Helm", Material.IRON_HELMET, 44, 11)),
    I50_60_2("50-60-2", 10, ItemHelper.createArmor("&f&lSniezna Zbroja", Material.DIAMOND_CHESTPLATE, 47, 11)),
    I50_60_3("50-60-3", 10, ItemHelper.createArmor("&f&lSniezne Kalesony", Material.DIAMOND_LEGGINGS, 46, 11)),
    I50_60_4("50-60-4", 10, ItemHelper.createArmor("&f&lSniezne Kozaki", Material.IRON_BOOTS, 43, 11)),
    I50_60_5("50-60-5", 10, ItemHelper.createSword("&f&lSniezny Sztylet", Material.DIAMOND_SWORD, 33, 16,true)),

    I50_60_6("50-60-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&f&lSniezny Naszyjnik").toItemStack()),
    I50_60_7("50-60-7", 10, new ItemBuilder(Material.WATCH).setName("&f&lSniezny Diadem").toItemStack()),
    I50_60_8("50-60-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&f&lSniezna Tarcza").toItemStack()),
    I50_60_9("50-60-9", 10, new ItemBuilder(Material.HOPPER_MINECART).setName("&f&lSniezne Kolczyki").toItemStack()),
    I50_60_10("50-60-10", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&f&lSniezny Pierscien").toItemStack()),
    I50_60_11("50-60-11", 6, new ItemBuilder(Material.MINECART).setName("&f&lSniezna Energia").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map50_60(String name, double dropChance, ItemStack item) {
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

    public static Map50_60 getByName(String name) {
        for (Map50_60 item : Map50_60.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        for (Map50_60 item : Map50_60.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f&l" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(37, 100),
                            ChanceHelper.getRandInt(13, 19), ChanceHelper.getRandInt(10, 16), ChanceHelper.getRandInt(50, 60), "&f&lSniezny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(9, 19),
                            ChanceHelper.getRandInt(10, 26), ChanceHelper.getRandInt(3, 6), ChanceHelper.getRandInt(50, 60), "&f&lSniezny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(20, 33),
                            ChanceHelper.getRandInt(18, 29), ChanceHelper.getRandInt(8, 13), ChanceHelper.getRandInt(50, 60), "&f&lSniezna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(9 ,12),
                            ChanceHelper.getRandInt(14, 18), ChanceHelper.getRandInt(-60, -45), ChanceHelper.getRandInt(50, 60), "&f&lSniezne Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(3, 19), ChanceHelper.getRandInt(5, 20), ChanceHelper.getRandInt(15, 65),
                            ChanceHelper.getRandInt(50, 60), "&f&lSniezny Pierscien"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.MINECART) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-16, -9), ChanceHelper.getRandInt(20, 34),
                            ChanceHelper.getRandInt(28, 44), ChanceHelper.getRandDouble(0.05, 0.1), ChanceHelper.getRandInt(-45, -30),ChanceHelper.getRandInt(50, 60), "&f&lSniezna Energia"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
