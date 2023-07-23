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

public enum Akce70_80 {
    I70_80_1("70-80-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&7&lMglista Tarcza").toItemStack()),
    I70_80_2("70-80-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&7&lMglisty Pierscien").toItemStack()),
    I70_80_3("70-80-3", 11, new ItemBuilder(Material.WATCH).setName("&7&lMglisty Diadem").toItemStack()),
    I70_80_4("70-80-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&7&lMglisty Naszyjnik").toItemStack()),
    I70_80_5("70-80-5", 9, new ItemBuilder(Material.HOPPER_MINECART).setName("&7&lMgliste Kolczyki").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Akce70_80(String name, double dropChance, ItemStack item) {
        this.name = name;
        this.dropChance = dropChance;
        this.item = item;
    }
    public String getName() { return name; }
    public double getDropChance() { return dropChance; }
    public ItemStack getItem() { return item; }
    public static Akce70_80 getByName(String name) {
        for (Akce70_80 item : Akce70_80.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce70_80 item : Akce70_80.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(298, 500),
                            ChanceHelper.getRandInt(18, 24), ChanceHelper.getRandInt(12, 20), ChanceHelper.getRandInt(70, 80), "&7&lMglisty Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(20, 26),
                            ChanceHelper.getRandInt(20, 35), ChanceHelper.getRandInt(4, 7), ChanceHelper.getRandInt(70, 80), "&7&lMglisty Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(27, 41),
                            ChanceHelper.getRandInt(28, 37), ChanceHelper.getRandInt(14, 22), ChanceHelper.getRandInt(70, 80), "&7&lMglista Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(15, 20),
                            ChanceHelper.getRandInt(19, 28), ChanceHelper.getRandInt(-77, -60), ChanceHelper.getRandInt(70, 80), "&7&lMgliste Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(15, 24), ChanceHelper.getRandInt(15, 25), ChanceHelper.getRandInt(20, 90),
                            ChanceHelper.getRandInt(70, 80), "&7&lMglisty Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player,szczescie);
    }
}
