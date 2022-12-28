package rpg.rpgcore.utils.globalitems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum Artefakty {

    I51("I51", new ItemBuilder(Material.INK_SACK, 1, (short) 14).setName("&6&lKamien Zaczarowania Zbroii").setLore(Arrays.asList("&8Pozwala zmienic bonusy na twojej zbroii")).toItemStack().clone());




    private final ItemStack itemStack;
    private final String name;

    Artefakty(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }
    public static Artefakty getByName(String name) {
        for (Artefakty rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }
    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = Artefakty.getByName(string).getItemStack();
        itemStack.setAmount(amount);
        return itemStack;
    }
    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
