package rpg.rpgcore.bonuses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusesUser {
    private int dodatkowehp, dodatkowezlotehp, dodatkoweobrazenia, truedamage, szczescie, szybkosc, dmgMetiny;
    private double srednieobrazenia, silnynaludzi, silnynapotwory, sredniadefensywa, defnaludzi, defnamoby, szansanakryta, szansanawzmocnieniekryta, blokciosu,
            przeszyciebloku, szansanakrwawienie, minussrednieobrazenia, minussredniadefensywa, minusdefnaludzi, minusdefnamoby, minusobrazenianaludzi, minusobrazenianamoby,
            spowolnienie, dodatkowyExp, oslepienie, przebiciePancerza, wampiryzm, wzmocnienieKryta;

    public BonusesUser(int dodatkowehp, int dodatkowezlotehp, int dodatkoweobrazenia, int truedamage, double srednieobrazenia, double silnynaludzi, double silnynapotwory, double sredniadefensywa,
                       double defnaludzi, double defnamoby, double szansanakryta, double szansanawzmocnieniekryta, double blokciosu, double przeszyciebloku, double szansanakrwawienie,
                       double minussrednieobrazenia, double minussredniadefensywa, double minusdefnaludzi, double minusdefnamoby, double minusobrazenianaludzi, double minusobrazenianamoby,
                       int szczescie, int szybkosc, double spowolnienie, double dodatkowyExp, double oslepienie, double przebiciePancerza, double wampiryzm, double wzmocnienieKryta, int dmgMetiny) {
        this.dodatkowehp = dodatkowehp;
        this.dodatkowezlotehp = dodatkowezlotehp;
        this.dodatkoweobrazenia = dodatkoweobrazenia;
        this.truedamage = truedamage;
        this.srednieobrazenia = srednieobrazenia;
        this.silnynaludzi = silnynaludzi;
        this.silnynapotwory = silnynapotwory;
        this.sredniadefensywa = sredniadefensywa;
        this.defnaludzi = defnaludzi;
        this.defnamoby = defnamoby;
        this.szansanakryta = szansanakryta;
        this.szansanawzmocnieniekryta = szansanawzmocnieniekryta;
        this.blokciosu = blokciosu;
        this.przeszyciebloku = przeszyciebloku;
        this.szansanakrwawienie = szansanakrwawienie;
        this.minussrednieobrazenia = minussrednieobrazenia;
        this.minussredniadefensywa = minussredniadefensywa;
        this.minusdefnaludzi = minusdefnaludzi;
        this.minusdefnamoby = minusdefnamoby;
        this.minusobrazenianaludzi = minusobrazenianaludzi;
        this.minusobrazenianamoby = minusobrazenianamoby;
        this.szczescie = szczescie;
        this.szybkosc = szybkosc;
        this.spowolnienie = spowolnienie;
        this.dodatkowyExp = dodatkowyExp;
        this.oslepienie = oslepienie;
        this.przebiciePancerza = przebiciePancerza;
        this.wampiryzm = wampiryzm;
        this.wzmocnienieKryta = wzmocnienieKryta;
        this.dmgMetiny = dmgMetiny;
    }
}
