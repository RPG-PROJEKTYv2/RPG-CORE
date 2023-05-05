package rpg.rpgcore.chests.Expowisko10;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PodziemnyRozpruwaczManager {

    private final Set<Items> podziemnyrozpruwacz = Sets.newConcurrentHashSet();

    public PodziemnyRozpruwaczManager() {
        this.podziemnyrozpruwacz.add(new Items("1", 8.0, ItemHelper.createArmor("&6&lHelm Podziemnego Rozpruwacza", Material.IRON_HELMET, 70, 17), 1));
        this.podziemnyrozpruwacz.add(new Items("2", 8.0, ItemHelper.createArmor("&6&lZbroja Podziemnego Rozpruwacza", Material.IRON_CHESTPLATE, 73, 17), 1));
        this.podziemnyrozpruwacz.add(new Items("3", 8.0, ItemHelper.createArmor("&6&lSpodnie Podziemnego Rozpruwacza", Material.IRON_LEGGINGS, 73, 17), 1));
        this.podziemnyrozpruwacz.add(new Items("4", 8.0, ItemHelper.createArmor("&6&lButy Podziemnego Rozpruwacza", Material.IRON_BOOTS, 67, 16), 1));
        this.podziemnyrozpruwacz.add(new Items("5", 6.0, ItemHelper.createSword("&6&lMiecz Podziemnego Rozpruwacza", Material.IRON_SWORD, 40, 40,false), 1));
        this.podziemnyrozpruwacz.add(new Items("6", 2.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&6&lNaszyjnik Podziemnego Rozpruwacza").toItemStack(),1 ));
        this.podziemnyrozpruwacz.add(new Items("7", 2.0, new ItemBuilder(Material.WATCH).setName("&6&lDiadem Podziemnego Rozpruwacza").toItemStack(),1 ));
        this.podziemnyrozpruwacz.add(new Items("8", 2.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&6&lPierscien Podziemnego Rozpruwacza").toItemStack(),1 ));
        this.podziemnyrozpruwacz.add(new Items("9", 2.0, new ItemBuilder(Material.ITEM_FRAME).setName("&6&lTarcza Podziemnego Rozpruwacza").toItemStack(),1 ));
        this.podziemnyrozpruwacz.add(new Items("10", 2.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&6&lKolczyki Podziemnego Rozpruwacza").toItemStack(),1 ));
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.podziemnyrozpruwacz) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
