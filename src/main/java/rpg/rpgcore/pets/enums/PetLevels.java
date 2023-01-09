package rpg.rpgcore.pets.enums;

import rpg.rpgcore.utils.DoubleUtils;

public enum PetLevels {
    I2(2, 100),
    I3(3, 200),
    I4(4, 300),
    I5(5, 400),
    I6(6, 500),
    I7(7, 600),
    I8(8, 700),
    I9(9, 800),
    I10(10, 900),
    I11(11, 1000),
    I12(12, 1250),
    I13(13, 1500),
    I14(14, 1750),
    I15(15, 2000),
    I16(16, 2250),
    I17(17, 2500),
    I18(18, 3000),
    I19(19, 3500),
    I20(20, 4000),
    I21(21, 4500),
    I22(22, 5000),
    I23(23, 5500),
    I24(24, 6000),
    I25(25, 6500),
    I26(26, 7000),
    I27(27, 7500),
    I28(28, 9000),
    I29(29, 10500),
    I30(30, 12000),
    I31(31, 13500),
    I32(32, 15000),
    I33(33, 16500),
    I34(34, 18000),
    I35(35, 19500),
    I36(36, 21000),
    I37(37, 22500),
    I38(38, 24000),
    I39(39, 25500),
    I40(40, 27000),
    I41(41, 30000),
    I42(42, 33000),
    I43(43, 36000),
    I44(44, 39000),
    I45(45, 42000),
    I46(46, 45000),
    I47(47, 48000),
    I48(48, 51000),
    I49(49, 54000),
    I50(50, 57000),
    I51(51, 60000),
    I52(52, 65000),
    I53(53, 70000),
    I54(54, 75000),
    I55(55, 80000),
    I56(56, 85000),
    I57(57, 90000),
    I58(58, 95000),
    I59(59, 100000),
    I60(60, 110000),
    I61(61, 120000),
    I62(62, 130000),
    I63(63, 140000),
    I64(64, 150000),
    I65(65, 160000),
    I66(66, 170000),
    I67(67, 180000),
    I68(68, 190000),
    I69(69, 200000),
    I70(70, 225000),
    I71(71, 250000),
    I72(72, 275000),
    I73(73, 300000),
    I74(74, 325000),
    I75(75, 350000),
    I76(76, 400000),
    I77(77, 450000),
    I78(78, 500000),
    I79(79, 550000),
    I80(80, 600000),
    I81(81, 700000),
    I82(82, 800000),
    I83(83, 900000),
    I84(84, 1250000),
    I85(85, 1500000),
    I86(86, 1750000),
    I87(87, 2000000),
    I88(88, 2250000),
    I89(89, 2500000),
    I90(90, 3000000),
    I91(91, 3500000),
    I92(92, 4000000),
    I93(93, 4500000),
    I94(94, 5000000),
    I95(95, 6000000),
    I96(96, 7000000),
    I97(97, 8000000),
    I98(98, 9000000),
    I99(99, 10000000),
    I100(100, 15000000);
    private final int Lvl;
    private final double reqExp;

    PetLevels(final int Lvl, final double reqExp) {
        this.Lvl = Lvl;
        this.reqExp = reqExp;
    }

    public int getLvl() {
        return this.Lvl;
    }

    public double getReqExp() {
        return DoubleUtils.round(this.reqExp, 2);
    }

    public static double getExp(final int lvl, final String rarity) {
        for (final PetLevels petLevels : values()) {
            if (petLevels.getLvl() == lvl) {
                switch (rarity) {
                    case "Zwykly":
                        return petLevels.getReqExp();
                    case "Rzadki":
                        return petLevels.getReqExp() * 1.25;
                    case "Epicki":
                        return petLevels.getReqExp() * 1.5;
                    case "Legendarny":
                        return petLevels.getReqExp() * 2;
                    case "Mityczny":
                        return petLevels.getReqExp() * 3;

                }
            }
        }
        return 0.0;
    }
}
