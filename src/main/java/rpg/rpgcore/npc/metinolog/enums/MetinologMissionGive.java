package rpg.rpgcore.npc.metinolog.enums;

import rpg.rpgcore.utils.DoubleUtils;

public enum MetinologMissionGive {
    M0(0, "1-10", 10, 3, 2),
    M1(1, "10-20", 20, 5, 2),
    M2(2, "20-30", 30, 10, 2),
    M3(3, "30-40", 40, 12, 2),
    M4(4, "40-50", 50, 20, 2),
    M5(5, "50-60", 60, 40, 2),
    M6(6, "Lodowej-Wiezy", 65, 40, 3),
    M7(7, "60-70", 70, 70, 3),
    M8(8, "70-80", 80, 200, 3),
    M9(9, "80-90", 90, 400, 3),
    M10(10, "90-100", 100, 600, 3),
    M11(11, "100-110", 110, 600, 3),
    M12(12, "110-120", 120, 900, 5),
    M13(13, "120-130", 130, 1100, 5);
    private final int id;
    private final String mapa;
    private final int reqAmount;
    private final int dodatkoweDmg;
    private final double srOdpo;

    MetinologMissionGive(int id, String mapa, int reqAmount, int dodatkoweDmg, double srOdpo) {
        this.id = id;
        this.mapa = mapa;
        this.reqAmount = reqAmount;
        this.dodatkoweDmg = dodatkoweDmg;
        this.srOdpo = srOdpo;
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

    public int getDodatkoweDmg() {
        return dodatkoweDmg;
    }

    public double getSrOdpo() {
        return DoubleUtils.round(srOdpo, 2);
    }

    public static MetinologMissionGive getMission(int id) {
        for (MetinologMissionGive mission : values()) {
            if (mission.getId() == id) {
                return mission;
            }
        }
        return null;
    }
}
