package rpg.rpgcore.utils.globalitems.niesy;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Map100_110 {
    I100_110_1("100-110-1", 14.0, ItemHelper.createArmor("&b&lPodwodny Czepek", Material.DIAMOND_HELMET, 75, 15)),
    I100_110_2("100-110-2", 14.0, ItemHelper.createArmor("&b&lPodwodna Kamizelka", Material.DIAMOND_CHESTPLATE, 78, 15)),
    I100_110_3("100-110-3", 14.0, ItemHelper.createArmor("&b&lPodwodne Gacie", Material.DIAMOND_LEGGINGS, 77, 15)),
    I100_110_4("100-110-4", 14.0, ItemHelper.createArmor("&b&lPodwodne Pletwy", Material.DIAMOND_BOOTS, 71, 15)),
    I100_110_5("100-110-5", 12.0, ItemHelper.createSword("&b&lPodwodny Sztylet", Material.DIAMOND_SWORD, 67, 33,true));

    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map100_110(String name, double dropChance, ItemStack item) {
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

    public static Map100_110 getByName(String name) {
        for (Map100_110 item : Map100_110.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map100_110 item : Map100_110.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
