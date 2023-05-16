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

public class KrysztalowyWladcaManager {
    private final Set<Items> krysztalowyboss = Sets.newConcurrentHashSet();

    public KrysztalowyWladcaManager() {
        this.krysztalowyboss.add(new Items("1", 8.0, ItemHelper.createArmor("&b&lHelm Krysztalowego Wladcy", Material.IRON_HELMET, 85, 18), 1));
        this.krysztalowyboss.add(new Items("2", 8.0, ItemHelper.createArmor("&b&lZbroja Krysztalowego Wladcy", Material.IRON_CHESTPLATE, 85, 18), 1));
        this.krysztalowyboss.add(new Items("3", 8.0, ItemHelper.createArmor("&b&lSpodnie Krysztalowego Wladcy", Material.IRON_LEGGINGS, 85, 17), 1));
        this.krysztalowyboss.add(new Items("4", 8.0, ItemHelper.createArmor("&b&lButy Krysztalowego Wladcy", Material.IRON_BOOTS, 82, 18), 1));
        this.krysztalowyboss.add(new Items("5", 6.0, ItemHelper.createSword("&b&lMiecz Krysztalowego Wladcy", Material.IRON_SWORD, 60, 50, false), 1));
        this.krysztalowyboss.add(new Items("6", 2.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&b&lNaszyjnik Krysztalowego Wladcy").toItemStack(), 1));
        this.krysztalowyboss.add(new Items("7", 2.0, new ItemBuilder(Material.WATCH).setName("&b&lDiadem Krysztalowego Wladcy").toItemStack(), 1));
        this.krysztalowyboss.add(new Items("8", 2.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&b&lPierscien Krysztalowego Wladcy").toItemStack(), 1));
        this.krysztalowyboss.add(new Items("9", 2.0, new ItemBuilder(Material.ITEM_FRAME).setName("&b&lTarcza Krysztalowego Wladcy").toItemStack(), 1));
        this.krysztalowyboss.add(new Items("10", 2.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&b&lKolczyki Krysztalowego Wladcy").toItemStack(), 1));
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.krysztalowyboss) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
