package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

public enum OsSkrzynki {
    M1(1, 0, 32, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M2(2, 1, 64, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M3(3, 2, 192, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M4(4, 3, 768, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M5(5, 4, 1_536, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M6(6, 5, 6_144, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M7(7, 6, 12_288, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M8(8, 7, 24_576, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone()),
    M9(9, 8, 50_000, new ItemBuilder(Material.STONE).setName("&4&lCZEKAM NA ROZPISKE").toItemStack().clone());


    private final int mission;
    private final int reqMission;
    private final int reqProgress;
    private final ItemStack reward;

    OsSkrzynki(final int mission, final int reqMission, final int reqProgress, final ItemStack reward) {
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

    public static OsSkrzynki getByMission(final int mission) {
        for (final OsSkrzynki osSkrzynki : values()) {
            if (osSkrzynki.getMission() == mission) {
                return osSkrzynki;
            }
        }
        return null;
    }
}
