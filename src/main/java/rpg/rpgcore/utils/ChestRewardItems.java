package rpg.rpgcore.utils;

import org.bukkit.inventory.ItemStack;

public class ChestRewardItems {

    private final String name;
    private final double chanceOfDropping;
    private final ItemStack rewardItem;

    public ChestRewardItems(String name, double chanceOfDropping, ItemStack rewardItem) {
        this.name = name;
        this.chanceOfDropping = chanceOfDropping;
        this.rewardItem = rewardItem;
    }

    public String getName() {
        return name;
    }

    public double getChanceOfDropping() {
        return this.chanceOfDropping;
    }

    public ItemStack getRewardItem() {
        return this.rewardItem;
    }


}
