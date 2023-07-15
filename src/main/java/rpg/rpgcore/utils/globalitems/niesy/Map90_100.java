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

public enum Map90_100 {
    I90_100_1("90-100-1", 12, ItemHelper.createArmor("&9&lSkradziony Helm", Material.DIAMOND_HELMET, 67, 15)),
    I90_100_2("90-100-2", 12, ItemHelper.createArmor("&9&lSkradziona Kamizelka", Material.DIAMOND_CHESTPLATE, 70, 15)),
    I90_100_3("90-100-3", 12, ItemHelper.createArmor("&9&lSkradzione Spodnie", Material.DIAMOND_LEGGINGS, 68, 15)),
    I90_100_4("90-100-4", 12, ItemHelper.createArmor("&9&lSkradzione Trzewiki", Material.DIAMOND_BOOTS, 63, 15)),
    I90_100_5("90-100-5", 12, ItemHelper.createSword("&9&lSkradziony Miecz", Material.DIAMOND_SWORD, 58, 30,true)),

    I90_100_6("90-100-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&9&lSkradziony Naszyjnik").toItemStack()),
    I90_100_7("90-100-7", 10, new ItemBuilder(Material.WATCH).setName("&9&lSkradziony Diadem").toItemStack()),
    I90_100_8("90-100-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&9&lSkradziona Tarcza").toItemStack()),
    I90_100_9("90-100-9", 10, new ItemBuilder(Material.HOPPER_MINECART).setName("&9&lSkradzione Kolczyki").toItemStack()),
    I90_100_10("90-100-10", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&9&lSkradziony Pierscien").toItemStack()),
    I90_100_11("90-100-11", 6, new ItemBuilder(Material.MINECART).setName("&9&lSkradziona Energia").toItemStack()),
    I90_100_12("90-100-12", 6, new ItemBuilder(Material.FIREBALL).setName("&9&lSkradziony Medalion").toItemStack()),
    I90_100_13("90-100-13", 6, new ItemBuilder(Material.LEASH).setName("&9&lSkradziony Pas").toItemStack()),
    I90_100_14("90-100-14", 6, new ItemBuilder(Material.LADDER).setName("&9&lSkradziona Szarfa").toItemStack());

    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map90_100(String name, double dropChance, ItemStack item) {
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

    public static Map90_100 getByName(String name) {
        for (Map90_100 item : Map90_100.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map90_100 item : Map90_100.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(540, 1800),
                            ChanceHelper.getRandInt(20, 28), ChanceHelper.getRandInt(15, 23), ChanceHelper.getRandInt(90, 100), "&9&lSkradziony Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(20, 29),
                            ChanceHelper.getRandInt(20, 44), ChanceHelper.getRandInt(5, 8), ChanceHelper.getRandInt(90, 100), "&9&lSkradziony Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(30, 53),
                            ChanceHelper.getRandInt(35, 48), ChanceHelper.getRandInt(20, 30), ChanceHelper.getRandInt(90, 100), "&9&lSkradziona Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(18, 25),
                            ChanceHelper.getRandInt(21, 35), ChanceHelper.getRandInt(-87, -75), ChanceHelper.getRandInt(90, 100), "&9&lSkradzione Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(18, 30), ChanceHelper.getRandInt(19, 29), ChanceHelper.getRandInt(60, 100),
                            ChanceHelper.getRandInt(90, 100), "&9&lSkradziony Pierscien"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.MINECART) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-45, -33), ChanceHelper.getRandInt(50, 61),
                            ChanceHelper.getRandInt(47, 68), ChanceHelper.getRandDouble(0.2, 0.4), ChanceHelper.getRandInt(-60, -45),ChanceHelper.getRandInt(90, 100), "&9&lSkradziona Energia"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.FIREBALL) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createMedalion(ChanceHelper.getRandInt(19, 27), ChanceHelper.getRandInt(19, 30), ChanceHelper.getRandInt(90, 100),"&9&lSkradziony Medalion"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.LEASH) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createPas(ChanceHelper.getRandInt(22, 36), ChanceHelper.getRandInt(22, 36), ChanceHelper.getRandInt(90, 100), "&9&lSkradziony Pas"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.LADDER) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createSzarfa(ChanceHelper.getRandInt(15, 21), ChanceHelper.getRandInt(15, 21), ChanceHelper.getRandInt(90, 100), "&9&lSkradziona Szarfa"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&cNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
