package rpg.rpgcore.wyszkolenie.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

public enum WyszkolenieItems {
    I1("I1", new ItemBuilder(Material.BOOK).setName("Podrecznik 1").toItemStack().clone()),
    I2("I2", new ItemBuilder(Material.BOOK).setName("Podrecznik 2").toItemStack().clone()),
    I3("I3", new ItemBuilder(Material.BOOK).setName("Podrecznik 3").toItemStack().clone()),
    I4("I4", new ItemBuilder(Material.BOOK).setName("Podrecznik 4").toItemStack().clone()),
    I5("I5", new ItemBuilder(Material.BOOK).setName("Podrecznik 5").toItemStack().clone()),
    I6("I6", new ItemBuilder(Material.BOOK).setName("Podrecznik 6").toItemStack().clone()),
    I7("I7", new ItemBuilder(Material.BOOK).setName("Podrecznik 7").toItemStack().clone()),
    I8("I8", new ItemBuilder(Material.BOOK).setName("Podrecznik 8").toItemStack().clone()),
    I9("I9", new ItemBuilder(Material.BOOK).setName("Podrecznik 9").toItemStack().clone()),
    I10("I10", new ItemBuilder(Material.BOOK).setName("Podrecznik 10").toItemStack().clone()),
    I11("I11", new ItemBuilder(Material.BOOK).setName("Podrecznik 11").toItemStack().clone()),
    I12("I12", new ItemBuilder(Material.BOOK).setName("Podrecznik 12").toItemStack().clone()),
    I13("I13", new ItemBuilder(Material.BOOK).setName("Podrecznik 13").toItemStack().clone()),
    I14("I14", new ItemBuilder(Material.BOOK).setName("Podrecznik 14").toItemStack().clone()),
    I15("I15", new ItemBuilder(Material.BOOK).setName("Podrecznik 15").toItemStack().clone()),
    I16("I16", new ItemBuilder(Material.BOOK).setName("Podrecznik 16").toItemStack().clone()),
    I17("I17", new ItemBuilder(Material.BOOK).setName("Podrecznik 17").toItemStack().clone());


    private final String name;
    private final ItemStack itemStack;

    WyszkolenieItems(final String name, final ItemStack itemStack) {
        this.name = name;
        this.itemStack = itemStack;
    }

    public String getName() {
        return this.name;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public static WyszkolenieItems getByName(String name) {
        for (WyszkolenieItems rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = WyszkolenieItems.getByName(string).getItemStack();
        itemStack.setAmount(amount);
        return itemStack;
    }


}
