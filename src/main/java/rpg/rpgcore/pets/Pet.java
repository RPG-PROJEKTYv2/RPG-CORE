package rpg.rpgcore.pets;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class Pet {
    private ItemStack item;
    private String name;
    private String rarity;
    private double value1, value2, value3, value4;
    private String ultimateAbility;

    public Pet(ItemStack item, String name, String rarity, double value1, double value2, double value3, double value4, String ultimateAbility) {
        this.item = item;
        this.name = name;
        this.rarity = rarity;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.ultimateAbility = ultimateAbility;
    }

    public void reset() {
        this.item = null;
        this.name = "";
        this.rarity = "";
        this.value1 = 0.0;
        this.value2 = 0.0;
        this.value3 = 0.0;
        this.value4 = 0.0;
        this.ultimateAbility = "";
    }
}
