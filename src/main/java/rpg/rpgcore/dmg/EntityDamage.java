package rpg.rpgcore.dmg;

public enum EntityDamage {
    MAP_1_10_1("Najemnik Lvl. 3", 2),
    MAP_1_10_2("Najemnik Lvl. 5", 2.3),
    MAP_1_10_3("Najemnik Lvl. 7", 2.6),
    MAP_1_10_BOSS("[BOSS] Krol Wygnancow", 5),
    MAP_10_20_1("Goblin Lvl. 14", 4.15),
    MAP_10_20_2("Goblin Lvl. 16", 4.6),
    MAP_10_20_3("Goblin Lvl. 19", 5),
    MAP_10_20_BOSS("[BOSS] Wodz Goblinow", 7),
    MAP_20_30_1("Goryl Lvl. 21", 6),
    MAP_20_30_2("Goryl Lvl. 25", 6.8),
    MAP_20_30_3("Goryl Lvl. 28", 7.5),
    MAP_20_30_BOSS("[BOSS] Krol Goryli", 15),
    MAP_30_40_1("Zjawa Lvl. 32", 16),
    MAP_30_40_2("Zjawa Lvl. 36", 18),
    MAP_30_40_3("Zjawa Lvl. 39", 20),
    MAP_30_40_BOSS("[BOSS] Przekleta Dusza", 24),
    MAP_40_50_1("Straznik Swiatyni Lvl. 43", 24),
    MAP_40_50_2("Straznik Swiatyni Lvl. 46", 27),
    MAP_40_50_3("Straznik Swiatyni Lvl. 47", 30),
    MAP_40_50_BOSS("[BOSS] Tryton", 50),
    MAP_50_60_1("Mrozny Wilk Lvl. 52", 68),
    MAP_50_60_2("Mrozny Wilk Lvl. 54", 75),
    MAP_50_60_3("Mrozny Wilk Lvl. 56", 83),
    MAP_60_70_1("Lodowy Sluga Lvl. 57", 180),
    MAP_60_70_2("Lodowy Sluga Lvl. 57", 20595),
    MAP_60_70_3("Lodowy Sluga Lvl. 58", 25743),
    MAP_70_80_1("Lodowy Sluga Lvl. 59", 39926),
    MAP_70_80_2("Mroczna Dusza Lvl. 75", 53235),
    MAP_70_80_3("Mroczna Dusza Lvl. 78", 66544);
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
