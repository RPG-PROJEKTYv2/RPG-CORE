package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public enum OsUlepszenia {
    M1(1, 0, 3, GlobalItem.getItem("I10", 1).clone()),
    M2(2, 1, 10, GlobalItem.getItem("I10", 2).clone()),
    M3(3, 2, 25, GlobalItem.getItem("I2", 1).clone()),
    M4(4, 3, 50, GlobalItem.getItem("I10", 4).clone()),
    M5(5, 4, 125, GlobalItem.getItem("I2", 3).clone()),
    M6(6, 5, 250, GlobalItem.getItem("I1", 3).clone()),
    M7(7, 6, 500, GlobalItem.getItem("I_OCZYSZCZENIE", 24).clone()),
    M8(8, 7, 1_000, GlobalItem.getItem("I2", 32).clone()),
    M9(9, 8, 2_000, GlobalItem.getItem("I1", 8).clone());


    private final int mission;
    private final int reqMission;
    private final int reqProgress;
    private final ItemStack reward;

    OsUlepszenia(final int mission, final int reqMission, final int reqProgress, final ItemStack reward) {
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

    public static OsUlepszenia getByMission(final int mission) {
        for (final OsUlepszenia osUlepszenia : values()) {
            if (osUlepszenia.getMission() == mission) {
                return osUlepszenia;
            }
        }
        return null;
    }

}
