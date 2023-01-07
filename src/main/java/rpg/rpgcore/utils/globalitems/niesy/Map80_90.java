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

public enum Map80_90 {
    I80_90_1("80-90-1", 12, ItemHelper.createArmor("&eSloneczny Kapelusz", Material.DIAMOND_HELMET, 60, 14)),
    I80_90_2("80-90-2", 12, ItemHelper.createArmor("&eSloneczna Zbroja", Material.DIAMOND_CHESTPLATE, 63, 14)),
    I80_90_3("80-90-3", 12, ItemHelper.createArmor("&eSloneczne Spodenki", Material.DIAMOND_LEGGINGS, 62, 14)),
    I80_90_4("80-90-4", 12, ItemHelper.createArmor("&eSloneczne Klapki", Material.DIAMOND_BOOTS, 59, 14)),
    I80_90_5("80-90-5", 12, ItemHelper.createSword("&eSloneczna Szabla", Material.DIAMOND_SWORD, 52, 23,true)),

    I80_90_6("80-90-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&eSloneczny Naszyjnik").toItemStack()),
    I80_90_7("80-90-7", 10, new ItemBuilder(Material.WATCH).setName("&eSloneczny Diadem").toItemStack()),
    I80_90_8("80-90-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&eSloneczna Tarcza").toItemStack()),
    I80_90_9("80-90-9", 10, new ItemBuilder(Material.HOPPER_MINECART).setName("&eSloneczne Kolczyki").toItemStack()),
    I80_90_10("80-90-10", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&eSloneczny Pierscien").toItemStack()),
    I80_90_11("80-90-11", 6, new ItemBuilder(Material.MINECART).setName("&eSloneczna Energia").toItemStack()),
    I80_90_12("80-90-12", 6, new ItemBuilder(Material.FIREBALL).setName("&eSloneczny Medalion").toItemStack()),
    I80_90_13("80-90-13", 6, new ItemBuilder(Material.LEASH).setName("&eSloneczny Pas").toItemStack()),
    I80_90_14("80-90-14", 6, new ItemBuilder(Material.LADDER).setName("&eSloneczna Szarfa").toItemStack());

    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map80_90(String name, double dropChance, ItemStack item) {
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

    public static Map80_90 getByName(String name) {
        for (Map80_90 item : Map80_90.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        for (Map80_90 item : Map80_90.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(299, 1100),
                            ChanceHelper.getRandInt(20, 26), ChanceHelper.getRandInt(15, 21), ChanceHelper.getRandInt(80, 90), "&eSloneczny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(17, 27),
                            ChanceHelper.getRandInt(17, 38), ChanceHelper.getRandInt(5, 8), ChanceHelper.getRandInt(80, 90), "&eSloneczny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(25, 46),
                            ChanceHelper.getRandInt(30, 57), ChanceHelper.getRandInt(18, 26), ChanceHelper.getRandInt(80, 90), "&eSloneczna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(15, 23),
                            ChanceHelper.getRandInt(20, 32), ChanceHelper.getRandInt(-100, -65), ChanceHelper.getRandInt(80, 90), "&eSloneczne Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(9, 26), ChanceHelper.getRandInt(12, 27), ChanceHelper.getRandInt(25, 95),
                            ChanceHelper.getRandInt(80, 90), "&eSloneczny Pierscien"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.MINECART) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-36, -28), ChanceHelper.getRandInt(40, 55),
                            ChanceHelper.getRandInt(45, 63), ChanceHelper.getRandDouble(0.2, 0.35), ChanceHelper.getRandInt(-60, -45),ChanceHelper.getRandInt(80, 90), "&eSloneczna Energia"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.FIREBALL) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createMedalion(ChanceHelper.getRandInt(16, 24), ChanceHelper.getRandInt(14, 20), ChanceHelper.getRandInt(80, 90),"&eSloneczny Medalion"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.LEASH) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createPas(ChanceHelper.getRandInt(19, 30), ChanceHelper.getRandInt(19, 30), ChanceHelper.getRandInt(80, 90), "&eSloneczny Pas"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.LADDER) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createSzarfa(ChanceHelper.getRandInt(12, 17), ChanceHelper.getRandInt(12, 17), ChanceHelper.getRandInt(1, 5), "&eSloneczna Szarfa"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
