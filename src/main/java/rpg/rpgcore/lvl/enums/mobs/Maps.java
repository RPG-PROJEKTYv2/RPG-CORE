package rpg.rpgcore.lvl.enums.mobs;

public enum Maps {

    MAP_1_10_1("Najemnik Lvl. 3", 3.8, 1.5),
    MAP_1_10_2("Najemnik Lvl. 5", 5, 2),
    MAP_1_10_3("Najemnik Lvl. 7", 6.3, 3.5),
    MAP_1_10_BOSS("[BOSS] Krol Wygnancow", 8, 5.5),
    MAP_10_20_1("Goblin Lvl. 14", 13.8, 8.5),
    MAP_10_20_2("Goblin Lvl. 16", 18.4, 10),
    MAP_10_20_3("Goblin Lvl. 19", 22.9, 11.5),
    MAP_10_20_BOSS("[BOSS] Wodz Goblinow", 35, 15),
    MAP_20_30_1("Goryl Lvl. 21", 52.4, 18.5),
    MAP_20_30_2("Goryl Lvl. 25", 69.8, 21),
    MAP_20_30_3("Goryl Lvl. 28", 87.3, 23.5),
    MAP_20_30_BOSS("[BOSS] Krol Goryli", 100.5, 27),
    MAP_30_40_1("Zjawa Lvl. 32", 256.7, 30.5),
    MAP_30_40_2("Zjawa Lvl. 36", 342.3, 33),
    MAP_30_40_3("Zjawa Lvl. 39", 427.9, 35.5),
    MAP_30_40_BOSS("[BOSS] Przekleta Dusza", 600.5, 40),
    MAP_40_50_1("Straznik Swiatyni Lvl. 43", 1144.4, 45.5),
    MAP_40_50_2("Straznik Swiatyni Lvl. 46", 1525.9, 48),
    MAP_40_50_3("Straznik Swiatyni Lvl. 47", 1907.3, 50.5),
    MAP_40_50_BOSS("[BOSS] Tryton", 2344.1, 53),
    MAP_50_60_1("Mrozny Wilk Lvl. 52", 4185.2, 58.5),
    MAP_50_60_2("Mrozny Wilk Lvl. 54", 5580.3, 61),
    MAP_50_60_3("Mrozny Wilk Lvl. 56", 6975.3, 63.5),
    MAP_50_60_BOSS("[BOSS] Mityczny Lodowy Golem", 12365.2, 63.5),
    MAP_60_70_1("Zywiolak Ognia Lvl. 64", 15446, 68.5),
    MAP_60_70_2("Zywiolak Ognia Lvl. 66", 20595, 71),
    MAP_60_70_3("Zywiolak Ognia Lvl. 68", 25743, 73.5),
    MAP_60_70_BOSS("[BOSS] Piekielny Rycerz", 30621, 80),
    MAP_70_80_1("Mroczna Dusza Lvl. 71", 39926, 1),
    MAP_70_80_2("Mroczna Dusza Lvl. 75", 53235, 1),
    MAP_70_80_3("Mroczna Dusza Lvl. 78", 66544, 1),
    MAP_80_90_1(" Lvl. ", 129665, 1),
    MAP_80_90_2(" Lvl. ", 172888, 1),
    MAP_80_90_3(" Lvl. ", 216109, 1),
    MAP_90_100_1(" Lvl. ", 225922, 1),
    MAP_90_100_2(" Lvl. ", 301203, 1),
    MAP_90_100_3(" Lvl. ", 376537, 1),
    MAP_100_110_1(" Lvl. ", 702084, 1),
    MAP_100_110_2(" Lvl. ", 936112, 1),
    MAP_100_110_3(" Lvl. ", 1170140, 1),
    MAP_110_120_1(" Lvl. ", 1968500, 1),
    MAP_110_120_2(" Lvl. ", 2624645, 1),
    MAP_110_120_3(" Lvl. ", 3280600, 1),
    MAP_120_130_1(" Lvl. ", 5406545, 1),
    MAP_120_130_2(" Lvl. ", 7208726, 1),
    MAP_120_130_3(" Lvl. ", 9010101, 1),
    ERROR("Error", 0, 0);

    private final String name;
    private final double exp;
    private final double kasa;

    Maps(final String name, final double exp, final double kasa) {
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
