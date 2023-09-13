package rpg.rpgcore.npc.rybak.enums;

import lombok.Getter;
import rpg.rpgcore.utils.DoubleUtils;

public enum WedkaExp {
    LVL_1(1, 50, 0.0),
    LVL_2(2, 60, 0.1),
    LVL_3(3, 75, 0.1),
    LVL_4(4, 100, 0.1),
    LVL_5(5, 115, 0.1),
    LVL_6(6, 130, 0.1),
    LVL_7(7, 145, 0.1),
    LVL_8(8, 160, 0.1),
    LVL_9(9, 175, 0.1),
    LVL_10(10, 200, 0.2),
    LVL_11(11, 225, 0.2),
    LVL_12(12, 250, 0.2),
    LVL_13(13, 275, 0.2),
    LVL_14(14, 300, 0.2),
    LVL_15(15, 325, 0.2),
    LVL_16(16, 350, 0.2),
    LVL_17(17, 375, 0.2),
    LVL_18(18, 400, 0.2),
    LVL_19(19, 450, 0.2),
    LVL_20(20, 500, 0.2),
    LVL_21(21, 550, 0.2),
    LVL_22(22, 600, 0.2),
    LVL_23(23, 650, 0.2),
    LVL_24(24, 700, 0.2),
    LVL_25(25, 850, 0.2),
    LVL_26(26, 1_000, 0.1),
    LVL_27(27, 1_050, 0.1),
    LVL_28(28, 1_100, 0.1),
    LVL_29(29, 1_150, 0.1),
    LVL_30(30, 1_200, 0.1),
    LVL_31(31, 1_250, 0.1),
    LVL_32(32, 1_400, 0.1),
    LVL_33(33, 1_550, 0.1),
    LVL_34(34, 1_700, 0.1),
    LVL_35(35, 1_850, 0.1),
    LVL_36(36, 2_000, 0.1),
    LVL_37(37, 2_250, 0.1),
    LVL_38(38, 2_500, 0.1),
    LVL_39(39, 2_750, 0.1),
    LVL_40(40, 3_000, 0.1),
    LVL_41(41, 3_500, 0.1),
    LVL_42(42, 4_000, 0.1),
    LVL_43(43, 4_500, 0.1),
    LVL_44(44, 5_000, 0.1),
    LVL_45(45, 5_500, 0.1),
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
