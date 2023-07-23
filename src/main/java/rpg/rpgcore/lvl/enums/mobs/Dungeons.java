package rpg.rpgcore.lvl.enums.mobs;

import rpg.rpgcore.utils.DoubleUtils;

public enum Dungeons {
    ICE_TOWER_MOB1("Lodowy Sluga Lvl. 57", 1_350, 250, 50, 60),
    ICE_TOWER_MOB2("Lodowy Sluga Lvl. 58", 1_850, 300, 50, 60),
    ICE_TOWER_MOB3("Lodowy Sluga Lvl. 59", 2_300, 325, 50, 60),
    ICE_TOWER_BOSS("[BOSS] Krol Lodu", 60_000, 50_000, 50, 60),
    PIEKIELNY_PRZEDSIONEK_MOB("Ognisty Duch Lvl. 69", 7_366.3, 800, 60, 70),
    PIEKIELNY_PRZEDSIONEK_BOSS("[BOSS] Piekielny Wladca", 500_500.0, 884_800, 60, 70),
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
