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

public enum Map80_90 {
    I80_90_1("80-90-1", 12, ItemHelper.createArmor("&e&lSloneczny Kapelusz", Material.DIAMOND_HELMET, 60, 14)),
    I80_90_2("80-90-2", 12, ItemHelper.createArmor("&e&lSloneczna Zbroja", Material.DIAMOND_CHESTPLATE, 63, 14)),
    I80_90_3("80-90-3", 12, ItemHelper.createArmor("&e&lSloneczne Spodenki", Material.DIAMOND_LEGGINGS, 62, 14)),
    I80_90_4("80-90-4", 12, ItemHelper.createArmor("&e&lSloneczne Klapki", Material.DIAMOND_BOOTS, 59, 14)),
    I80_90_5("80-90-5", 12, ItemHelper.createSword("&e&lSloneczna Szabla", Material.DIAMOND_SWORD, 52, 23,true));
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map80_90(String name, double dropChance, ItemStack item) {
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

    public static Map80_90 getByName(String name) {
        for (Map80_90 item : Map80_90.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map80_90 item : Map80_90.values()) {
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
