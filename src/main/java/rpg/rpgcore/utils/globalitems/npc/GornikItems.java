package rpg.rpgcore.utils.globalitems.npc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum GornikItems {
    I1("I1", new ItemBuilder(Material.NETHER_STAR).setName("&6&lLegendarny Krysztal Wzmocnienia").setLore(Arrays.asList("&8Posiada niesamowita moc, ktora pozwala na", "&8rozwiniecie swoich umiejetnosci", "&8w dziedzinie gornictwa")).toItemStack()),
    G1("G1", new ItemBuilder(Material.COAL).setName("&0Krysztal Mroku").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G2("G2", new ItemBuilder(Material.IRON_INGOT).setName("&7Krysztal Powietrza").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G3("G3", new ItemBuilder(Material.GOLD_INGOT).setName("&eKrysztal Blasku").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G4("G4", new ItemBuilder(Material.INK_SACK, 1, (short) 4).setName("&1Krysztal Wody").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G5("G5", new ItemBuilder(Material.EMERALD).setName("&2Krysztal Lasu").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G6("G6", new ItemBuilder(Material.DIAMOND).setName("&bKrysztal Lodu").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G7("G7", new ItemBuilder(Material.REDSTONE).setName("&cKrysztal Ognia").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack());
    private final String name;
    private final ItemStack is;

    GornikItems(String name, ItemStack is) {
        this.name = name;
        this.is = is;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return is.clone();
    }

    public static GornikItems getByName(String name) {
        for (GornikItems gornikItems : GornikItems.values()) {
            if (gornikItems.getName().equalsIgnoreCase(name)) {
                return gornikItems;
            }
        }
        return null;
    }

    public static ItemStack getItem(String name, int amount) {
        ItemStack is = GornikItems.getByName(name).getItemStack();
        is.setAmount(amount);
        return is;
    }
}
