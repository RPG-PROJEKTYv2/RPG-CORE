package rpg.rpgcore.utils.globalitems.npc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum PrzyrodnikItems {

    I0("1-10", new ItemBuilder(Material.WATCH).setName("&6Zardzewialy Pierscien").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).toItemStack().clone()),
    I1("10-20", new ItemBuilder(Material.WATER_LILY).setName("&2Ucho Goblina").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).toItemStack().clone()),
    I2("20-30", new ItemBuilder(Material.GHAST_TEAR).setName("&7Zab Goryla").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).toItemStack().clone()),
    I3("30-40", new ItemBuilder(Material.SULPHUR).setName("&8Prochy Zjawy").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).toItemStack().clone()),
    I4("40-50", new ItemBuilder(Material.BOOK).setName("&5Swiatynna Pieczec").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).addGlowing().toItemStack().clone()),
    I5("50-60", new ItemBuilder(Material.FEATHER).setName("&bKiel Mroznego Wilka").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).toItemStack().clone()),
    I6("60-70", new ItemBuilder(Material.REDSTONE).setName("&cRdzen Zywiolaka").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).toItemStack().clone()),
    I7("70-80", new ItemBuilder(Material.FLINT).setName("&9Odlamek Kosci Czarnego Szkieleta").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).toItemStack().clone()),
    I8("80-90", new ItemBuilder(Material.INK_SACK, 1, (short) 10).setName("&aToksyczny Jad").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).toItemStack().clone()),
    I9("90-100", new ItemBuilder(Material.ARROW).setName("&eZatruta Strzala").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).toItemStack().clone()),
    I10("100-110", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&bLuska Straznika").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).toItemStack().clone()),
    I11("110-120", new ItemBuilder(Material.INK_SACK, 1, (short)12).setName("&fMrozna Pieczec").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).toItemStack().clone()),
    I12("120-130", new ItemBuilder(Material.SUGAR).setName("&f&lAnielski Pyl").setLore(Arrays.asList("&8&oChyba Przyrodnik tego potrzebuje...")).toItemStack().clone());


    private final String name;
    private final ItemStack item;

    PrzyrodnikItems(String name, ItemStack item){
        this.name = name;
        this.item = item;
    }

    public static ItemStack getItem(String name) {
        ItemStack itemStack = PrzyrodnikItems.getByName(name).getItemStack();
        return itemStack;
    }

    public static PrzyrodnikItems getByName(String name) {
        for (PrzyrodnikItems item : values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return item;
    }
}
