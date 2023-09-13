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

public enum Map70_80 {
    I70_80_1("70-80-1", 14.0, ItemHelper.createArmor("&7&lMglisty Helm", Material.DIAMOND_HELMET, 57, 14)),
    I70_80_2("70-80-2", 14.0, ItemHelper.createArmor("&7&lMglista Wiatrowka", Material.DIAMOND_CHESTPLATE, 58, 14)),
    I70_80_3("70-80-3", 14.0, ItemHelper.createArmor("&7&lMgliste Galoty", Material.DIAMOND_LEGGINGS, 60, 15)),
    I70_80_4("70-80-4", 14.0, ItemHelper.createArmor("&7&lMgliste Buty", Material.DIAMOND_BOOTS, 55, 20)),
    I70_80_5("70-80-5", 12.0, ItemHelper.createSword("&7&lMglisty Noz", Material.DIAMOND_SWORD, 44, 20,true));
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map70_80(String name, double dropChance, ItemStack item) {
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

    public static Map70_80 getByName(String name) {
        for (Map70_80 item : Map70_80.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map70_80 item : Map70_80.values()) {
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
