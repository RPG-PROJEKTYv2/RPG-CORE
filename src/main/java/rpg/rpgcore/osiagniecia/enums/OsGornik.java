package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.ItemShop;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

import java.util.Objects;

public enum OsGornik {
    M1(1, 0, 256, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l5 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M2(2, 1, 512, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l10 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M3(3, 2, 1536, GlobalItem.getItem("I50", 10).clone()),
    M4(4, 3, 3072, GlobalItem.getItem("I_KAMIENBAO", 10).clone()),
    M5(5, 4, 6400, GlobalItem.getItem("I_KSIEGAMAGII_PLUS", 4).clone()),
    M6(6, 5, 9600, Objects.requireNonNull(GornikItems.getItem("Skrzynia_Gornika", 64)).clone()),
    M7(7, 6, 15360, GlobalItem.getItem("I_KSIEGAMAGII", 7).clone()),
    M8(8, 7, 25600, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l52 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M9(9, 8, 38400, GlobalItem.getHells(200).clone());


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
