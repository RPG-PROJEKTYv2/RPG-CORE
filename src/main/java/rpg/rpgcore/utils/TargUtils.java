package rpg.rpgcore.utils;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TargUtils {

    public static List<ItemStack> getPageItems(final List<ItemStack> items, int page, int freeSpace) {
        final List<ItemStack> pageItems = new ArrayList<>();

        int upperBound = page * freeSpace;
        int lowerBound = upperBound - freeSpace;

        for (int i = lowerBound; i < upperBound; i++) {
            try {
                pageItems.add(items.get(i));
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        return pageItems;
    }


    public static boolean isPageValid(final List<ItemStack> items, int page, int freeSpace) {

        if (page <= 0) { return false; }

        int upperBound = page * freeSpace;
        int lowerBound = upperBound - freeSpace;

        return items.size() > lowerBound;
    }
}
