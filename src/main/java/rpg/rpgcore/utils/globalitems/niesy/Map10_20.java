package rpg.rpgcore.utils.globalitems.niesy;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Map10_20 {
    I10_20_1("10-20-1", 10, ItemHelper.createArmor("&2&lZielony Beret", Material.LEATHER_HELMET, 8, 2)),
    I10_20_2("10-20-2", 10, ItemHelper.createArmor("&2&lZielony Kubrak", Material.LEATHER_CHESTPLATE, 8, 2)),
    I10_20_3("10-20-3", 10, ItemHelper.createArmor("&2&lZielone Spodnie", Material.LEATHER_LEGGINGS, 8, 1)),
    I10_20_4("10-20-4", 10, ItemHelper.createArmor("&2&lZielone Buty", Material.LEATHER_BOOTS, 7, 2)),
    I10_20_5("10-20-5", 10, ItemHelper.createSword("&2&lZielona Maczeta", Material.STONE_SWORD, 6, 3,true)),

    I10_20_6("10-20-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&2&lZielony Naszyjnik").toItemStack()),
    I10_20_7("10-20-7", 10, new ItemBuilder(Material.WATCH).setName("&2&lZielony Diadem").toItemStack()),
    I10_20_8("10-20-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&2&lZielona Tarcza").toItemStack()),
    I10_20_9("10-20-9", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&2&lZielony Pierscien").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map10_20(String name, double dropChance, ItemStack item) {
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

    public static Map10_20 getByName(String name) {
        for (Map10_20 item : Map10_20.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map10_20 item : Map10_20.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(3, 10),
                            ChanceHelper.getRandInt(4, 7), ChanceHelper.getRandInt(3, 9), ChanceHelper.getRandInt(10, 20), "&2&lZielony Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(3, 8), ChanceHelper.getRandInt(4, 10), ChanceHelper.getRandInt(1, 3),
                            ChanceHelper.getRandInt(10, 20), "&2&lZielony Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(8, 13),
                            ChanceHelper.getRandInt(6, 11), ChanceHelper.getRandInt(2, 5), ChanceHelper.getRandInt(10, 20), "&2&lZielona Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(1, 6), ChanceHelper.getRandInt(2, 8), ChanceHelper.getRandInt(5, 25),
                            ChanceHelper.getRandInt(10, 20), "&2&lZielony Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
