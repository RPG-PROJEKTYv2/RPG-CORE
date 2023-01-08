package rpg.rpgcore.utils.globalitems.niesy;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaD.helpers.AkcesoriaDodatHelper;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum Map120_130 {
    I120_130_1("120-130-1", 12, ItemHelper.createArmor("&b&lNiebianski Helm", Material.DIAMOND_HELMET, 92, 15)),
    I120_130_2("120-130-2", 12, ItemHelper.createArmor("&b&lNiebianska Zbroja", Material.DIAMOND_CHESTPLATE, 95, 15)),
    I120_130_3("120-130-3", 12, ItemHelper.createArmor("&b&lNiebianskie Spodnie", Material.DIAMOND_LEGGINGS, 95, 15)),
    I120_130_4("120-130-4", 12, ItemHelper.createArmor("&b&lNiebianskie Klapki", Material.DIAMOND_BOOTS, 90, 15)),
    I120_130_5("120-130-5", 12, ItemHelper.createSword("&b&lNiebianski Sztylet", Material.DIAMOND_SWORD, 92, 48,true)),

    I120_130_6("120-130-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&f&lAnielski Naszyjnik").toItemStack()),
    I120_130_7("120-130-7", 10, new ItemBuilder(Material.WATCH).setName("&f&lAnielski Diadem").toItemStack()),
    I120_130_8("120-130-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&f&lAnielska Tarcza").toItemStack()),
    I120_130_9("120-130-9", 10, new ItemBuilder(Material.HOPPER_MINECART).setName("&f&lAnielskie Kolczyki").toItemStack()),
    I120_130_10("120-130-10", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&f&lAnielski Pierscien").toItemStack()),
    I120_130_11("120-130-11", 6, new ItemBuilder(Material.MINECART).setName("&f&lAnielska Energia").toItemStack()),
    I120_130_12("120-130-12", 6, new ItemBuilder(Material.FIREBALL).setName("&f&lAnielski Medalion").toItemStack()),
    I120_130_13("120-130-13", 6, new ItemBuilder(Material.LEASH).setName("&f&lAnielski Pas").toItemStack()),
    I120_130_14("120-130-14", 6, new ItemBuilder(Material.LADDER).setName("&f&lAnielska Szarfa").toItemStack());

    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map120_130(String name, double dropChance, ItemStack item) {
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

    public static Map120_130 getByName(String name) {
        for (Map120_130 item : Map120_130.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        for (Map120_130 item : Map120_130.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f&l" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(3500, 6000),
                            ChanceHelper.getRandInt(20, 36), ChanceHelper.getRandInt(20, 30), ChanceHelper.getRandInt(120, 130), "&f&lAnielski Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(30, 40),
                            ChanceHelper.getRandInt(40, 75), ChanceHelper.getRandInt(8, 15), ChanceHelper.getRandInt(120, 130), "&f&lAnielski Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(40, 78),
                            ChanceHelper.getRandInt(40, 80), ChanceHelper.getRandInt(40, 60), ChanceHelper.getRandInt(120, 130), "&f&lAnielska Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(20, 35),
                            ChanceHelper.getRandInt(30, 50), ChanceHelper.getRandInt(-145, -120), ChanceHelper.getRandInt(120, 130), "&f&lAnielskie Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(30, 40), ChanceHelper.getRandInt(25, 40), ChanceHelper.getRandInt(100, 125),
                            ChanceHelper.getRandInt(120, 130), "&f&lAnielski Pierscien"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.MINECART) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-65, -47), ChanceHelper.getRandInt(65, 85),
                            ChanceHelper.getRandInt(65, 85), ChanceHelper.getRandDouble(0.3, 0.6), ChanceHelper.getRandInt(-85, -60),ChanceHelper.getRandInt(120, 130), "&f&lAnielska Energia"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.FIREBALL) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createMedalion(ChanceHelper.getRandInt(25, 35), ChanceHelper.getRandInt(30, 45), ChanceHelper.getRandInt(120, 130),"&f&lAnielski Medalion"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.LEASH) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createPas(ChanceHelper.getRandInt(35, 50), ChanceHelper.getRandInt(35, 50), ChanceHelper.getRandInt(120, 130), "&f&lAnielski Pas"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.LADDER) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createSzarfa(ChanceHelper.getRandInt(21, 30), ChanceHelper.getRandInt(21, 30), ChanceHelper.getRandInt(120, 130), "&f&lAnielska Szarfa"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
