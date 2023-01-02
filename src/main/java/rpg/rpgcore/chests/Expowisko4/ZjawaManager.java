package rpg.rpgcore.chests.Expowisko4;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ZjawaManager {

    private final Set<Items> zjawa = Sets.newConcurrentHashSet();

    public ZjawaManager() {
        this.zjawa.add(new Items("1", 8.0, ItemHelper.createArmor("&f&lHelm Zjawy", Material.GOLD_HELMET, 20, 0), 1));
        this.zjawa.add(new Items("2", 8.0, ItemHelper.createArmor("&f&lZbroja Zjawy", Material.GOLD_CHESTPLATE, 20, 0), 1));
        this.zjawa.add(new Items("3", 8.0, ItemHelper.createArmor("&f&lSpodnie Zjawy", Material.GOLD_LEGGINGS, 20, 0), 1));
        this.zjawa.add(new Items("4", 8.0, ItemHelper.createArmor("&f&lButy Zjawy", Material.GOLD_BOOTS, 20, 0), 1));
        this.zjawa.add(new Items("5", 6.0, ItemHelper.createSword("&f&lMiecz Zjawy", Material.GOLD_SWORD, 9, 5,false), 1));

    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.zjawa) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
