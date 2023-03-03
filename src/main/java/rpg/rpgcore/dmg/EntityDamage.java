package rpg.rpgcore.dmg;

public enum EntityDamage {
    MAP_1_10_1("Najemnik Lvl. 3", 2),
    MAP_1_10_2("Najemnik Lvl. 5", 2.3),
    MAP_1_10_3("Najemnik Lvl. 7", 2.6),
    MAP_1_10_BOSS("[BOSS] Krol Wygnancow", 2.99),

    MAP_10_20_1("Goblin Lvl. 14", 4.15),
    MAP_10_20_2("Goblin Lvl. 16", 4.6),
    MAP_10_20_3("Goblin Lvl. 19", 5),
    MAP_10_20_BOSS("[BOSS] Wodz Goblinow", 5.98),

    MAP_20_30_1("Goryl Lvl. 21", 6),
    MAP_20_30_2("Goryl Lvl. 25", 6.8),
    MAP_20_30_3("Goryl Lvl. 28", 7.5),
    MAP_20_30_BOSS("[BOSS] Krol Goryli", 8.84),

    MAP_30_40_1("Zjawa Lvl. 32", 16),
    MAP_30_40_2("Zjawa Lvl. 36", 18),
    MAP_30_40_3("Zjawa Lvl. 39", 20),
    MAP_30_40_BOSS("[BOSS] Przekleta Dusza", 23.4),

    MAP_40_50_1("Straznik Swiatyni Lvl. 43", 24),
    MAP_40_50_2("Straznik Swiatyni Lvl. 46", 27),
    MAP_40_50_3("Straznik Swiatyni Lvl. 47", 30),
    MAP_40_50_BOSS("[BOSS] Tryton", 45.1),

    MAP_50_60_1("Mrozny Wilk Lvl. 52", 68),
    MAP_50_60_2("Mrozny Wilk Lvl. 54", 75),
    MAP_50_60_3("Mrozny Wilk Lvl. 56", 83),

    MAP_DT_1("Lodowy Sluga Lvl. 57", 120),
    MAP_DT_2("Lodowy Sluga Lvl. 58", 134),
    MAP_DT_3("Lodowy Sluga Lvl. 59", 156),
    MAP_DT_BOSS("[BOSS] Mrozny Wladca", 245),

    MAP_60_70_1("Zywiolak Ognia Lvl. 64", 290),
    MAP_60_70_2("Zywiolak Ognia Lvl. 66", 325),
    MAP_60_70_3("Zywiolak Ognia Lvl. 68", 355),
    MAP_60_70_BOSS("[BOSS] Piekielna Dusza", 422.5),

    MAP_70_80_1("Mroczna Dusza Lvl. 71", 1500),
    MAP_70_80_2("Mroczna DuszaLvl. 75", 1650),
    MAP_70_80_3("Mroczna Dusza Lvl. 78", 1800),
    MAP_70_80_BOSS("[BOSS] Przeklety Czarnoksieznik", 2145),

    MAP_80_90_1("Pustynny Ptasznik Lvl. 84", 2800),
    MAP_80_90_2("Pustynny Ptasznik Lvl. 87", 3150),
    MAP_80_90_3("Pustynny Ptasznik Lvl. 89", 3500),
    MAP_80_90_BOSS("[BOSS] Mityczny Pajak", 4095),

    MAP_90_100_1("Podziemna Lowczyni Lvl. 92", 6000),
    MAP_90_100_2("Podziemna Lowczyni Lvl. 95", 6700),
    MAP_90_100_3("Podziemna Lowczyni Lvl. 98", 7350),
    MAP_90_100_BOSS("[BOSS] Podziemny Rozpruwacz", 8710),

    MAP_100_110_1("Podwodny Straznik Lvl. 104", 13300),
    MAP_100_110_2("Podwodny Straznik Lvl. 106", 14700),
    MAP_100_110_3("Podwodny Straznik Lvl. 109", 16200),
    MAP_100_110_BOSS("[BOSS] Mityczny Kraken", 19110),

    MAP_110_120_1("Mrozny Stroz Lvl. 114", 17800),
    MAP_110_120_2("Mrozny Stroz Lvl. 116", 19800),
    MAP_110_120_3("Mrozny Stroz Lvl. 118", 21800),
    MAP_110_120_BOSS("[BOSS] Krysztalowy Wladca", 25740),

    MAP_120_130_1("Mnich Lvl. 123", 22900),
    MAP_120_130_2("Mnich Lvl. 126", 26000),
    MAP_120_130_3("Mnich Lvl. 129", 28000),
    MAP_120_130_BOSS("[BOSS] Starozytny Smoczy Cesarz", 33800);
    private final String name;
    private final double damage;

    EntityDamage(final String name, final double damage) {
        this.name = name;
        this.damage = damage;
    }


    public String getName() {
        return name;
    }


    public double getDamage() {
        return damage;
    }


    public static double getByName(final String name) {
        for (final EntityDamage entityDamage : values()) {
            if (entityDamage.getName().equalsIgnoreCase(name)) {
                return entityDamage.getDamage();
            }
        }
        return 0;
    }
}
