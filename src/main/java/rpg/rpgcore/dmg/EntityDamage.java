package rpg.rpgcore.dmg;

public enum EntityDamage {
    MAP_1_10_1("Rozbojnik Lvl. 3", 1.3),
    MAP_1_10_2("Rozbojnik Lvl. 5", 1.6),
    MAP_1_10_3("Rozbojnik Lvl. 7", 2),
    MAP_1_10_BOSS("[BOSS] Dowodca Rozbojnikow", 2.99),

    MAP_10_20_1("Goblin Lvl. 14", 2.6),
    MAP_10_20_2("Goblin Lvl. 16", 3.2),
    MAP_10_20_3("Goblin Lvl. 19", 3.5),
    MAP_10_20_BOSS("[BOSS] Wodz Goblinow", 5),

    MAP_20_30_1("Goryl Lvl. 21", 3.8),
    MAP_20_30_2("Goryl Lvl. 25", 4.8),
    MAP_20_30_3("Goryl Lvl. 28", 5.3),
    MAP_20_30_BOSS("[BOSS] Krol Goryli", 6.5),

    MAP_30_40_1("Zjawa Lvl. 32", 7.8),
    MAP_30_40_2("Zjawa Lvl. 36", 9.7),
    MAP_30_40_3("Zjawa Lvl. 39", 10.7),
    MAP_30_40_BOSS("[BOSS] Przekleta Dusza", 15),

    MAP_40_50_1("Straznik Swiatyni Lvl. 43", 13),
    MAP_40_50_2("Straznik Swiatyni Lvl. 46", 16.5),
    MAP_40_50_3("Straznik Swiatyni Lvl. 47", 18.5),
    MAP_40_50_BOSS("[BOSS] Tryton", 26),

    MAP_50_60_1("Mrozny Wilk Lvl. 52", 35),
    MAP_50_60_2("Mrozny Wilk Lvl. 54", 43),
    MAP_50_60_3("Mrozny Wilk Lvl. 56", 48),

    MAP_DT_1("Lodowy Sluga Lvl. 57", 35),
    MAP_DT_2("Lodowy Sluga Lvl. 58", 43),
    MAP_DT_3("Lodowy Sluga Lvl. 59", 48),
    MAP_DT_BOSS("[BOSS] Mrozny Wladca", 75),

    MAP_60_70_1("Zywiolak Ognia Lvl. 64", 180),
    MAP_60_70_2("Zywiolak Ognia Lvl. 66", 220),
    MAP_60_70_3("Zywiolak Ognia Lvl. 68", 250),
    MAP_60_70_BOSS("[BOSS] Piekielna Dusza", 400),

    MAP_70_80_1("Mroczna Dusza Lvl. 71", 750),
    MAP_70_80_2("Mroczna DuszaLvl. 75", 950),
    MAP_70_80_3("Mroczna Dusza Lvl. 78", 1050),
    MAP_70_80_BOSS("[BOSS] Przeklety Czarnoksieznik", 1745),

    MAP_80_90_1("Pustynny Ptasznik Lvl. 84", 2200),
    MAP_80_90_2("Pustynny Ptasznik Lvl. 87", 2800),
    MAP_80_90_3("Pustynny Ptasznik Lvl. 89", 3100),
    MAP_80_90_BOSS("[BOSS] Mityczny Pajak", 5095),

    MAP_90_100_1("Podziemna Lowczyni Lvl. 92", 5500),
    MAP_90_100_2("Podziemna Lowczyni Lvl. 95", 6600),
    MAP_90_100_3("Podziemna Lowczyni Lvl. 98", 7150),
    MAP_90_100_BOSS("[BOSS] Podziemny Rozpruwacz", 10710),

    MAP_100_110_1("Podwodny Straznik Lvl. 104", 10300),
    MAP_100_110_2("Podwodny Straznik Lvl. 106", 12700),
    MAP_100_110_3("Podwodny Straznik Lvl. 109", 14200),
    MAP_100_110_BOSS("[BOSS] Mityczny Kraken", 22110),

    MAP_110_120_1("Mrozny Stroz Lvl. 114", 18800),
    MAP_110_120_2("Mrozny Stroz Lvl. 116", 20800),
    MAP_110_120_3("Mrozny Stroz Lvl. 118", 23800),
    MAP_110_120_BOSS("[BOSS] Krysztalowy Wladca", 30500),

    MAP_120_130_1("Mnich Lvl. 123", 24900),
    MAP_120_130_2("Mnich Lvl. 126", 27000),
    MAP_120_130_3("Mnich Lvl. 129", 29000),
    MAP_120_130_BOSS("[BOSS] Starozytny Smoczy Cesarz", 39000);
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
