package rpg.rpgcore.npc.wyslannik.enums;

import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public enum WyslannikMissionOpen {

    M1(1, "&6Skrzynia Rozbojnika",12, GlobalItem.getItem("I4", 1)),
    M2(2, "&2Skrzynia Goblina",20, GlobalItem.getItem("I4", 1)),
    M3(3, "&7Skrzynia Goryla",25, GlobalItem.getItem("I4", 1)),
    M4(4, "&8Skrzynia Zjawy",32, GlobalItem.getItem("I4", 2)),
    M5(5, "&3Skrzynia Straznika Swiatyni",36, GlobalItem.getItem("I4", 2)),
    M6(6, "&bSkrzynia Mroznego Wilka",40, GlobalItem.getItem("I4", 2)),
    M7(7, "&6Skrzynia Zywiolaka Ognia",48, GlobalItem.getItem("I4", 3)),
    M8(8, "&fSkrzynia Mrocznej Duszy",56, GlobalItem.getItem("I4", 3));
    private final int mission;
    private final String chestName;
    private final int reqAmount;
    private final ItemStack reward;

    WyslannikMissionOpen(final int mission, final String chestName, final int reqAmount, final ItemStack reward) {
        this.mission = mission;
        this.chestName = chestName;
        this.reqAmount = reqAmount;
        this.reward = reward;
    }

    public int getMission() {
        return this.mission;
    }

    public String getChestName() {
        return this.chestName;
    }

    public int getReqAmount() {
        return this.reqAmount;
    }

    public ItemStack getReward() {
        return this.reward;
    }

    public static WyslannikMissionOpen getByMission(final int mission) {
        for (final WyslannikMissionOpen wyslannikMissionOpen : values()) {
            if (wyslannikMissionOpen.getMission() == mission) {
                return wyslannikMissionOpen;
            }
        }
        return null;
    }
}
