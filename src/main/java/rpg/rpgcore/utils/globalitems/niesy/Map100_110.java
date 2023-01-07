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

public enum Map100_110 {
    I100_110_1("100-110-1", 12, ItemHelper.createArmor("&bPodwodny Czepek", Material.DIAMOND_HELMET, 75, 15)),
    I100_110_2("100-110-2", 12, ItemHelper.createArmor("&bPodwodna Kamizelka", Material.DIAMOND_CHESTPLATE, 78, 15)),
    I100_110_3("100-110-3", 12, ItemHelper.createArmor("&bPodwodne Gacie", Material.DIAMOND_LEGGINGS, 77, 15)),
    I100_110_4("100-110-4", 12, ItemHelper.createArmor("&bPodwodne Pletwy", Material.DIAMOND_BOOTS, 71, 15)),
    I100_110_5("100-110-5", 12, ItemHelper.createSword("&bPodwodny Sztylet", Material.DIAMOND_SWORD, 67, 33,true)),

    I100_110_6("100-110-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&bMityczny Naszyjnik").toItemStack()),
    I100_110_7("100-110-7", 10, new ItemBuilder(Material.WATCH).setName("&bMityczny Diadem").toItemStack()),
    I100_110_8("100-110-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&bMityczna Tarcza").toItemStack()),
    I100_110_9("100-110-9", 10, new ItemBuilder(Material.HOPPER_MINECART).setName("&bMityczne Kolczyki").toItemStack()),
    I100_110_10("100-110-10", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&bMityczny Pierscien").toItemStack()),
    I100_110_11("100-110-11", 6, new ItemBuilder(Material.MINECART).setName("&bMityczna Energia").toItemStack()),
    I100_110_12("100-110-12", 6, new ItemBuilder(Material.FIREBALL).setName("&bMityczny Medalion").toItemStack()),
    I100_110_13("100-110-13", 6, new ItemBuilder(Material.LEASH).setName("&bMityczny Pas").toItemStack()),
    I100_110_14("100-110-14", 6, new ItemBuilder(Material.LADDER).setName("&bMityczna Szarfa").toItemStack());

    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map100_110(String name, double dropChance, ItemStack item) {
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

    public static Map100_110 getByName(String name) {
        for (Map100_110 item : Map100_110.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        for (Map100_110 item : Map100_110.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(1337, 2700),
                            ChanceHelper.getRandInt(20, 30), ChanceHelper.getRandInt(20, 25), ChanceHelper.getRandInt(100, 110), "&bMityczny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(22, 32),
                            ChanceHelper.getRandInt(27, 52), ChanceHelper.getRandInt(6, 10), ChanceHelper.getRandInt(100, 110), "&bMityczny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(30, 60),
                            ChanceHelper.getRandInt(35, 68), ChanceHelper.getRandInt(25, 37), ChanceHelper.getRandInt(100, 110), "&bMityczna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(17, 28),
                            ChanceHelper.getRandInt(25, 40), ChanceHelper.getRandInt(-98, -80), ChanceHelper.getRandInt(100, 110), "&bMityczne Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(16, 34), ChanceHelper.getRandInt(19, 32), ChanceHelper.getRandInt(30, 105),
                            ChanceHelper.getRandInt(100, 110), "&bMityczny Pierscien"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.MINECART) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-52, -39), ChanceHelper.getRandInt(58, 68),
                            ChanceHelper.getRandInt(57, 75), ChanceHelper.getRandDouble(0.25, 0.45), ChanceHelper.getRandInt(-70, -50),ChanceHelper.getRandInt(100, 110), "&bMityczna Energia"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.FIREBALL) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createMedalion(ChanceHelper.getRandInt(22, 30), ChanceHelper.getRandInt(22, 35), ChanceHelper.getRandInt(100, 110),"&bMityczny Medalion"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.LEASH) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createPas(ChanceHelper.getRandInt(25, 41), ChanceHelper.getRandInt(25, 41), ChanceHelper.getRandInt(100, 110), "&bMityczny Pas"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.LADDER) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createSzarfa(ChanceHelper.getRandInt(18, 25), ChanceHelper.getRandInt(18, 25), ChanceHelper.getRandInt(1, 5), "&bMityczna Szarfa"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
