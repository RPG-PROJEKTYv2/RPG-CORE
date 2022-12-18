package rpg.rpgcore.dmg;

public enum EntityDamage {
    MAP_1_10_1("Najemnik Lvl. 3", 2.5),
    MAP_1_10_2("Najemnik Lvl. 5", 3.5),
    MAP_1_10_3("Najemnik Lvl. 7", 4.5),
    MAP_1_10_BOSS("[BOSS] Krol Wygnancow", 7),
    MAP_10_20_1("Goblin Lvl. 14", 3.5),
    MAP_10_20_2("Goblin Lvl. 16", 5),
    MAP_10_20_3("Goblin Lvl. 19", 7.5),
    MAP_10_20_BOSS("[BOSS] Wodz Goblinow", 10),
    MAP_20_30_1("Goryl Lvl. 21", 5.5),
    MAP_20_30_2("Goryl Lvl. 25", 8),
    MAP_20_30_3("Goryl Lvl. 28", 10.5),
    MAP_20_30_BOSS("[BOSS] Krol Goryli", 13),
    MAP_30_40_1("Zjawa Lvl. 32", 8),
    MAP_30_40_2("Zjawa Lvl. 36", 11),
    MAP_30_40_3("Zjawa Lvl. 39", 14),
    MAP_30_40_BOSS("[BOSS] Przekleta Dusza", 16),
    MAP_40_50_1("Straznik Swiatyni Lvl. 43", 11),
    MAP_40_50_2("Straznik Swiatyni Lvl. 46", 15),
    MAP_40_50_3("Straznik Swiatyni Lvl. 47", 19),
    MAP_40_50_BOSS("[BOSS] Tryton", 23),
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
