package rpg.rpgcore.npc.rybak;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RybakUser {
    private int mission, progress;
    private double value1, value2, value3, value4;

    public RybakUser(int mission, int progress, double value1, double value2, double value3, double value4) {
        this.mission = mission;
        this.progress = progress;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }
}
