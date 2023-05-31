package rpg.rpgcore.dungeons.maps.icetower;

public enum IceTowerMobType {
    M1("DT-MOB1"),
    M2("DT-MOB2"),
    M3("DT-MOB3"),
    BOSS("DT-BOSS");

    private final String mobName;

    IceTowerMobType(String mobName) {
        this.mobName = mobName;
    }

    public String getMobName() {
        return mobName;
    }
}
