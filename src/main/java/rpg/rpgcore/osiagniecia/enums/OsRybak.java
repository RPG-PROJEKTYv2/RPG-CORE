package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.ItemShop;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

public enum OsRybak {
    M1(1, 0, 200, GlobalItem.getItem("I10", 3).clone()),
    M2(2, 1, 500, GlobalItem.getItem("I_OCZYSZCZENIE", 3).clone()),
    M3(3, 2, 1000, GlobalItem.getItem("I_METAL", 3).clone()),
    M4(4, 3, 2_500, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l25 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M5(5, 4, 5_000, GlobalItem.getItem("I_KSIEGAMAGII", 5).clone()),
    M6(6, 5, 7_500, GlobalItem.getItem("I_METAL", 8).clone()),
    M7(7, 6, 12_500, GlobalItem.getItem("I_OCZYSZCZENIE", 10).clone()),
    M8(8, 7, 24_000, GlobalItem.getItem("I10", 12).clone()),
    M9(9, 8, 50_000, ItemShop.IS14.getItems().get(0).clone());


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
