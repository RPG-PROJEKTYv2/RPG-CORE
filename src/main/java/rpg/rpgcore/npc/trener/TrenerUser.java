package rpg.rpgcore.npc.trener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrenerUser {
    private int points;
    private double sredniDmg, sredniDef, blokCiosu, silnyNaLudzi, defNaLudzi, kryt;
    private int dodatkoweHp, szczescie;

    public TrenerUser(int points, double sredniDmg, double sredniDef, double blokCiosu, int szczescie, double silnyNaLudzi, double defNaLudzi, double kryt, int dodatkoweHp) {
        this.points = points;
        this.sredniDmg = sredniDmg;
        this.sredniDef = sredniDef;
        this.blokCiosu = blokCiosu;
        this.szczescie = szczescie;
        this.silnyNaLudzi = silnyNaLudzi;
        this.defNaLudzi = defNaLudzi;
        this.kryt = kryt;
        this.dodatkoweHp = dodatkoweHp;
    }

    public void reset() {
        this.points = 0;
        this.sredniDmg = 0;
        this.sredniDef = 0;
        this.blokCiosu = 0;
        this.szczescie = 0;
        this.silnyNaLudzi = 0;
        this.defNaLudzi = 0;
        this.kryt = 0;
        this.dodatkoweHp = 0;
    }

}
