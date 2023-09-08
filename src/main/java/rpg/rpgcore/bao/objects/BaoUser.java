package rpg.rpgcore.bao.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaoUser implements Cloneable {
    private String bonus1, bonus2, bonus3, bonus4, bonus5;
    private int value1, value2, value3, value4, value5;

    public BaoUser(String bonus1, int value1, String bonus2, int value2, String bonus3, int value3, String bonus4, int value4, String bonus5, int value5) {
        this.bonus1 = bonus1;
        this.value1 = value1;
        this.bonus2 = bonus2;
        this.value2 = value2;
        this.bonus3 = bonus3;
        this.value3 = value3;
        this.bonus4 = bonus4;
        this.value4 = value4;
        this.bonus5 = bonus5;
        this.value5 = value5;
    }

    @Override
    public BaoUser clone() {
        try {
            return (BaoUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
