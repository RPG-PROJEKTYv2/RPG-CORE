package rpg.rpgcore.npc.rybak.enums;

public enum WedkaStats {
    I1(1, 100, 0, 0, 0, 0),
    I2(2, 125, 0.5, 0.1, 0, 0.001),
    I3(3, 150, 1, 0.15, 0, 0.002),
    I4(4, 175, 1.5, 0.2, 0, 0.003),
    I5(5, 200, 2, 0.25, 0, 0.003),
    I6(6, 250, 2.5, 0.5, 0, 0.003),
    I7(7, 300, 3.5, 0.75, 0, 0.004),
    I8(8, 350, 4.5, 1, 0, 0.004),
    I9(9, 400, 5, 1.5, 0, 0.004),
    I10(10, 500, 6, 2, 0.125, 0.005),
    I11(11, 600, 7, 2.5, 0.25, 0.005),
    I12(12, 700, 8, 5, 0.375, 0.005),
    I13(13, 800, 9, 7.5, 0.5, 0.006),
    I14(14, 900, 10, 10, 0.625, 0.006),
    I15(15, 1000, 12.5, 12.5, 0.75, 0.006),
    I16(16, 1275, 15, 15, 0.875, 0.007),
    I17(17, 1550, 17.5, 17.5, 1, 0.007),
    I18(18, 1825, 20, 20, 1.25, 0.008),
    I19(19, 2100, 22.5, 22.5, 1.5, 0.009),
    I20(20, 999999999, 25, 25, 1.75, 0.01), //exp = 2500 na lvl 21
    I99(21, 0, 0, 0, 0, 0);

    private final int lvl;
    private final double exp;
    private final double morskieSzczescie;
    private final double podwojnyDrop;
    private final double kufer;
    private final double nies;

    WedkaStats(final int lvl, final double exp, final double morskieSzczescie, final double podwojnyDrop, final double kufer, final double nies) {
        this.lvl = lvl;
        this.exp = exp;
        this.morskieSzczescie = morskieSzczescie;
        this.podwojnyDrop = podwojnyDrop;
        this.kufer = kufer;
        this.nies = nies;
    }

    public int getLvl() {
        return lvl;
    }

    public double getExp() {
        return exp;
    }

    public double getMorskieSzczescie() {
        return morskieSzczescie;
    }

    public double getPodwojnyDrop() {
        return podwojnyDrop;
    }

    public double getKufer() {
        return kufer;
    }

    public double getNies() {
        return nies;
    }

    public static WedkaStats getByLvl(final int lvl) {
        for (final WedkaStats wedkaStats : WedkaStats.values()) {
            if (wedkaStats.getLvl() == lvl) {
                return wedkaStats;
            }
        }
        return null;
    }
}
