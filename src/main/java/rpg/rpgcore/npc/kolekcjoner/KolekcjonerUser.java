package rpg.rpgcore.npc.kolekcjoner;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class KolekcjonerUser {
    private int mission;
    private List<Boolean> missionProgress;
    private int szczescie, dodatkowe;
    private double silnyNaLudzi;

    public KolekcjonerUser(int mission, List<Boolean> missionProgress, int szczescie, double silnyNaLudzi, int dodatkowe) {
        this.mission = mission;
        this.missionProgress = missionProgress;
        this.szczescie = szczescie;
        this.silnyNaLudzi = silnyNaLudzi;
        this.dodatkowe = dodatkowe;
    }

    public void resetMissionProgress() {
        this.missionProgress = Arrays.asList(false, false, false, false, false);
    }
}
