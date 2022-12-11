package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class HellcaseManager {

    private final Set<Items> hellcase = Sets.newConcurrentHashSet();

    public HellcaseManager() {
        // SET
        this.hellcase.add(new Items("1", 12.0 , ItemHelper.createArmor("&c&lPiekielny Helm", Material.DIAMOND_HELMET, 50, 5,false), 1));
        this.hellcase.add(new Items("2", 12.0 , ItemHelper.createArmor("&c&lPiekielny Napiersnik", Material.DIAMOND_CHESTPLATE, 50, 5,false), 1));
        this.hellcase.add(new Items("3", 12.0 , ItemHelper.createArmor("&c&lPiekielne Nogawice", Material.DIAMOND_LEGGINGS, 50, 5,false), 1));
        this.hellcase.add(new Items("4", 12.0 , ItemHelper.createArmor("&c&lPiekielne Buty", Material.DIAMOND_BOOTS, 50, 5,false), 1));
        // MIECZ
        this.hellcase.add(new Items("5", 8.0, ItemHelper.createSword("&4&lPiekielny Miecz", Material.DIAMOND_SWORD, 50, 5, true), 1));
        // AKCE
        // WALUTA
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.hellcase) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
