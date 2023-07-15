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

public enum Map70_80 {
    I70_80_1("70-80-1", 12, ItemHelper.createArmor("&7&lMglisty Helm", Material.DIAMOND_HELMET, 57, 14)),
    I70_80_2("70-80-2", 12, ItemHelper.createArmor("&7&lMglista Wiatrowka", Material.DIAMOND_CHESTPLATE, 58, 14)),
    I70_80_3("70-80-3", 12, ItemHelper.createArmor("&7&lMgliste Galoty", Material.DIAMOND_LEGGINGS, 60, 15)),
    I70_80_4("70-80-4", 12, ItemHelper.createArmor("&7&lMgliste Buty", Material.DIAMOND_BOOTS, 55, 20)),
    I70_80_5("70-80-5", 12, ItemHelper.createSword("&7&lMglisty Noz", Material.DIAMOND_SWORD, 44, 20,true)),

    I70_80_6("70-80-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&7&lMglisty Naszyjnik").toItemStack()),
    I70_80_7("70-80-7", 10, new ItemBuilder(Material.WATCH).setName("&7&lMglisty Diadem").toItemStack()),
    I70_80_8("70-80-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&7&lMglista Tarcza").toItemStack()),
    I70_80_9("70-80-9", 10, new ItemBuilder(Material.HOPPER_MINECART).setName("&7&lMgliste Kolczyki").toItemStack()),
    I70_80_10("70-80-10", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&7&lMglisty Pierscien").toItemStack()),
    I70_80_11("70-80-11", 6, new ItemBuilder(Material.MINECART).setName("&7&lMglista Energia").toItemStack()),
    I70_80_12("70-80-12", 6, new ItemBuilder(Material.FIREBALL).setName("&7&lMglisty Medalion").toItemStack()),
    I70_80_13("70-80-13", 6, new ItemBuilder(Material.LEASH).setName("&7&lMglisty Pas").toItemStack());

    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map70_80(String name, double dropChance, ItemStack item) {
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

    public static Map70_80 getByName(String name) {
        for (Map70_80 item : Map70_80.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map70_80 item : Map70_80.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
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
                            ChanceHelper.getRandInt(50, 90), "&7&lMglisty Pierscien"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.MINECART) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-30, -21), ChanceHelper.getRandInt(33, 46),
                            ChanceHelper.getRandInt(38, 58), ChanceHelper.getRandDouble(0.1, 0.3), ChanceHelper.getRandInt(-55, -40),ChanceHelper.getRandInt(70, 80), "&7&lMglista Energia"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.FIREBALL) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createMedalion(ChanceHelper.getRandInt(13, 21), ChanceHelper.getRandInt(11, 15), ChanceHelper.getRandInt(70, 80),"&7&lMglisty Medalion"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.LEASH) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createPas(ChanceHelper.getRandInt(16, 25), ChanceHelper.getRandInt(16, 25), ChanceHelper.getRandInt(70, 80), "&7&lMglisty Pas"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
