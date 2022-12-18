package rpg.rpgcore.chests.Expowisko3;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class KrolGoryliManager {
    private final Set<Items> krolgoryli = Sets.newConcurrentHashSet();

    public KrolGoryliManager() {
        this.krolgoryli.add(new Items("1", 8.0, ItemHelper.createArmor("&f&lHelm Krola Goryli", Material.GOLD_HELMET, 17, 0,false), 1));
        this.krolgoryli.add(new Items("2", 8.0, ItemHelper.createArmor("&f&lZbroja Krola Goryli", Material.GOLD_CHESTPLATE, 17, 0,false), 1));
        this.krolgoryli.add(new Items("3", 8.0, ItemHelper.createArmor("&f&lSpodnie Krola Goryli", Material.GOLD_LEGGINGS, 17, 0,false), 1));
        this.krolgoryli.add(new Items("4", 8.0, ItemHelper.createArmor("&f&lButy Krola Goryli", Material.GOLD_BOOTS, 17, 0,false), 1));
        this.krolgoryli.add(new Items("5", 6.0, ItemHelper.createSword("&f&lMiecz Krola Goryli", Material.GOLD_SWORD, 10, 4,false), 1));
        this.krolgoryli.add(new Items("6", 2.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&f&lNaszyjnik Krola Goryli").toItemStack(),1 ));
        this.krolgoryli.add(new Items("7", 2.0, new ItemBuilder(Material.WATCH).setName("&f&lDiadem Krola Goryli").toItemStack(),1 ));
        this.krolgoryli.add(new Items("8", 2.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&f&lPierscien Krola Goryli").toItemStack(),1 ));
        this.krolgoryli.add(new Items("9", 2.0, new ItemBuilder(Material.ITEM_FRAME).setName("&f&lTarcza Krola Goryli").toItemStack(),1 ));

    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.krolgoryli) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
