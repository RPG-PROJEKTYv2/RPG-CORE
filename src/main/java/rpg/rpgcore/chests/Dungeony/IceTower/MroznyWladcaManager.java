package rpg.rpgcore.chests.Dungeony.IceTower;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MroznyWladcaManager {
    private final Set<Items> mroznywladca = Sets.newConcurrentHashSet();

    public MroznyWladcaManager() {
        this.mroznywladca.add(new Items("2", 0.045, GlobalItem.getItem("I2", 1), 1));
        this.mroznywladca.add(new Items("2", 0.020, GlobalItem.getItem("I_FRAGMENT_STALI", 1), 1));
        this.mroznywladca.add(new Items("1", 9.0, ItemHelper.createSword("&b&lLodowy Sztylet", Material.DIAMOND_SWORD, 26, 18, false), 1));

        this.mroznywladca.add(new Items("4", 5.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&b&lZwykly Naszyjnik Mroznego Wladcy").toItemStack(), 1));
        this.mroznywladca.add(new Items("5", 5.0, new ItemBuilder(Material.WATCH).setName("&b&lZwykly Diadem Mroznego Wladcy").toItemStack(), 1));
        this.mroznywladca.add(new Items("6", 5.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&b&lZwykly Pierscien Mroznego Wladcy").toItemStack(), 1));
        this.mroznywladca.add(new Items("7", 5.0, new ItemBuilder(Material.ITEM_FRAME).setName("&b&lZwykla Tarcza Mroznego Wladcy").toItemStack(), 1));
        this.mroznywladca.add(new Items("8", 0.75, new ItemBuilder(Material.STORAGE_MINECART).setName("&b&lUlepszony Naszyjnik Mroznego Wladcy").toItemStack(), 1));
        this.mroznywladca.add(new Items("9", 0.75, new ItemBuilder(Material.WATCH).setName("&b&lUlepszony Diadem Mroznego Wladcy").toItemStack(), 1));
        this.mroznywladca.add(new Items("10", 0.75, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&b&lUlepszony Pierscien Mroznego Wladcy").toItemStack(), 1));
        this.mroznywladca.add(new Items("11", 0.75, new ItemBuilder(Material.ITEM_FRAME).setName("&b&lUlepszona Tarcza Mroznego Wladcy").toItemStack(), 1));

    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.mroznywladca) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
