package rpg.rpgcore.old.gornik.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

public enum GornikOres {
    O1(Material.COAL_ORE, "&8&lRuda Mroku", 10, GornikItems.getItem("R1", 1), 1, 90.0),
    O2(Material.IRON_ORE, "&7&lRuda Cyrkonu", 12, GornikItems.getItem("R2", 1), 3, 85.0),
    O3(Material.GOLD_ORE, "&e&lRuda Blasku", 14, GornikItems.getItem("R3", 1), 5, 80.0),
    O4(Material.LAPIS_ORE, "&3&lRuda Szafiru", 15, GornikItems.getItem("R4", 1), 7, 75.0),
    O5(Material.EMERALD_ORE, "&2&lRuda Jadeitu", 20, GornikItems.getItem("R5", 1), 10, 60.0),
    O6(Material.DIAMOND_ORE, "&b&lRuda Tanzanitu", 25, GornikItems.getItem("R6", 1), 15, 55.0),
    O7(Material.REDSTONE_ORE, "&c&lRuda Rubinu", 30, GornikItems.getItem("R7", 1), 20, 50.0),
    O8(Material.GLOWING_REDSTONE_ORE, "&c&lRuda Rubinu", 30, GornikItems.getItem("R7", 1), 20, 50.0);
    private final Material material;
    private final String name;
    private final int maxHp;
    private final ItemStack drop;
    private final int exp;
    private final double dropChance;


    GornikOres(Material material, String name, int maxHp, ItemStack drop, int exp, double dropChance) {
        this.material = material;
        this.name = name;
        this.maxHp = maxHp;
        this.drop = drop;
        this.exp = exp;
        this.dropChance = dropChance;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public ItemStack getDrop() {
        return drop;
    }

    public int getExp() {
        return exp;
    }

    public double getDropChance() {
        return dropChance;
    }

    public static GornikOres getOre(Material material) {
        for (GornikOres ore : GornikOres.values()) {
            if (ore.getMaterial() == material) {
                return ore;
            }
        }
        return null;
    }
}
