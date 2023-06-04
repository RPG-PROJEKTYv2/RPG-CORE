package rpg.rpgcore.npc.lowca.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LowcaUser {
    private int mission;
    private int progress;
    private int szczescie, szybkosc, dodatkoweDmg;

    public LowcaUser(int mission, int progress, int szczescie, int szybkosc, int dodatkoweDmg) {
        this.mission = mission;
        this.progress = progress;
        this.szczescie = szczescie;
        this.szybkosc = szybkosc;
        this.dodatkoweDmg = dodatkoweDmg;
    }
}
