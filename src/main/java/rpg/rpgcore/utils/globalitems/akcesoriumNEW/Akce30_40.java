package rpg.rpgcore.utils.globalitems.akcesoriumNEW;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Akce30_40 {
    I30_40_1("30-40-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&c&lPrzekleta Tarcza").toItemStack()),
    I30_40_2("30-40-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&c&lPrzeklety Pierscien").toItemStack()),
    I30_40_3("30-40-3", 11, new ItemBuilder(Material.WATCH).setName("&c&lPrzeklety Diadem").toItemStack()),
    I30_40_4("30-40-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&c&lPrzeklety Naszyjnik").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Akce30_40(String name, double dropChance, ItemStack item) {
        this.name = name;
        this.dropChance = dropChance;
        this.item = item;
    }
    public String getName() { return name; }
    public double getDropChance() { return dropChance; }
    public ItemStack getItem() { return item; }
    public static Akce30_40 getByName(String name) {
        for (Akce30_40 item : Akce30_40.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        for (Akce30_40 item : Akce30_40.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(37, 54),
                            ChanceHelper.getRandInt(8, 13), ChanceHelper.getRandInt(6, 12), ChanceHelper.getRandInt(30, 40), "&c&lPrzeklety Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(14, 16),
                            ChanceHelper.getRandInt(15, 20), ChanceHelper.getRandInt(2, 5), ChanceHelper.getRandInt(30, 40), "&c&lPrzeklety Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(15, 23),
                            ChanceHelper.getRandInt(10, 18), ChanceHelper.getRandInt(6, 9), ChanceHelper.getRandInt(30, 40), "&c&lPrzekleta Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(12, 16), ChanceHelper.getRandInt(14, 17), ChanceHelper.getRandInt(30, 35),
                            ChanceHelper.getRandInt(30, 40), "&c&lPrzeklety Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player,szczescie);
    }
}
