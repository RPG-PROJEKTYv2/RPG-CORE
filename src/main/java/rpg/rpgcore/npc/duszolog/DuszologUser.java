package rpg.rpgcore.npc.duszolog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuszologUser {
    private int mission, progress;
    private double value1, value2;

    public DuszologUser(int mission, int progress, double value1, double value2) {
        this.mission = mission;
        this.progress = progress;
        this.value1 = value1;
        this.value2 = value2;
    }
}
