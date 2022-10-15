package rpg.rpgcore.npc.gornik.enums;

public enum GornikDlutoLevels {
    L2(2, 20),
    L3(3, 30),
    L4(4, 40),
    L5(5, 50),
    L6(6, 60),
    L7(7, 70),
    L8(8, 80),
    L9(9, 90),
    L10(10, 100),
    L11(11, 110),
    L12(12, 120),
    L13(13, 130),
    L14(14, 140),
    L15(15, 150),
    L16(16, 160),
    L17(17, 170),
    L18(18, 180),
    L19(19, 190),
    L20(20, 200),
    L21(21, 210),
    L22(22, 220),
    L23(23, 230),
    L24(24, 240),
    L25(25, 250),
    L26(26, 260),
    L27(27, 270),
    L28(28, 280),
    L29(29, 290),
    L30(30, 300),
    L31(31, 310),
    L32(32, 320),
    L33(33, 330),
    L34(34, 340),
    L35(35, 350),
    L36(36, 360),
    L37(37, 370),
    L38(38, 380),
    L39(39, 390),
    L40(40, 400),
    L41(41, 410),
    L42(42, 420),
    L43(43, 430),
    L44(44, 440),
    L45(45, 450),
    L46(46, 460),
    L47(47, 470),
    L48(48, 480),
    L49(49, 490),
    L50(50, 500);

    private final int level;
    private final int exp;

    GornikDlutoLevels(int level, int exp) {
        this.level = level;
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public static int getExpByLevel(int level, String tier) {
        for (GornikDlutoLevels gornikDlutoLevels : GornikDlutoLevels.values()) {
            if (gornikDlutoLevels.getLevel() == level) {
                switch (tier) {
                    case "T0":
                        return gornikDlutoLevels.getExp();
                    case "T1":
                        return gornikDlutoLevels.getExp() + 10;
                    case "T2":
                        return gornikDlutoLevels.getExp() + 25;
                    case "T3":
                        return gornikDlutoLevels.getExp() + 40;
                    case "T4":
                        return gornikDlutoLevels.getExp() + 50;
                }
            }
        }
        return 0;
    }
}
