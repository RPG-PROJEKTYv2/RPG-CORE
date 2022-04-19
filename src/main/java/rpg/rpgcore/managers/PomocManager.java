package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class PomocManager {

    private Inventory gui;
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private ArrayList<String> itemLore = new ArrayList<>();

    private final RPGCORE rpgcore;

    public PomocManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

//===========================================================================================================================
    public Inventory pomocGUIMAIN() {
        this.gui = Bukkit.createInventory(null, 3 * 9, Utils.format("&e&lPomoc serwerowa."));

        for (int i = 0; i < gui.getSize(); i++) {
            this.itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            this.itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(" ");

            this.itemStack.setItemMeta(itemMeta);
            gui.setItem(i, itemStack);
        }
        this.itemLore.clear();

        // REGULAMIN I TARYFIKATOR

        this.itemStack = new ItemStack(Material.BOOK);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&4Regulamin &f& &cTaryfikator"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fKliknij aby zobaczyć regulamin serwerowy."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(10, itemStack);
        this.itemLore.clear();

        // POMOC


        this.itemStack = new ItemStack(351, 1, (short) 1);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&aKomendy serwerowe"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fKliknij aby zobaczyć spis komend."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 13, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(13, itemStack);
        this.itemLore.clear();

        // TARYFIKATOR

        this.itemStack = new ItemStack(Material.FIREWORK_CHARGE);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&bPoradnik"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fKliknij aby wyświetlić poradnik na czacie."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 16, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(16, itemStack);
        this.itemLore.clear();

        return this.gui;
    }
//===========================================================================================================================
    public Inventory pomocGUIREGULAMINTARYFIKATOR(final Player player) {
        this.gui = Bukkit.createInventory(null, 9, Utils.format("&4Regulamin &f& &cTaryfikator."));

        for (int i = 0; i < gui.getSize(); i++) {
            this.itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            this.itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(" ");

            this.itemStack.setItemMeta(itemMeta);
            gui.setItem(i, itemStack);
        }

        // REGULAMIN
        this.itemStack = new ItemStack(Material.BOOK_AND_QUILL);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&4Regulamin"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fKliknij aby zobaczyć regulamin serwerowy."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 2, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(2, itemStack);
        this.itemLore.clear();

        // TARYFIKATOR
        this.itemStack = new ItemStack(Material.PAPER);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&cTaryfikator"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fKliknij aby zobaczyć taryfikator serwerowy."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 6, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(6, itemStack);
        this.itemLore.clear();

        return this.gui;
    }
//===========================================================================================================================
    public Inventory pomocGUIPODSTAWOWEKOMENDY(final Player player) {
        this.gui = Bukkit.createInventory(null, 4*9, Utils.format("&aPodstawowe komendy."));
        for (int i = 0; i < gui.getSize(); i++) {
            this.itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            this.itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(" ");

            this.itemStack.setItemMeta(itemMeta);
            gui.setItem(i, itemStack);
        }

        // KOMENDA 1
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/listanpc"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fSpis wszystkich npc na spawnie."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(10, itemStack);
        this.itemLore.clear();

        // KOMENDA 2
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/itemshop"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fPrzenośny itemshop serwerowy."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 11, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(11, itemStack);
        this.itemLore.clear();

        // KOMENDA 3
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/money"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fStan twojego konta."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 12, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(12, itemStack);
        this.itemLore.clear();

        // KOMENDA 4
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/wyplac <kwota>"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fWypłacanie danej kwoty z konta."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 13, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(13, itemStack);
        this.itemLore.clear();

        // KOMENDA 5
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/targ"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fTarg serwerowy."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 14, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(14, itemStack);
        this.itemLore.clear();

        // KOMENDA 6
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/wystaw <kwota>"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fWystawianie na targ itemu z łapki za daną cene."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 15, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(15, itemStack);
        this.itemLore.clear();

        // KOMENDA 7
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/eventy"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fSpis aktualnych eventów."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 16, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(16, itemStack);
        this.itemLore.clear();

        // KOMENDA 8
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/level"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fTwój aktualny level."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 19, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(19, itemStack);
        this.itemLore.clear();

        // KOMENDA 9
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/level <gracz>"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fAktualny level gracza."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 20, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(20, itemStack);
        this.itemLore.clear();

        // KOMENDA 10
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/misje"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fTwoje odebrane misje."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 21, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(21, itemStack);
        this.itemLore.clear();

        // KOMENDA 11
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6[eq]"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fMenu pokazywania rzeczy na czacie."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 22, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(22, itemStack);
        this.itemLore.clear();

        // KOMENDA 12
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/rangi"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fOpis rang w menu."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 23, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(23, itemStack);
        this.itemLore.clear();

        // KOMENDA 13
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/konto"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fOpis twojego konta."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 24, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(24, itemStack);
        this.itemLore.clear();

        // KOMENDA 14
        this.itemStack = new ItemStack(Material.ITEM_FRAME);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&8* &6/administracja"));
        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&fSpis administracji."));

        itemMeta.addEnchant(Enchantment.DURABILITY, 25, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(itemLore);
        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(25, itemStack);
        this.itemLore.clear();
        return this.gui;
    }
//===========================================================================================================================
/*public Inventory pomocGUIREGULAMIN(final Player player) {
    this.gui = Bukkit.createInventory(null, 3*9, Utils.format("&4Regulamin."));
    for (int i = 0; i < gui.getSize(); i++) {
        this.itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        this.itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(" ");

        this.itemStack.setItemMeta(itemMeta);
        gui.setItem(i, itemStack);
    }

    return this.gui;
    }
 */
}
