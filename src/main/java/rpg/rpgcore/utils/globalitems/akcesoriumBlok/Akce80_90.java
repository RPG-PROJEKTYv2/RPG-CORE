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

public enum Akce80_90 {
    I80_90_1("80-90-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&e&lSloneczna Tarcza").toItemStack()),
    I80_90_2("80-90-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&e&lSloneczny Pierscien").toItemStack()),
    I80_90_3("80-90-3", 11, new ItemBuilder(Material.WATCH).setName("&e&lSloneczny Diadem").toItemStack()),
    I80_90_4("80-90-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&e&lSloneczny Naszyjnik").toItemStack()),
    I80_90_5("80-90-5", 9, new ItemBuilder(Material.HOPPER_MINECART).setName("&e&lSloneczne Kolczyki").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Akce80_90(String name, double dropChance, ItemStack item) {
        this.name = name;
        this.dropChance = dropChance;
        this.item = item;
    }
    public String getName() { return name; }
    public double getDropChance() { return dropChance; }
    public ItemStack getItem() { return item; }
    public static Akce80_90 getByName(String name) {
        for (Akce80_90 item : Akce80_90.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce80_90 item : Akce80_90.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(399, 1100),
                            ChanceHelper.getRandInt(20, 26), ChanceHelper.getRandInt(15, 21), ChanceHelper.getRandInt(80, 90), "&e&lSloneczny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(20, 27),
                            ChanceHelper.getRandInt(20, 38), ChanceHelper.getRandInt(5, 8), ChanceHelper.getRandInt(80, 90), "&e&lSloneczny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(27, 46),
                            ChanceHelper.getRandInt(30, 42), ChanceHelper.getRandInt(18, 26), ChanceHelper.getRandInt(80, 90), "&e&lSloneczna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(17, 23),
                            ChanceHelper.getRandInt(21, 32), ChanceHelper.getRandInt(-100, -65), ChanceHelper.getRandInt(80, 90), "&e&lSloneczne Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(16, 26), ChanceHelper.getRandInt(18, 27), ChanceHelper.getRandInt(60, 95),
                            ChanceHelper.getRandInt(80, 90), "&e&lSloneczny Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player,szczescie);
    }
}
