package rpg.rpgcore.npc.gornik.ore.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Ores {
    O1(Material.COAL_ORE, 4, 1, 22, 100, 1, GornikItems.I1.getItemStack()),
    O2(Material.IRON_ORE, 5, 2, 23.5, 45, 1, GornikItems.I2.getItemStack()),
    O3(Material.GOLD_ORE, 7, 4, 25, 25, 2, GornikItems.I3.getItemStack()),
    O4(Material.LAPIS_ORE, 9, 6, 27.5, 17.5, 2, GornikItems.I4.getItemStack()),
    O5(Material.EMERALD_ORE, 11, 8, 30, 12.5,3, GornikItems.I5.getItemStack()),
    O6(Material.DIAMOND_ORE, 14, 12, 32.5, 7.5,3, GornikItems.I6.getItemStack()),
    O7(Material.REDSTONE_ORE, 18, 15, 35, 2.5, 4, GornikItems.I7.getItemStack()),
    O8(Material.GLOWING_REDSTONE_ORE, 18, 15, 35, 0, 4, GornikItems.I7.getItemStack());

    private final Material material;
    private final int hp, exp, reqPriority;
    private final double failChance, spawnChance;
    private final ItemStack reward;

    Ores(final Material material, final int hp, final int exp, final double failChance, final double spawnChance, final int reqPriority, final ItemStack reward) {
        this.material = material;
        this.hp = hp;
        this.exp = exp;
        this.failChance = failChance;
        this.spawnChance = spawnChance;
        this.reqPriority = reqPriority;
        this.reward = reward;
    }

    public Material getMaterial() {
        return material;
    }

    public int getHp() {
        return hp;
    }

    public int getExp() {
        return exp;
    }

    public double getFailChance() {
        return failChance;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    public int getReqPriority() {
        return reqPriority;
    }

    public ItemStack getReward() {
        return reward.clone();
    }

    public static Ores getRandomOre() {
        List<Ores> ores = Arrays.asList(values());
        Collections.reverse(ores);
        return ores.stream().filter(ore -> ChanceHelper.getChance(ore.getSpawnChance())).findFirst().orElse(O1);
    }

    public static Ores getOre(final Material material) {
        for (final Ores ores : values()) {
            if (ores.getMaterial() == material) {
                return ores;
            }
        }
        return null;
    }
}
