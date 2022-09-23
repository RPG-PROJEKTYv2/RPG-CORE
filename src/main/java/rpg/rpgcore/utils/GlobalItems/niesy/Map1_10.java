package rpg.rpgcore.utils.GlobalItems.niesy;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Map1_10 {
    I1_10_1("1-10-1", 1, ItemHelper.createArmor("&8Zaginiona Czapka Najemnika", Material.LEATHER_HELMET, 8, 1, true, true)),
    I1_10_2("1-10-2", 1, ItemHelper.createArmor("&8Zaginiona Koszula Najemnika", Material.LEATHER_CHESTPLATE, 8, 1, true, true)),
    I1_10_3("1-10-3", 1, ItemHelper.createArmor("&8Zaginione Spodnie Najemnika", Material.LEATHER_LEGGINGS, 8, 1, true, true)),
    I1_10_4("1-10-4", 1, ItemHelper.createArmor("&8Zaginiona Buty Najemnika", Material.LEATHER_BOOTS, 8, 1, true, true)),
    I99("null", 0, null);
    private String name;
    private double dropChance;
    private ItemStack item;

    Map1_10(String name, double dropChance, ItemStack item) {
        this.name = name;
        this.dropChance = dropChance;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public double getDropChance() {
        return dropChance;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public static Map1_10 getByName(String name) {
        for (Map1_10 item : Map1_10.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return I99;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        for (Map1_10 item : Map1_10.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
