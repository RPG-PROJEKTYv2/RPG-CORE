package rpg.rpgcore.npc.trener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrenerUser {
    private int points;
    private double sredniDmg, sredniDef, blokCiosu, szczescie, silnyNaLudzi, defNaLudzi, kryt;
    private int dodatkoweHp;

    public TrenerUser(int points, double sredniDmg, double sredniDef, double blokCiosu, double szczescie, double silnyNaLudzi, double defNaLudzi, double kryt, int dodatkoweHp) {
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

}
