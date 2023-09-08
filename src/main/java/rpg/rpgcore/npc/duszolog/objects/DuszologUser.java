package rpg.rpgcore.npc.duszolog.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuszologUser implements Cloneable {
    private int mission, progress;
    private double value1, value2;

    public DuszologUser(int mission, int progress, double value1, double value2) {
        this.mission = mission;
        this.progress = progress;
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public DuszologUser clone() {
        try {
            return (DuszologUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
