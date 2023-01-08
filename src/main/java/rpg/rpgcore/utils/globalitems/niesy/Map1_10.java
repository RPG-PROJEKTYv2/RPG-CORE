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

public enum Map1_10 {
    I1_10_1("1-10-1", 10, ItemHelper.createArmor("&8&lZaginiona Czapka", Material.LEATHER_HELMET, 4, 1)),
    I1_10_2("1-10-2", 10, ItemHelper.createArmor("&8&lZaginiona Kurtka", Material.LEATHER_CHESTPLATE, 4, 1)),
    I1_10_3("1-10-3", 10, ItemHelper.createArmor("&8&lZaginione Spodenki", Material.LEATHER_LEGGINGS, 4, 1)),
    I1_10_4("1-10-4", 10, ItemHelper.createArmor("&8&lZaginione Buty", Material.LEATHER_BOOTS, 4, 1)),
    I1_10_5("1-10-5", 10, ItemHelper.createSword("&8&lZaginiona Brzytwa", Material.WOOD_SWORD, 5, 1,true)),

    I1_10_6("1-10-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&8&lZaginiony Naszyjnik").toItemStack()),
    I1_10_7("1-10-7", 10, new ItemBuilder(Material.WATCH).setName("&8&lZaginiony Diadem").toItemStack()),
    I1_10_8("1-10-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&8&lZaginiona Tarcza").toItemStack()),
    I1_10_9("1-10-9", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&8&lZaginiony Pierscien").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map1_10(String name, double dropChance, ItemStack item) {
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

    public static Map1_10 getByName(String name) {
        for (Map1_10 item : Map1_10.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        for (Map1_10 item : Map1_10.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(2, 8), ChanceHelper.getRandInt(3, 5),
                            ChanceHelper.getRandInt(2, 7), ChanceHelper.getRandInt(1, 10), "&8&lZaginiony Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(2, 5), ChanceHelper.getRandInt(3, 7), ChanceHelper.getRandInt(1, 3),
                            ChanceHelper.getRandInt(1, 10), "&8&lZaginiony Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(5, 9), ChanceHelper.getRandInt(9, 20),
                            ChanceHelper.getRandInt(1, 3), ChanceHelper.getRandInt(1, 10), "&8&lZaginiona Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(1, 4), ChanceHelper.getRandInt(2, 5), ChanceHelper.getRandInt(5, 20),
                            ChanceHelper.getRandInt(1, 10), "&8&lZaginiony Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
