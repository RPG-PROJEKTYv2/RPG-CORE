package rpg.rpgcore.utils.globalitems.akcesoriumNEW;

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

public enum Akce60_70 {
    I60_70_1("60-70-1", 13, new ItemBuilder(Material.ITEM_FRAME).setName("&4&lOgnista Tarcza").toItemStack()),
    I60_70_2("60-70-2", 12, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&4&lOgnisty Pierscien").toItemStack()),
    I60_70_3("60-70-3", 11, new ItemBuilder(Material.WATCH).setName("&4&lOgnisty Diadem").toItemStack()),
    I60_70_4("60-70-4", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&4&lOgnisty Naszyjnik").toItemStack()),
    I60_70_5("60-70-5", 9, new ItemBuilder(Material.HOPPER_MINECART).setName("&4&lOgniste Kolczyki").toItemStack());
    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Akce60_70(String name, double dropChance, ItemStack item) {
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

    public ItemStack getItem() {
        return item;
    }

    public static Akce60_70 getByName(String name) {
        for (Akce60_70 item : Akce60_70.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Akce60_70 item : Akce60_70.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItem(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(145, 200),
                            ChanceHelper.getRandInt(16, 22), ChanceHelper.getRandInt(12, 18), ChanceHelper.getRandInt(60, 70), "&4&lOgnisty Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(20, 23),
                            ChanceHelper.getRandInt(20, 31), ChanceHelper.getRandInt(3, 7), ChanceHelper.getRandInt(60, 70), "&4&lOgnisty Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(26, 37),
                            ChanceHelper.getRandInt(22, 31), ChanceHelper.getRandInt(12, 16), ChanceHelper.getRandInt(60, 70), "&4&lOgnista Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(14, 17),
                            ChanceHelper.getRandInt(18, 23), ChanceHelper.getRandInt(-65, -50), ChanceHelper.getRandInt(60, 70), "&4&lOgniste Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(15, 22), ChanceHelper.getRandInt(15, 23), ChanceHelper.getRandInt(20, 80),
                            ChanceHelper.getRandInt(60, 70), "&4&lOgnisty Pierscien"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        getDrop(player, szczescie);
    }
}