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

public enum Map30_40 {
    I30_40_1("30-40-1", 14.0, ItemHelper.createArmor("&c&lPrzeklety Helm", Material.GOLD_HELMET, 23, 5)),
    I30_40_2("30-40-2", 14.0, ItemHelper.createArmor("&c&lPrzekleta Zbroja", Material.GOLD_CHESTPLATE, 21, 5)),
    I30_40_3("30-40-3", 14.0, ItemHelper.createArmor("&c&lPrzeklete Spodnie", Material.GOLD_LEGGINGS, 20, 5)),
    I30_40_4("30-40-4", 14.0, ItemHelper.createArmor("&c&lPrzeklete Trepy", Material.GOLD_BOOTS, 20, 5)),
    I30_40_5("30-40-5", 12.0, ItemHelper.createSword("&c&lPrzekleta Kosa", Material.GOLD_SWORD, 18, 8, true));
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map30_40(String name, double dropChance, ItemStack item) {
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

    public static Map30_40 getByName(String name) {
        for (Map30_40 item : Map30_40.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map30_40 item : Map30_40.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&c&lNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
