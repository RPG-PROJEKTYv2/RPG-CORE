package rpg.rpgcore.utils.globalitems.niesy;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaD.helpers.AkcesoriaDodatHelper;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Map120_130 {
    I120_130_1("120-130-1", 12, ItemHelper.createArmor("&6&lStarozytny Kaszkiet", Material.DIAMOND_HELMET, 92, 15)),
    I120_130_2("120-130-2", 12, ItemHelper.createArmor("&6&lStarozytna Zbroja", Material.DIAMOND_CHESTPLATE, 95, 15)),
    I120_130_3("120-130-3", 12, ItemHelper.createArmor("&6&lStarozytne Spodnie", Material.DIAMOND_LEGGINGS, 95, 15)),
    I120_130_4("120-130-4", 12, ItemHelper.createArmor("&6&lStarozytne Sandaly", Material.DIAMOND_BOOTS, 90, 15)),
    I120_130_5("120-130-5", 12, ItemHelper.createSword("&6&lStarozytna Wlocznia", Material.DIAMOND_SWORD, 92, 48,true));
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map120_130(String name, double dropChance, ItemStack item) {
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

    public static Map120_130 getByName(String name) {
        for (Map120_130 item : Map120_130.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map120_130 item : Map120_130.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&2+ &f&l" + item.getRewardItem().getItemMeta().getDisplayName()));
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
