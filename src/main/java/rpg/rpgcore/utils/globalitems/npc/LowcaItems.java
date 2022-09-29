package rpg.rpgcore.utils.globalitems.npc;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum LowcaItems {

    I1("1-10", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 1-10").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack()),
    I2("10-20", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 10-20").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack()),
    I3("20-30", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 20-30").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack()),
    I4("30-40", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 30-40").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack()),
    I5("40-50", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 40-50").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack()),
    I6("50-60", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 50-60").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack()),
    I7("60-70", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 60-70").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack()),
    I8("70-80", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 70-80").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack()),
    I9("80-90", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 80-90").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack()),
    I10("90-100", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 90-100").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack()),
    I11("100-110", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 100-110").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack()),
    I12("110-120", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 110-120").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack()),
    I13("120-130", new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&4Trofeum Bossa 120-130").setLore(Arrays.asList("&8Chyba lowca tego potrzebuje...")).hideFlag().toItemStack());

    private final String name;
    private final ItemStack item;

    LowcaItems(final String name, final ItemStack item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return this.name;
    }

    public ItemStack getItem() {
        return this.item.clone();
    }

    public static LowcaItems getByName(final String name) {
        for (final LowcaItems item : LowcaItems.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = LowcaItems.getByName(string).getItem();
        itemStack.setAmount(amount);
        return itemStack;
    }


}
