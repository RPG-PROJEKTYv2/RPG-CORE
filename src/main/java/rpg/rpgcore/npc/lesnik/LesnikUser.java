package rpg.rpgcore.npc.lesnik;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LesnikUser {
    private int mission, progress;
    private double przeszycie, wzmKryta, defNaLudzi;

    public LesnikUser(int mission, int progress, double przeszycie, double wzmKryta, double defNaLudzi) {
        this.mission = mission;
        this.progress = progress;
        this.przeszycie = przeszycie;
        this.wzmKryta = wzmKryta;
        this.defNaLudzi = defNaLudzi;
    }
}
