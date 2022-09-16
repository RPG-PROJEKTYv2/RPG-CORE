package rpg.rpgcore.utils.GlobalItems.expowiska;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.GlobalItems.GlobalItem;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public enum Map1Items {

    I1("I1", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lSkrzynia &9&lWygnanca").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc...")).hideFlag().toItemStack().clone()),
    I2("I2", new ItemBuilder(Material.CHEST).setName("&8Skrzynia Najemnika").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co w srodku...")).hideFlag().toItemStack().clone());


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

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = GlobalItem.getByName(string).getItemStack();
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
