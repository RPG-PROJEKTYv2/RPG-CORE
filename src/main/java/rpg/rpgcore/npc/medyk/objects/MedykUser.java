package rpg.rpgcore.npc.medyk.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedykUser {
    private int bonus, progress;
    private boolean done;

    public MedykUser(int bonus, int progress, boolean done) {
        this.bonus = bonus;
        this.progress = progress;
        this.done = done;
    }
}
