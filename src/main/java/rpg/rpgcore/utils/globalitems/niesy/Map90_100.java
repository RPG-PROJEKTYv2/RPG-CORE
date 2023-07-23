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

public enum Map90_100 {
    I90_100_1("90-100-1", 12, ItemHelper.createArmor("&9&lSkradziony Helm", Material.DIAMOND_HELMET, 67, 15)),
    I90_100_2("90-100-2", 12, ItemHelper.createArmor("&9&lSkradziona Kamizelka", Material.DIAMOND_CHESTPLATE, 70, 15)),
    I90_100_3("90-100-3", 12, ItemHelper.createArmor("&9&lSkradzione Spodnie", Material.DIAMOND_LEGGINGS, 68, 15)),
    I90_100_4("90-100-4", 12, ItemHelper.createArmor("&9&lSkradzione Trzewiki", Material.DIAMOND_BOOTS, 63, 15)),
    I90_100_5("90-100-5", 12, ItemHelper.createSword("&9&lSkradziony Miecz", Material.DIAMOND_SWORD, 58, 30,true));
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map90_100(String name, double dropChance, ItemStack item) {
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

    public static Map90_100 getByName(String name) {
        for (Map90_100 item : Map90_100.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map90_100 item : Map90_100.values()) {
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
