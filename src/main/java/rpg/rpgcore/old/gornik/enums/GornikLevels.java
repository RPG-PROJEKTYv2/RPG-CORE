package rpg.rpgcore.old.gornik.enums;

public enum GornikLevels {
    L2(2, 100),
    L3(3, 100),
    L4(4, 100),
    L5(5, 100),
    L6(6, 100),
    L7(7, 100),
    L8(8, 100),
    L9(9, 100),
    L10(10, 100),
    L11(11, 150),
    L12(12, 150),
    L13(13, 150),
    L14(14, 150),
    L15(15, 150),
    L16(16, 150),
    L17(17, 150),
    L18(18, 150),
    L19(19, 150),
    L20(20, 150),
    L21(21, 200),
    L22(22, 200),
    L23(23, 200),
    L24(24, 200),
    L25(25, 200),
    L26(26, 200),
    L27(27, 200),
    L28(28, 200),
    L29(29, 200),
    L30(30, 200),
    L31(31, 250),
    L32(32, 250),
    L33(33, 250),
    L34(34, 250),
    L35(35, 250),
    L36(36, 250),
    L37(37, 250),
    L38(38, 250),
    L39(39, 250),
    L40(40, 250),
    L41(41, 300),
    L42(42, 300),
    L43(43, 300),
    L44(44, 300),
    L45(45, 300),
    L46(46, 300),
    L47(47, 300),
    L48(48, 300),
    L49(49, 300),
    L50(50, 300),
    LERROR(0, 0);

    private final int lvl;
    private final int exp;

    GornikLevels(int lvl, int exp) {
        this.lvl = lvl;
        this.exp = exp;
    }

    public int getLvl() {
        return lvl;
    }

    public int getExp() {
        return exp;
    }

    public static GornikLevels getByLvl(int lvl) {
        for (GornikLevels level : GornikLevels.values()) {
            if (level.getLvl() == lvl) {
                return level;
            }
        }
        return LERROR;
    }
}
