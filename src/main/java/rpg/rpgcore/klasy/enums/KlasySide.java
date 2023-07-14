package rpg.rpgcore.klasy.enums;

public enum KlasySide {

    BERSERK("Berserk"),
    PALADYN("Paladyn"),
    NINJA("Ninja"),
    SKRYTOBOJCA("Skrytobojca"),
    NEKROMANTA("Nekromanta"),
    MAG_ZYWIOLOW("Mag Zywiolow"),
    NIE_WYBRANO("");

    private final String name;

    KlasySide(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static KlasySide getByName(final String name) {
        for (final KlasySide klasySide : values()) {
            if (klasySide.getName().equalsIgnoreCase(name)) {
                return klasySide;
            }
        }
        return NIE_WYBRANO;
    }
}
