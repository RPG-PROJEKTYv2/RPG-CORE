package rpg.rpgcore.npc.duszolog.enums;

import lombok.Getter;
import rpg.rpgcore.utils.DoubleUtils;

public enum DuszologMissions {
    M1("Rozbojnik Lvl. 3", 5, 0.1,0.1, 0.1),
    M2("Rozbojnik Lvl. 5", 5, 0.1,0.1, 0.1),
    M3("Rozbojnik Lvl. 7", 5, 0.1,0.1, 0.1),
    M4("[BOSS] Dowodca Rozbojnikow", 3, 12.5,0.25, 0.25),

    M5("Goblin Lvl. 14", 5, 0.1,0.15, 0.15),
    M6("Goblin Lvl. 16", 5, 0.1,0.15, 0.15),
    M7("Goblin Lvl. 19", 5, 0.1,0.15, 0.15),
    M8("[BOSS] Wodz Goblinow", 3, 12.5,0.35, 0.35),

    M9("Goryl Lvl. 21", 5, 0.08,0.25, 0.25),
    M10("Goryl Lvl. 25", 5, 0.08,0.25, 0.25),
    M11("Goryl Lvl. 28", 5, 0.08,0.25, 0.25),
    M12("[BOSS] Krol Goryli", 3, 10,0.5, 0.5),

    M13("Zjawa Lvl. 32", 5, 0.07,0.45, 0.45),
    M14("Zjawa Lvl. 36", 5, 0.07,0.45, 0.45),
    M15("Zjawa Lvl. 39", 5, 0.07,0.45, 0.45),
    M16("[BOSS] Przekleta Dusza", 3, 10, 1, 1),

    M17("Straznik Swiatyni Lvl. 43", 5, 0.065,0.65, 0.65),
    M18("Straznik Swiatyni Lvl. 46", 5, 0.065,0.65, 0.65),
    M19("Straznik Swiatyni Lvl. 47", 5, 0.065,0.65, 0.65),
    M20("[BOSS] Tryton", 3, 8,1.25, 1.25),

    M21("Mrozny Wilk Lvl. 52", 5, 0.05,0.75, 0.75),
    M22("Mrozny Wilk Lvl. 54", 5, 0.05,0.75, 0.75),
    M23("Mrozny Wilk Lvl. 56", 5, 0.05,0.75, 0.75),

    M24("Lodowy Sluga Lvl. 57", 5, 0.045,0.8, 0.8),
    M25("Lodowy Sluga Lvl. 58", 5, 0.045,0.8, 0.8),
    M26("Lodowy Sluga Lvl. 59", 5, 0.045,0.8, 0.8),
    M27("[BOSS] Krol Lodu", 3, 12.5,1.5, 1.5),

    M28("Zywiolak Ognia Lvl. 64", 5, 0.03,0.9, 0.9),
    M29("Zywiolak Ognia Lvl. 66", 5, 0.03,0.9, 0.9),
    M30("Zywiolak Ognia Lvl. 68", 5, 0.03,0.9, 0.9),
    M31("[BOSS] Piekielny Rycerz", 3, 10,1.75, 1.75),

    M32("Ognisty Duch Lvl. 69", 5, 0.05,1.05, 1.05),

    M33("Mroczna Dusza Lvl. 71", 5, 0.015,1.05, 1.05),
    M34("Mroczna DuszaLvl. 75", 5, 0.015,1.05, 1.05),
    M35("Mroczna Dusza Lvl. 78", 5, 0.015,1.05, 1.05),
    M36("[BOSS] Przeklety Czarnoksieznik", 3, 15,2, 2),

    M37("Zapomniany Wojownik Lvl. 75", 5, 0.04,1, 1),
    M38("Zapomniany Wojownik Lvl. 79", 5, 0.3, 1, 1),

    M39("Pustynny Ptasznik Lvl. 84", 5, 0.0085,1.25, 1.25),
    M40("Pustynny Ptasznik Lvl. 87", 5, 0.0085,1.25, 1.25),
    M41("Pustynny Ptasznik Lvl. 89", 5, 0.0085,1.25, 1.25),
    M42("[BOSS] Mityczny Pajak", 3, 15,2.75, 2.75),

    M43("Truposz Lvl. 85", 5, 0.0175,1.1, 1.1),
    M44("Truposz Lvl. 89", 5, 0.05,1.7, 1.7),

    M45("Podziemna Lowczyni Lvl. 92", 5, 0.0045,1.35, 1.35),
    M46("Podziemna Lowczyni Lvl. 95", 5, 0.0045,1.35, 1.35),
    M47("Podziemna Lowczyni Lvl. 98", 5, 0.0045,1.35, 1.35),
    M48("[BOSS] Podziemny Rozpruwacz", 2, 25,3, 3),

    M49("Demoniczy Lowca Lvl. 95", 5, 0.0055,0.9, 0.9),

    M50("Podwodny Straznik Lvl. 104", 5, 0.0022,1.45, 1.45),
    M51("Podwodny Straznik Lvl. 106", 5, 0.0022,1.45, 1.45),
    M52("Podwodny Straznik Lvl. 109", 5, 0.0022,1.45, 1.45),
    M53("[BOSS] Mistyczny Kraken", 1, 10,5, 5),

    M54("Mrozny Stroz Lvl. 114", 5, 0.0011,1.6, 1.6),
    M55("Mrozny Stroz Lvl. 116", 5, 0.0011,1.6, 1.6),
    M56("Mrozny Stroz Lvl. 118", 5, 0.0011,1.6, 1.6),
    M57("[BOSS] Krysztalowy Wladca", 1, 5,7, 7),

    ;

    @Getter
    private final String entityName;
    @Getter
    private final int reqAmount;
    private final double spawnChance;
    private final double silnyNaLudzi, krytyk;

    DuszologMissions(final String entityName, final int reqAmount, final double spawnChance, final double silnyNaLudzi, final double krytyk) {
        this.entityName = entityName;
        this.reqAmount = reqAmount;
        this.spawnChance = spawnChance;
        this.silnyNaLudzi = silnyNaLudzi;
        this.krytyk = krytyk;
    }

    public double getSpawnChance() {
        return DoubleUtils.round(this.spawnChance, 4);
    }

    public double getSilnyNaLudzi() {
        return DoubleUtils.round(this.silnyNaLudzi, 2);
    }

    public double getKrytyk() {
        return DoubleUtils.round(this.krytyk, 2);
    }

    public static DuszologMissions getByEntityName(final String entityName) {
        for (DuszologMissions mission : values()) {
            if (mission.getEntityName().equals(entityName)) return mission;
        }
        return null;
    }
}
