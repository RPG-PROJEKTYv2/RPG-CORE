package rpg.rpgcore.ranks.types;

public enum RankType {

    DEV("DEV", "&4&lDEV &c", 99),
    HA("HA","&4&lH@ &c", 90),
    ADMIN("ADMIN","&4&lAdmin &c", 10),
    GM("GM", "&2&lGM &a",9),
    MOD("MOD", "&2&lMod &a",8),
    KIDMOD("KIDMOD", "&2&lKidMod &a",7),
    HELPER("HELPER", "&3&lHelper &b",6),
    JUNIORHELPER("JUNIORHELPER", "&3&lJrHelper &b",5),
    GRACZ("GRACZ", "&7 ",0);

    private final String name, prefix;
    private final int priority;

    RankType(String name, String prefix, int priority) {
        this.name = name;
        this.prefix = prefix;
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

    public String getPrefix() {
        return prefix;
    }


    public boolean can(RankType rankType) {
        return getPriority() >= rankType.getPriority();
    }


    public int getPriority() {
        return priority;
    }
}
