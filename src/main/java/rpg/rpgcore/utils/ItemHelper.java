package rpg.rpgcore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemHelper {


    public static ItemStack createArmor(final String name, final Material itemType, final int prot, final int thorns, final boolean hideFlags, final boolean addGlint) {
        final ItemBuilder set = new ItemBuilder(itemType);
        final List<String> lore = new ArrayList<>();

        set.setName(name);
        lore.add("&7Obrona: &f" + prot);
        lore.add("&7Ciernie: &f" + thorns);
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

        lore.add("&7Obrazenia: &c" + sharp);
        lore.add("&7Obrazenia na potwory: &c" + bane);
        sword.setLore(lore);

        if (hideFlags) {
            sword.hideFlag();
        }

        if (addGlowing) {
            sword.addGlowing();
        }

        return sword.toItemStack();
    }

    public static void setEnchants(final ItemStack is, final String type, final int v1, final int v2) {
        final ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (im.hasLore()) {
            lore = im.getLore();
            if (type.equalsIgnoreCase("zbroja")) {
                lore.set(0, Utils.format("&7Obrona: &f" + v1));
                lore.set(1, Utils.format("&7Ciernie: &f" + v2));
            } else if (type.equalsIgnoreCase("miecz")) {
                lore.set(0, Utils.format("&7Obrazenia: &c" + v1));
                lore.set(1, Utils.format("&7Obrazenia na potwory: &c" + v2));
            }
        } else {
            if (type.equalsIgnoreCase("zbroja")) {
                lore.add(Utils.format("&7Obrona: &f" + v1));
                lore.add(Utils.format("&7Ciernie: &f" + v2));
            } else if (type.equalsIgnoreCase("miecz")) {
                lore.add(Utils.format("&7Obrazenia: &c" + v1));
                lore.add(Utils.format("&7Obrazenia na potwory: &c" + v2));
            }
        }
        im.setLore(lore);
        is.setItemMeta(im);
    }
}
