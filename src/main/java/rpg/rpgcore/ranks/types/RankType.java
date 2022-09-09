package rpg.rpgcore.ranks.types;

public enum RankType {

    DEV("DEV", 99),
    HA("HA", 90),
    ADMIN("ADMIN", 10),
    GM("GM", 9),
    MOD("MOD", 8),
    KIDMOD("KIDMOD", 7),
    HELPER("HELPER", 6),
    JUNIORHELPER("JUNIORHELPER", 5),
    GRACZ("GRACZ", 0);

    private final String name;
    private final int priority;

    RankType(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public static RankType getByName(String name) {
        for (RankType rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return RankType.GRACZ;
    }

    public String getName() {
        return name;
    }


    public boolean can(RankType rankType) {
        return getPriority() > rankType.getPriority();
    }


    public int getPriority() {
        return priority;
    }
}
