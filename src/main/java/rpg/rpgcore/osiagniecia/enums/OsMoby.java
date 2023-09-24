package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.ItemShop;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;

public enum OsMoby {
    M1(1, 0, 25000, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l3 500 000&2$").addGlowing().toItemStack().clone().clone()),
    M2(2, 1, 60000, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l7 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M3(3, 2, 120000, GlobalItem.getItem("I10", 8).clone()),
    M4(4, 3, 200000, SkrzynkiOther.getItem("I2", 8).clone()),
    M5(5, 4, 280000, GlobalItem.getItem("I55", 1).clone()),
    M6(6, 5, 520000, GlobalItem.getItem("I_METAL", 8).clone()),
    M7(7, 6, 1000000, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l100 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M8(8, 7, 2500000, ItemShop.IS14.getItems().get(0).clone()),
    M9(9, 8, 5000000, BonType.SREDNIE_5.getBon());

    private final int mission;
    private final int reqMission;
    private final int reqProgress;
    private final ItemStack reward;

    OsMoby(final int mission, final int reqMission, final int reqProgress, final ItemStack reward) {
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

    public static OsMoby getByMission(final int mission) {
        for (final OsMoby osMoby : values()) {
            if (osMoby.getMission() == mission) {
                return osMoby;
            }
        }
        return null;
    }
}
