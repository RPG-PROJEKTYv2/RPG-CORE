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

public enum Map110_120 {
    I110_120_1("110-120-1", 12, ItemHelper.createArmor("&f&fMrozny Kaszkiet", Material.DIAMOND_HELMET, 84, 15)),
    I110_120_2("110-120-2", 12, ItemHelper.createArmor("&f&fMrozna Koszula", Material.DIAMOND_CHESTPLATE, 87, 15)),
    I110_120_3("110-120-3", 12, ItemHelper.createArmor("&f&lMrozne Spodnie", Material.DIAMOND_LEGGINGS, 86, 15)),
    I110_120_4("110-120-4", 12, ItemHelper.createArmor("&f&lMrozne Buty", Material.DIAMOND_BOOTS, 82, 15)),
    I110_120_5("110-120-5", 12, ItemHelper.createSword("&f&lMrozna Katana", Material.DIAMOND_SWORD, 77, 43,true)),

    I110_120_6("110-120-6", 10, new ItemBuilder(Material.STORAGE_MINECART).setName("&3&lSzkarlatny Naszyjnik").toItemStack()),
    I110_120_7("110-120-7", 10, new ItemBuilder(Material.WATCH).setName("&3&lSzkarlatny Diadem").toItemStack()),
    I110_120_8("110-120-8", 10, new ItemBuilder(Material.ITEM_FRAME).setName("&3&lSzkarlatna Tarcza").toItemStack()),
    I110_120_9("110-120-9", 10, new ItemBuilder(Material.HOPPER_MINECART).setName("&3&lSzkarlatne Kolczyki").toItemStack()),
    I110_120_10("110-120-10", 10, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&3&lSzkarlatny Pierscien").toItemStack()),
    I110_120_11("110-120-11", 6, new ItemBuilder(Material.MINECART).setName("&3&lSzkarlatna Energia").toItemStack()),
    I110_120_12("110-120-12", 6, new ItemBuilder(Material.FIREBALL).setName("&3&lSzkarlatny Medalion").toItemStack()),
    I110_120_13("110-120-13", 6, new ItemBuilder(Material.LEASH).setName("&3&lSzkarlatny Pas").toItemStack()),
    I110_120_14("110-120-14", 6, new ItemBuilder(Material.LADDER).setName("&3&lSzkarlatna Szarfa").toItemStack());

    private final String name;
    private final double dropChance;
    private final ItemStack item;

    Map110_120(String name, double dropChance, ItemStack item) {
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

    public static Map110_120 getByName(String name) {
        for (Map110_120 item : Map110_120.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public static void getDrop(final Player player, final double szczescie) {
        final Set<Items> drop = Sets.newConcurrentHashSet();
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Map110_120 item : Map110_120.values()) {
            drop.add(new Items(item.getName(), item.getDropChance(), item.getItemStack(), 1));
        }
        for (Items item : drop) {
            if (item.getChance() + szczescie >= 100.0 || item.getChance() + szczescie > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.STORAGE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(1890, 4090),
                            ChanceHelper.getRandInt(20, 33), ChanceHelper.getRandInt(25, 27), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatny Naszyjnik"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.WATCH) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandInt(25, 36),
                            ChanceHelper.getRandInt(30, 62), ChanceHelper.getRandInt(7, 12), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatny Diadem"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.ITEM_FRAME) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandInt(35, 68),
                            ChanceHelper.getRandInt(37, 60), ChanceHelper.getRandInt(30, 45), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatna Tarcza"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.HOPPER_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ChanceHelper.getRandInt(20, 32),
                            ChanceHelper.getRandInt(30, 44), ChanceHelper.getRandInt(-110, -90), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatne Kolczyki"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.EXPLOSIVE_MINECART) {
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandInt(20, 37), ChanceHelper.getRandInt(24, 36), ChanceHelper.getRandInt(38, 115),
                            ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatny Pierscien"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.MINECART) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-64, -43), ChanceHelper.getRandInt(67, 79),
                            ChanceHelper.getRandInt(60, 80), ChanceHelper.getRandDouble(0.3, 0.55), ChanceHelper.getRandInt(-75, -50),ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatna Energia"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.FIREBALL) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createMedalion(ChanceHelper.getRandInt(25, 32), ChanceHelper.getRandInt(25, 40), ChanceHelper.getRandInt(110, 120),"&3&lSzkarlatny Medalion"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.LEASH) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createPas(ChanceHelper.getRandInt(30, 45), ChanceHelper.getRandInt(30, 45), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatny Pas"));
                    return;
                }
                if (item.getRewardItem().getType() == Material.LADDER) {
                    player.getInventory().addItem(AkcesoriaDodatHelper.createSzarfa(ChanceHelper.getRandInt(20, 27), ChanceHelper.getRandInt(20, 27), ChanceHelper.getRandInt(110, 120), "&3&lSzkarlatna Szarfa"));
                    return;
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        if (user.isNiesDropEnabled()) player.sendMessage(Utils.format("&c&lNiestety niesamowity przedmiot okazal sie byc uszkodzony!"));
    }
}
