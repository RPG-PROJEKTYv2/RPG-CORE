package rpg.rpgcore.npc.handlarz.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

public enum HandlarzSellItems {

    I1("Helm Najemnika", Material.LEATHER_HELMET, 10.0),
    I2("Zbroja Najemnika", Material.LEATHER_CHESTPLATE,10.0),
    I3("Spodnie Najemnika", Material.LEATHER_LEGGINGS,  10.0),
    I4("Buty Najemnika", Material.LEATHER_BOOTS, 10.0),
    I5("Tepy Miecz Najemnika", Material.WOOD_SWORD, 12.0),
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
