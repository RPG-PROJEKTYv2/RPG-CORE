package rpg.rpgcore.dmg;

public enum EntityDamage {
    MAP_1_10_1("Rozbojnik Lvl. 3", 15),
    MAP_1_10_2("Rozbojnik Lvl. 5", 17),
    MAP_1_10_3("Rozbojnik Lvl. 7", 18),
    MAP_1_10_BOSS("[BOSS] Dowodca Rozbojnikow", 30),

    MAP_10_20_1("Goblin Lvl. 14", 34),
    MAP_10_20_2("Goblin Lvl. 16", 38),
    MAP_10_20_3("Goblin Lvl. 19", 44),
    MAP_10_20_BOSS("[BOSS] Wodz Goblinow", 56),

    MAP_20_30_1("Goryl Lvl. 21", 70),
    MAP_20_30_2("Goryl Lvl. 25", 75),
    MAP_20_30_3("Goryl Lvl. 28", 80),
    MAP_20_30_BOSS("[BOSS] Krol Goryli", 125),

    MAP_30_40_1("Zjawa Lvl. 32", 140),
    MAP_30_40_2("Zjawa Lvl. 36", 180),
    MAP_30_40_3("Zjawa Lvl. 39", 200),
    MAP_30_40_BOSS("[BOSS] Przekleta Dusza", 280),

    MAP_40_50_1("Straznik Swiatyni Lvl. 43", 240),
    MAP_40_50_2("Straznik Swiatyni Lvl. 46", 300),
    MAP_40_50_3("Straznik Swiatyni Lvl. 47", 340),
    MAP_40_50_BOSS("[BOSS] Tryton", 380),

    MAP_50_60_1("Mrozny Wilk Lvl. 52", 560),
    MAP_50_60_2("Mrozny Wilk Lvl. 54", 600),
    MAP_50_60_3("Mrozny Wilk Lvl. 56", 680),

    MAP_DT_1("Lodowy Sluga Lvl. 57", 640),
    MAP_DT_2("Lodowy Sluga Lvl. 58", 660),
    MAP_DT_3("Lodowy Sluga Lvl. 59", 680),
    MAP_DT_BOSS("[BOSS] Krol Lodu", 1_000),

    MAP_60_70_1("Zywiolak Ognia Lvl. 64", 1_200),
    MAP_60_70_2("Zywiolak Ognia Lvl. 66", 1_300),
    MAP_60_70_3("Zywiolak Ognia Lvl. 68", 1_400),
    MAP_60_70_BOSS("[BOSS] Piekielny Rycerz", 2_000),

    PIEKIELNY_PRZEDSIONEK_MOB("Ognisty Duch Lvl. 69", 1_350),
    PIEKIELNY_PRZEDSIONEK_BOSS("[BOSS] Piekielny Wladca", 2_500),

    MAP_70_80_1("Mroczna Dusza Lvl. 71", 4_000),
    MAP_70_80_2("Mroczna DuszaLvl. 75", 4_250),
    MAP_70_80_3("Mroczna Dusza Lvl. 78", 4_500),
    MAP_70_80_BOSS("[BOSS] Przeklety Czarnoksieznik", 7_500),

    KOLOSEUM_MOB1("Zapomniany Wojownik Lvl. 75", 4_200),
    KOLOSEUM_MOB2("Zapomniany Wojownik Lvl. 79", 4_300),
    KOLOSEUM_MINIBOSS1("[MiniBOSS] Wyznawca Ateny", 5_400),
    KOLOSEUM_MINIBOSS2("[MiniBOSS] Wyznawca Posejdona", 5_400),
    KOLOSEUM_MINIBOSS3("[MiniBOSS] Wyznawca Zeusa", 5_400),
    KOLOSEUM_MINIBOSS4("[MiniBOSS] Wyznawca Hadesa", 5_400),
    KOLOSEUM_BOSS("[BOSS] Czempion Areny", 8_000),

    MAP_80_90_1("Pustynny Ptasznik Lvl. 84", 13_800),
    MAP_80_90_2("Pustynny Ptasznik Lvl. 87", 15_000),
    MAP_80_90_3("Pustynny Ptasznik Lvl. 89", 16_500),
    MAP_80_90_BOSS("[BOSS] Mityczny Pajak", 23_500),

    TAJEMNICZE_PIASKI_MOB1("Truposz Lvl. 85", 14_500),
    TAJEMNICZE_PIASKI_MOB2("Truposz Lvl. 89", 16_700),
    TAJEMNICZE_PIASKI_MINIBOSS1("[MiniBOSS] Pustynny Tarczownik", 21_000),
    TAJEMNICZE_PIASKI_MINIBOSS2("[MiniBOSS] Pustynny Przyzywacz", 23_500),
    TAJEMNICZE_PIASKI_BOSS("[BOSS] Cesarz Pustyni", 26_000),

    MAP_90_100_1("Podziemna Lowczyni Lvl. 92", 22_000),
    MAP_90_100_2("Podziemna Lowczyni Lvl. 95", 24_800),
    MAP_90_100_3("Podziemna Lowczyni Lvl. 98", 28_500),
    MAP_90_100_BOSS("[BOSS] Podziemny Rozpruwacz", 47_750),

    DEMONICZNE_SALE_MOB1("Demoniczy Lowca Lvl. 95", 26_750),
    DEMONICZNE_SALE_MINIBOSS1("[MiniBOSS] Elitarny Sluga", 40_500),
    DEMONICZNE_SALE_BOSS("[BOSS] Demon Ciemnosci", 55_000),

    // DO TAD SKONCZONE POWYZEJ NIE !
    MAP_100_110_1("Podwodny Straznik Lvl. 104", 31_500),
    MAP_100_110_2("Podwodny Straznik Lvl. 106", 32_400),
    MAP_100_110_3("Podwodny Straznik Lvl. 109", 33_300),
    MAP_100_110_BOSS("[BOSS] Mistyczny Kraken", 150_000),

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
        this.damage = damage * 2;
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
