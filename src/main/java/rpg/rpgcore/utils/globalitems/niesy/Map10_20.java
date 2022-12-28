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

public enum Map10_20 {
    I10_20_1("10-20-1", 10, ItemHelper.createArmor("&8Zaginiona Czapka Goblina", Material.LEATHER_HELMET, 11, 0,true)),
    I10_20_2("10-20-2", 10, ItemHelper.createArmor("&8Zaginiona Zbroja Goblina", Material.LEATHER_CHESTPLATE, 11, 0,true)),
    I10_20_3("10-20-3", 10, ItemHelper.createArmor("&8Zaginione Spodnie Goblina", Material.LEATHER_LEGGINGS, 11, 0,true)),
    I10_20_4("10-20-4", 10, ItemHelper.createArmor("&8Zaginione Buty Goblina", Material.LEATHER_BOOTS, 11, 0,true)),
    I10_20_5("10-20-5", 10, ItemHelper.createSword("&8Zaginiona Czapka Najemnika", Material.STONE_SWORD, 6, 3,true)),
    I10_20_6("10-20-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&8Goblinski Naszyjnik").toItemStack()),
    I10_20_7("10-20-7", 10, new ItemBuilder(Material.WATCH).setName("&8Goblinski Diadem").toItemStack()),
    I10_20_8("10-20-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&8Goblinska Tarcza").toItemStack()),
    I10_20_9("10-20-9", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&8Goblinski Pierscien").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map10_20(String name, double dropChance, ItemStack item) {
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

    public static Map10_20 getByName(String name) {
        for (Map10_20 item : Map10_20.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        for (Map10_20 item : Map10_20.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(3, 9),
                            ChanceHelper.getRandDouble(4, 7), ChanceHelper.getRandDouble(3, 4), ChanceHelper.getRandInt(10, 20), "&8Goblinski Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandDouble(1, 5),
                            ChanceHelper.getRandDouble(1, 5), 1, ChanceHelper.getRandInt(10, 20), "&8Goblinski Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandDouble(3, 10),
                            ChanceHelper.getRandDouble(2, 7), ChanceHelper.getRandInt(1, 3), ChanceHelper.getRandInt(10, 20), "&8Goblinska Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandDouble(0.05, 0.1),
                            ChanceHelper.getRandDouble(2, 4), 1, ChanceHelper.getRandInt(10, 20), "&8Goblinski Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
