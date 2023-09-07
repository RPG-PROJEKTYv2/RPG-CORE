package rpg.rpgcore.lvl.enums.mobs;

import rpg.rpgcore.utils.DoubleUtils;

public enum Dungeons {
    ICE_TOWER_MOB1("Lodowy Sluga Lvl. 57", 1_350, 100, 50, 60),
    ICE_TOWER_MOB2("Lodowy Sluga Lvl. 58", 1_850, 110, 50, 60),
    ICE_TOWER_MOB3("Lodowy Sluga Lvl. 59", 2_300, 120, 50, 60),
    ICE_TOWER_BOSS("[BOSS] Krol Lodu", 60_000, 50_000, 50, 60),
    // 60-70
    PIEKIELNY_PRZEDSIONEK_MOB("Ognisty Duch Lvl. 69", 7_366.3, 125, 60, 70),
    PIEKIELNY_PRZEDSIONEK_BOSS("[BOSS] Piekielny Wladca", 500_500.0, 600_000, 60, 70),
    // 70-80
    KOLOSEUM_MOB1("Zapomniany Wojownik Lvl. 75", 30_596.0,135,70,80),
    KOLOSEUM_MOB2("Zapomniany Wojownik Lvl. 79", 32_596.0,155,70,80),
    KOLOSEUM_MINIBOSS1("[MiniBOSS] Wyznawca Ateny", 57_661.0,500_000,70,80),
    KOLOSEUM_MINIBOSS2("[MiniBOSS] Wyznawca Posejdona", 57_661.0,500_000,70,80),
    KOLOSEUM_MINIBOSS3("[MiniBOSS] Wyznawca Zeusa", 57_661.0,500_000,70,80),
    KOLOSEUM_MINIBOSS4("[MiniBOSS] Wyznawca Hadesa", 57_661.0,500_000,70,80),
    KOLOSEUM_BOSS("[BOSS] Czempion Areny", 2_000_000,1_500_000,70,80),
    // 80-90
    TAJEMNICZE_PIASKI_MOB1("Truposz Lvl. 85", 85_396.0,1,80,90),
    TAJEMNICZE_PIASKI_MOB2("Truposz Lvl. 89", 96_327.0,1,80,90),
    TAJEMNICZE_PIASKI_MINIBOSS1("[MiniBOSS] Pustynny Tarczownik", 156_327.0,1,80,90),
    TAJEMNICZE_PIASKI_MINIBOSS2("[MiniBOSS] Pustynny Przyzywacz", 196_327.0,1,80,90),
    TAJEMNICZE_PIASKI_BOSS("[BOSS] Cesarz Pustyni", 6_908_910.0,3_500_000,80,90),
    // 90-100
    DEMONICZNE_SALE_MOB1("Demoniczy Lowca Lvl. 95", 213_007.0,1,90,100),
    DEMONICZNE_SALE_MINIBOSS1("[MiniBOSS] Elitarny Sluga", 759_756.0,1,90,100),
    DEMONICZNE_SALE_BOSS("[BOSS] Demon Ciemnosci", 16_625_921.0,6_000_000,90,100),
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
