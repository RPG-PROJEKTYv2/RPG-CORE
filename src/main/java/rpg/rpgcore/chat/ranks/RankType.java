package rpg.rpgcore.chat.ranks;

public enum RankType {

    HA("H@", 99),
    ADMIN("ADMIN", 10),
    GM("GM", 9),
    MOD("MOD", 8),
    KIDMOD("KIDMOD", 7),
    HELPER("HELPER", 6),
    JUNIORHELPER("JUNIORHELPER", 5),
    ELITA("ELITA", 3),
    SVIP("SVIP", 2),
    VIP("VIP", 1),
    BUDOWNICZY("BUDOWNICZY", 0),
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
