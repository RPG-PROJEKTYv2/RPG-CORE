package rpg.rpgcore.npc.test.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Arrays;

public enum TestMissions {
    // PRZYKLADOWA 1 MISJA
    I1(1, new ItemBuilder(Material.BOOK).setName("&c&lMisja #1").setLore(Arrays.asList(
            "fsgfsgsfgsfgs",
            "dgsggsgfsgsfg",
            "sfgfsgfsgfsgsg"
    )).toItemStack().clone(), 100, 5, 2.5, GlobalItem.getItem("I3", 1)),

    // ERROR TEZ MUSI BYC ZAWSZE BO TO JEST ZE UKONCZYL KAMPANIE
    I99(99, GlobalItem.getItem("error", 1),0,0,0,null);
    private final int mission;
    private final ItemStack missionItem;
    private final int reqAmount;
    private final int ddmg;
    private final double srdmg;
    private final ItemStack reward;


    TestMissions(final int mission, final ItemStack missionItem, final int reqAmount, final int ddmg, final double srdmg, final ItemStack reward) {
        this.mission = mission;
        this.missionItem = missionItem;
        this.reqAmount = reqAmount;
        this.ddmg = ddmg;
        this.srdmg = srdmg;
        this.reward = reward;
    }

    public int getMission() {
        return mission;
    }

    public ItemStack getMissionItem() {
        return missionItem;
    }

    public int getReqAmount() {
        return reqAmount;
    }

    public int getDdmg() {
        return ddmg;
    }

    public double getSrdmg() {
        return srdmg;
    }

    public ItemStack getReward() {
        return reward;
    }

    public static TestMissions getMission(final int mission) {
        for (final TestMissions testMissions : TestMissions.values()) {
            if (testMissions.getMission() == mission) {
                return testMissions;
            }
        }
        return I99;
    }
}
