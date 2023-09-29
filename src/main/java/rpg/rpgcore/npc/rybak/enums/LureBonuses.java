package rpg.rpgcore.npc.rybak.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum LureBonuses {
    LVL_0(0, 0),
    LVL_1(1, 20),
    LVL_2(2, 40),
    LVL_3(3, 60),
    LVL_4(4, 80),
    LVL_5(5, 100),
    LVL_6(6, 150),
    LVL_7(7, 200);


    private final int lureLvl;
    private final int fishingSpeed;

    LureBonuses(final int lureLvl, final int fishingSpeed) {
        this.lureLvl = lureLvl;
        this.fishingSpeed = fishingSpeed;
    }

    public static int getLureFishingSpeed(final int lureLvl) {
        for (final LureBonuses lureBonuses : values()) {
            if (lureBonuses.getLureLvl() == lureLvl) {
                return lureBonuses.getFishingSpeed();
            }
        }
        return 0;
    }
}
