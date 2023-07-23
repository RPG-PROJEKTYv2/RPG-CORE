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

public enum Akce20_30 {
    I20_30_1("20-30-1", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&6&lTropikalny Naszyjnik").toItemStack()),
    I20_30_2("20-30-2", 10, new ItemBuilder(Material.WATCH).setName("&6&lTropikalny Diadem").toItemStack()),
    I20_30_3("20-30-3", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&6&lTropikalna Tarcza").toItemStack()),
    I20_30_4("20-30-4", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&6&lTropikalny Pierscien").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Akce20_30(String name, double dropChance, ItemStack item) {
        this.name = name;
        this.dropChance = dropChance;
        this.item = item;
    }
    public String getName() { return name; }
    public double getDropChance() { return dropChance; }
    public ItemStack getItem() { return item; }
    public static Akce20_30 getByName(String name) {
        for (Akce20_30 item : Akce20_30.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce20_30 item : Akce20_30.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(20, 25),
                            ChanceHelper.getRandInt(6, 9), ChanceHelper.getRandInt(4, 10), ChanceHelper.getRandInt(20, 30), "&6&lTropikalny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(10, 12),
                            ChanceHelper.getRandInt(15, 17), ChanceHelper.getRandInt(1, 5), ChanceHelper.getRandInt(20, 30), "&6&lTropikalny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(14, 19),
                            ChanceHelper.getRandInt(7, 14), ChanceHelper.getRandInt(5, 8), ChanceHelper.getRandInt(20, 30), "&6&lTropikalna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(8, 15), ChanceHelper.getRandInt(13, 16), ChanceHelper.getRandInt(8, 30),
                            ChanceHelper.getRandInt(20, 30), "&6&lTropikalny Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player,szczescie);
    }
}
