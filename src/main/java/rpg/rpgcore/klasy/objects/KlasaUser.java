package rpg.rpgcore.klasy.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class KlasaUser {

    private String name, mission;
    private int lvl, exp, missionProgress, points, globalPoints;
    private double value1, value2, value3, value4, value5;


    public KlasaUser(final String name, final int lvl, final int exp, final String mission, final int missionProgress, final int globalPoints, final int points, final double value1, final double value2, final double value3, final double value4, final double value5) {
        this.name = name;
        this.lvl = lvl;
        this.exp = exp;
        this.mission = mission;
        this.missionProgress = missionProgress;
        this.globalPoints = globalPoints;
        this.points = points;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
    }
}
