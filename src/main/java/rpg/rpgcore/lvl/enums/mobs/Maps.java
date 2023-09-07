package rpg.rpgcore.lvl.enums.mobs;

import rpg.rpgcore.utils.DoubleUtils;

public enum Maps {

    MAP_1_10_1("Rozbojnik Lvl. 3", 3.4, 1, 1, 15),
    MAP_1_10_2("Rozbojnik Lvl. 5", 4.6, 2, 1, 15),
    MAP_1_10_3("Rozbojnik Lvl. 7", 6.0, 3, 1, 15),
    MAP_1_10_BOSS("[BOSS] Dowodca Rozbojnikow", 20, 300, 1, 15),
    MAP_10_20_1("Goblin Lvl. 14", 13.8, 5, 10, 25),
    MAP_10_20_2("Goblin Lvl. 16", 18.4, 6, 10, 25),
    MAP_10_20_3("Goblin Lvl. 19", 22.9, 7, 10, 25),
    MAP_10_20_BOSS("[BOSS] Wodz Goblinow", 75, 600, 10, 25),
    MAP_20_30_1("Goryl Lvl. 21", 52.4, 11, 20, 35),
    MAP_20_30_2("Goryl Lvl. 25", 69.8, 12, 20, 35),
    MAP_20_30_3("Goryl Lvl. 28", 87.3, 13, 20, 35),
    MAP_20_30_BOSS("[BOSS] Krol Goryli", 300.5, 2_000, 20, 35),
    MAP_30_40_1("Zjawa Lvl. 32", 256.7, 15, 30, 45),
    MAP_30_40_2("Zjawa Lvl. 36", 342.3, 16, 30, 45),
    MAP_30_40_3("Zjawa Lvl. 39", 427.9, 17, 30, 45),
    MAP_30_40_BOSS("[BOSS] Przekleta Dusza", 1_900.5, 10_000, 30, 45),
    MAP_40_50_1("Straznik Swiatyni Lvl. 43", 21, 200, 40, 55),
    MAP_40_50_2("Straznik Swiatyni Lvl. 46", 22, 250, 40, 55),
    MAP_40_50_3("Straznik Swiatyni Lvl. 47", 23, 300, 40, 55),
    MAP_40_50_BOSS("[BOSS] Tryton", 9_344.1, 40_000, 40, 55),
    MAP_50_60_1("Mrozny Wilk Lvl. 52", 1750.2, 75, 50, 65),
    MAP_50_60_2("Mrozny Wilk Lvl. 54", 2384.3, 77, 50, 65),
    MAP_50_60_3("Mrozny Wilk Lvl. 56", 2980.3, 80, 50, 65),
    MAP_60_70_1("Zywiolak Ognia Lvl. 64", 5_524.7, 100, 60, 70),
    MAP_60_70_2("Zywiolak Ognia Lvl. 66", 7_366.3, 110, 60, 70),
    MAP_60_70_3("Zywiolak Ognia Lvl. 68", 9_207.8, 120, 60, 70),
    MAP_60_70_BOSS("[BOSS] Piekielny Rycerz", 205_542.0, 200_000, 60, 70),
    MAP_70_80_1("Mroczna Dusza Lvl. 71", 16_596.0, 135, 70, 80),
    MAP_70_80_2("Mroczna Dusza Lvl. 75", 22_129.0, 145, 70, 80),
    MAP_70_80_3("Mroczna Dusza Lvl. 78", 27_661.0, 155, 70, 80),
    MAP_70_80_BOSS("[BOSS] Przeklety  Czarnoksieznik", 1_200_000, 500_000, 70, 80),
    MAP_80_90_1("Pustynny Ptasznik Lvl. 84", 55_396.0, 170, 80, 90),
    MAP_80_90_2("Pustynny Ptasznik Lvl. 87", 73_861.0, 180, 80, 90),
    MAP_80_90_3("Pustynny Ptasznik Lvl. 89", 92_327.0, 190, 80, 90),
    MAP_80_90_BOSS("[BOSS] Mityczny Pajak", 5_908_910.0, 2_000_000, 80, 90),
    MAP_90_100_1("Podziemna Lowczyni Lvl. 92", 159_756.0, 250, 90, 100),
    MAP_90_100_2("Podziemna Lowczyni Lvl. 95", 213_007.0, 260, 90, 100),
    MAP_90_100_3("Podziemna Lowczyni Lvl. 98", 266_259.0, 280, 90, 100),
    MAP_90_100_BOSS("[BOSS] Podziemny Rozpruwacz", 16_625_921.0, 3_550_000, 90, 100),
    MAP_100_110_1(" Lvl. ", 519_905.0, 440, 100, 110),
    MAP_100_110_2(" Lvl. ", 696_112.0, 460, 100, 110),
    MAP_100_110_3(" Lvl. ", 870_140.0, 480, 100, 110),
    MAP_100_110_BOSS(" Lvl. ", 106_647_176.0, 5_280_000, 100, 110),
    MAP_110_120_1(" Lvl. ", 1_368_500.0, 600, 110, 120),
    MAP_110_120_2(" Lvl. ", 1_824_645.0, 620, 110, 120),
    MAP_110_120_3(" Lvl. ", 2_280_600.0, 640, 110, 120),
    MAP_110_120_BOSS(" Lvl. ", 640_745_179.0, 7_300_000, 110, 120),
    MAP_120_130_1(" Lvl. ", 3_906_545.0, 960, 120, 130),
    MAP_120_130_2(" Lvl. ", 5_208_726.0, 980, 120, 130),
    MAP_120_130_3(" Lvl. ", 6_510_101.0, 1_000, 120, 130),
    MAP_120_130_BOSS(" Lvl. ", 3_849_650_777.0, 8_675_000, 120, 130),
    ERROR("Error", 0, 0, 0, 0);

    private final String name;
    private final double exp;
    private final double kasa;
    private final int minLvl;
    private final int reqLvl;

    Maps(final String name, final double exp, final double kasa, final int minLvl, final int reqLvl) {
        this.name = name;
        this.exp = exp;
        this.kasa = kasa;
        this.minLvl = minLvl;
        this.reqLvl = reqLvl;
    }

    public String getName() {
        return name;
    }

    public double getKasa() {
        return DoubleUtils.round(kasa, 2);
    }

    public double getExp() {
        return DoubleUtils.round(exp, 2);
    }

    public int getMinLvl() {
        return minLvl;
    }

    public int getReqLvl() {
        return reqLvl;
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
