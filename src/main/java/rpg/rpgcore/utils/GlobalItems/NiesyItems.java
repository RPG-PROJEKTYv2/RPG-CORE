package rpg.rpgcore.utils.GlobalItems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.GlobalItems.npc.PrzyrodnikItems;
import rpg.rpgcore.utils.ItemBuilder;

public enum NiesyItems {
    N1("1-10", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 1-10").addGlowing().toItemStack().clone()),
    N2("10-20", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 10-20").addGlowing().toItemStack().clone()),
    N3("20-30", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 20-30").addGlowing().toItemStack().clone()),
    N4("30-40", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 30-40").addGlowing().toItemStack().clone()),
    N5("40-50", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 40-50").addGlowing().toItemStack().clone()),
    N6("50-60", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 50-60").addGlowing().toItemStack().clone()),
    N7("60-70", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 60-70").addGlowing().toItemStack().clone()),
    N8("70-80", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 70-80").addGlowing().toItemStack().clone()),
    N9("80-90", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 80-90").addGlowing().toItemStack().clone()),
    N10("90-100", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 90-100").addGlowing().toItemStack().clone()),
    N11("100-110", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 100-110").addGlowing().toItemStack().clone()),
    N12("110-120", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 110-120").addGlowing().toItemStack().clone()),
    N13("120-130", new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNiesamowity Przedmiot 120-130").addGlowing().toItemStack().clone());
    private final ItemStack itemStack;
    private final String name;
    NiesyItems(String name, ItemStack item) {
        this.name = name;
        this.itemStack = item;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public static NiesyItems getByName(String name) {
        for (NiesyItems niesyItems : NiesyItems.values()) {
            if (niesyItems.getName().equalsIgnoreCase(name)) {
                return niesyItems;
            }
        }
        return null;
    }

    public static ItemStack getItem(String name) {
        ItemStack itemStack = PrzyrodnikItems.getByName(name).getItemStack();
        return itemStack;
    }
}
