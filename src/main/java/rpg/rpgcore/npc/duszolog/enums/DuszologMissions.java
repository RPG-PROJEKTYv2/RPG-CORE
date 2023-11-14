package rpg.rpgcore.npc.duszolog.enums;

import lombok.Getter;
import rpg.rpgcore.utils.DoubleUtils;

public enum DuszologMissions {
    M1("Rozbojnik Lvl. 3", 5, 2,0.1, 0.1),
    M2("Rozbojnik Lvl. 5", 5, 2,0.1, 0.1),
    M3("Rozbojnik Lvl. 7", 5, 2,0.1, 0.1),
    M4("[BOSS] Dowodca Rozbojnikow", 3, 15,0.2, 0.2),

    M5("Goblin Lvl. 14", 5, 2,0.1, 0.1),
    M6("Goblin Lvl. 16", 5, 2,0.1, 0.1),
    M7("Goblin Lvl. 19", 5, 2,0.1, 0.1),
    M8("[BOSS] Wodz Goblinow", 3, 15,0.25, 0.25),

    M9("Goryl Lvl. 21", 5, 2,0.1, 0.1),
    M10("Goryl Lvl. 25", 5, 2,0.1, 0.1),
    M11("Goryl Lvl. 28", 5, 2,0.1, 0.1),
    M12("[BOSS] Krol Goryli", 3, 15,0.1, 0.1),

    M13("Zjawa Lvl. 32", 5, 1.5,0.1, 0.1),
    M14("Zjawa Lvl. 36", 5, 1.5,0.1, 0.1),
    M15("Zjawa Lvl. 39", 5, 1.5,0.1, 0.1),
    M16("[BOSS] Przekleta Dusza", 3, 12.5, 0.5, 0.5),

    M17("Straznik Swiatyni Lvl. 43", 5, 1.5,0.1, 0.1),
    M18("Straznik Swiatyni Lvl. 46", 5, 1.5,0.1, 0.1),
    M19("Straznik Swiatyni Lvl. 47", 5, 1.5,0.1, 0.1),
    M20("[BOSS] Tryton", 3, 12.5,0.5, 0.5),

    M21("Mrozny Wilk Lvl. 52", 5, 1,0.1, 0.1),
    M22("Mrozny Wilk Lvl. 54", 5, 1,0.1, 0.1),
    M23("Mrozny Wilk Lvl. 56", 5, 1,0.1, 0.1),

    M24("Lodowy Sluga Lvl. 57", 5, 1,0.1, 0.1),
    M25("Lodowy Sluga Lvl. 58", 5, 1,0.1, 0.1),
    M26("Lodowy Sluga Lvl. 59", 5, 1,0.1, 0.1),
    M27("[BOSS] Krol Lodu", 3, 10,1, 1),

    M28("Zywiolak Ognia Lvl. 64", 5, 0.75,0.25, 0.25),
    M29("Zywiolak Ognia Lvl. 66", 5, 0.75,0.25, 0.25),
    M30("Zywiolak Ognia Lvl. 68", 5, 0.75,0.25, 0.25),
    M31("[BOSS] Piekielny Rycerz", 3, 10,1.25, 1.25),

    M32("Ognisty Duch Lvl. 69", 5, 1,0.5, 0.5),

    M33("Mroczna Dusza Lvl. 71", 5, 0.75,0.25, 0.25),
    M34("Mroczna DuszaLvl. 75", 5, 0.75,0.25, 0.25),
    M35("Mroczna Dusza Lvl. 78", 5, 0.75,0.25, 0.25),
    M36("[BOSS] Przeklety Czarnoksieznik", 3, 15,1.5, 1.5),

    M37("Zapomniany Wojownik Lvl. 75", 5, 1,0.25, 0.25),
    M38("Zapomniany Wojownik Lvl. 79", 5, 1,0.25, 0.25),

    M39("Pustynny Ptasznik Lvl. 84", 5, 0.5,0.5, 0.5),
    M40("Pustynny Ptasznik Lvl. 87", 5, 0.5,0.5, 0.5),
    M41("Pustynny Ptasznik Lvl. 89", 5, 0.5,0.5, 0.5),
    M42("[BOSS] Mityczny Pajak", 3, 33.3,2, 2),

    M43("Truposz Lvl. 85", 5, 0.75,0.5, 0.5),
    M44("Truposz Lvl. 89", 5, 0.75,0.5, 0.5),

    M45("Podziemna Lowczyni Lvl. 92", 5, 0.25,0.5, 0.5),
    M46("Podziemna Lowczyni Lvl. 95", 5, 0.25,0.5, 0.5),
    M47("Podziemna Lowczyni Lvl. 98", 5, 0.25,0.5, 0.5),
    M48("[BOSS] Podziemny Rozpruwacz", 2, 25,2.5, 2.5),

    M49("Demoniczy Lowca Lvl. 95", 5, 0.75,1.5, 1.5),

    M50("Podwodny Straznik Lvl. 104", 5, 0.05,0.5, 0.5),
    M51("Podwodny Straznik Lvl. 106", 5, 0.05,0.5, 0.5),
    M52("Podwodny Straznik Lvl. 109", 5, 0.05,0.5, 0.5),
    M53("[BOSS] Mistyczny Kraken", 1, 15,0.5, 0.5),

    M54("Mrozny Stroz Lvl. 114", 5, 0.005,0.5, 0.5),
    M55("Mrozny Stroz Lvl. 116", 5, 0.005,0.5, 0.5),
    M56("Mrozny Stroz Lvl. 118", 5, 0.005,0.5, 0.5),
    M57("[BOSS] Krysztalowy Wladca", 3, 25,0.5, 0.5),

    ;

    @Getter
    private final String entityName;
    @Getter
    private final int reqAmount;
    private final double spawnChance;
    private final double przeszyka, krytyk;

    DuszologMissions(final String entityName, final int reqAmount, final double spawnChance, final double przeszyka, final double krytyk) {
        this.entityName = entityName;
        this.reqAmount = reqAmount;
        this.spawnChance = spawnChance;
        this.przeszyka = przeszyka;
        this.krytyk = krytyk;
    }

    public double getSpawnChance() {
        return DoubleUtils.round(this.spawnChance, 3);
    }

    public double getPrzeszyka() {
        return DoubleUtils.round(this.przeszyka, 2);
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
