package rpg.rpgcore.chests.Dungeony.IceTower;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class LodowySlugaManager {

    private final Set<Items> lodowysluga = Sets.newConcurrentHashSet();

    public LodowySlugaManager() {
        this.lodowysluga.add(new Items("1", 18.0 , ItemHelper.createArmor("&bHelm Lodowego Slugi", Material.IRON_HELMET, 30, 6), 1));
        this.lodowysluga.add(new Items("2", 18.0 , ItemHelper.createArmor("&bZbroja Lodowego Slugi", Material.IRON_CHESTPLATE, 33, 6), 1));
        this.lodowysluga.add(new Items("3", 18.0 , ItemHelper.createArmor("&bSpodnie Lodowego Slugi", Material.IRON_LEGGINGS, 30, 6), 1));
        this.lodowysluga.add(new Items("4", 18.0 , ItemHelper.createArmor("&bButy Lodowego Slugi", Material.IRON_BOOTS, 30, 6), 1));
        this.lodowysluga.add(new Items("5", 16.0, ItemHelper.createSword("&bMiecz Lodowego Slugi", Material.IRON_SWORD, 20, 18,false), 1));
    }



    public Items getDrawnItems(final Player player) {
        for (Items item : this.lodowysluga) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
