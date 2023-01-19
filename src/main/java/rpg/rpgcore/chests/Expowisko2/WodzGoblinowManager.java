package rpg.rpgcore.chests.Expowisko2;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class WodzGoblinowManager {

    private final Set<Items> wodzgoblin = Sets.newConcurrentHashSet();

    public WodzGoblinowManager() {
        this.wodzgoblin.add(new Items("1", 10.0, ItemHelper.createArmor("&a&lHelm Wodza Goblinow", Material.LEATHER_HELMET, 9, 2), 1));
        this.wodzgoblin.add(new Items("2", 10.0, ItemHelper.createArmor("&a&lZbroja Wodza Goblinow", Material.LEATHER_CHESTPLATE, 10, 2), 1));
        this.wodzgoblin.add(new Items("3", 10.0, ItemHelper.createArmor("&a&lSpodnie Wodza Goblinow", Material.LEATHER_LEGGINGS, 8, 2), 1));
        this.wodzgoblin.add(new Items("4", 10.0, ItemHelper.createArmor("&a&lButy Wodza Goblinow", Material.LEATHER_BOOTS, 6, 2), 1));
        this.wodzgoblin.add(new Items("5", 9.0, ItemHelper.createSword("&a&lMiecz Wodza Goblinow", Material.STONE_SWORD, 5, 3, false), 1));
        this.wodzgoblin.add(new Items("6", 7.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&a&lZwykly Naszyjnik Wodza Goblinow").toItemStack(),1 ));
        this.wodzgoblin.add(new Items("7", 7.0, new ItemBuilder(Material.WATCH).setName("&a&lZwykly Diadem Wodza Goblinow").toItemStack(),1 ));
        this.wodzgoblin.add(new Items("8", 7.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&a&lZwykly Pierscien Wodza Goblinow").toItemStack(),1 ));
        this.wodzgoblin.add(new Items("9", 7.0, new ItemBuilder(Material.ITEM_FRAME).setName("&a&lZwykla Tarcza Wodza Goblinow").toItemStack(),1 ));
        this.wodzgoblin.add(new Items("10", 0.5, new ItemBuilder(Material.STORAGE_MINECART).setName("&a&lUlepszony Naszyjnik Wodza Goblinow").toItemStack(),1 ));
        this.wodzgoblin.add(new Items("11", 0.5, new ItemBuilder(Material.WATCH).setName("&a&lUlepszony Diadem Wodza Goblinow").toItemStack(),1 ));
        this.wodzgoblin.add(new Items("12", 0.5, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&a&lUlepszony Pierscien Wodza Goblinow").toItemStack(),1 ));
        this.wodzgoblin.add(new Items("13", 0.5, new ItemBuilder(Material.ITEM_FRAME).setName("&a&lUlepszona Tarcza Wodza Goblinow").toItemStack(),1 ));
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.wodzgoblin) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
