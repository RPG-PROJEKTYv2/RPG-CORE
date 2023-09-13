package rpg.rpgcore.utils.globalitems.niesy;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Map50_60 {
    I50_60_1("50-60-1", 14.0, ItemHelper.createArmor("&f&lSniezny Helm", Material.IRON_HELMET, 44, 11)),
    I50_60_2("50-60-2", 14.0, ItemHelper.createArmor("&f&lSniezna Zbroja", Material.DIAMOND_CHESTPLATE, 47, 11)),
    I50_60_3("50-60-3", 14.0, ItemHelper.createArmor("&f&lSniezne Kalesony", Material.DIAMOND_LEGGINGS, 46, 11)),
    I50_60_4("50-60-4", 14.0, ItemHelper.createArmor("&f&lSniezne Kozaki", Material.IRON_BOOTS, 43, 11)),
    I50_60_5("50-60-5", 12.0, ItemHelper.createSword("&f&lSniezny Sztylet", Material.DIAMOND_SWORD, 33, 16,true));
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map50_60(String name, double dropChance, ItemStack item) {
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

    public static Map50_60 getByName(String name) {
        for (Map50_60 item : Map50_60.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map50_60 item : Map50_60.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&2+ &f&l" + item.getRewardItem().getItemMeta().getDisplayName()));
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player, szczescie);
    }
}
