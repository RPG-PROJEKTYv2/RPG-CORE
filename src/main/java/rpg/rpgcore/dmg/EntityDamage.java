package rpg.rpgcore.dmg;

public enum EntityDamage {
    MAP_1_10_1("Rozbojnik Lvl. 3", 1.3),
    MAP_1_10_2("Rozbojnik Lvl. 5", 1.6),
    MAP_1_10_3("Rozbojnik Lvl. 7", 2.0),
    MAP_1_10_BOSS("[BOSS] Dowodca Rozbojnikow", 3.2),

    MAP_10_20_1("Goblin Lvl. 14", 2.6),
    MAP_10_20_2("Goblin Lvl. 16", 3.2),
    MAP_10_20_3("Goblin Lvl. 19", 3.5),
    MAP_10_20_BOSS("[BOSS] Wodz Goblinow", 5),

    MAP_20_30_1("Goryl Lvl. 21", 3.8),
    MAP_20_30_2("Goryl Lvl. 25", 4.8),
    MAP_20_30_3("Goryl Lvl. 28", 5.3),
    MAP_20_30_BOSS("[BOSS] Krol Goryli", 6.5),

    MAP_30_40_1("Zjawa Lvl. 32", 4.8),
    MAP_30_40_2("Zjawa Lvl. 36", 5.2),
    MAP_30_40_3("Zjawa Lvl. 39", 5.6),
    MAP_30_40_BOSS("[BOSS] Przekleta Dusza", 8.5),

    MAP_40_50_1("Straznik Swiatyni Lvl. 43", 7),
    MAP_40_50_2("Straznik Swiatyni Lvl. 46", 7.4),
    MAP_40_50_3("Straznik Swiatyni Lvl. 47", 8.2),
    MAP_40_50_BOSS("[BOSS] Tryton", 12),

    MAP_50_60_1("Mrozny Wilk Lvl. 52", 9.7),
    MAP_50_60_2("Mrozny Wilk Lvl. 54", 10.5),
    MAP_50_60_3("Mrozny Wilk Lvl. 56", 11.2),

    MAP_DT_1("Lodowy Sluga Lvl. 57", 11.7),
    MAP_DT_2("Lodowy Sluga Lvl. 58", 12.7),
    MAP_DT_3("Lodowy Sluga Lvl. 59", 14.3),
    MAP_DT_BOSS("[BOSS] Krol Lodu", 21),

    MAP_60_70_1("Zywiolak Ognia Lvl. 64", 14.6),
    MAP_60_70_2("Zywiolak Ognia Lvl. 66", 16.8),
    MAP_60_70_3("Zywiolak Ognia Lvl. 68", 19.7),
    MAP_60_70_BOSS("[BOSS] Piekielny Rycerz", 26.0),

    PIEKIELNY_PRZEDSIONEK_MOB("Ognisty Duch Lvl. 69", 20.7),
    PIEKIELNY_PRZEDSIONEK_BOSS("[BOSS] Piekielny Wladca", 37.3),

    MAP_70_80_1("Mroczna Dusza Lvl. 71", 85.5),
    MAP_70_80_2("Mroczna DuszaLvl. 75", 90.5),
    MAP_70_80_3("Mroczna Dusza Lvl. 78", 100.0),
    MAP_70_80_BOSS("[BOSS] Przeklety Czarnoksieznik", 160.0),

    KOLOSEUM_MOB1("Zapomniany Wojownik Lvl. 75", 120.0),
    KOLOSEUM_MOB2("Zapomniany Wojownik Lvl. 79", 125.0),
    KOLOSEUM_MINIBOSS1("[MiniBOSS] Wyznawca Ateny", 140.0),
    KOLOSEUM_MINIBOSS2("[MiniBOSS] Wyznawca Posejdona", 145.0),
    KOLOSEUM_MINIBOSS3("[MiniBOSS] Wyznawca Zeusa", 150.0),
    KOLOSEUM_MINIBOSS4("[MiniBOSS] Wyznawca Hadesa", 155.0),
    KOLOSEUM_BOSS("[BOSS] Czempion Areny", 240.0),

    MAP_80_90_1("Pustynny Ptasznik Lvl. 84", 100.0),
    MAP_80_90_2("Pustynny Ptasznik Lvl. 87", 120.0),
    MAP_80_90_3("Pustynny Ptasznik Lvl. 89", 135.0),
    MAP_80_90_BOSS("[BOSS] Mityczny Pajak", 300.0),

    TAJEMNICZE_PIASKI_MOB1("Truposz Lvl. 85", 135.0),
    TAJEMNICZE_PIASKI_MOB2("Truposz Lvl. 89", 150.0),
    TAJEMNICZE_PIASKI_MINIBOSS1("[MiniBOSS] Pustynny Tarczownik", 180.0),
    TAJEMNICZE_PIASKI_MINIBOSS2("[MiniBOSS] Pustynny Przyzywacz", 200.0),
    TAJEMNICZE_PIASKI_BOSS("[BOSS] Cesarz Pustyni", 250.0),

    MAP_90_100_1("Podziemna Lowczyni Lvl. 92", 180.0),
    MAP_90_100_2("Podziemna Lowczyni Lvl. 95", 220.0),
    MAP_90_100_3("Podziemna Lowczyni Lvl. 98", 240.0),
    MAP_90_100_BOSS("[BOSS] Podziemny Rozpruwacz", 400.0),

    DEMONICZNE_SALE_MOB1("Demoniczy Lowca Lvl. 95", 1000.0),
    DEMONICZNE_SALE_MINIBOSS1("[MiniBOSS] Elitarny Sluga", 1600.0),
    DEMONICZNE_SALE_BOSS("[BOSS] Demon Ciemnosci", 2200.0),

    // DO TAD SKONCZONE POWYZEJ NIE !
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
