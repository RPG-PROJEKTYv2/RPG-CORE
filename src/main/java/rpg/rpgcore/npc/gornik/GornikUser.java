package rpg.rpgcore.npc.gornik;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GornikUser {
    private int mission, progress;
    private double bonus1, bonus2, bonus3, bonus4;

    public GornikUser(int mission, int progress, double bonus1, double bonus2, double bonus3, double bonus4) {
        this.mission = mission;
        this.progress = progress;
        this.bonus1 = bonus1;
        this.bonus2 = bonus2;
        this.bonus3 = bonus3;
        this.bonus4 = bonus4;
    }
}
