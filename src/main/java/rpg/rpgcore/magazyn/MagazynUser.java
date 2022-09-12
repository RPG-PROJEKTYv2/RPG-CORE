package rpg.rpgcore.magazyn;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MagazynUser {
    public String magazyn1, magazyn2, magazyn3, magazyn4, magazyn5;
    public boolean unlocked1, unlocked2, unlocked3, unlocked4, unlocked5;

    public MagazynUser(String magazyn1, String magazyn2, String magazyn3, String magazyn4, String magazyn5, boolean unlocked1, boolean unlocked2, boolean unlocked3, boolean unlocked4, boolean unlocked5) {
        this.magazyn1 = magazyn1;
        this.magazyn2 = magazyn2;
        this.magazyn3 = magazyn3;
        this.magazyn4 = magazyn4;
        this.magazyn5 = magazyn5;
        this.unlocked1 = unlocked1;
        this.unlocked2 = unlocked2;
        this.unlocked3 = unlocked3;
        this.unlocked4 = unlocked4;
        this.unlocked5 = unlocked5;
    }

    public String getMagazyn(final int nr) {
        switch (nr) {
            case 1:
                return this.magazyn1;
            case 2:
                return this.magazyn2;
            case 3:
                return this.magazyn3;
            case 4:
                return this.magazyn4;
            case 5:
                return this.magazyn5;
        }
        return "";
    }

    public void setMagazyn(final String s, final int nr) {
        switch (nr) {
            case 1:
                this.magazyn1 = s;
                break;
            case 2:
                this.magazyn2 = s;
                break;
            case 3:
                this.magazyn3 = s;
                break;
            case 4:
                this.magazyn4 = s;
                break;
            case 5:
                this.magazyn5 = s;
                break;
        }
    }

    public boolean isEmpty(final int nr) {
        switch (nr) {
            case 1:
                return this.magazyn1.isEmpty();
            case 2:
                return this.magazyn2.isEmpty();
            case 3:
                return this.magazyn3.isEmpty();
            case 4:
                return this.magazyn4.isEmpty();
            case 5:
                return this.magazyn5.isEmpty();
        }
        return true;
    }

    public boolean isUnlocked(final int nr) {
        switch (nr) {
            case 1:
                return this.unlocked1;
            case 2:
                return this.unlocked2;
            case 3:
                return this.unlocked3;
            case 4:
                return this.unlocked4;
            case 5:
                return this.unlocked5;
        }
        return false;
    }
}
