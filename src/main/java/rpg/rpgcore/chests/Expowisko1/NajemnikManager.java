package rpg.rpgcore.chests.Expowisko1;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class NajemnikManager {

    private final Set<Items> najemnik = Sets.newConcurrentHashSet();

    public NajemnikManager() {
        this.najemnik.add(new Items("1", 12.0 , ItemHelper.createArmor("&8Helm Najemnika", Material.LEATHER_HELMET, 3, 0,false), 1));
        this.najemnik.add(new Items("2", 12.0 , ItemHelper.createArmor("&8Zbroja Najemnika", Material.LEATHER_CHESTPLATE, 3, 0,false), 1));
        this.najemnik.add(new Items("3", 12.0 , ItemHelper.createArmor("&8Spodnie Najemnika", Material.LEATHER_LEGGINGS, 3, 0,false), 1));
        this.najemnik.add(new Items("4", 12.0 , ItemHelper.createArmor("&8Buty Najemnika", Material.LEATHER_BOOTS, 3, 0,false), 1));
        this.najemnik.add(new Items("5", 10.0, ItemHelper.createSword("&8Tepy Miecz Najemnika", Material.WOOD_SWORD, 1, 1,false), 1));
    }



    public Items getDrawnItems(final Player player) {
        for (Items item : this.najemnik) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
