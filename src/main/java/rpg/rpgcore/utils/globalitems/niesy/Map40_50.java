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

public enum Map40_50 {
    I40_50_1("40-50-1", 10, ItemHelper.createArmor("&b&lPradawny Helm", Material.IRON_HELMET, 36, 9)),
    I40_50_2("40-50-2", 10, ItemHelper.createArmor("&b&lPradawny Kaftan", Material.IRON_CHESTPLATE, 30, 7)),
    I40_50_3("40-50-3", 10, ItemHelper.createArmor("&b&lPradawne Portki", Material.IRON_LEGGINGS, 35, 8)),
    I40_50_4("40-50-4", 10, ItemHelper.createArmor("&b&lPradawne Buty", Material.IRON_BOOTS, 33, 8)),
    I40_50_5("40-50-5", 10, ItemHelper.createSword("&b&lPradawny Sztylet", Material.IRON_SWORD, 30, 12,true));
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map40_50(String name, double dropChance, ItemStack item) {
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

    public static Map40_50 getByName(String name) {
        for (Map40_50 item : Map40_50.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map40_50 item : Map40_50.values()) {
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
