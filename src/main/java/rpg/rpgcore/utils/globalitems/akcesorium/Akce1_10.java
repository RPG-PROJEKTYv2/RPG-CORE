package rpg.rpgcore.utils.globalitems.akcesorium;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Akce1_10 {
    I1_10_1("1-10-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&8&lZaginiona Tarcza").toItemStack()),
    I1_10_2("1-10-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&8&lZaginiony Pierscien").toItemStack()),
    I1_10_3("1-10-3", 11, new ItemBuilder(Material.WATCH).setName("&8&lZaginiony Diadem").toItemStack()),
    I1_10_4("1-10-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&8&lZaginiony Naszyjnik").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Akce1_10(String name, double dropChance, ItemStack item) {
        this.name = name;
        this.dropChance = dropChance;
        this.item = item;
    }
    public String getName() { return name; }
    public double getDropChance() { return dropChance; }
    public ItemStack getItem() { return item; }
    public static Akce1_10 getByName(String name) {
        for (Akce1_10 item : Akce1_10.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce1_10 item : Akce1_10.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(8, 15), ChanceHelper.getRandInt(3, 5),
                            ChanceHelper.getRandInt(2, 7), ChanceHelper.getRandInt(1, 10), "&8&lZaginiony Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(9, 10), ChanceHelper.getRandInt(11, 15), ChanceHelper.getRandInt(1, 5),
                            ChanceHelper.getRandInt(1, 10), "&8&lZaginiony Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(8, 13), ChanceHelper.getRandInt(4, 8),
                            ChanceHelper.getRandInt(3, 4), ChanceHelper.getRandInt(1, 10), "&8&lZaginiona Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(3, 9), ChanceHelper.getRandInt(5, 10), ChanceHelper.getRandInt(20, 30),
                            ChanceHelper.getRandInt(1, 10), "&8&lZaginiony Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player,szczescie);
    }
}
