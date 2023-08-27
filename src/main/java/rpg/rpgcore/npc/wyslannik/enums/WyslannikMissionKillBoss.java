package rpg.rpgcore.npc.wyslannik.enums;

public enum WyslannikMissionKillBoss {
    M1(1, 2,3,"&8[&4&lBOSS&8] &c&lDowodca Rozbojnikow"),
    M2(2, 2,3,"&8[&4&lBOSS&8] &a&lWodz Goblinow"),
    M3(3, 3,3,"&8[&4&lBOSS&8] &f&lKrol Goryli"),
    M4(4, 3,3,"&8[&4&lBOSS&8] &7&lPrzekleta Dusza"),
    M5(5, 3,3,"&8[&4&lBOSS&8] &e&lTryton"),
    M6(6, 4,3,"&8[&4&lBOSS&8] &bKrol Lodu"),
    M7(7, 4,3,"&8[&4&lBOSS&8] &c&lPiekielny Rycerz"),
    M8(8, 5,3,"&8[&4&lBOSS&8] &5&lPrzeklety Czarnoksieznik");
    private final int mission;
    private final int sredniDEF;
    private final int bossAmount;
    private final String bossName;

    WyslannikMissionKillBoss(final int mission, final int sredniDEF, final int bossAmount, final String bossName) {
        this.mission = mission;
        this.sredniDEF = sredniDEF;
        this.bossAmount = bossAmount;
        this.bossName = bossName;
    }
    public int getMission() {
        return this.mission;
    }
    public int getSredniDEF() {
        return sredniDEF;
    }
    public int getBossAmount() { return bossAmount; }
    public String getBossName() {
        return this.bossName;
    }

    public static WyslannikMissionKillBoss getByMission(final int mission) {
        for (final WyslannikMissionKillBoss wyslannikMissionKillBoss : values()) {
            if (wyslannikMissionKillBoss.getMission() == mission) return wyslannikMissionKillBoss;
        }
        return null;
    }
}
