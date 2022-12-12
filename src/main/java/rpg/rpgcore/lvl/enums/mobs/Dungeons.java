package rpg.rpgcore.lvl.enums.mobs;

public enum Dungeons {
    NAZWA_DUNGEONA("NAZWA_MOOBA", 0),
    ERROR("Error", 0);

    private final String name;
    private final double exp;

    Dungeons(String name, double exp) {
        this.name = name;
        this.exp = exp;
    }

    public String getName() {
        return name;
    }

    public double getExp() {
        return exp;
    }

    public static Dungeons getByName(String name) {
        for (Dungeons dungeons : values()) {
            if (dungeons.getName().equalsIgnoreCase(name)) {
                return dungeons;
            }
        }
        return ERROR;
    }

    public static boolean isDungeonMob(final String name) {
        for (Dungeons dungeons : values()) {
            if (dungeons.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
