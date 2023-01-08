package rpg.rpgcore.chests.Expowisko7;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ZywiolakOgniaManager {

    private final Set<Items> zywiolak = Sets.newConcurrentHashSet();

    public ZywiolakOgniaManager() {
        this.zywiolak.add(new Items("1", 8.0, ItemHelper.createArmor("&4Helm Zywiolaka Ognia", Material.GOLD_HELMET, 7, 0), 1));
        this.zywiolak.add(new Items("2", 8.0, ItemHelper.createArmor("&4Zbroja Zywiolaka Ognia", Material.IRON_CHESTPLATE, 7, 0), 1));
        this.zywiolak.add(new Items("3", 8.0, ItemHelper.createArmor("&4Spodnie Zywiolaka Ognia", Material.IRON_LEGGINGS, 7, 0), 1));
        this.zywiolak.add(new Items("4", 8.0, ItemHelper.createArmor("&4Buty Zywiolaka Ognia", Material.GOLD_BOOTS, 7, 0), 1));
        this.zywiolak.add(new Items("5", 6.0, ItemHelper.createSword("&4Miecz Zywiolaka Ognia", Material.GOLD_SWORD, 26, 15,false), 1));
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.zywiolak) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
