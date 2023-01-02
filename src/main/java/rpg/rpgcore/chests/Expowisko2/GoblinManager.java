package rpg.rpgcore.chests.Expowisko2;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GoblinManager {

    private final Set<Items> goblin = Sets.newConcurrentHashSet();

    public GoblinManager() {
        this.goblin.add(new Items("1", 8.0, ItemHelper.createArmor("&f&lHelm Goblina", Material.LEATHER_HELMET, 7, 0), 1));
        this.goblin.add(new Items("2", 8.0, ItemHelper.createArmor("&f&lZbroja Goblina", Material.LEATHER_CHESTPLATE, 7, 0), 1));
        this.goblin.add(new Items("3", 8.0, ItemHelper.createArmor("&f&lSpodnie Goblina", Material.LEATHER_LEGGINGS, 7, 0), 1));
        this.goblin.add(new Items("4", 8.0, ItemHelper.createArmor("&f&lButy Goblina", Material.LEATHER_BOOTS, 7, 0), 1));
        this.goblin.add(new Items("5", 6.0, ItemHelper.createSword("&f&lMiecz Goblina", Material.WOOD_SWORD, 3, 2,false), 1));
    }


    public Items getDrawnItems(final Player player) {
        for (Items item : this.goblin) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
