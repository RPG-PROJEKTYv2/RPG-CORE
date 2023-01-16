package rpg.rpgcore.npc.handlarz.enums;

import org.bukkit.Material;

public enum HandlarzSellItems {


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

    public double getPrice() {
        return price;
    }

    public static HandlarzSellItems getByMaterial(Material material) {
        for (HandlarzSellItems kupiecItem : HandlarzSellItems.values()) {
            if (kupiecItem.getMaterial() == material) {
                return kupiecItem;
            }
        }
        return I_ERROR;
    }

    public static HandlarzSellItems getByName(String name) {
        for (HandlarzSellItems kupiecItem : HandlarzSellItems.values()) {
            if (kupiecItem.getName().equalsIgnoreCase(name)) {
                return kupiecItem;
            }
        }
        return I_ERROR;
    }
}
