package rpg.rpgcore.lvl.enums.mobs;

public enum Maps {

    MAP_1_10_1("Wygnaniec Lvl. 1", 8),
    MAP_1_10_2("Wygnaniec Lvl. 3", 10),
    MAP_1_10_3("Wygnaniec Lvl. 5", 12),
    ERROR("Error", 0);

    private final String name;
    private final double exp;

    Maps(String name, double exp) {
        this.name = name;
        this.exp = exp;
    }

    public String getName() {
        return name;
    }

    public double getExp() {
        return exp;
    }

    public static Maps getByName(String name) {
        for (Maps maps : values()) {
            if (maps.getName().equalsIgnoreCase(name)) {
                return maps;
            }
        }
        return ERROR;
    }

    public static boolean isMob(final String name) {
        for (Maps maps : values()) {
            if (maps.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
