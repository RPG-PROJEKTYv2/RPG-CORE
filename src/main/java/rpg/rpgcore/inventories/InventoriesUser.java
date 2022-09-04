package rpg.rpgcore.inventories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoriesUser {
    private String inventory, enderchest, armor, magazyn1, magazyn2, magazyn3, magazyn4, magazyn5;

    public InventoriesUser(String inventory, String enderchest, String armor, String magazyn1, String magazyn2, String magazyn3, String magazyn4, String magazyn5) {
        this.inventory = inventory;
        this.enderchest = enderchest;
        this.armor = armor;
        this.magazyn1 = magazyn1;
        this.magazyn2 = magazyn2;
        this.magazyn3 = magazyn3;
        this.magazyn4 = magazyn4;
        this.magazyn5 = magazyn5;
    }
}
