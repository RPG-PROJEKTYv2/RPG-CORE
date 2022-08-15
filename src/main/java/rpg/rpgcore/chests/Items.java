package rpg.rpgcore.chests;

import org.bukkit.inventory.ItemStack;

public class Items {

    private final String name;
    private final double chance;
    private final ItemStack rewardItem;
    private final int amount;

    public Items(String name, double chance, ItemStack rewardItem, int amount) {
        this.name = name;
        this.chance = chance;
        this.rewardItem = rewardItem;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getChance() {
        return this.chance;
    }

    public ItemStack getRewardItem() {
        return this.rewardItem;
    }

    public int getAmount() {
        return amount;
    }
}
