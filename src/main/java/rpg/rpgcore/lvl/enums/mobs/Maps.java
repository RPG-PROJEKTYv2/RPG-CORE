package rpg.rpgcore.lvl.enums.mobs;

public enum Maps {

    MAP_1_10_1("Najemnik Lvl. 3", 3.8),
    MAP_1_10_2("Najemnik Lvl. 5", 5),
    MAP_1_10_3("Najemnik Lvl. 7", 6.3),
    MAP_10_20_1("Goblin Lvl. 14", 13.8),
    MAP_10_20_2("Goblin Lvl. 16", 18.4),
    MAP_10_20_3("Goblin Lvl. 19", 22.9),
    MAP_20_30_1("Goryl Lvl. 21", 52.4),
    MAP_20_30_2("Goryl Lvl. 25", 69.8),
    MAP_20_30_3("Goryl Lvl. 28", 87.3),
    MAP_30_40_1("Zjawa Lvl. 32", 256.7),
    MAP_30_40_2("Zjawa Lvl. 36", 342.3),
    MAP_30_40_3("Zjawa Lvl. 39", 427.9),
    MAP_40_50_1("Straznik Swiatyni Lvl. 43", 1144.4),
    MAP_40_50_2("Straznik Swiatyni Lvl. 46", 1525.9),
    MAP_40_50_3("Straznik Swiatyni Lvl. 47", 1907.3),
    MAP_50_60_1("Mrozny Wilk Lvl. 52", 4185.2),
    MAP_50_60_2("Mrozny Wilk Lvl. 54", 5580.3),
    MAP_50_60_3("Mrozny Wilk Lvl. 56", 6975.3),
    MAP_60_70_1("Zywiolak Ognia Lvl. 64", 15446),
    MAP_60_70_2("Zywiolak Ognia Lvl. 66", 20595),
    MAP_60_70_3("Zywiolak Ognia Lvl. 68", 25743),
    MAP_70_80_1("Mroczna Dusza Lvl. 71", 39926),
    MAP_70_80_2("Mroczna Dusza Lvl. 75", 53235),
    MAP_70_80_3("Mroczna Dusza Lvl. 78", 66544),
    MAP_80_90_1(" Lvl. ", 129665),
    MAP_80_90_2(" Lvl. ", 172888),
    MAP_80_90_3(" Lvl. ", 216109),
    MAP_90_100_1(" Lvl. ", 225922),
    MAP_90_100_2(" Lvl. ", 301203),
    MAP_90_100_3(" Lvl. ", 376537),
    MAP_100_110_1(" Lvl. ", 702084),
    MAP_100_110_2(" Lvl. ", 936112),
    MAP_100_110_3(" Lvl. ", 1170140),
    MAP_110_120_1(" Lvl. ", 1968500),
    MAP_110_120_2(" Lvl. ", 2624645),
    MAP_110_120_3(" Lvl. ", 3280600),
    MAP_120_130_1(" Lvl. ", 5406545),
    MAP_120_130_2(" Lvl. ", 7208726),
    MAP_120_130_3(" Lvl. ", 9010101),
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
