package rpg.rpgcore.chests.Expowisko1;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class DowodcaRozbojnikow {

    private final Set<Items> wygnaniec = Sets.newConcurrentHashSet();

    public DowodcaRozbojnikow() {
        this.wygnaniec.add(new Items("1", 10.0, ItemHelper.createArmor("&c&lHelm Dowodcy Rozbojnikow", Material.LEATHER_HELMET, 4, 1), 1));
        this.wygnaniec.add(new Items("2", 10.0, ItemHelper.createArmor("&c&lZbroja Dowodcy Rozbojnikow", Material.LEATHER_CHESTPLATE, 5, 1), 1));
        this.wygnaniec.add(new Items("3", 10.0, ItemHelper.createArmor("&c&lSpodnie Dowodcy Rozbojnikow", Material.LEATHER_LEGGINGS, 4, 1), 1));
        this.wygnaniec.add(new Items("4", 10.0, ItemHelper.createArmor("&c&lButy Dowodcy Rozbojnikow", Material.LEATHER_BOOTS, 3, 1), 1));
        this.wygnaniec.add(new Items("5", 9.0, ItemHelper.createSword("&c&lMiecz Dowodcy Rozbojnikow", Material.WOOD_SWORD, 3, 2, false), 1));
        this.wygnaniec.add(new Items("6", 5.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&c&lZwykly Naszyjnik Dowodcy Rozbojnikow").toItemStack(), 1));
        this.wygnaniec.add(new Items("7", 5.0, new ItemBuilder(Material.WATCH).setName("&c&lZwykly Diadem Dowodcy Rozbojnikow").toItemStack(), 1));
        this.wygnaniec.add(new Items("8", 5.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&c&lZwykly Pierscien Dowodcy Rozbojnikow").toItemStack(), 1));
        this.wygnaniec.add(new Items("9", 5.0, new ItemBuilder(Material.ITEM_FRAME).setName("&c&lZwykla Tarcza Dowodcy Rozbojnikow").toItemStack(), 1));
        this.wygnaniec.add(new Items("10", 1.5, new ItemBuilder(Material.STORAGE_MINECART).setName("&c&lUlepszony Naszyjnik Dowodcy Rozbojnikow").toItemStack(), 1));
        this.wygnaniec.add(new Items("11", 1.5, new ItemBuilder(Material.WATCH).setName("&c&lUlepszony Diadem Dowodcy Rozbojnikow").toItemStack(), 1));
        this.wygnaniec.add(new Items("12", 1.5, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&c&lUlepszony Pierscien Dowodcy Rozbojnikow").toItemStack(), 1));
        this.wygnaniec.add(new Items("13", 1.5, new ItemBuilder(Material.ITEM_FRAME).setName("&c&lUlepszona Tarcza Dowodcy Rozbojnikow").toItemStack(), 1));
    }


    public Items getDrawnItems(final Player player) {
        for (Items item : this.wygnaniec) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
