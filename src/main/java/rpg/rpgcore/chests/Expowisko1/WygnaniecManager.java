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
        this.wygnaniec.add(new Items("1", 8.0, ItemHelper.createArmor("&8&lHelm Wygnanca", Material.LEATHER_HELMET, 6, 0), 1));
        this.wygnaniec.add(new Items("2", 8.0, ItemHelper.createArmor("&8&lZbroja Wygnanca", Material.LEATHER_CHESTPLATE, 6, 0), 1));
        this.wygnaniec.add(new Items("3", 8.0, ItemHelper.createArmor("&8&lSpodnie Wygnanca", Material.LEATHER_LEGGINGS, 6, 0), 1));
        this.wygnaniec.add(new Items("4", 8.0, ItemHelper.createArmor("&8&lButy Wygnanca", Material.LEATHER_BOOTS, 6, 0), 1));
        this.wygnaniec.add(new Items("5", 6.0, ItemHelper.createSword("&9&lMiecz Wygnanego", Material.WOOD_SWORD, 3, 2,false), 1));
        this.wygnaniec.add(new Items("6", 2.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&8&lNaszyjnik Wygnanca").toItemStack(),1 ));
        this.wygnaniec.add(new Items("7", 2.0, new ItemBuilder(Material.WATCH).setName("&8&lDiadem Wyganca").toItemStack(),1 ));
        this.wygnaniec.add(new Items("8", 2.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&8&lPierscien Wyganca").toItemStack(),1 ));
        this.wygnaniec.add(new Items("9", 2.0, new ItemBuilder(Material.ITEM_FRAME).setName("&8&lTarcza Wygnanca").toItemStack(),1 ));
        this.wygnaniec.add(new Items("10", 2.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&8&lKolczyki Wyganca").toItemStack(),1 ));
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
