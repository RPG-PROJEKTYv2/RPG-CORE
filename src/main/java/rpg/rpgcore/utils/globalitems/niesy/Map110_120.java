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

public enum Map110_120 {
    I110_120_1("110-120-1", 14.0, ItemHelper.createArmor("&f&fMrozny Kaszkiet", Material.DIAMOND_HELMET, 84, 15)),
    I110_120_2("110-120-2", 14.0, ItemHelper.createArmor("&f&fMrozna Koszula", Material.DIAMOND_CHESTPLATE, 87, 15)),
    I110_120_3("110-120-3", 14.0, ItemHelper.createArmor("&f&lMrozne Spodnie", Material.DIAMOND_LEGGINGS, 86, 15)),
    I110_120_4("110-120-4", 14.0, ItemHelper.createArmor("&f&lMrozne Buty", Material.DIAMOND_BOOTS, 82, 15)),
    I110_120_5("110-120-5", 12.0, ItemHelper.createSword("&f&lMrozna Katana", Material.DIAMOND_SWORD, 77, 43,true));
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map110_120(String name, double dropChance, ItemStack item) {
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

    public static Map110_120 getByName(String name) {
        for (Map110_120 item : Map110_120.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map110_120 item : Map110_120.values()) {
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
