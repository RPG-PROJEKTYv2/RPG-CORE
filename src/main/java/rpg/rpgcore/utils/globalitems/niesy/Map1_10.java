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

public enum Map1_10 {
    I1_10_1("1-10-1", 10, ItemHelper.createArmor("&8&lZaginiona Czapka", Material.LEATHER_HELMET, 4, 1)),
    I1_10_2("1-10-2", 10, ItemHelper.createArmor("&8&lZaginiona Kurtka", Material.LEATHER_CHESTPLATE, 4, 1)),
    I1_10_3("1-10-3", 10, ItemHelper.createArmor("&8&lZaginione Spodenki", Material.LEATHER_LEGGINGS, 4, 1)),
    I1_10_4("1-10-4", 10, ItemHelper.createArmor("&8&lZaginione Buty", Material.LEATHER_BOOTS, 4, 1)),
    I1_10_5("1-10-5", 10, ItemHelper.createSword("&8&lZaginiona Brzytwa", Material.WOOD_SWORD, 5, 1,true));
    private final String name;
    private final double dropChance;
    private final ItemStack item;

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
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map1_10 item : Map1_10.values()) {
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
