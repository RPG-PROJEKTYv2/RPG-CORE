package rpg.rpgcore.npc.rybak;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RybakUser {
    private int mission, progress;
    private double srDef, kryt, morskieSzczescie, trueDmg;

    public RybakUser(int mission, int progress, double srDef, double kryt, double morskieSzczescie, double trueDmg) {
        this.mission = mission;
        this.progress = progress;
        this.srDef = srDef;
        this.kryt = kryt;
        this.morskieSzczescie = morskieSzczescie;
        this.trueDmg = trueDmg;
    }
}
