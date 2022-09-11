package rpg.rpgcore.utils.GlobalItems.expowiska;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum Map1Items {

    I1("I1", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lKufer &9&lWygnanca").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone());


    private final ItemStack itemStack;
    private final String name;

    Map1Items(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }
    public static Map1Items getByName(String name) {
        for (Map1Items rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
