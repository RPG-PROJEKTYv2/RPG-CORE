package rpg.rpgcore.npc.handlarz.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

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
        return DoubleUtils.round(price, 2);
    }

    public static double getItemPrice(final ItemStack item) {
        for (final HandlarzSellItems items : HandlarzSellItems.values()) {
            if (items.getMaterial() == item.getType() && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && Utils.removeColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(items.getName())) {
                return items.getPrice();
            }
        }
        return 0.0;
    }
}
