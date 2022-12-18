package rpg.rpgcore.chests.Expowisko3;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GorylManager {


    private final Set<Items> goryl = Sets.newConcurrentHashSet();

    public GorylManager() {
        this.goryl.add(new Items("1", 8.0, ItemHelper.createArmor("&f&lHelm Goryla", Material.LEATHER_HELMET, 14, 0,false), 1));
        this.goryl.add(new Items("2", 8.0, ItemHelper.createArmor("&f&lZbroja Goryla", Material.CHAINMAIL_CHESTPLATE, 14, 0,false), 1));
        this.goryl.add(new Items("3", 8.0, ItemHelper.createArmor("&f&lSpodnie Goryla", Material.LEATHER_LEGGINGS, 14, 0,false), 1));
        this.goryl.add(new Items("4", 8.0, ItemHelper.createArmor("&f&lButy Goryla", Material.LEATHER_BOOTS, 14, 0,false), 1));
        this.goryl.add(new Items("5", 6.0, ItemHelper.createSword("&f&lMiecz Goryla", Material.IRON_SWORD, 5, 3,false), 1));
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.goryl) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }




}
