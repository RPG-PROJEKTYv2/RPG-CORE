package rpg.rpgcore.utils.globalitems.akcesoriumBlok;

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
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Akce90_100 {
    I90_100_1("90-100-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&9&lSkradziona Tarcza").toItemStack()),
    I90_100_2("90-100-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&9&lSkradziony Pierscien").toItemStack()),
    I90_100_3("90-100-3", 11, new ItemBuilder(Material.WATCH).setName("&9&lSkradziony Diadem").toItemStack()),
    I90_100_4("90-100-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&9&lSkradziony Naszyjnik").toItemStack()),
    I90_100_5("90-100-5", 9, new ItemBuilder(Material.HOPPER_MINECART).setName("&9&lSkradzione Kolczyki").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Akce90_100(String name, double dropChance, ItemStack item) {
        this.name = name;
        this.dropChance = dropChance;
        this.item = item;
    }
    public String getName() { return name; }
    public double getDropChance() { return dropChance; }
    public ItemStack getItem() { return item; }
    public static Akce90_100 getByName(String name) {
        for (Akce90_100 item : Akce90_100.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce90_100 item : Akce90_100.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(540, 1800),
                            ChanceHelper.getRandInt(20, 28), ChanceHelper.getRandInt(15, 23), ChanceHelper.getRandInt(90, 100), "&9&lSkradziony Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(20, 29),
                            ChanceHelper.getRandInt(20, 44), ChanceHelper.getRandInt(5, 8), ChanceHelper.getRandInt(90, 100), "&9&lSkradziony Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(30, 53),
                            ChanceHelper.getRandInt(35, 48), ChanceHelper.getRandInt(20, 30), ChanceHelper.getRandInt(90, 100), "&9&lSkradziona Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(18, 25),
                            ChanceHelper.getRandInt(21, 35), ChanceHelper.getRandInt(-87, -75), ChanceHelper.getRandInt(90, 100), "&9&lSkradzione Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(18, 30), ChanceHelper.getRandInt(19, 29), ChanceHelper.getRandInt(60, 100),
                            ChanceHelper.getRandInt(90, 100), "&9&lSkradziony Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player,szczescie);
    }
}
