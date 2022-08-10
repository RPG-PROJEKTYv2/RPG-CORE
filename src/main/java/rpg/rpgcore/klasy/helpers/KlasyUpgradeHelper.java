package rpg.rpgcore.klasy.helpers;

public enum KlasyUpgradeHelper {

    I1(1, 100, 1500000, 0.5),
    I2(2, 250, 3000000, 0.5),
    I3(3, 400, 4500000, 0.5),
    I4(4, 550, 6000000, 0.5),
    I5(5, 700, 7500000, 0.5),
    I6(6, 850, 8000000, 1.5),
    I7(7, 1000, 9500000, 2.5),
    I8(8, 1150, 10000000, 3.5),
    I9(9, 1300, 11500000, 4.5),
    I10(10, 1450, 13500000, 5.5);

    private final int lvl, points;
    private final double money, toAdd;

    KlasyUpgradeHelper(int lvl, int points, double money, double toAdd) {
        this.lvl = lvl;
        this.points = points;
        this.money = money;
        this.toAdd = toAdd;
    }

    public static KlasyUpgradeHelper getByLvl(int lvl) {
        for(KlasyUpgradeHelper klasyUpgradeHelper : values()) {
            if(klasyUpgradeHelper.lvl == lvl) {
                return klasyUpgradeHelper;
            }
        }
        return KlasyUpgradeHelper.I1;
    }

    public int getLvl() {
        return this.lvl;
    }

    public int getPoints() {
        return this.points;
    }

    public double getMoney() {
        return this.money;
    }

    public double getToAdd() {
        return this.toAdd;
    }

}
