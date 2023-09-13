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

public enum Map10_20 {
    I10_20_1("10-20-1", 14.0, ItemHelper.createArmor("&2&lZielony Beret", Material.LEATHER_HELMET, 8, 2)),
    I10_20_2("10-20-2", 14.0, ItemHelper.createArmor("&2&lZielony Kubrak", Material.LEATHER_CHESTPLATE, 8, 2)),
    I10_20_3("10-20-3", 14.0, ItemHelper.createArmor("&2&lZielone Spodnie", Material.LEATHER_LEGGINGS, 8, 1)),
    I10_20_4("10-20-4", 14.0, ItemHelper.createArmor("&2&lZielone Buty", Material.LEATHER_BOOTS, 7, 2)),
    I10_20_5("10-20-5", 12.0, ItemHelper.createSword("&2&lZielona Maczeta", Material.STONE_SWORD, 6, 3,true));
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map10_20(String name, double dropChance, ItemStack item) {
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

    public static Map10_20 getByName(String name) {
        for (Map10_20 item : Map10_20.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map10_20 item : Map10_20.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player, szczescie);
    }
}
