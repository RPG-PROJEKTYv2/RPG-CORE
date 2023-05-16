package rpg.rpgcore.chests.Expowisko6;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MroznyWilkManager {

    private final Set<Items> mroznywilk = Sets.newConcurrentHashSet();

    public MroznyWilkManager() {
        this.mroznywilk.add(new Items("1", 18.0, ItemHelper.createArmor("&fHelm Mroznego Wilka", Material.GOLD_HELMET, 28, 5), 1));
        this.mroznywilk.add(new Items("2", 18.0, ItemHelper.createArmor("&fZbroja Mroznego Wilka", Material.IRON_CHESTPLATE, 32, 6), 1));
        this.mroznywilk.add(new Items("3", 18.0, ItemHelper.createArmor("&fSpodnie Mroznego Wilka", Material.IRON_LEGGINGS, 30, 6), 1));
        this.mroznywilk.add(new Items("4", 18.0, ItemHelper.createArmor("&fButy Mroznego Wilka", Material.GOLD_BOOTS, 28, 4), 1));
        this.mroznywilk.add(new Items("5", 16.0, ItemHelper.createSword("&fWilczy Kiel", Material.GOLD_SWORD, 20, 14,false), 1));
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.mroznywilk) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
