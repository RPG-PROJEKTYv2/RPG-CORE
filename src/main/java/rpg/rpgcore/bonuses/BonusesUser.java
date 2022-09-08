package rpg.rpgcore.bonuses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusesUser {
    private int dodatkowehp, dodatkowezlotehp, dodatkoweobrazenia, truedamage;
    private double srednieobrazenia, silnynaludzi, silnynapotwory, sredniadefensywa, defnaludzi, defnamoby, szansanakryta, szansanawzmocnieniekryta, blokciosu, przeszyciebloku, szansanakrwawienie, minussrednieobrazenia, minussredniadefensywa, minusdefnaludzi, minusdefnamoby, minusobrazenianaludzi, minusobrazenianamoby;

    public BonusesUser(int dodatkowehp, int dodatkowezlotehp, int dodatkoweobrazenia, int truedamage, double srednieobrazenia, double silnynaludzi, double silnynapotwory, double sredniadefensywa, double defnaludzi, double defnamoby, double szansanakryta, double szansanawzmocnieniekryta, double blokciosu, double przeszyciebloku, double szansanakrwawienie, double minussrednieobrazenia, double minussredniadefensywa, double minusdefnaludzi, double minusdefnamoby, double minusobrazenianaludzi, double minusobrazenianamoby) {
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
    }
}
