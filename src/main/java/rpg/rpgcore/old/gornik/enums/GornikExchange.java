package rpg.rpgcore.old.gornik.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

public enum GornikExchange {
    R1(Material.COAL_ORE, GornikItems.getItem("G1", 1), 75),
    R2(Material.IRON_ORE, GornikItems.getItem("G2", 1), 65),
    R3(Material.GOLD_ORE, GornikItems.getItem("G3", 1), 60),
    R4(Material.LAPIS_ORE, GornikItems.getItem("G4", 1), 55),
    R5(Material.EMERALD_ORE, GornikItems.getItem("G5", 1), 50),
    R6(Material.DIAMOND_ORE, GornikItems.getItem("G6", 1), 45),
    R7(Material.REDSTONE_ORE, GornikItems.getItem("G7", 1), 40);

    private final Material material;
    private final ItemStack item;
    private final double dropChance;

    GornikExchange(Material material, ItemStack item, double dropChance) {
        this.material = material;
        this.item = item;
        this.dropChance = dropChance;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemStack getItem() {
        return item;
    }

    public double getDropChance() {
        return dropChance;
    }

    public static GornikExchange getExchange(Material material) {
        for (GornikExchange exchange : GornikExchange.values()) {
            if (exchange.getMaterial() == material) {
                return exchange;
            }
        }
        return null;
    }
}
