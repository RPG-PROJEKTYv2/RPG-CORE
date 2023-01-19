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
        this.tryton.add(new Items("1", 10.0, ItemHelper.createArmor("&6&lHelm Trytona", Material.IRON_HELMET, 35, 9), 1));
        this.tryton.add(new Items("2", 10.0, ItemHelper.createArmor("&6&lZbroja Trytona", Material.IRON_CHESTPLATE, 30, 7), 1));
        this.tryton.add(new Items("3", 10.0, ItemHelper.createArmor("&6&lSpodnie Trytona", Material.IRON_LEGGINGS, 44, 15), 1));
        this.tryton.add(new Items("4", 10.0, ItemHelper.createArmor("&6&lButy Trytona", Material.IRON_BOOTS, 33, 8), 1));
        this.tryton.add(new Items("5", 9.0, ItemHelper.createSword("&6&lMiecz Trytona", Material.IRON_SWORD, 23, 15,false), 1));
        this.tryton.add(new Items("6", 7.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&5&lZwykly Naszyjnik Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("7", 7.0, new ItemBuilder(Material.WATCH).setName("&5&lZwykly Diadem Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("8", 7.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&5&lZwykly Pierscien Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("9", 7.0, new ItemBuilder(Material.ITEM_FRAME).setName("&5&lZwykla Tarcza Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("10", 0.5, new ItemBuilder(Material.STORAGE_MINECART).setName("&5&lUlepszona Naszyjnik Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("11", 0.5, new ItemBuilder(Material.WATCH).setName("&5&lUlepszona Diadem Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("12", 0.5, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&5&lUlepszona Pierscien Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("13", 0.5, new ItemBuilder(Material.ITEM_FRAME).setName("&5&lUlepszona Tarcza Trytona").toItemStack(),1 ));
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
