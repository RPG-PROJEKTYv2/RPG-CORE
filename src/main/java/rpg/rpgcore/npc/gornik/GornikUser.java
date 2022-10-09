package rpg.rpgcore.npc.gornik;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GornikUser {
    private int mission;
    private int progress;
    private double przebiciePancerza, szybkosc, szansaNaWzmocnienieKrytyka, sredniaOdpornosc;

    public GornikUser(int mission, int progress, double przebiciePancerza, double szybkosc, double szansaNaWzmocnienieKrytyka, double sredniaOdpornosc) {
        this.mission = mission;
        this.progress = progress;
        this.przebiciePancerza = przebiciePancerza;
        this.szybkosc = szybkosc;
        this.szansaNaWzmocnienieKrytyka = szansaNaWzmocnienieKrytyka;
        this.sredniaOdpornosc = sredniaOdpornosc;
    }
}
