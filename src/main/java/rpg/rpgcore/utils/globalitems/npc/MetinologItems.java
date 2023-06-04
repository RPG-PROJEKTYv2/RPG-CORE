package rpg.rpgcore.utils.globalitems.npc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum MetinologItems {
    I1("I1-10", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 1-10").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I2("I10-20", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 10-20").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I3("I20-30", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 20-30").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I4("I30-40", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 30-40").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I5("I40-50", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 40-50").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I6("I50-60", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 50-60").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I7("ILodowej-Wiezy", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin Lodowej Wiezy").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I8("I60-70", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 60-70").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I9("I70-80", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 70-80").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I10("I80-90", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 80-90").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I11("I90-100", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 90-100").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I12("I100-110", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 100-110").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I13("I110-120", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 110-120").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I14("I120-130", new ItemBuilder(Material.PRISMARINE_SHARD).setName("&4Odlamek Kamienia Metin 120-130").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone());

    private final String name;
    private final ItemStack itemStack;

    MetinologItems(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }


    public static MetinologItems getByName(String name) {
        for (MetinologItems rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = MetinologItems.getByName(string).getItemStack();
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
