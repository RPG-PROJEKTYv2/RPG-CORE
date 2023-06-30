package rpg.rpgcore.old.gornik.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GornikUser {
    private int mission;
    private int progress;
    private double sredniaOdpornosc, blokCiosu, przeszycieBloku;
    private boolean dalszeDone;
    private boolean d1, d2, d3_1, d3_2, d3_3, d4_1, d4_2, d4_3, d4_4, d4_5, d4_6, d4_7, d5_1, d5_2, d5_3, d5_4, d5_5, d6_1, d6_2, d6_3, d6_4, d6_5, d7_1, d7_2, d7_3, d7_4, d8_1, d8_2, d9_1, d9_2;

    public GornikUser(int mission, int progress, double sredniaOdpornosc, double blokCiosu, double przeszycieBloku, boolean dalszeDone, boolean d1, boolean d2, boolean d3_1,
                      boolean d3_2, boolean d3_3, boolean d4_1, boolean d4_2, boolean d4_3, boolean d4_4, boolean d4_5, boolean d4_6, boolean d4_7, boolean d5_1, boolean d5_2,
                      boolean d5_3, boolean d5_4, boolean d5_5, boolean d6_1, boolean d6_2, boolean d6_3, boolean d6_4, boolean d6_5, boolean d7_1, boolean d7_2, boolean d7_3,
                      boolean d7_4, boolean d8_1, boolean d8_2, boolean d9_1, boolean d9_2) {
        this.mission = mission;
        this.progress = progress;
        this.sredniaOdpornosc = sredniaOdpornosc;
        this.blokCiosu = blokCiosu;
        this.przeszycieBloku = przeszycieBloku;
        this.dalszeDone = dalszeDone;
        this.d1 = d1;
        this.d2 = d2;
        this.d3_1 = d3_1;
        this.d3_2 = d3_2;
        this.d3_3 = d3_3;
        this.d4_1 = d4_1;
        this.d4_2 = d4_2;
        this.d4_3 = d4_3;
        this.d4_4 = d4_4;
        this.d4_5 = d4_5;
        this.d4_6 = d4_6;
        this.d4_7 = d4_7;
        this.d5_1 = d5_1;
        this.d5_2 = d5_2;
        this.d5_3 = d5_3;
        this.d5_4 = d5_4;
        this.d5_5 = d5_5;
        this.d6_1 = d6_1;
        this.d6_2 = d6_2;
        this.d6_3 = d6_3;
        this.d6_4 = d6_4;
        this.d6_5 = d6_5;
        this.d7_1 = d7_1;
        this.d7_2 = d7_2;
        this.d7_3 = d7_3;
        this.d7_4 = d7_4;
        this.d8_1 = d8_1;
        this.d8_2 = d8_2;
        this.d9_1 = d9_1;
        this.d9_2 = d9_2;
    }

    public int getDrzewkoUnlocked() {
        int count = 0;
        if (d1) count++;
        if (d2) count++;
        if (d3_1) count++;
        if (d3_2) count++;
        if (d3_3) count++;
        if (d4_1) count++;
        if (d4_2) count++;
        if (d4_3) count++;
        if (d4_4) count++;
        if (d4_5) count++;
        if (d4_6) count++;
        if (d4_7) count++;
        if (d5_1) count++;
        if (d5_2) count++;
        if (d5_3) count++;
        if (d5_4) count++;
        if (d5_5) count++;
        if (d6_1) count++;
        if (d6_2) count++;
        if (d6_3) count++;
        if (d6_4) count++;
        if (d6_5) count++;
        if (d7_1) count++;
        if (d7_2) count++;
        if (d7_3) count++;
        if (d7_4) count++;
        if (d8_1) count++;
        if (d8_2) count++;
        if (d9_1) count++;
        if (d9_2) count++;
        return count;
    }
}
