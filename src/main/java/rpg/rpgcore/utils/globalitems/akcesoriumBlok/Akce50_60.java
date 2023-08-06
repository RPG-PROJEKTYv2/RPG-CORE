package rpg.rpgcore.utils.globalitems.akcesoriumBlok;

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
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Akce50_60 {
    I50_60_1("50-60-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&f&lSniezna Tarcza").toItemStack()),
    I50_60_2("50-60-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&f&lSniezny Pierscien").toItemStack()),
    I50_60_3("50-60-3", 11, new ItemBuilder(Material.WATCH).setName("&f&lSniezny Diadem").toItemStack()),
    I50_60_4("50-60-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&f&lSniezny Naszyjnik").toItemStack()),
    I50_60_5("50-60-5", 9, new ItemBuilder(Material.HOPPER_MINECART).setName("&f&lSniezne Kolczyki").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Akce50_60(String name, double dropChance, ItemStack item) {
        this.name = name;
        this.dropChance = dropChance;
        this.item = item;
    }
    public String getName() { return name; }
    public double getDropChance() { return dropChance; }
    public ItemStack getItem() { return item; }
    public static Akce50_60 getByName(String name) {
        for (Akce50_60 item : Akce50_60.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce50_60 item : Akce50_60.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(80, 120),
                            ChanceHelper.getRandInt(13, 19), ChanceHelper.getRandInt(10, 16), ChanceHelper.getRandInt(50, 60), "&f&lSniezny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(20, 22),
                            ChanceHelper.getRandInt(20, 28), ChanceHelper.getRandInt(3, 6), ChanceHelper.getRandInt(50, 60), "&f&lSniezny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(25, 33),
                            ChanceHelper.getRandInt(18, 29), ChanceHelper.getRandInt(8, 13), ChanceHelper.getRandInt(50, 60), "&f&lSniezna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(12, 14),
                            ChanceHelper.getRandInt(16, 18), ChanceHelper.getRandInt(-60, -50), ChanceHelper.getRandInt(50, 60), "&f&lSniezne Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(15, 19), ChanceHelper.getRandInt(15, 20), ChanceHelper.getRandInt(45, 65),
                            ChanceHelper.getRandInt(50, 60), "&f&lSniezny Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player,szczescie);
    }
}
