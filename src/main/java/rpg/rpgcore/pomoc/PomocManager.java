package rpg.rpgcore.pomoc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;

public class PomocManager {
    private Inventory gui;
    private final ItemBuilder fillInventory = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ItemBuilder regulaminItaryfikator = new ItemBuilder(Material.BOOK);
    private final ItemBuilder regulamin = new ItemBuilder(Material.BOOK_AND_QUILL);
    private final ItemBuilder taryfikator = new ItemBuilder(Material.PAPER);
    private final ItemBuilder poradnik = new ItemBuilder(Material.FIREWORK_CHARGE);
    private final ItemBuilder komendy = new ItemBuilder(Material.INK_SACK, 1, (short) 1);
    private final ItemBuilder komendyList = new ItemBuilder(Material.ITEM_FRAME);
    private final ArrayList<String> itemLore = new ArrayList<>();

//===========================================================================================================================
    public Inventory pomocGUIMAIN() {
        this.gui = Bukkit.createInventory(null, 3 * 9, Utils.format("&e&lPomoc serwerowa."));

        fillInventory.setName(" ");
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }
        this.itemLore.clear();

        // REGULAMIN I TARYFIKATOR

        regulaminItaryfikator.setName("&4Regulamin &f& &cTaryfikator");

        this.itemLore.add(" ");
        this.itemLore.add("&fKliknij aby zobaczyć regulamin serwerowy.");

        regulaminItaryfikator.addGlowing();


        regulaminItaryfikator.setLore(itemLore);
        gui.setItem(10, regulaminItaryfikator.toItemStack());
        this.itemLore.clear();

        // KOMENDY SERWEROWE


        komendy.setName("&aKomendy serwerowe");

        this.itemLore.add(" ");
        this.itemLore.add("&fKliknij aby zobaczyć spis komend.");

        komendy.addGlowing();


        komendy.setLore(itemLore);
        gui.setItem(13, komendy.toItemStack());
        this.itemLore.clear();

        // PORADNIK

        poradnik.setName("&bPoradnik");

        this.itemLore.add(" ");
        this.itemLore.add("&fKliknij aby wyświetlić poradnik.");

        poradnik.addGlowing();


        poradnik.setLore(itemLore);
        gui.setItem(16, poradnik.toItemStack());
        this.itemLore.clear();

        return this.gui;
    }
//===========================================================================================================================
    public Inventory pomocGUIREGULAMINTARYFIKATOR() {
        this.gui = Bukkit.createInventory(null, 9, Utils.format("&4Regulamin &f& &cTaryfikator."));

        fillInventory.setName(" ");

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }

        // REGULAMIN
        regulamin.setName("&4Regulamin");

        this.itemLore.add(" ");
        this.itemLore.add("&fKliknij aby otrzymać link do regulaminu.");

        regulamin.addGlowing();


        regulamin.setLore(itemLore);
        gui.setItem(2, regulamin.toItemStack());
        this.itemLore.clear();

        // TARYFIKATOR
        taryfikator.setName("&cTaryfikator");

        this.itemLore.add(" ");
        this.itemLore.add("&fKliknij aby otrzymać link do taryfikatora.");

        taryfikator.addGlowing();


        taryfikator.setLore(itemLore);
        gui.setItem(6, taryfikator.toItemStack());
        this.itemLore.clear();

        return this.gui;
    }
//===========================================================================================================================
    public Inventory pomocGUIPODSTAWOWEKOMENDY() {
        this.gui = Bukkit.createInventory(null, 4*9, Utils.format("&aPodstawowe komendy."));

        fillInventory.setName(" ");

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }

        komendyList.addGlowing();

        // KOMENDA 1
        komendyList.setName("&8* &6/listanpc");
        this.itemLore.add(" ");
        this.itemLore.add("&fSpis wszystkich npc na spawnie.");


        komendyList.setLore(itemLore);

        gui.setItem(10, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 2
        komendyList.setName("&8* &6/itemshop");
        this.itemLore.add(" ");
        this.itemLore.add("&fPrzenośny itemshop serwerowy.");


        komendyList.setLore(itemLore);

        gui.setItem(11, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 3
        komendyList.setName("&8* &6/money");
        this.itemLore.add(" ");
        this.itemLore.add("&fStan twojego konta.");


        komendyList.setLore(itemLore);

        gui.setItem(12, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 4
        komendyList.setName("&8* &6/wyplac <kwota>");
        this.itemLore.add(" ");
        this.itemLore.add("&fWypłacanie danej kwoty z konta.");


        komendyList.setLore(itemLore);

        gui.setItem(13, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 5
        komendyList.setName("&8* &6/targ");
        this.itemLore.add(" ");
        this.itemLore.add("&fTarg serwerowy.");


        komendyList.setLore(itemLore);

        gui.setItem(14, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 6
        komendyList.setName("&8* &6/wystaw <kwota>");
        this.itemLore.add(" ");
        this.itemLore.add("&fWystawianie na targ itemu z łapki za daną cene.");

        komendyList.setLore(itemLore);

        gui.setItem(15, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 7
        komendyList.setName("&8* &6/eventy");
        this.itemLore.add(" ");
        this.itemLore.add("&fSpis aktualnych eventów.");


        komendyList.setLore(itemLore);

        gui.setItem(16, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 8
        komendyList.setName("&8* &6/level");
        this.itemLore.add(" ");
        this.itemLore.add("&fTwój aktualny level.");


        komendyList.setLore(itemLore);

        gui.setItem(19, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 9
        komendyList.setName("&8* &6/level <gracz>");
        this.itemLore.add(" ");
        this.itemLore.add("&fAktualny level gracza.");


        komendyList.setLore(itemLore);

        gui.setItem(20, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 10
        komendyList.setName("&8* &6/misje");
        this.itemLore.add(" ");
        this.itemLore.add("&fTwoje odebrane misje.");


        komendyList.setLore(itemLore);

        gui.setItem(21, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 11
        komendyList.setName("&8* &6[eq]");
        this.itemLore.add(" ");
        this.itemLore.add("&fMenu pokazywania rzeczy na czacie.");


        komendyList.setLore(itemLore);

        gui.setItem(22, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 12
        komendyList.setName("&8* &6/rangi");
        this.itemLore.add(" ");
        this.itemLore.add("&fOpis rang w menu.");


        komendyList.setLore(itemLore);

        gui.setItem(23, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 13
        komendyList.setName("&8* &6/konto");
        this.itemLore.add(" ");
        this.itemLore.add("&fOpis twojego konta.");


        komendyList.setLore(itemLore);

        gui.setItem(24, komendyList.toItemStack());
        this.itemLore.clear();

        // KOMENDA 14
        komendyList.setName("&8* &6/administracja");
        this.itemLore.add(" ");
        this.itemLore.add("&fSpis administracji.");


        komendyList.setLore(itemLore);

        gui.setItem(25, komendyList.toItemStack());
        this.itemLore.clear();
        return this.gui;
    }
}
