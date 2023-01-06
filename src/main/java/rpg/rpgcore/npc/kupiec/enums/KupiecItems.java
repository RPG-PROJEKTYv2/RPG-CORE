package rpg.rpgcore.npc.kupiec.enums;

import rpg.rpgcore.utils.DoubleUtils;

public enum KupiecItems {
    I1("Helm Najemnika", 10.0),
    I2("Zbroja Najemnika", 10.0),
    I3("Spodnie Najemnika", 10.0),
    I4("Buty Najemnika", 10.0);

    private final String itemName;
    private final double price;

    KupiecItems(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return DoubleUtils.round(price, 2);
    }

    public static double getItemPrice(String itemName) {
        for (KupiecItems kupiecItems : KupiecItems.values()) {
            if (kupiecItems.getItemName().equals(itemName)) {
                return kupiecItems.getPrice();
            }
        }
        return 0.0;
    }
}
