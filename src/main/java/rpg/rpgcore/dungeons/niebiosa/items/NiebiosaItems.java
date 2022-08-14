package rpg.rpgcore.dungeons.niebiosa.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

public enum NiebiosaItems {

    I1("klucz", new ItemBuilder(Material.TRIPWIRE_HOOK).setName("&bKlucz do Bramy Niebios").addGlowing().toItemStack().clone()),
    I2("skrzynia", new ItemBuilder(Material.CHEST).setName("&b&lSkrzynia Wladcy Niebios").hideFlag().toItemStack().clone());

    //TODO Zrobic drop ze skrzynki

    private final ItemStack itemStack;
    private final String name;

    NiebiosaItems(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }


    public static NiebiosaItems getByName(String name) {
        for (NiebiosaItems rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int number) {
        ItemStack itemStack = NiebiosaItems.getByName(string).getItemStack();
        itemStack.setAmount(number);
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
