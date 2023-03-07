package rpg.rpgcore.utils.globalitems.expowiska;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum Przepustki {
    I1(1000000,"I1", new ItemBuilder(Material.PAPER).setName("&eWejsciowka do &bSwiatyni").setLore(Arrays.asList("&7Jednorazowa wejsciowka...")).toItemStack().clone()),
    I2(2000000,"I2", new ItemBuilder(Material.PAPER).setName("&eWejsciowka do &fKrysztalowej Sali").setLore(Arrays.asList("&7Jednorazowa wejsciowka...")).toItemStack().clone()),
    I3(5000000,"I3", new ItemBuilder(Material.PAPER).setName("&eWejsciowka do &fNieba").setLore(Arrays.asList("&7Jednorazowa wejsciowka...")).toItemStack().clone()),
    I4(0,"I4", new ItemBuilder(Material.BOOK_AND_QUILL).setName("&f&lTalizman &e&lWejsciowki").setLore(Arrays.asList("&7Nieskonczona wejsciowka...", "", "&cWymagany poziom: &6120")).toItemStack().clone());
    private final int cena;
    private final ItemStack itemStack;
    private final String name;

    Przepustki( int cena,String name, ItemStack itemStack) {
        this.cena = cena;
        this.name = name;
        this.itemStack = itemStack;
    }
    public int getCena() {
        return cena;
    }
    public static Przepustki getByName(String name) {
        for (Przepustki przeps : values()) {
            if (przeps.getName().equalsIgnoreCase(name)) {
                return przeps;
            }
        }
        return null;
    }
    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = Przepustki.getByName(string).getItemStack();
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
