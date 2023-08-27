package rpg.rpgcore.utils.globalitems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.globalitems.npc.PrzyrodnikItems;
import rpg.rpgcore.utils.ItemBuilder;

public enum AkceItems {
    A1("1-10", new  ItemBuilder(Material.IRON_BLOCK).setName("&8&lZaginione Akcesorium &6&l1-10").addGlowing().toItemStack().clone()),
    A2("10-20", new ItemBuilder(Material.IRON_BLOCK).setName("&2&lZielone Akcesorium &6&l10-20").addGlowing().toItemStack().clone()),
    A3("20-30", new ItemBuilder(Material.IRON_BLOCK).setName("&6&lTropikalne Akcesorium &6&l20-30").addGlowing().toItemStack().clone()),
    A4("30-40", new ItemBuilder(Material.IRON_BLOCK).setName("&c&lPrzeklete Akcesorium &6&l30-40").addGlowing().toItemStack().clone()),
    A5("40-50", new ItemBuilder(Material.IRON_BLOCK).setName("&b&lPradawne Akcesorium &6&l40-50").addGlowing().toItemStack().clone()),
    A6("50-60", new ItemBuilder(Material.IRON_BLOCK).setName("&f&lSniezne Akcesorium &6&l50-60").addGlowing().toItemStack().clone()),
    A7("60-70", new ItemBuilder(Material.IRON_BLOCK).setName("&c&lOgniste Akcesorium &6&l60-70").addGlowing().toItemStack().clone()),
    A8("70-80", new ItemBuilder(Material.IRON_BLOCK).setName("&7&lMgliste Akcesorium &6&l70-80").addGlowing().toItemStack().clone()),
    A9("80-90", new ItemBuilder(Material.IRON_BLOCK).setName("&e&lSloneczne Akcesorium &6&l80-90").addGlowing().toItemStack().clone()),
    A10("90-100", new ItemBuilder(Material.IRON_BLOCK).setName("&9&lSkradzione Akcesorium &6&l90-100").addGlowing().toItemStack().clone()),
    A11("100-110", new ItemBuilder(Material.IRON_BLOCK).setName("&b&lMityczne Akcesorium &6&l100-110").addGlowing().toItemStack().clone()),
    A12("110-120", new ItemBuilder(Material.IRON_BLOCK).setName("&3&lSzkarlatne Akcesorium &6&l110-120").addGlowing().toItemStack().clone()),
    A13("120-130", new ItemBuilder(Material.IRON_BLOCK).setName("&9&lStarozytne Akcesorium &6&l120-130").addGlowing().toItemStack().clone());
    private final ItemStack itemStack;
    private final String name;

    AkceItems(String name, ItemStack item) {
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
        for (NiesyItems akceItems : NiesyItems.values()) {
            if (akceItems.getName().equalsIgnoreCase(name)) {
                return akceItems;
            }
        }
        return null;
    }

    public static ItemStack getItem(String name) {
        ItemStack itemStack = NiesyItems.getByName(name).getItemStack();
        return itemStack;
    }
    public ItemStack getAkceItems() {
        return this.itemStack;
    }
}
