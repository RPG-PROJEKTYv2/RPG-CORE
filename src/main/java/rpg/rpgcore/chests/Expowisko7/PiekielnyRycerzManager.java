package rpg.rpgcore.chests.Expowisko7;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PiekielnyRycerzManager {

    private final Set<Items> przekletyrycerz = Sets.newConcurrentHashSet();

    public PiekielnyRycerzManager() {
        this.przekletyrycerz.add(new Items("1", 5.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&c&lNaszyjnik Przekletego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("2", 5.0, new ItemBuilder(Material.WATCH).setName("&c&lDiadem Przekletego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("3", 5.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&c&lPierscien Przekletego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("4", 5.0, new ItemBuilder(Material.ITEM_FRAME).setName("&c&lTarcza Przekletego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("5", 5.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&c&lKolczyki Przekletego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("6", 9.0, ItemHelper.createSword("&c&lMiecz Przekletego Rycerza", Material.DIAMOND_SWORD, 32, 22,false), 1));
        this.przekletyrycerz.add(new Items("7", 12.0, ItemHelper.createArmor("&c&lHelm Przekletego Rycerza", Material.IRON_HELMET, 55, 12), 1));
        this.przekletyrycerz.add(new Items("8", 12.0, ItemHelper.createArmor("&c&lZbroja Przekletego Rycerza", Material.IRON_CHESTPLATE, 57, 14), 1));
        this.przekletyrycerz.add(new Items("9", 12.0, ItemHelper.createArmor("&c&lSpodnie Przekletego Rycerza", Material.IRON_LEGGINGS, 60, 20), 1));
        this.przekletyrycerz.add(new Items("10", 12.0, ItemHelper.createArmor("&c&lButy Przekletego Rycerza", Material.IRON_BOOTS, 55, 13), 1));

    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.przekletyrycerz) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
