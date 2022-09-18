package rpg.rpgcore.npc.lowca;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LowcaUser {
    private int mission;
    private int progress;
    private int szczescie, szybkosc, truedmg;

    public LowcaUser(int mission, int progress, int szczescie, int szybkosc, int truedmg) {
        this.mission = mission;
        this.progress = progress;
        this.szczescie = szczescie;
        this.szybkosc = szybkosc;
        this.truedmg = truedmg;
    }
}
