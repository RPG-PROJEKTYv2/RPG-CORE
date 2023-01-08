package rpg.rpgcore.chests.Expowisko11;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MitycznyKrakenManager {

    private final Set<Items> mitycznykraken = Sets.newConcurrentHashSet();

    public MitycznyKrakenManager() {
        this.mitycznykraken.add(new Items("1", 8.0, ItemHelper.createArmor("&b&lHelm Mitycznego Krakena", Material.IRON_HELMET, 30, 1), 1));
        this.mitycznykraken.add(new Items("2", 8.0, ItemHelper.createArmor("&b&lZbroja Mitycznego Krakena", Material.IRON_CHESTPLATE, 30, 1), 1));
        this.mitycznykraken.add(new Items("3", 8.0, ItemHelper.createArmor("&b&lSpodnie Mitycznego Krakena", Material.IRON_LEGGINGS, 30, 1), 1));
        this.mitycznykraken.add(new Items("4", 8.0, ItemHelper.createArmor("&b&lButy Mitycznego Krakena", Material.IRON_BOOTS, 30, 1), 1));
        this.mitycznykraken.add(new Items("5", 6.0, ItemHelper.createSword("&b&lMiecz Mitycznego Krakena", Material.IRON_SWORD, 50, 40,false), 1));
        this.mitycznykraken.add(new Items("6", 2.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&b&lNaszyjnik Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("7", 2.0, new ItemBuilder(Material.WATCH).setName("&b&lDiadem Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("8", 2.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&b&lPierscien Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("9", 2.0, new ItemBuilder(Material.ITEM_FRAME).setName("&b&lTarcza Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("10", 2.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&b&lKolczyki Mitycznego Krakena").toItemStack(),1 ));
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.mitycznykraken) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
