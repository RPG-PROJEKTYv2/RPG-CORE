package rpg.rpgcore.npc.gornik;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GornikUser {
    private int mission;
    private int progress;
    private double sredniaOdpornosc, blokCiosu, przeszycieBloku;
    private boolean dalszeDone;

    public GornikUser(int mission, int progress, double sredniaOdpornosc, double blokCiosu, double przeszycieBloku, boolean dalszeDone) {
        this.mission = mission;
        this.progress = progress;
        this.sredniaOdpornosc = sredniaOdpornosc;
        this.blokCiosu = blokCiosu;
        this.przeszycieBloku = przeszycieBloku;
        this.dalszeDone = dalszeDone;
    }
}
