package rpg.rpgcore.utils;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomItems<E> {
    private final NavigableMap<Double, E> map = new TreeMap<>();
    private final Random random;
    private double total = 0;

    public RandomItems() {
        this(new Random());
    }

    public RandomItems(Random random) {
        this.random = random;
    }

    public RandomItems<E> add(double weight, E result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }

    public void clear() {
        map.clear();
    }

    /*
    public Item getDrawnItem(Player player, int number) {
        if (number == 0) {
            for (Item item : this.items) {
                if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current()
                        .nextDouble(0.0, 100.0)) {
                    MessageHelper.build("&8[&2+&8] &f" + ChatColor.stripColor(item.getRewardItem().getItemMeta().getDisplayName())).send(player);
                    return item;
                }
            }
        }
     */
}
