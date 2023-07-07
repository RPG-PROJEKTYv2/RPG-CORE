package rpg.rpgcore.npc.gornik.enums;

import org.bukkit.Material;

public enum PickaxePriority {
    P1(Material.STONE_PICKAXE, 1),
    P2(Material.IRON_PICKAXE, 2),
    P3(Material.GOLD_PICKAXE, 3),
    P4(Material.DIAMOND_PICKAXE, 4);
    private final Material type;
    private final int priority;

    PickaxePriority(final Material type, final int priority) {
        this.type = type;
        this.priority = priority;
    }

    public Material getType() {
        return this.type;
    }

    public int getPriority() {
        return this.priority;
    }

    public static int getPickaxePriority(final Material type) {
        for (final PickaxePriority pickaxePriority : values()) {
            if (pickaxePriority.getType() == type) {
                return pickaxePriority.getPriority();
            }
        }
        return 0;
    }
}
