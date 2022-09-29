package rpg.rpgcore.utils.globalitems.npc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum RybakItems {
    I1("I1", new ItemBuilder(Material.RAW_FISH).setName("&6Sledz").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I2("I2", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Dorsz").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I3("I3", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Losos").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I4("I4", new ItemBuilder(Material.RAW_FISH, 1, (short) 2).setName("&6Krasnopiorka").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I5("I5", new ItemBuilder(Material.COOKED_FISH, 1, (short) 1).setName("&6Dorsz Czarny").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I6("I6", new ItemBuilder(Material.RAW_FISH).setName("&6Dorada").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I7("I7", new ItemBuilder(Material.COOKED_FISH).setName("&6Cierniczek").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I8("I8", new ItemBuilder(Material.RAW_FISH, 1, (short) 3).setName("&6Fladra").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I9("I9", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Karas").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I10("I10", new ItemBuilder(Material.COOKED_FISH).setName("&6Karp").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I11("I11", new ItemBuilder(Material.COOKED_FISH, 1, (short) 1).setName("&6Leszcz").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I12("I12", new ItemBuilder(Material.COOKED_FISH).setName("&6Makrela").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I13("I13", new ItemBuilder(Material.COOKED_FISH).setName("&6Mintaj").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I14("I14", new ItemBuilder(Material.RAW_FISH, 1, (short) 3).setName("&6Okon").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I15("I15", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Plotka").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone());

    private final String name;
    private final ItemStack itemStack;

    RybakItems(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }
    public static RybakItems getByName(String name) {
        for (RybakItems rank : values()) {
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
