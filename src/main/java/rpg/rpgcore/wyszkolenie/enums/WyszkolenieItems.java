package rpg.rpgcore.wyszkolenie.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

public enum WyszkolenieItems {
    I1("I1", new ItemBuilder(Material.BOOK).setName("&cPodrecznik Potegi").toItemStack().clone()),
    I2("I2", new ItemBuilder(Material.BOOK).setName("&ePodrecznik Talentu I").toItemStack().clone()),
    I3("I3", new ItemBuilder(Material.BOOK).setName("&dPodrecznik Silnego Ciala").toItemStack().clone()),
    I4("I4", new ItemBuilder(Material.BOOK).setName("&aPodrecznik Sily").toItemStack().clone()),
    I5("I5", new ItemBuilder(Material.BOOK).setName("&6Podrecznik Pogromcy Potworow").toItemStack().clone()),
    I6("I6", new ItemBuilder(Material.BOOK).setName("&7Stalowy Podrecznik").toItemStack().clone()),
    I7("I7", new ItemBuilder(Material.BOOK).setName("&6Podrecznik Telentu II").toItemStack().clone()),
    I8("I8", new ItemBuilder(Material.BOOK).setName("&3Podrecznik Defensywy").toItemStack().clone()),
    I9("I9", new ItemBuilder(Material.BOOK).setName("&6Podrecznik Wojownika I").toItemStack().clone()),
    I10("I10", new ItemBuilder(Material.BOOK).setName("&7Podrecznik Niszczyciela Tarcz").toItemStack().clone()),
    I11("I11", new ItemBuilder(Material.BOOK).setName("&6Podrecznik Bloku").toItemStack().clone()),
    I12("I12", new ItemBuilder(Material.BOOK).setName("&1Podrecznik Barbarzyncy").toItemStack().clone()),
    I13("I13", new ItemBuilder(Material.BOOK).setName("&bPodrecznik Wojownika II").toItemStack().clone()),
    I14("I14", new ItemBuilder(Material.BOOK).setName("&ePodrecznik Zabojczego Ostrza").toItemStack().clone()),
    I15("I15", new ItemBuilder(Material.BOOK).setName("&6Niezniszczalny Podrecznik").toItemStack().clone()),
    I16("I16", new ItemBuilder(Material.BOOK).setName("&aPodrecznik Zdrowia").toItemStack().clone()),
    I17("I17", new ItemBuilder(Material.BOOK).setName("&4Podrecznik Obrazen").toItemStack().clone());


    private final String name;
    private final ItemStack itemStack;

    WyszkolenieItems(final String name, final ItemStack itemStack) {
        this.name = name;
        this.itemStack = itemStack;
    }

    public String getName() {
        return this.name;
    }
    public ItemStack getItem() {
        return itemStack.clone();
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
