package rpg.rpgcore.ranks.types;

public enum RankType {

    DEV("DEV", "&4&lDEV &c", "&c", 99),
    HA("HA", "&4&lH@ &c", "&c", 90),
    ADMIN("ADMIN", "&4&lAdmin &c", "&c", 10),
    GM("GM", "&2&lGM &a", "&2", 9),
    MOD("MOD", "&2&lMod &a", "&2", 8),
    KIDMOD("KIDMOD", "&2&lKidMod &a", "&2", 7),
    HELPER("HELPER", "&3&lHelper &b", "&b", 6),
    JUNIORHELPER("JUNIORHELPER", "&3&lJrHelper &b", "&b", 5),
    GRACZ("GRACZ", "&7 ", "", 0);

    private final String name, prefix, messageColour;
    private final int priority;

    RankType(String name, String prefix, String messageColour, int priority) {
        this.name = name;
        this.prefix = prefix;
        this.messageColour = messageColour;
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

    public String getMessageColour() {
        return messageColour;
    }


    public boolean can(RankType rankType) {
        return getPriority() >= rankType.getPriority();
    }


    public int getPriority() {
        return priority;
    }
}
