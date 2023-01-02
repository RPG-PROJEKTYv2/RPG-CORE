package rpg.rpgcore.chests.Expowisko5;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class StraznikSwiatyniManager {

    private final Set<Items> straznik = Sets.newConcurrentHashSet();

    public StraznikSwiatyniManager() {
        this.straznik.add(new Items("1", 8.0, ItemHelper.createArmor("&f&lHelm Straznika Swiatyni", Material.GOLD_HELMET, 7, 0), 1));
        this.straznik.add(new Items("2", 8.0, ItemHelper.createArmor("&f&lZbroja Straznika Swiatyni", Material.IRON_CHESTPLATE, 7, 0), 1));
        this.straznik.add(new Items("3", 8.0, ItemHelper.createArmor("&f&lSpodnie Straznika Swiatyni", Material.IRON_LEGGINGS, 7, 0), 1));
        this.straznik.add(new Items("4", 8.0, ItemHelper.createArmor("&f&lButy Straznika Swiatyni", Material.GOLD_BOOTS, 7, 0), 1));
        this.straznik.add(new Items("5", 6.0, ItemHelper.createSword("&f&lMiecz Straznika Swiatyni", Material.GOLD_SWORD, 3, 2,false), 1));
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.straznik) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
