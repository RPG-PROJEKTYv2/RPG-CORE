package rpg.rpgcore.lvl.enums.mobs;

import rpg.rpgcore.utils.DoubleUtils;

public enum Dungeons {
    PIEKIELNY_PRZEDSIONEK_MOB("Ognisty Duch Lvl. 69", 30471, 200, 60, 70),
    PIEKIELNY_PRZEDSIONEK_BOSS("[BOSS] Piekielny Wladca", 35861, 5_000, 60, 70),
    ERROR("Error", 0, 0, 1, 1);

    private final String name;
    private final double kasa;
    private final double exp;
    private final int minLvl;
    private final int reqLvl;

    Dungeons(final String name, final double exp, final double kasa, final int minLvl, final int reqLvl) {
        this.name = name;
        this.exp = exp;
        this.kasa = kasa;
        this.minLvl = minLvl;
        this.reqLvl = reqLvl;
    }

    public String getName() {
        return name;
    }

    public double getKasa() {
        return DoubleUtils.round(kasa, 2);
    }

    public double getExp() {
        return DoubleUtils.round(exp, 2);
    }

    public int getMinLvl() {
        return minLvl;
    }

    public int getReqLvl() {
        return reqLvl;
    }

    public static Dungeons getByName(String name) {
        for (Dungeons dungeons : values()) {
            if (dungeons.getName().equalsIgnoreCase(name)) {
                return dungeons;
            }
        }
        return ERROR;
    }

    public static boolean isDungeonMob(final String name) {
        for (Dungeons dungeons : values()) {
            if (dungeons.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
