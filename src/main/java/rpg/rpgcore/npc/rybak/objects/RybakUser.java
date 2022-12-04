package rpg.rpgcore.npc.rybak.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RybakUser {
    private int mission, progress, trueDmg;
    private double srDef, kryt, morskieSzczescie;

    public RybakUser(int mission, int progress, double srDef, double kryt, double morskieSzczescie, int trueDmg) {
        this.mission = mission;
        this.progress = progress;
        this.srDef = srDef;
        this.kryt = kryt;
        this.morskieSzczescie = morskieSzczescie;
        this.trueDmg = trueDmg;
    }
}
