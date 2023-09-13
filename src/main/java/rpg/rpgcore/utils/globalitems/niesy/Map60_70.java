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

public enum Map60_70 {
    I60_70_1("60-70-1", 14.0, ItemHelper.createArmor("&4&lOgnisty Kask", Material.DIAMOND_HELMET, 50, 13)),
    I60_70_2("60-70-2", 14.0, ItemHelper.createArmor("&4&lOgnista Kurtka", Material.DIAMOND_CHESTPLATE, 53, 13)),
    I60_70_3("60-70-3", 14.0, ItemHelper.createArmor("&4&lOgniste Spodnie", Material.DIAMOND_LEGGINGS, 52, 13)),
    I60_70_4("60-70-4", 14.0, ItemHelper.createArmor("&4&lOgniste Buty", Material.DIAMOND_BOOTS, 55, 13)),
    I60_70_5("60-70-5", 12.0, ItemHelper.createSword("&4&lOgnista Szpada", Material.DIAMOND_SWORD, 38, 19,true));
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map60_70(String name, double dropChance, ItemStack item) {
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

    public static Map60_70 getByName(String name) {
        for (Map60_70 item : Map60_70.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map60_70 item : Map60_70.values()) {
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
