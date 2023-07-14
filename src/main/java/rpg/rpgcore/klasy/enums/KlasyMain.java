package rpg.rpgcore.klasy.enums;

public enum KlasyMain {

    WOJOWNIK("Wojownik"),
    ZABOJCA("Zabojca"),
    CZARODZIEJ("Czarodziej"),
    NIE_WYBRANO("");

    private final String name;

    KlasyMain(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static KlasyMain getByName(String name) {
        for (KlasyMain klasyMain : values()) {
            if (klasyMain.getName().equalsIgnoreCase(name)) {
                return klasyMain;
            }
        }
        return NIE_WYBRANO;
    }
}
