package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;

public class PomocManager {

    private Inventory gui;
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private ArrayList<String> itemLore = new ArrayList<>();

    private final RPGCORE rpgcore;

    public PomocManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }


    public Inventory pomocGUI() {
        this.gui = Bukkit.createInventory(null, 3*9, Utils.format("&e&lPomoc serwerowa."));

        for (int i = 0; i < gui.getSize(); i++) {
            this.itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
            this.itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(" ");

            this.itemStack.setItemMeta(itemMeta);
            gui.setItem(i, itemStack);
        }
        this.itemLore.clear();

        // REGULAMIN

        this.itemStack = new ItemStack(Material.BOOK);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&4Regulamin"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&f&lKliknij aby zobaczyć regulamin serwerowy."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(10, itemStack);
        this.itemLore.clear();

        // POMOC


        this.itemStack = new ItemStack(351, 1, (short) 14);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&aOgólne"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&f&lKliknij aby zobaczyć ogólną pomoc."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(10, itemStack);
        this.itemLore.clear();

        // TARYFIKATOR

        this.itemStack = new ItemStack(Material.SIGN);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&cTaryfikator."));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&f&lKliknij aby zobaczyć taryfikator serwera."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(10, itemStack);
        this.itemLore.clear();

        return this.gui;
    }
}
