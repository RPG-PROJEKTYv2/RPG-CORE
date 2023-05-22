package rpg.rpgcore.npc.metinolog.enums;

import rpg.rpgcore.utils.DoubleUtils;

public enum MetinologMissionKill {
    M0(0, "1-10", 10, 2, 1),
    M1(1, "10-20", 20, 2, 1),
    M2(2, "20-30", 35, 2, 2),
    M3(3, "30-40", 50, 2, 2),
    M4(4, "40-50", 65, 2, 2),
    M5(5, "50-60", 80, 2, 2),
    M6(6, "Lodowej-Wiezy", 85, 3, 2),
    M7(7, "60-70", 95, 3, 3),
    M8(8, "70-80", 110, 3, 3),
    M9(9, "80-90", 130, 3, 3),
    M10(10, "90-100", 150, 3, 3),
    M11(11, "100-110", 170, 3, 4),
    M12(12, "110-120", 190, 5, 4),
    M13(13, "120-130", 210, 5, 4);
    private final int id;
    private final String mapa;
    private final int reqAmount;
    private final double srOdpo;
    private final double przeszycie;

    MetinologMissionKill(int id, String mapa, int reqAmount, double srOdpo, double przeszycie) {
        this.id = id;
        this.mapa = mapa;
        this.reqAmount = reqAmount;
        this.srOdpo = srOdpo;
        this.przeszycie = przeszycie;
    }

    public int getId() {
        return id;
    }

    public String getMapa() {
        return mapa;
    }

    public int getReqAmount() {
        return reqAmount;
    }

    public double getSrOdpo() {
        return DoubleUtils.round(srOdpo, 2);
    }

    public double getPrzeszycie() {
        return DoubleUtils.round(przeszycie, 2);
    }

    public static MetinologMissionKill getMission(int id) {
        for (MetinologMissionKill mission : values()) {
            if (mission.getId() == id) {
                return mission;
            }
        }
        return null;
    }
}
