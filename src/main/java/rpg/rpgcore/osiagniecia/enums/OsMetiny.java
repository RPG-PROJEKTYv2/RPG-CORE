package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public enum OsMetiny {
    // tajemnicza x3
    M1(1, 0, 5, GlobalItem.getItem("I4", 1).clone()),
    // pozlacana x2
    M2(2, 1, 15, GlobalItem.getCheck(1_000).clone()),
    // spodnie niszczyciela metinow
    M3(3, 2, 50, ItemHelper.createArmor("&bSpodnie Niszczyciela Metinow", Material.IRON_LEGGINGS, 18, 2).clone()),
    // pozlacana x6
    M4(4, 3, 150, GlobalItem.getItem("I50", 2).clone()),
    // hells x15
    M5(5, 4, 500, ItemHelper.createArmor("&bButy Niszczyciela Metinow", Material.IRON_BOOTS, 45, 15).clone()),
    // czek 50kk
    M6(6, 5, 1_250, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l50 000 000&2$").addGlowing().toItemStack().clone().clone()),
    //x`
    M7(7, 6, 2_500, GlobalItem.getHells(50).clone()),
    //
    M8(8, 7, 5_000, GlobalItem.getItem("I1", 4).clone()),
    // elita 14d
    M9(9, 8, 10_000, GlobalItem.getItem("I1", 8).clone());


    private final int mission;
    private final int reqMission;
    private final int reqProgress;
    private final ItemStack reward;

    OsMetiny(final int mission, final int reqMission, final int reqProgress, final ItemStack reward) {
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

    public static OsMetiny getByMission(final int mission) {
        for (final OsMetiny osMetiny : values()) {
            if (osMetiny.getMission() == mission) {
                return osMetiny;
            }
        }
        return null;
    }
}
