package rpg.rpgcore.dmg;

public enum EntityDamage {
    MAP_1_10_1("Najemnik Lvl. 3", 3.5),
    MAP_1_10_2("Najemnik Lvl. 5", 4.5),
    MAP_1_10_3("Najemnik Lvl. 7", 5.5),
    MAP_1_10_BOSS("[BOSS] Krol Wygnancow", 8),
    MAP_10_20_1("Goblin Lvl. 14", 5.5),
    MAP_10_20_2("Goblin Lvl. 16", 8),
    MAP_10_20_3("Goblin Lvl. 19", 10.5),
    MAP_10_20_BOSS("[BOSS] Wodz Goblinow", 14),
    MAP_20_30_1("Goryl Lvl. 21", 11.5),
    MAP_20_30_2("Goryl Lvl. 25", 16),
    MAP_20_30_3("Goryl Lvl. 28", 20.5),
    MAP_20_30_BOSS("[BOSS] Krol Goryli", 25),
    MAP_30_40_1("Zjawa Lvl. 32", 21.5),
    MAP_30_40_2("Zjawa Lvl. 36", 26.5),
    MAP_30_40_3("Zjawa Lvl. 39", 31.5),
    MAP_30_40_BOSS("[BOSS] Przekleta Dusza", 33),
    MAP_40_50_1("Straznik Swiatyni Lvl. 43", 31.5),
    MAP_40_50_2("Straznik Swiatyni Lvl. 46", 35),
    MAP_40_50_3("Straznik Swiatyni Lvl. 47", 38),
    MAP_40_50_BOSS("[BOSS] Tryton", 43),
    MAP_50_60_1("Mrozny Wilk Lvl. 52", 4185.2),
    MAP_50_60_2("Mrozny Wilk Lvl. 54", 5580.3),
    MAP_50_60_3("Mrozny Wilk Lvl. 56", 6975.3),
    MAP_60_70_1("Zywiolak Ognia Lvl. 64", 15446),
    MAP_60_70_2("Zywiolak Ognia Lvl. 66", 20595),
    MAP_60_70_3("Zywiolak Ognia Lvl. 68", 25743),
    MAP_70_80_1("Mroczna Dusza Lvl. 71", 39926),
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
