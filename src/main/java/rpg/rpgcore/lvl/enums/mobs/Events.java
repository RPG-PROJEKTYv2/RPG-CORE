package rpg.rpgcore.lvl.enums.mobs;
public enum Events {
    NAZWA_MOBA_EVENTOWEGO("NAZWA_MOOBA", 0, 0),
    ERROR("Error", 0, 0);
    private final String name;
    private final double kasa;
    private final double exp;

    Events(final String name, final double exp, final double kasa) {
        this.name = name;
        this.exp = exp;
        this.kasa = kasa;
    }

    public String getName() {
        return name;
    }

    public double getKasa() {
        return kasa;
    }

    public double getExp() {
        return exp;
    }

    public static Events getByName(String name) {
        for (Events events : values()) {
            if (events.getName().equalsIgnoreCase(name)) {
                return events;
            }
        }
        return ERROR;
    }

    public static boolean isEventMob(final String name) {
        for (Events events : values()) {
            if (events.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
