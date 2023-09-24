package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public enum OsNiesy {
    M1(1, 0, 5,  new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l2 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M2(2, 1, 10, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l5 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M3(3, 2, 25, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l15 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M4(4, 3, 50, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l30 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M5(5, 4, 75, GlobalItem.getHells(65).clone()),
    M6(6, 5, 100, GlobalItem.getItem("I_KAMIENBAO", 5).clone()),
    M7(7, 6, 160, GlobalItem.getItem("I53", 2).clone()),
    M8(8, 7, 260, GlobalItem.getItem("I_KSIEGAMAGII_PLUS", 5).clone()),
    M9(9, 8, 1_000, BonType.KRYTYK_5.getBon());


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
