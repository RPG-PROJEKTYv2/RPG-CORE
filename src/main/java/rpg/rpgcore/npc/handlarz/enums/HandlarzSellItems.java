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
    I11("Zwykly Naszyjnik Dowodcy Rozbojnikow", Material.STORAGE_MINECART, 100.0),
    I12("Zwykly Diadem Dowodcy Rozbojnikow", Material.WATCH, 100.0),
    I13("Zwykly Pierscien Dowodcy Rozbojnikow", Material.EXPLOSIVE_MINECART, 100.0),
    I14("Zwykla Tarcza Dowodcy Rozbojnikow", Material.ITEM_FRAME, 100.0),
    // 1-10 BOSS AKCE ULEPSZONE
    I15("Ulepszony Naszyjnik Dowodcy Rozbojnikow", Material.STORAGE_MINECART, 120.0),
    I16("Ulepszony Diadem Dowodcy Rozbojnikow", Material.WATCH, 120.0),
    I17("Ulepszony Pierscien Dowodcy Rozbojnikow", Material.EXPLOSIVE_MINECART, 120.0),
    I18("Ulepszona Tarcza Dowodcy Rozbojnikow", Material.ITEM_FRAME, 120.0),
    
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
    I28("Miecz Wodza Goblinow", Material.STONE_SWORD, 23.0),
    // 10-20 BOSS AKCE ZWYKŁE
    I29("Zwykly Naszyjnik Wodza Goblinow", Material.STORAGE_MINECART, 120.0),
    I30("Zwykly Diadem Wodza Goblinow", Material.WATCH, 120.0),
    I31("Zwykly Pierscien Wodza Goblinow", Material.EXPLOSIVE_MINECART, 120.0),
    I32("Zwykla Tarcza Wodza Goblinow", Material.ITEM_FRAME, 120.0),
    // 10-20 BOSS AKCE ULEPSZONE
    I33("Ulepszony Naszyjnik Wodza Goblinow", Material.STORAGE_MINECART, 180.0),
    I34("Ulepszony Diadem Wodza Goblinow", Material.WATCH, 180.0),
    I35("Ulepszony Pierscien Wodza Goblinow", Material.EXPLOSIVE_MINECART, 180.0),
    I36("Ulepszona Tarcza Wodza Goblinow", Material.ITEM_FRAME, 180.0),

    // 20-30 MOB
    I37("Helm Goryla", Material.LEATHER_HELMET, 17.0),
    I38("Zbroja Goryla", Material.CHAINMAIL_CHESTPLATE,19.0),
    I39("Spodnie Goryla", Material.LEATHER_LEGGINGS,  18.0),
    I40("Buty Goryla", Material.LEATHER_BOOTS, 16.0),
    I41("Miecz Goryla", Material.STONE_SWORD, 20.0),
    // 20-30 BOSS
    I42("Helm Krola Goryli", Material.GOLD_HELMET, 22.0),
    I43("Zbroja Krola Goryli", Material.GOLD_CHESTPLATE, 24.0),
    I44("Spodnie Krola Goryli", Material.GOLD_LEGGINGS, 23.0),
    I45("Buty Krola Goryli", Material.GOLD_BOOTS, 21.0),
    I46("Miecz Krola Goryli", Material.GOLD_SWORD, 25.0),
    // 20-30 BOSS AKCE ZWYKŁE
    I47("Zwykly Naszyjnik Krola Goryli", Material.STORAGE_MINECART, 200.0),
    I48("Zwykly Diadem Krola Goryli", Material.WATCH, 200.0),
    I49("Zwykly Pierscien Krola Goryli", Material.EXPLOSIVE_MINECART, 200.0),
    I50("Zwykla Tarcza Krola Goryli", Material.ITEM_FRAME, 200.0),
    // 20-30 BOSS AKCE ULEPSZONE
    I51("Ulepszony Naszyjnik Krola Goryli", Material.STORAGE_MINECART, 250.0),
    I52("Ulepszony Diadem Krola Goryli", Material.WATCH, 250.0),
    I53("Ulepszony Pierscien Krola Goryli", Material.EXPLOSIVE_MINECART, 250.0),
    I54("Ulepszona Tarcza Krola Goryli", Material.ITEM_FRAME, 250.0),

    // 30-40 MOB
    I55("Helm Zjawy", Material.GOLD_HELMET, 30.0),
    I56("Zbroja Zjawy", Material.GOLD_CHESTPLATE,39.0),
    I57("Spodnie Zjawy", Material.GOLD_LEGGINGS,  38.0),
    I58("Buty Zjawy", Material.GOLD_BOOTS, 36.0),
    I59("Miecz Zjawy", Material.GOLD_SWORD, 30.0),
    // 30-40 BOSS
    I60("Helm Przekletej Duszy", Material.CHAINMAIL_HELMET, 52.0),
    I61("Zbroja Przekletej Duszy", Material.CHAINMAIL_CHESTPLATE, 74.0),
    I62("Spodnie Przekletej Duszy", Material.CHAINMAIL_LEGGINGS, 53.0),
    I63("Buty Przekletej Duszy", Material.CHAINMAIL_BOOTS, 41.0),
    I64("Miecz Przekletej Duszy", Material.STONE_SWORD, 65.0),
    // 30-40 BOSS AKCE ZWYKLE
    I65("Zwykly Naszyjnik Przekletej Duszy", Material.STORAGE_MINECART, 300.0),
    I66("Zwykly Diadem Przekletej Duszy", Material.WATCH, 300.0),
    I67("Zwykly Pierscien Przekletej Duszy", Material.EXPLOSIVE_MINECART, 300.0),
    I68("Zwykla Tarcza Przekletej Duszy", Material.ITEM_FRAME, 300.0),
    // 30-40 BOSS AKCE ULEPSZONE
    I69("Ulepszony Naszyjnik Przekletej Duszy", Material.STORAGE_MINECART, 400.0),
    I70("Ulepszony Diadem Przekletej Duszy", Material.WATCH, 400.0),
    I71("Ulepszony Pierscien Przekletej Duszy", Material.EXPLOSIVE_MINECART, 400.0),
    I72("Ulepszona Tarcza Przekletej Duszy", Material.ITEM_FRAME, 400.0),

    // 40-50 MOB
    I73("Helm Straznika Swiatyni", Material.GOLD_HELMET, 67.0),
    I74("Zbroja Straznika Swiatyni", Material.GOLD_HELMET,79.0),
    I75("Spodnie Straznika Swiatyni", Material.IRON_LEGGINGS,  58.0),
    I76("Buty Straznika Swiatyni", Material.IRON_BOOTS, 66.0),
    I77("Miecz Straznika Swiatyni", Material.GOLD_SWORD, 70.0),
    // 40-50 BOSS
    I78("Helm Trytona", Material.IRON_HELMET, 62.0),
    I79("Zbroja Trytona", Material.IRON_CHESTPLATE, 84.0),
    I80("Spodnie Trytona", Material.IRON_LEGGINGS, 73.0),
    I81("Buty Trytona", Material.IRON_BOOTS, 84.0),
    I82("Miecz Trytona", Material.IRON_SWORD, 95.0),
    // 40-50 BOSS AKCE ZWYKLE
    I83("Zwykly Naszyjnik Trytona", Material.STORAGE_MINECART, 500.0),
    I84("Zwykly Diadem Trytona", Material.WATCH, 500.0),
    I85("Zwykly Pierscien Trytona", Material.EXPLOSIVE_MINECART, 500.0),
    I86("Zwykla Tarcza Trytona", Material.ITEM_FRAME, 500.0),
    // 40-50 BOSS AKCE ULEPSZONE
    I87("Ulepszony Naszyjnik Trytona", Material.STORAGE_MINECART, 700.0),
    I88("Ulepszony Diadem Trytona", Material.WATCH, 700.0),
    I89("Ulepszony Pierscien Trytona", Material.EXPLOSIVE_MINECART, 700.0),
    I90("Ulepszona Tarcza Trytona", Material.ITEM_FRAME, 700.0),

    // 50-60 MOB
    I91("Helm Lodowego Slugi", Material.IRON_HELMET, 107.0),
    I92("Zbroja Lodowego Slugi", Material.IRON_CHESTPLATE,128.0),
    I93("Spodnie Lodowego Slugi", Material.IRON_LEGGINGS,  118.0),
    I94("Buty Lodowego Slugi", Material.IRON_BOOTS, 106.0),
    I95("Miecz Lodowego Slugi", Material.IRON_SWORD, 130.0),
    // 50-60 MOB ICETOWER
    I96("Helm Mroznego Wilka", Material.IRON_HELMET, 137.0),
    I97("Zbroja Mroznego Wilka", Material.GOLD_CHESTPLATE,149.0),
    I98("Spodnie Mroznego Wilka", Material.IRON_LEGGINGS,  138.0),
    I99("Buty Mroznego Wilka", Material.GOLD_BOOTS, 126.0),
    I100("Wilczy Kiel", Material.IRON_SWORD, 170.0),
    // 50-60 BOSS ICETOWER
    I101("&b&lLodowy Sztylet", Material.DIAMOND_SWORD, 195.0),
    // 50-60 BOSS AKCE ZWYKLE ICETOWER
    I102("Zwykly Naszyjnik Krola Lodu", Material.STORAGE_MINECART, 800.0),
    I103("Zwykly Diadem Krola Lodu", Material.WATCH, 800.0),
    I104("Zwykly Pierscien Krola Lodu", Material.EXPLOSIVE_MINECART, 800.0),
    I105("Zwykla Tarcza Krola Lodu", Material.ITEM_FRAME, 800.0),
    // 50-60 BOSS AKCE ULEPSZONE ICETOWER
    I106("Ulepszony Naszyjnik Krola Lodu", Material.STORAGE_MINECART, 1000.0),
    I107("Ulepszony Diadem Krola Lodu", Material.WATCH, 1000.0),
    I108("Ulepszony Pierscien Krola Lodu", Material.EXPLOSIVE_MINECART, 1000.0),
    I109("Ulepszona Tarcza Krola Lodu", Material.ITEM_FRAME, 1000.0),


    // 60-70 MOB
    I114("Helm Zywiolaka Ognia", Material.IRON_HELMET, 267.0),
    I115("Zbroja Zywiolaka Ognia", Material.IRON_CHESTPLATE,279.0),
    I116("Spodnie Zywiolaka Ognia", Material.IRON_LEGGINGS,  258.0),
    I117("Buty Zywiolaka Ognia", Material.IRON_BOOTS, 266.0),
    I118("Miecz Zywiolaka Ognia", Material.IRON_SWORD, 270.0),
    // 60-70 BOSS
    I119("Helm Piekielnego Rycerza", Material.DIAMOND_HELMET, 282.0),
    I120("Zbroja Piekielnego Rycerza", Material.DIAMOND_CHESTPLATE, 284.0),
    I121("Spodnie Piekielnego Rycerza", Material.DIAMOND_LEGGINGS, 273.0),
    I122("Buty Piekielnego Rycerza", Material.DIAMOND_BOOTS, 284.0),
    I123("Miecz Piekielnego Rycerza", Material.DIAMOND_SWORD, 295.0),
    // 60-70 BOSS AKCE ZWYKLE
    I124("Zwykly Naszyjnik Piekielnego Rycerza", Material.STORAGE_MINECART, 1200.0),
    I125("Zwykly Diadem Piekielnego Rycerza", Material.WATCH, 1200.0),
    I126("Zwykly Pierscien Piekielnego Rycerza", Material.EXPLOSIVE_MINECART, 1200.0),
    I127("Zwykla Tarcza Piekielnego Rycerza", Material.ITEM_FRAME, 1200.0),
    // 60-70 BOSS AKCE ULEPSZONE
    I128("Ulepszony Naszyjnik Piekielnego Rycerza", Material.STORAGE_MINECART, 1600.0),
    I129("Ulepszony Diadem Piekielnego Rycerza", Material.WATCH, 1600.0),
    I130("Ulepszony Pierscien Piekielnego Rycerza", Material.EXPLOSIVE_MINECART, 1600.0),
    I131("Ulepszona Tarcza Piekielnego Rycerza", Material.ITEM_FRAME, 1600.0),
    
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
