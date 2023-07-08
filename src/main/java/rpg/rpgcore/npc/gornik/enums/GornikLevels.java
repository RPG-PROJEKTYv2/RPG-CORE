package rpg.rpgcore.npc.gornik.enums;

public enum GornikLevels {

    L1(1, 100),
    L2(2, 125),
    L3(3, 150),
    L4(4, 175),
    L5(5, 200),
    L6(6, 250),
    L7(7, 300),
    L8(8, 350),
    L9(9, 400),
    L10(10, 450),
    L11(11, 500),
    L12(12, 600),
    L13(13, 700),
    L14(14, 800),
    L15(15, 900),
    L16(16, 1_000),
    L17(17, 1_150),
    L18(18, 1_300),
    L19(19, 1_450),
    L20(20, 1_600),
    L21(21, 1_800),
    L22(22, 2_000),
    L23(23, 2_200),
    L24(24, 2_400),
    L25(25, 2_600),
    L26(26, 2_800),
    L27(27, 3_000),
    L28(28, 3_250),
    L29(29, 3_500),
    L30(30, 4_000),
    L99(0, 999999999);


    private final int lvl, exp;

    GornikLevels(final int lvl, final int exp) {
        this.lvl = lvl;
        this.exp = exp;
    }

    public int getLvl() {
        return this.lvl;
    }

    public int getReqExp() {
        return this.exp;
    }

    public static GornikLevels getByLvl(final int lvl) {
        for (final GornikLevels gornikLevels : values()) {
            if (gornikLevels.getLvl() == lvl) {
                return gornikLevels;
            }
        }
        return L99;
    }
}
