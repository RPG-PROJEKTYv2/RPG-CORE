package rpg.rpgcore.utils.globalitems.expowiska;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public enum Skrzynki {

    // Expowisko 1
    I1("I1", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lSkrzynia &9&lWygnanca").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I2("I2", new ItemBuilder(Material.CHEST).setName("&8Skrzynia Najemnika").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 2
    I3("I3", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lSkrzynia &a&lWodza Goblinow").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I4("I4", new ItemBuilder(Material.CHEST).setName("&fSkrzynia Goblina").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 3
    I5("I5", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lSkrzynia &f&lKrola Goryli").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I6("I6", new ItemBuilder(Material.CHEST).setName("&7Skrzynia Goryla").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 4
    I7("I7", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lSkrzynia &7&lPrzekletej Duszy").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I8("I8", new ItemBuilder(Material.CHEST).setName("&8Skrzynia Zjawy").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 5
    I9("I9", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lSkrzynia &e&lTrytona").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I10("I10", new ItemBuilder(Material.CHEST).setName("&3Skrzynia Straznika Swiatyni").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone());

    private final ItemStack itemStack;
    private final String name;

    Skrzynki(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static void getList(Player player) {
        int i = 1;
        for (Skrzynki rank : values()) {
            ItemStack itemStack = rank.getItemStack();
            player.sendMessage(Utils.format(i + ". " + itemStack.getItemMeta().getDisplayName()));
            i = i + 1;
        }
    }

    public static Skrzynki getByName(String name) {
        for (Skrzynki rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = Skrzynki.getByName(string).getItemStack();
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
