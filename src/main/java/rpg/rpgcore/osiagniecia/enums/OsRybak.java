package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

public enum OsRybak {
    M1(1, 0, 50, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M2(2, 1, 200, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M3(3, 2, 500, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M4(4, 3, 1_200, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M5(5, 4, 3_000, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M6(6, 5, 7_500, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M7(7, 6, 12_500, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M8(8, 7, 30_000, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M9(9, 8, 50_000, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone());


    private final int mission;
    private final int reqMission;
    private final int reqProgress;
    private final ItemStack reward;

    OsRybak(final int mission, final int reqMission, final int reqProgress, final ItemStack reward) {
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

    public static OsRybak getByMission(final int mission) {
        for (final OsRybak osRybak : values()) {
            if (osRybak.getMission() == mission) {
                return osRybak;
            }
        }
        return null;
    }
}
