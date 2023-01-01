package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.ItemShop;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

public enum OsRybak {
    M1(1, 0, 50, new ItemBuilder(RybakItems.getByName("I11").getItemStack().clone()).setAmount(3).toItemStack().clone()),
    M2(2, 1, 200, GlobalItem.getItem("I6", 2).clone()),
    M3(3, 2, 500, new ItemBuilder(RybakItems.getByName("I11").getItemStack().clone()).setAmount(5).toItemStack().clone()),
    M4(4, 3, 1_200, GlobalItem.getItem("I2", 4).clone()),
    M5(5, 4, 3_000, GlobalItem.getItem("I1", 2).clone()),
    M6(6, 5, 7_500, GlobalItem.getItem("I1", 4).clone()),
    M7(7, 6, 12_500, GlobalItem.getItem("I1", 6).clone()),
    M8(8, 7, 30_000, ItemShop.IS12.getItems().get(0).clone()),
    M9(9, 8, 50_000, new ItemBuilder(Material.STONE).setName("&d&lTajemnicza Moc &4&l(SOON)").toItemStack().clone());


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
