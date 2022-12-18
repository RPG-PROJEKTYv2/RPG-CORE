package rpg.rpgcore.chests.Expowisko5;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TrytonManager {

    private final Set<Items> tryton = Sets.newConcurrentHashSet();

    public TrytonManager() {
        this.tryton.add(new Items("1", 8.0, ItemHelper.createArmor("&f&lHelm Trytona", Material.IRON_HELMET, 30, 1,false), 1));
        this.tryton.add(new Items("2", 8.0, ItemHelper.createArmor("&f&lZbroja Trytona", Material.IRON_CHESTPLATE, 30, 1,false), 1));
        this.tryton.add(new Items("3", 8.0, ItemHelper.createArmor("&f&lSpodnie Trytona", Material.IRON_LEGGINGS, 30, 1,false), 1));
        this.tryton.add(new Items("4", 8.0, ItemHelper.createArmor("&f&lButy Trytona", Material.IRON_BOOTS, 30, 1,false), 1));
        this.tryton.add(new Items("5", 6.0, ItemHelper.createSword("&f&lMiecz Trytona", Material.IRON_SWORD, 22, 13,false), 1));
        this.tryton.add(new Items("6", 2.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&f&lNaszyjnik Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("7", 2.0, new ItemBuilder(Material.WATCH).setName("&f&lDiadem Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("8", 2.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&f&lPierscien Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("9", 2.0, new ItemBuilder(Material.ITEM_FRAME).setName("&f&lTarcza Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("10", 2.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&f&lKolczyki Trytona").toItemStack(),1 ));
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.tryton) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
