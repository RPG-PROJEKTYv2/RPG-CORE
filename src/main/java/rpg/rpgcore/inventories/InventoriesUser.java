package rpg.rpgcore.inventories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoriesUser {
    private String inventory, enderchest, armor;

    public InventoriesUser(String inventory, String enderchest, String armor) {
        this.inventory = inventory;
        this.enderchest = enderchest;
        this.armor = armor;
    }
}
