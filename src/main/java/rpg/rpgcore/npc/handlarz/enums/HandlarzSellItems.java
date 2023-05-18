package rpg.rpgcore.npc.handlarz.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

public enum HandlarzSellItems {
    // 1-10 MOB
    I1("Helm Rozbojnika", Material.LEATHER_HELMET, 10.0),
    I2("Zbroja Rozbojnika", Material.LEATHER_CHESTPLATE,10.0),
    I3("Spodnie Rozbojnika", Material.LEATHER_LEGGINGS,  10.0),
    I4("Buty Rozbojnika", Material.LEATHER_BOOTS, 10.0),
    I5("Tepy Miecz Rozbojnika", Material.WOOD_SWORD, 12.0),
    // 1-10 BOSS
    I6("Helm Dowodcy Rozbojnikow", Material.LEATHER_HELMET, 12.0),
    I7("Zbroja Dowodcy Rozbojnikow", Material.LEATHER_CHESTPLATE, 12.0),
    I8("Spodnie Dowodcy Rozbojnikow", Material.LEATHER_LEGGINGS, 12.0),
    I9("Buty Dowodcy Rozbojnikow", Material.LEATHER_BOOTS, 12.0),
    I10("Miecz Dowodcy Rozbojnikow", Material.WOOD_SWORD, 15.0),
    // 1-10 BOSS AKCE ZWYKŁE
    I11("Zwykly Naszyjnik Dowodcy Rozbojnikow", Material.WOOD_SWORD, 25.0),
    I12("Zwykly Diadem Dowodcy Rozbojnikow", Material.WOOD_SWORD, 20.0),
    I13("Zwykly Pierscien Dowodcy Rozbojnikow", Material.WOOD_SWORD, 20.0),
    I14("Zwykla Tarcza Dowodcy Rozbojnikow", Material.WOOD_SWORD, 25.0),
    // 1-10 BOSS AKCE ULEPSZONE
    I15("Ulepszony Naszyjnik Dowodcy Rozbojnikow", Material.STORAGE_MINECART, 35.0),
    I16("Ulepszony Diadem Dowodcy Rozbojnikow", Material.WATCH, 30.0),
    I17("Ulepszony Pierscien Dowodcy Rozbojnikow", Material.EXPLOSIVE_MINECART, 30.0),
    I18("Ulepszona Tarcza Dowodcy Rozbojnikow", Material.ITEM_FRAME, 35.0),
    
    // 10-20 MOB
    I19("Helm Goblina", Material.LEATHER_HELMET, 15.0),
    I20("Zbroja Goblina", Material.LEATHER_CHESTPLATE,17.0),
    I21("Spodnie Goblina", Material.LEATHER_LEGGINGS,  16.0),
    I22("Buty Goblina", Material.LEATHER_BOOTS, 14.0),
    I23("Miecz Goblina", Material.WOOD_SWORD, 18.0),
    // 10-20 BOSS
    I24("Helm Wodza Goblinow", Material.LEATHER_HELMET, 20.0),
    I25("Zbroja Wodza Goblinow", Material.LEATHER_CHESTPLATE, 22.0),
    I26("Spodnie Wodza Goblinow", Material.LEATHER_LEGGINGS, 21.0),
    I27("Buty Wodza Goblinow", Material.LEATHER_BOOTS, 19.0),
    I28("Miecz Wodza Goblinow", Material.WOOD_SWORD, 23.0),
    // 10-20 BOSS AKCE ZWYKŁE
    I29("Zwykly Naszyjnik Wodza Goblinow", Material.STORAGE_MINECART, 35.0),
    I30("Zwykly Diadem Wodza Goblinow", Material.WATCH, 30.0),
    I31("Zwykly Pierscien Wodza Goblinow", Material.EXPLOSIVE_MINECART, 30.0),
    I32("Zwykla Tarcza Wodza Goblinow", Material.ITEM_FRAME, 35.0),
    // 10-20 BOSS AKCE ULEPSZONE
    I33("Ulepszony Naszyjnik Wodza Goblinow", Material.STORAGE_MINECART, 50.0),
    I34("Ulepszony Diadem Wodza Goblinow", Material.WATCH, 45.0),
    I35("Ulepszony Pierscien Wodza Goblinow", Material.EXPLOSIVE_MINECART, 45.0),
    I36("Ulepszona Tarcza Wodza Goblinow", Material.ITEM_FRAME, 50.0),

