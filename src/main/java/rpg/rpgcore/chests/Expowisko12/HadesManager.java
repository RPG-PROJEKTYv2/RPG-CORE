package rpg.rpgcore.chests.Expowisko12;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class HadesManager {

    private final Set<Items> hadesboss = Sets.newConcurrentHashSet();

    public HadesManager() {
        this.hadesboss.add(new Items("1", 8.0, ItemHelper.createArmor("&c&lHelm Hadesa", Material.IRON_HELMET, 30, 1), 1));
        this.hadesboss.add(new Items("2", 8.0, ItemHelper.createArmor("&c&lZbroja Hadesa", Material.IRON_CHESTPLATE, 30, 1), 1));
        this.hadesboss.add(new Items("3", 8.0, ItemHelper.createArmor("&c&lSpodnie Hadesa", Material.IRON_LEGGINGS, 30, 1), 1));
        this.hadesboss.add(new Items("4", 8.0, ItemHelper.createArmor("&c&lButy Hadesa", Material.IRON_BOOTS, 30, 1), 1));
        this.hadesboss.add(new Items("5", 6.0, ItemHelper.createSword("&c&lMiecz Hadesa", Material.IRON_SWORD, 60, 50,false), 1));
        this.hadesboss.add(new Items("6", 2.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&c&lNaszyjnik Hadesa").toItemStack(),1 ));
        this.hadesboss.add(new Items("7", 2.0, new ItemBuilder(Material.WATCH).setName("&c&lDiadem Hadesa").toItemStack(),1 ));
        this.hadesboss.add(new Items("8", 2.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&c&lPierscien Hadesa").toItemStack(),1 ));
        this.hadesboss.add(new Items("9", 2.0, new ItemBuilder(Material.ITEM_FRAME).setName("&c&lTarcza Hadesa").toItemStack(),1 ));
        this.hadesboss.add(new Items("10", 2.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&c&lKolczyki Hadesa").toItemStack(),1 ));
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.hadesboss) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
