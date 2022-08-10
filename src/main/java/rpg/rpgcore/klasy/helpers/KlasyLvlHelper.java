package rpg.rpgcore.klasy.helpers;

public enum KlasyLvlHelper {
    I1(1, 500, 475),
    I2(2, 600, 570),
    I3(3, 720, 684),
    I4(4, 864, 821),
    I5(5, 1037, 985),
    I6(6, 1244, 1182),
    I7(7, 1493, 1418),
    I8(8, 1792, 1702),
    I9(9, 2150, 2042),
    I10(10, 2580, 2451),
    I11(11, 3096, 2941),
    I12(12, 3715, 3529),
    I13(13, 4458, 4235),
    I14(14, 5350, 5082),
    I15(15, 6420, 6099),
    I16(16, 7704, 7318),
    I17(17, 9244, 8782),
    I18(18, 11093, 10538),
    I19(19, 13312, 12646),
    I20(20, 15974, 15175),
    I21(21, 19169, 18210),
    I22(22, 23003, 21852),
    I23(23, 27603, 26223),
    I24(24, 33124, 31468),
    I25(25, 39748, 37761),
    I26(26, 47698, 45313),
    I27(27, 57238, 54376),
    I28(28, 68685, 65251),
    I29(29, 82422, 78301),
    I30(30, 98907, 93961),
    I31(31, 118688, 112754),
    I32(32, 142426, 135304),
    I33(33, 170911, 162365),
    I34(34, 205093, 194838),
    I35(35, 246112, 233806),
    I36(36, 295334, 280567),
    I37(37, 354401, 336681),
    I38(38, 425281, 404017),
    I39(39, 510337, 484820),
    I40(40, 612405, 581785),
    I41(41, 734886, 698141),
    I42(42, 881863, 837770),
    I43(43, 1058236, 1005324),
    I44(44, 1269883, 1206389),
    I45(45, 1523859, 1447666),
    I46(46, 1828631, 1737199),
    I47(47, 2194357, 2084639),
    I48(48, 2633229, 2501567),
    I49(49, 3159874, 3001881),
    I50(50, 3791850, 3602257);




    private final int lvl, exp, expForSvip;

    KlasyLvlHelper(int lvl, int exp, int expForSvip) {
        this.lvl = lvl;
        this.exp = exp;
        this.expForSvip = expForSvip;
    }

    public static KlasyLvlHelper getByLvl(int lvl) {
        for (KlasyLvlHelper klasyHelper : values()) {
            if (klasyHelper.lvl == lvl) {
                return klasyHelper;
            }
        }
        return KlasyLvlHelper.I1;
    }

    public int getLvl() {
        return this.lvl;
    }

    public int getExp() {
        return this.exp;
    }

    public int getExpForSvip() {
        return this.expForSvip;
    }

}
