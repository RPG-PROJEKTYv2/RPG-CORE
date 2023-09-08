package rpg.rpgcore.inventories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoriesUser implements Cloneable {
    private String inventory, enderchest, armor;

    public InventoriesUser(String inventory, String enderchest, String armor) {
        this.inventory = inventory;
        this.enderchest = enderchest;
        this.armor = armor;
    }

    @Override
    public InventoriesUser clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (InventoriesUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
