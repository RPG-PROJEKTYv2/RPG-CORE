package rpg.rpgcore.utils.GlobalItems.npc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum LesnikItems {

    I1("I1", new ItemBuilder(Material.GRASS).setName("&bMietowa Trawa").setLore(Arrays.asList("&8Chyba Lesnik tego potrzebuje...")).toItemStack().clone());

    private final String name;
    private final ItemStack item;

    LesnikItems(String name, ItemStack item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public static LesnikItems getByName(String name) {
        for (LesnikItems item : LesnikItems.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static ItemStack getByItem(final String name, final int amount) {
        final ItemStack item = getByName(name).getItem();
        item.setAmount(amount);
        return item;
    }
}
