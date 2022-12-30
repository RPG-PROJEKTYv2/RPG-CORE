package rpg.rpgcore.ranks.types;

public enum RankTypePlayer {
    GRACZ("GRACZ", "&7", 0),
    BUDOWNICZY("BUDOWNICZY", "&d&lBud &7", 1),
    VIP("VIP", "&e&lVip &7", 1),
    SVIP("SVIP", "&6&lS&e&lvip &7", 2),
    ELITA("ELITA", "&5&lELITA &7",  3);

    private final String name, prefix;
    private final int priority;

    RankTypePlayer(String name, String prefix, int priority) {
        this.name = name;
        this.prefix = prefix;
        this.priority = priority;
    }

    public static RankTypePlayer getByName(String name) {
        for (RankTypePlayer rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean can(RankTypePlayer rankType) {
        return getPriority() >= rankType.getPriority();
    }

    public int getPriority() {
        return priority;
    }
}