    // 20-30 MOB
    I37("Helm Goryla", Material.LEATHER_HELMET, 17.0),
    I38("Zbroja Goryla", Material.CHAINMAIL_CHESTPLATE,19.0),
    I39("Spodnie Goryla", Material.LEATHER_LEGGINGS,  18.0),
    I40("Buty Goryla", Material.LEATHER_BOOTS, 16.0),
    I41("Miecz Goryla", Material.IRON_SWORD, 20.0),
    // 20-30 BOSS
    I42("Helm Krola Goryli", Material.GOLD_HELMET, 22.0),
    I43("Zbroja Krola Goryli", Material.GOLD_CHESTPLATE, 24.0),
    I44("Spodnie Krola Goryli", Material.GOLD_LEGGINGS, 23.0),
    I45("Buty Krola Goryli", Material.GOLD_BOOTS, 21.0),
    I46("Miecz Krola Goryli", Material.GOLD_SWORD, 25.0),
    // 20-30 BOSS AKCE ZWYKŁE
    I47("Zwykly Naszyjnik Krola Goryli", Material.STORAGE_MINECART, 45.0),
    I48("Zwykly Diadem Krola Goryli", Material.WATCH, 40.0),
    I49("Zwykly Pierscien Krola Goryli", Material.EXPLOSIVE_MINECART, 40.0),
    I50("Zwykla Tarcza Krola Goryli", Material.ITEM_FRAME, 45.0),
    // 20-30 BOSS AKCE ULEPSZONE
    I51("Ulepszony Naszyjnik Krola Goryli", Material.STORAGE_MINECART, 65.0),
    I52("Ulepszony Diadem Krola Goryli", Material.WATCH, 60.0),
    I53("Ulepszony Pierscien Krola Goryli", Material.EXPLOSIVE_MINECART, 60.0),
    I54("Ulepszona Tarcza Krola Goryli", Material.ITEM_FRAME, 65.0),

    // 30-40 MOB
    I_ERROR("ERROR", Material.AIR, 0);
    private final String name;
    private final Material material;
    private final double price;

    HandlarzSellItems(String name, Material material, double price) {
        this.name = name;
        this.material = material;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public double getPrice(final ItemStack item) {
        double dodatek = 0;
        if (item.getType().toString().contains("_SWORD")) {
            final int sharp = Utils.getTagInt(item, "dmg");
            int mnoznik = 0;
            if (sharp > 20) {
                mnoznik = 50;
            }
            if (sharp > 60) {
                mnoznik = 100;
            }
            if (sharp > 250) {
                mnoznik = 200;
            }
            if (sharp > 500) {
                mnoznik = 300;
            }
            if (sharp > 1000) {
                mnoznik = 400;
            }
            if (sharp > 2000) {
                mnoznik = 500;
            }
            if (sharp >= 2500) {
                mnoznik = 600;
            }
            dodatek += sharp * mnoznik;
            final int moby = Utils.getTagInt(item, "moby");
            if (moby > 20) {
                mnoznik = 50;
            }
            if (moby > 60) {
                mnoznik = 100;
            }
            if (moby > 250) {
                mnoznik = 200;
            }
            if (moby > 500) {
                mnoznik = 300;
            }
            if (moby > 1000) {
                mnoznik = 400;
            }
            if (moby > 2000) {
                mnoznik = 500;
            }
            if (moby >= 2500) {
                mnoznik = 600;
            }
            dodatek += moby * mnoznik;
        } else if (item.getType().toString().contains("_HELMET") || item.getType().toString().contains("_CHESTPLATE") || item.getType().toString().contains("_LEGGINGS") || item.getType().toString().contains("_BOOTS")) {
            final int prot = Utils.getTagInt(item, "prot");
            int mnoznik = 0;
            if (prot > 20) {
                mnoznik = 50;
            }
            if (prot > 50) {
                mnoznik = 75;
            }
            if (prot > 60) {
                mnoznik = 100;
            }
            if (prot > 80) {
                mnoznik = 125;
            }
            if (prot > 100) {
                mnoznik = 150;
            }
            if (prot > 120) {
                mnoznik = 175;
            }
            if (prot > 140) {
                mnoznik = 200;
            }
            if (prot > 160) {
                mnoznik = 225;
            }
            if (prot > 180) {
                mnoznik = 250;
            }
            if (prot > 200) {
                mnoznik = 275;
            }
            if (prot > 220) {
                mnoznik = 300;
            }
            if (prot > 240) {
                mnoznik = 325;
            }
            if (prot >= 250) {
                mnoznik = 350;
            }
            dodatek += prot * mnoznik;

            final double thorns = Utils.getTagDouble(item, "thorns");
            if (thorns > 10) {
                mnoznik = 50;
            }
            if (thorns > 20) {
                mnoznik = 100;
            }
            if (thorns > 30) {
                mnoznik = 150;
            }
            if (thorns > 40) {
                mnoznik = 200;
            }
            if (thorns >= 50) {
                mnoznik = 250;
            }
            dodatek += thorns * mnoznik;
        }
        return DoubleUtils.round(price + dodatek, 2);
    }

    public static HandlarzSellItems checkIfSellItem(final ItemStack item) {
        for (final HandlarzSellItems items : HandlarzSellItems.values()) {
            if (items.getMaterial() == item.getType() && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && Utils.removeColor(item.getItemMeta().getDisplayName()).equals(items.getName())) {
                return items;
            }
        }
        return null;
    }
}
