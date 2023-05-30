package rpg.rpgcore.chests.Expowisko13;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class StarozytnySmoczyCesarzManager {
    private final Set<Items> starozytnycesarz = Sets.newConcurrentHashSet();

    public StarozytnySmoczyCesarzManager() {
        this.starozytnycesarz.add(new Items("1", 8.0, ItemHelper.createArmor("&f&lHelm Archaniola", Material.DIAMOND_HELMET, 95, 20), 1));
        this.starozytnycesarz.add(new Items("2", 8.0, ItemHelper.createArmor("&f&lZbroja Archaniola", Material.DIAMOND_CHESTPLATE, 99, 20), 1));
        this.starozytnycesarz.add(new Items("3", 8.0, ItemHelper.createArmor("&f&lSpodnie Archaniola", Material.DIAMOND_LEGGINGS, 99, 20), 1));
        this.starozytnycesarz.add(new Items("4", 8.0, ItemHelper.createArmor("&f&lButy Archaniola", Material.DIAMOND_BOOTS, 95, 20), 1));
        this.starozytnycesarz.add(new Items("5", 6.0, ItemHelper.createSword("&f&lMiecz Archaniola", Material.DIAMOND_SWORD, 80, 50, false), 1));
        this.starozytnycesarz.add(new Items("6", 2.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&f&lNaszyjnik Archaniola").toItemStack(), 1));
        this.starozytnycesarz.add(new Items("7", 2.0, new ItemBuilder(Material.WATCH).setName("&f&lDiadem Archaniola").toItemStack(), 1));
        this.starozytnycesarz.add(new Items("8", 2.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&f&lPierscien Archaniola").toItemStack(), 1));
        this.starozytnycesarz.add(new Items("9", 2.0, new ItemBuilder(Material.ITEM_FRAME).setName("&f&lTarcza Archaniola").toItemStack(), 1));
        this.starozytnycesarz.add(new Items("10", 2.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&f&lKolczyki Archaniola").toItemStack(), 1));
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.starozytnycesarz) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
