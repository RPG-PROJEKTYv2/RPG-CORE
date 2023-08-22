package rpg.rpgcore.npc.wyslannik.enums;

public enum WyslannikMissionKillMob {
    M1(1, 2,300,"&6Rozbojnik"),
    M2(2, 2,600, "&2Goblin"),
    M3(3,3,1_000,"&7Goryl"),
    M4(4, 3,1_300,"&8Zjawa"),
    M5(5,3,1_600, "&3Straznik Swiatyni"),
    M6(6,4,2_500, "&bMrozny Wilk"),
    M7(7, 4,6_000,"&6Zywiolak Ognia"),
    M8(8, 5,12_000,"&fMroczna Dusza");
    private final int mission;
    private final int sredniDMG;
    private final int mobsAmount;
    private final String mobName;

    WyslannikMissionKillMob(final int mission, final int sredniDMG, final int mobsAmount, final String mobName) {
        this.mission = mission;
        this.sredniDMG = sredniDMG;
        this.mobsAmount = mobsAmount;
        this.mobName = mobName;
    }
    public int getMission() {
        return this.mission;
    }
    public int getSredniDMG() {
        return sredniDMG;
    }
    public int getMobsAmount() { return mobsAmount; }
    public String getMobName() {
        return this.mobName;
    }

    public static WyslannikMissionKillMob getByMission(final int mission) {
        for (final WyslannikMissionKillMob wyslannikMissionKillMob : values()) {
            return wyslannikMissionKillMob;
        }
        return null;
    }
}

