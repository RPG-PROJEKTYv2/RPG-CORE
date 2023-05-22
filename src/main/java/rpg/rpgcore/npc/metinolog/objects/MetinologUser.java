package rpg.rpgcore.npc.metinolog.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetinologUser {

    private int postepKill;
    private int postepMisjiKill;
    private int postepGive;
    private int postepMisjiGive;
    private double przeszycie;
    private double srOdpo;
    private int dodatkowedmg;

    public MetinologUser(int postepKill, int postepMisjiKill, int postepGive, int postepMisjiGive, double przeszycie, double srOdpo, int dodatkowedmg) {
        this.postepKill = postepKill;
        this.postepMisjiKill = postepMisjiKill;
        this.postepGive = postepGive;
        this.postepMisjiGive = postepMisjiGive;
        this.przeszycie = przeszycie;
        this.srOdpo = srOdpo;
        this.dodatkowedmg = dodatkowedmg;
    }
}
