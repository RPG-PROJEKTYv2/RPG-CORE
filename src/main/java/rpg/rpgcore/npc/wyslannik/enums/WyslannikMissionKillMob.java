package rpg.rpgcore.npc.wyslannik.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ItemBuilder;

public enum WyslannikMissionKillMob {
    M1(1, "&8Najemnik", 300, AkcesoriaPodsHelper.createTarcza(5, 4, 2, 8, "&a&lSlaba Tarcza Wyslannika")),
    M2(2, "&2Goblin", 600, AkcesoriaPodsHelper.createNaszyjnik(4, 5, 3, 15, "&a&lSlaby Naszyjnik Wyslannika")),
    M3(3, "&7Goryl", 1_000, AkcesoriaPodsHelper.createTarcza(12, 9, 3, 27, "&a&lTarcza Wyslannika")),
    M4(4, "&8Zjawa", 1_200, AkcesoriaPodsHelper.createDiadem(9, 9, 2, 36, "&a&lDiadem Wyslannika")),
    M5(5, "&3Straznik Swiatyni", 1_600, AkcesoriaPodsHelper.createTarcza(20, 13, 4, 49, "&a&lWzmocniona Tarcza Wyslannika")),
    M6(6, "&bMrozny Wilk", 2_500, AkcesoriaPodsHelper.createKolczyki(8, 8, -1, 58, "&a&lKolczyki Wyslannika")),
    M7(7, "&6Zywiolak Ognia", 4_000, AkcesoriaPodsHelper.createPierscien(0.3, 5, 1, 67, "&a&lPierscien Wyslannika")),
    M8(8, "&fMroczna Dusza", 10_000, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack());
    private final int mission;
    private final String mobName;
    private final int reqAmount;
    private final ItemStack reward;

    WyslannikMissionKillMob(final int mission, final String mobName, final int reqAmount, final ItemStack reward) {
        this.mission = mission;
        this.mobName = mobName;
        this.reqAmount = reqAmount;
        this.reward = reward;
    }

    public int getMission() {
        return this.mission;
    }

    public String getMobName() {
        return this.mobName;
    }

    public int getReqAmount() {
        return this.reqAmount;
    }

    public ItemStack getReward() {
        return this.reward;
    }

    public static WyslannikMissionKillMob getByMission(final int mission) {
        for (final WyslannikMissionKillMob wyslannikMissionKillMob : values()) {
            if (wyslannikMissionKillMob.getMission() == mission) {
                return wyslannikMissionKillMob;
            }
        }
        return null;
    }
}
