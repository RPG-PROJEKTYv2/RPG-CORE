package rpg.rpgcore.dungeons;

public enum DungeonStatus {
    ENDED(""),
    RESETTING(""),
    STARTED(""),
    ETAP_0("0"),
    ETAP_1("1"),
    ETAP_1_RDZENIE("1"),
    ETAP_1_MINIBOSS("1"),
    ETAP_2("2"),
    ETAP_3("3"),
    ONGOING(""),
    FINISHED("");

    private final String name;

    DungeonStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
