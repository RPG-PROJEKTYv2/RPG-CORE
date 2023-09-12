package rpg.rpgcore.chests;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class Items implements Cloneable {

    private final String name;
    private final double chance;
    private ItemStack rewardItem;
    private int amount;

    public Items(String name, double chance, ItemStack rewardItem, int amount) {
        this.name = name;
        this.chance = chance;
        this.rewardItem = rewardItem;
        this.amount = amount;
    }


    public ItemStack getRewardItem() {
        return this.rewardItem.clone();
    }

    @Override
    public Items clone() {
        try {
            return (Items) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
