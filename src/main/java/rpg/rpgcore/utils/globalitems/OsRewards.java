package rpg.rpgcore.utils.globalitems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum OsRewards {
    P0("P0", new ItemBuilder(Material.DIAMOND_SWORD).setName("&7&lTestowy Miecz").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    M0("M0", new ItemBuilder(Material.DIAMOND_SWORD).setName("&7&lTestowy Miecz2").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    T0("T0", new ItemBuilder(Material.DIAMOND_SWORD).setName("&7&lTestowy Miecz3").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    B0("B0", new ItemBuilder(Material.DIAMOND_SWORD).setName("&7&lTestowy Miecz4").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    F0("F0", new ItemBuilder(Material.DIAMOND_SWORD).setName("&7&lTestowy Miecz5").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    C0("C0", new ItemBuilder(Material.DIAMOND_SWORD).setName("&7&lTestowy Miecz6").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    U0("U0", new ItemBuilder(Material.DIAMOND_SWORD).setName("&7&lTestowy Miecz7").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    N0("N0", new ItemBuilder(Material.DIAMOND_SWORD).setName("&7&lTestowy Miecz8").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    D0("D0", new ItemBuilder(Material.DIAMOND_SWORD).setName("&7&lTestowy Miecz9").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    L0("L0", new ItemBuilder(Material.DIAMOND_SWORD).setName("&7&lTestowy Miecz10").setLore(Arrays.asList("&7&oChyba metinolog tego potrzebuje")).addGlowing().toItemStack().clone()),
    I_ERROR("ERROR", new ItemBuilder(Material.DIRT).setName("&c&lNie znaleziono Nagrody").toItemStack().clone());

    private final String name;
    private final ItemStack itemStack;

    OsRewards(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }


    public static OsRewards getByName(String name) {
        for (OsRewards rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return OsRewards.I_ERROR;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = OsRewards.getByName(string).getItemStack();
        itemStack.setAmount(amount);
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getItemName() {
        return itemStack.getItemMeta().getDisplayName();
    }

    public int getAmount() {
        return itemStack.getAmount();
    }
}
