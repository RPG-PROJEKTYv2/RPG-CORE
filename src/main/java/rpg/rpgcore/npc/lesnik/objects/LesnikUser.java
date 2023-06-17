package rpg.rpgcore.npc.lesnik.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LesnikUser {
    private int mission, progress;
    private double przeszycie, wzmKryta, defNaLudzi;
    private long cooldown;

    public LesnikUser(int mission, int progress, double przeszycie, double wzmKryta, double defNaLudzi, long cooldown) {
        this.mission = mission;
        this.progress = progress;
        this.przeszycie = przeszycie;
        this.wzmKryta = wzmKryta;
        this.defNaLudzi = defNaLudzi;
        this.cooldown = cooldown;
    }

    public boolean hasCooldown() {
        return this.cooldown != 0 && System.currentTimeMillis() < cooldown;
    }

    public void giveCooldown() {
        this.cooldown = System.currentTimeMillis() + 210_000;
    }
}
