package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;

public enum OsGracze {
    M1(1, 0, 5, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l2 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M2(2, 1, 25, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l5 500 000&2$").addGlowing().toItemStack().clone().clone()),
    M3(3, 2, 50, GlobalItem.getItem("I_OCZYSZCZENIE", 3).clone()),
    M4(4, 3, 100, GlobalItem.getItem("I_KSIEGAMAGII", 3).clone()),
    M5(5, 4, 200, GlobalItem.getItem("I_KSIEGAMAGII_PLUS", 1).clone()),
    M6(6, 5, 550, GlobalItem.getHells(75).clone()),
    M7(7, 6, 1_000, SkrzynkiOther.getItem("I2", 16).clone()),
    M8(8, 7, 3_500, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l50 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M9(9, 8, 7_000, BonType.DEFENSYWA_5.getBon());

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
