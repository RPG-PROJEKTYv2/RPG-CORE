package rpg.rpgcore.npc.rybak.enums;

import lombok.Getter;
import rpg.rpgcore.utils.DoubleUtils;

public enum WedkaExp {
    LVL_1(1, 20, 0.0),
    LVL_2(2, 30, 0.1),
    LVL_3(3, 40, 0.1),
    LVL_4(4, 50, 0.1),
    LVL_5(5, 60, 0.1),
    LVL_6(6, 70, 0.1),
    LVL_7(7, 80, 0.1),
    LVL_8(8, 90, 0.1),
    LVL_9(9, 100, 0.1),
    LVL_10(10, 115, 0.2),
    LVL_11(11, 130, 0.2),
    LVL_12(12, 145, 0.2),
    LVL_13(13, 160, 0.2),
    LVL_14(14, 175, 0.2),
    LVL_15(15, 190, 0.2),
    LVL_16(16, 205, 0.2),
    LVL_17(17, 220, 0.2),
    LVL_18(18, 235, 0.2),
    LVL_19(19, 250, 0.2),
    LVL_20(20, 275, 0.2),
    LVL_21(21, 300, 0.2),
    LVL_22(22, 325, 0.2),
    LVL_23(23, 350, 0.2),
    LVL_24(24, 375, 0.2),
    LVL_25(25, 400, 0.2),
    LVL_26(26, 425, 0.1),
    LVL_27(27, 450, 0.1),
    LVL_28(28, 475, 0.1),
    LVL_29(29, 500, 0.1),
    LVL_30(30, 550, 0.1),
    LVL_31(31, 600, 0.1),
    LVL_32(32, 650, 0.1),
    LVL_33(33, 700, 0.1),
    LVL_34(34, 750, 0.1),
    LVL_35(35, 800, 0.1),
    LVL_36(36, 850, 0.1),
    LVL_37(37, 900, 0.1),
    LVL_38(38, 950, 0.1),
    LVL_39(39, 1000, 0.1),
    LVL_40(40, 1100, 0.1),
    LVL_41(41, 1200, 0.1),
    LVL_42(42, 1300, 0.1),
    LVL_43(43, 1400, 0.1),
    LVL_44(44, 1500, 0.1),
    LVL_45(45, 1600, 0.1),
    LVL_46(46, 1700, 0.1),
    LVL_47(47, 1800, 0.1),
    LVL_48(48, 1900, 0.1),
    LVL_49(49, 2000, 0.1),
    LVL_50(50, 2150, 0.1),
    LVL_51(51, 2300, 0.1),
    LVL_52(52, 2450, 0.1),
    LVL_53(53, 2600, 0.1),
    LVL_54(54, 2750, 0.1),
    LVL_55(55, 2900, 0.1),
    LVL_56(56, 3050, 0.1),
    LVL_57(57, 3200, 0.1),
    LVL_58(58, 3350, 0.1),
    LVL_59(59, 3500, 0.1),
    LVL_60(60, 3750, 0.1),
    LVL_61(61, 4000, 0.1),
    LVL_62(62, 4250, 0.1),
    LVL_63(63, 4500, 0.1),
    LVL_64(64, 4750, 0.1),
    LVL_65(65, 5000, 0.1),
    LVL_66(66, 5250, 0.1),
    LVL_67(67, 5500, 0.1),
    LVL_68(68, 5750, 0.1),
    LVL_69(69, 6000, 0.1),
    LVL_70(70, 6500, 0.1),
    LVL_71(71, 7000, 0.1),
    LVL_72(72, 7500, 0.1),
    LVL_73(73, 8000, 0.1),
    LVL_74(74, 8500, 0.1),
    LVL_75(75, 10_000, 0.1),
    LVL_99(99, 999999, 0.1);

    @Getter
    private final int lvl, exp;
    private final double dropx2;

    WedkaExp(final int lvl, final int exp, final double dropx2) {
        this.lvl = lvl;
        this.exp = exp;
        this.dropx2 = dropx2;
    }
    
    public double getDropx2() {
        return DoubleUtils.round(dropx2, 1);
    }

    public static WedkaExp getWedkaExp(final int lvl) {
        for (final WedkaExp wedkaExp : values()) {
            if (wedkaExp.getLvl() == lvl) return wedkaExp;
        }
        return LVL_99;
    }
}
