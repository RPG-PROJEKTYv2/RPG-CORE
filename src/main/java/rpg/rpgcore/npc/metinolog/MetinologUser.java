package rpg.rpgcore.npc.metinolog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetinologUser {

    private int postepKill;
    private int postepMisjiKill;
    private int postepGive;
    private int postepMisjiGive;
    private double value1;
    private double value2;
    private double value3;
    private double value4;

    public MetinologUser(int postepKill, int postepMisjiKill, int postepGive, int postepMisjiGive, double value1, double value2, double value3, double value4) {
        this.postepKill = postepKill;
        this.postepMisjiKill = postepMisjiKill;
        this.postepGive = postepGive;
        this.postepMisjiGive = postepMisjiGive;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }
}
