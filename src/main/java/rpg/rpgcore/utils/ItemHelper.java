package rpg.rpgcore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemHelper {

    private final List<String> lore = new ArrayList<>();

    public static ItemStack createArmor(final String name, final Material itemType, final int prot, final int thorns, final boolean hideFlags, final boolean addGlint) {
        final ItemBuilder set = new ItemBuilder(itemType);
        final List<String> lore = new ArrayList<>();

        set.setName(name);
        lore.add("&b❣ &7Obrona: &f" + prot);
        lore.add("&6✪ &7Thorns: &f" + thorns);
        set.setLore(lore);

        if (hideFlags) {
            set.hideFlag();
        }

        if (addGlint) {
            set.addGlowing();
        }

        return set.toItemStack();
    }

    public static ItemStack createSword(final String name, final Material itemType, final int sharp, final int bane, final boolean hideFlags, final boolean addGlowing) {
        final ItemBuilder sword = new ItemBuilder(itemType);
        final List<String> lore = new ArrayList<>();

        sword.setName(name);

        lore.add("&4⚔ &7Obrazenia: &c" + sharp);
        lore.add("&f☠ &7Obrazenia na potwory: &c" + bane);
        sword.setLore(lore);

        if (hideFlags) {
            sword.hideFlag();
        }

        if (addGlowing) {
            sword.addGlowing();
        }

        return sword.toItemStack();
    }
}
