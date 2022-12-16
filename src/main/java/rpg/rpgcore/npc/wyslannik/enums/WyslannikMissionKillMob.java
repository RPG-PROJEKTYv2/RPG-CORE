package rpg.rpgcore.npc.wyslannik.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

public enum WyslannikMissionKillMob {
    M1(1, "&8Najemnik", 300, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M2(2, "&2Goblin", 600, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M3(3, "&7Goryl", 1_000, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M4(4, "&8Zjawa", 1_200, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M5(5, "&3Straznik Swiatyni", 1_600, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M6(6, "&bMrozny Wilk", 2_500, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M7(7, "&6Zywiolak Ognia", 4_000, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
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
