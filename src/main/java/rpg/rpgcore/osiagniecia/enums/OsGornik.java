package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.ItemShop;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

import java.util.Objects;

public enum OsGornik {
    M1(1, 0, 100, GlobalItem.getItem("I6", 2).clone()),
    M2(2, 1, 250, Objects.requireNonNull(GornikItems.getItem("Skrzynia_Gornika", 3)).clone()),
    M3(3, 2, 500, GlobalItem.getItem("I2", 3).clone()),
    M4(4, 3, 1_500, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l75 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M5(5, 4, 3_000, GlobalItem.getItem("I1", 3).clone()),
    M6(6, 5, 7_500, Objects.requireNonNull(GornikItems.getItem("Skrzynia_Gornika", 16)).clone()),
    M7(7, 6, 12_500, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l150 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M8(8, 7, 50_000, Objects.requireNonNull(GornikItems.getItem("Skrzynia_Gornika", 48)).clone()),
    M9(9, 8, 100_000, ItemShop.IS12.getItems().get(0).clone());


    private final int mission;
    private final int reqMission;
    private final int reqProgress;
    private final ItemStack reward;

    OsGornik(final int mission, final int reqMission, final int reqProgress, final ItemStack reward) {
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

    public static OsGornik getByMission(final int mission) {
        for (final OsGornik osGornik : values()) {
            if (osGornik.getMission() == mission) {
                return osGornik;
            }
        }
        return null;
    }
}
