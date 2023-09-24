package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.ItemShop;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;

public enum OsMetiny {
    M1(1, 0, 200, SkrzynkiOther.getItem("I2", 3).clone()),
    M2(2, 1, 500, GlobalItem.getItem("I_KAMIENBAO", 5).clone()),
    M3(3, 2, 1000, GlobalItem.getItem("I_OCZYSZCZENIE", 8).clone()),
    M4(4, 3, 2000, GlobalItem.getItem("I10", 8).clone()),
    M5(5, 4, 5000, GlobalItem.getItem("I_METAL", 7).clone()),
    M6(6, 5, 7_550, SkrzynkiOther.getItem("I2", 16).clone()),
    M7(7, 6, 12_000, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l50 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M8(8, 7, 16_500, GlobalItem.getHells(100).clone()),
    M9(9, 8, 25_000, ItemShop.IS14.getItems().get(0).clone());


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
