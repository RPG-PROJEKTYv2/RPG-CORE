package rpg.rpgcore.utils.globalitems.npc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum LesnikItems {

    I1("I1", new ItemBuilder(Material.DOUBLE_PLANT).setName("&6Zywica").setLore(Arrays.asList("&8Chyba Lesnik tego potrzebuje...")).toItemStack().clone()),
    POTION("Potion", new ItemBuilder(Material.GLASS_BOTTLE).setName("&2Wywar z Kory").setLore(Arrays.asList("&8Dzieki tej miksturze posiadasz &8o &650%", "&8szans wiecej na przyjecie przedmiotu u &2&lLesnika")).toItemStack());

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
