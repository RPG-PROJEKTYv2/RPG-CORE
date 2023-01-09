package rpg.rpgcore.chests.Expowisko1;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class WygnaniecManager {

    private final Set<Items> wygnaniec = Sets.newConcurrentHashSet();

    public WygnaniecManager() {
        this.wygnaniec.add(new Items("1", 10.0, ItemHelper.createArmor("&8&lHelm Wygnanca", Material.LEATHER_HELMET, 4, 0), 1));
        this.wygnaniec.add(new Items("2", 10.0, ItemHelper.createArmor("&8&lZbroja Wygnanca", Material.LEATHER_CHESTPLATE, 5, 1), 1));
        this.wygnaniec.add(new Items("3", 10.0, ItemHelper.createArmor("&8&lSpodnie Wygnanca", Material.LEATHER_LEGGINGS, 4, 1), 1));
        this.wygnaniec.add(new Items("4", 10.0, ItemHelper.createArmor("&8&lButy Wygnanca", Material.LEATHER_BOOTS, 3, 1), 1));
        this.wygnaniec.add(new Items("5", 10.0, ItemHelper.createSword("&8&lMiecz Wygnanego", Material.WOOD_SWORD, 3, 2, false), 1));
        this.wygnaniec.add(new Items("6", 7.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&8&lZwykly Naszyjnik Wygnanca").toItemStack(), 1));
        this.wygnaniec.add(new Items("7", 7.0, new ItemBuilder(Material.WATCH).setName("&8&lZwykly Diadem Wyganca").toItemStack(), 1));
        this.wygnaniec.add(new Items("8", 7.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&8&lZwykly Pierscien Wyganca").toItemStack(), 1));
        this.wygnaniec.add(new Items("9", 7.0, new ItemBuilder(Material.ITEM_FRAME).setName("&8&lZwykla Tarcza Wygnanca").toItemStack(), 1));
        this.wygnaniec.add(new Items("10", 0.5, new ItemBuilder(Material.STORAGE_MINECART).setName("&8&lUlepszony Naszyjnik Wygnanca").toItemStack(), 1));
        this.wygnaniec.add(new Items("11", 0.5, new ItemBuilder(Material.WATCH).setName("&8&lUlepszony Diadem Wyganca").toItemStack(), 1));
        this.wygnaniec.add(new Items("12", 0.5, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&8&lUlepszony Pierscien Wyganca").toItemStack(), 1));
        this.wygnaniec.add(new Items("13", 0.5, new ItemBuilder(Material.ITEM_FRAME).setName("&8&lUlepszona Tarcza Wygnanca").toItemStack(), 1));
    }


    public Items getDrawnItems(final Player player) {
        for (Items item : this.wygnaniec) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
