package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public enum OsNiesy {
    M1(1, 0, 3, GlobalItem.getItem("I6", 1).clone()),
    M2(2, 1, 10, GlobalItem.getHells(10).clone()),
    M3(3, 2, 25, GlobalItem.getItem("I2", 3).clone()),
    M4(4, 3, 50, GlobalItem.getHells(10).clone()),
    M5(5, 4, 75, GlobalItem.getItem("I2", 5).clone()),
    M6(6, 5, 120, GlobalItem.getItem("I1", 2).clone()),
    M7(7, 6, 240, GlobalItem.getItem("I1", 4).clone()),
    M8(8, 7, 550, GlobalItem.getHells(75).clone()),
    M9(9, 8, 1_000, new ItemBuilder(Material.STONE).setName("&e&lSzczesliwy Pierscien &4&l(SOON)").toItemStack().clone());


    private final int mission;
    private final int reqMission;
    private final int reqProgress;
    private final ItemStack reward;

    OsNiesy(final int mission, final int reqMission, final int reqProgress, final ItemStack reward) {
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

    public static OsNiesy getByMission(final int mission) {
        for (final OsNiesy osNiesy : values()) {
            if (osNiesy.getMission() == mission) {
                return osNiesy;
            }
        }
        return null;
    }
}
