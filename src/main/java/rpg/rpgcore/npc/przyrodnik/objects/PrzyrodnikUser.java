package rpg.rpgcore.npc.przyrodnik.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrzyrodnikUser {
    private int mission, progress;
    private double dmg, def;

    public PrzyrodnikUser(int mission, int progress, double dmg, double def) {
        this.mission = mission;
        this.progress = progress;
        this.dmg = dmg;
        this.def = def;
    }
}
