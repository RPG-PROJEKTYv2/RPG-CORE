package rpg.rpgcore.npc.przyrodnik.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrzyrodnikUser implements Cloneable {
    private int mission, progress;
    private double dmg, def;

    public PrzyrodnikUser(int mission, int progress, double dmg, double def) {
        this.mission = mission;
        this.progress = progress;
        this.dmg = dmg;
        this.def = def;
    }

    @Override
    public PrzyrodnikUser clone() {
        try {
            return (PrzyrodnikUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
