package rpg.rpgcore.npc.wyslannik.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public enum WyslannikMissionOpen {

    M1(1, "&8Skrzynia Najemnika",12, GlobalItem.getItem("I4", 1)),
    M2(2, "&2Skrzynia Goblina",24, GlobalItem.getItem("I4", 1)),
    M3(3, "&7Skrzynia Goryla",40, GlobalItem.getItem("I4", 1)),
    M4(4, "&8Skrzynia Zjawy",52, GlobalItem.getItem("I4", 2)),
    M5(5, "&3Skrzynia Straznika Swiatyni",72, GlobalItem.getItem("I4", 2)),
    M6(6, "&bSkrzynia Mroznego Wilka",48, GlobalItem.getItem("I4", 2)),
    M7(7, "&6Skrzynia Zywiolaka Ognia",64, GlobalItem.getItem("I4", 3)),
    M8(8, "&fSkrzynia Mrocznej Duszy",160, GlobalItem.getItem("I4", 3));
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
