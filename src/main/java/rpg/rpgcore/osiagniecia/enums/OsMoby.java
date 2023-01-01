package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.ItemShop;

public enum OsMoby {
    M1(1, 0, 300, AkcesoriaPodsHelper.createTarcza(8, 3, 2, 9, "&dTarcza Pogromcy Potworow").clone()),
    M2(2, 1, 1_000, GlobalItem.getItem("I5", 64).clone()),
    M3(3, 2, 3_000, ItemHelper.createSword("&dMiecz Pogromcy Potworow", Material.GOLD_SWORD, 22, 5, false).clone()),
    M4(4, 3, 10_000, GlobalItem.getItem("I6", 3).clone()),
    M5(5, 4, 25_000, GlobalItem.getItem("I_OCZYSZCZENIE", 1).clone()),
    M6(6, 5, 75_000, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l10 000 000&2$").addGlowing().toItemStack().clone().clone()),
    M7(7, 6, 200_000, GlobalItem.getItem("I52", 3).clone()),
    M8(8, 7, 750_000, ItemShop.IS15.getItems().get(0).clone()),
    M9(9, 8, 2_000_000, new ItemBuilder(Material.STONE).setName("&4&lWKROTCE").toItemStack().clone());

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
