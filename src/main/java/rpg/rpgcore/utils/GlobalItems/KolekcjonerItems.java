package rpg.rpgcore.utils.GlobalItems;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;

public enum KolekcjonerItems {

    I1_10_1("1-10-1", ItemHelper.createArmor("&8Zaginiona Czapka Najemnika", Material.LEATHER_HELMET, 10, 1, true, true)),
    I1_10_2("1-10-2", ItemHelper.createArmor("&8Zaginiona Koszula Najemnika", Material.LEATHER_CHESTPLATE, 10, 1, true, true)),
    I1_10_3("1-10-3", ItemHelper.createArmor("&8Zaginione Spodnie Najemnika", Material.LEATHER_LEGGINGS, 10, 1, true, true)),
    I1_10_4("1-10-4", ItemHelper.createArmor("&8Zaginiona Buty Najemnika", Material.LEATHER_BOOTS, 10, 1, true, true)),
    I99("ERROR", new ItemBuilder(Material.DIRT).setName("&c&lCos sie popsulo :<").toItemStack().clone());
    private final String name;
    private final ItemStack item;

    KolekcjonerItems(final String name, final ItemStack item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return this.name;
    }

    public ItemStack getItem() {
        return this.item.clone();
    }

    public static KolekcjonerItems getByName(final String name) {
        for (final KolekcjonerItems item : KolekcjonerItems.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return I99;
    }
}