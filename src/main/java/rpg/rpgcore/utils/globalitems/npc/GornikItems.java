package rpg.rpgcore.utils.globalitems.npc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum GornikItems {
    I1("I1", new ItemBuilder(Material.NETHER_STAR).setName("&6&lLegendarny Krysztal Wzmocnienia").setLore(Arrays.asList("&8Posiada niesamowita moc, ktora pozwala na", "&8rozwiniecie swoich umiejetnosci", "&8w dziedzinie gornictwa")).toItemStack());
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
