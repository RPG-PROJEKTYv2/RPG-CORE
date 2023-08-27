package rpg.rpgcore.dungeons;

public enum DungeonStatus {
    ENDED(""),
    RESETTING("Resetowanie..."),
    WAITING("Oczekiwanie..."),
    UPGRADING("Ulepszanie..."),
    OPENED_GATE("&eBrama Otwarta"),
    CLOSED_GATE("&cBrama Zamknięta"),
    STARTED("Rozpoczeto"),
    ETAP_0("0"),
    ETAP_1("1"),
    ETAP_1_RDZENIE("1"),
    ETAP_1_MINIBOSS("1"),
    ETAP_1_BOSS("1"),
    ETAP_2("2"),
    ETAP_3("3"),
    ETAP_4("4"),
    ETAP_5("5"),
    ETAP_6("6"),
    ETAP_7("7"),
    BOSS("BOSS"),
    ONGOING(""),
    FINISHED("");

    private final String name;

    DungeonStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
