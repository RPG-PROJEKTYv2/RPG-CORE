package rpg.rpgcore.npc.metinolog.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetinologUser implements Cloneable {

    private int postepKill;
    private int postepMisjiKill;
    private int postepGive;
    private int postepMisjiGive;
    private double przeszycie;
    private double srOdpo;
    private int dodatkowedmg;
    private int dmgMetiny;

    public MetinologUser(int postepKill, int postepMisjiKill, int postepGive, int postepMisjiGive, double przeszycie, double srOdpo, int dodatkowedmg, int dmgMetiny) {
        this.postepKill = postepKill;
        this.postepMisjiKill = postepMisjiKill;
        this.postepGive = postepGive;
        this.postepMisjiGive = postepMisjiGive;
        this.przeszycie = przeszycie;
        this.srOdpo = srOdpo;
        this.dodatkowedmg = dodatkowedmg;
        this.dmgMetiny = dmgMetiny;
    }

    @Override
    public MetinologUser clone() {
        try {
            return (MetinologUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
