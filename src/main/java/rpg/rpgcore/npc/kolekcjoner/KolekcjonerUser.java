package rpg.rpgcore.npc.kolekcjoner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KolekcjonerUser {
    private int mission, missionProgress;
    private double value1, value2, value3;

    public KolekcjonerUser(int mission, int missionProgress, double value1, double value2, double value3) {
        this.mission = mission;
        this.missionProgress = missionProgress;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }
}
