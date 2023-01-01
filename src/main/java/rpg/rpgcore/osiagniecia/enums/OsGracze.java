package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

public enum OsGracze {
    M1(1, 0, 5, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M2(2, 1, 20, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M3(3, 2, 50, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M4(4, 3, 120, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M5(5, 4, 300, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M6(6, 5, 750, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M7(7, 6, 1_500, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M8(8, 7, 4_500, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M9(9, 8, 7_500, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone());

    private final int mission;
    private final int reqMission;
    private final int reqProgress;
    private final ItemStack reward;

    OsGracze(final int mission, final int reqMission, final int reqProgress, final ItemStack reward) {
        this.mission = mission;
        this.reqMission = reqMission;
        this.reqProgress = reqProgress;
        this.reward = reward;
    }

    public int getMission() {
        return this.mission;
    }

    public int getReqMission() {
        return this.reqMission;
    }

    public int getReqProgress() {
        return this.reqProgress;
    }

    public ItemStack getReward() {
        return this.reward;
    }

    public static OsGracze getByMission(final int mission) {
        for (final OsGracze osGracze : values()) {
            if (osGracze.getMission() == mission) {
                return osGracze;
            }
        }
        return null;
    }
}
