package rpg.rpgcore.managers.npc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DuszologNPC {
    private Inventory gui;
    private final ItemBuilder fillInventory = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ArrayList<String> itemLore = new ArrayList<>();
    private final ItemBuilder craftowanie = new ItemBuilder(Material.BEACON);
    private final ItemBuilder potrzebneitemy = new ItemBuilder(Material.MONSTER_EGG);
    private final ItemBuilder spiswszystkiego = new ItemBuilder(Material.EXP_BOTTLE);


    private ItemBuilder testDUSZA1 = new ItemBuilder(Material.PRISMARINE_CRYSTALS, 1);
    private ItemBuilder testDUSZA2 = new ItemBuilder(Material.INK_SACK, 1, (short) 1);
    private ItemBuilder testDUSZA3 = new ItemBuilder(Material.BLAZE_POWDER, 1);
    private ItemBuilder testDUSZA4 = new ItemBuilder(Material.IRON_INGOT, 1);
    private ItemBuilder testDUSZA5 = new ItemBuilder(Material.NETHER_STAR, 1);

    public void craftowanieDUSZ(final Player player) {
        if (player.getInventory().contains(Material.STONE, 1) || player.getInventory().contains(Material.COBBLESTONE, 1)) {
            player.sendMessage("test");
        } else {
            player.sendMessage("brak stone i cobbla");
        }
    }

    public Inventory duszologMAIN() {
        this.gui = Bukkit.createInventory(null, 3 * 9, Utils.format("&a&lDuszolog"));

        fillInventory.setName(" ");
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }
        this.itemLore.clear();

        // craftowanie

        craftowanie.setName("&bCraftowanie: &f&lDusza &cx1");
        this.itemLore.add(" ");
        this.itemLore.add("&8>> &eKliknij aby stworzyc losowa &f&ldusze&e!");
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eSzanse na dusze:");
        this.itemLore.add("&8* &bOdbicia: &c10%");
        this.itemLore.add("&8* &cZycia: &c10%");
        this.itemLore.add("&8* &4Obrazen: &c10%");
        this.itemLore.add("&8* &aObrony: &c10%");
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eSzanse na inne:");
        this.itemLore.add("&8* &cPustka: &c40%");
        this.itemLore.add("&8* &fZwrot wiekszosci itemow: &c10%");
        this.itemLore.add(" ");

        craftowanie.addGlowing();
        craftowanie.setLore(itemLore);
        gui.setItem(10, craftowanie.toItemStack());
        this.itemLore.clear();

        // potrzebne itemy

        potrzebneitemy.setName("&aPotrzebne przedmioty");
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eLista:");
        this.itemLore.add("&8>> &f1. &6Stone &cx64&8.");
        this.itemLore.add("&8>> &f1. &6Skrzynka &cx32&8.");
        this.itemLore.add(" ");
        potrzebneitemy.addGlowing();
        potrzebneitemy.setLore(itemLore);
        gui.setItem(11, potrzebneitemy.toItemStack());
        this.itemLore.clear();

        // spis wszystkiego

        spiswszystkiego.setName("&cOpis &fDusz");
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eWszystkie dusze:");
        this.itemLore.add("&8>> &f&lDusza &bOdbicia: &c3%");
        this.itemLore.add("&8>> &f&lDusza &cZycia: &c+15");
        this.itemLore.add("&8>> &f&lDusza &4Obrazen: &c8%");
        this.itemLore.add("&8>> &f&lDusza &aObrony: &c8%");
        this.itemLore.add("&8>> &f&lDusza &5Dropu: &c0.0001% &8|| &4&lARTEFAKT");
        this.itemLore.add(" ");
        spiswszystkiego.addGlowing();
        spiswszystkiego.setLore(itemLore);
        gui.setItem(16, spiswszystkiego.toItemStack());
        this.itemLore.clear();

        return this.gui;
    }

    public void dajTestDusze(final Player player) {
        List<String> testlore = new ArrayList<>();

        testDUSZA1.setName("&8* &f&lDusza &bOdbicia &8*");
        testlore.add(" ");
        testlore.add("&8>> &eBonus odbicia ciosu: &c3%");
        testlore.add(" ");
        testDUSZA1.addGlowing();
        testDUSZA1.setLore(testlore);

        testlore.clear();
        testDUSZA2.setName("&8* &f&lDusza &cZycia &8*");
        testlore.add(" ");
        testlore.add("&8>> &eBonus dodatkowego zdrowia: &c+15");
        testlore.add(" ");
        testDUSZA2.addGlowing();
        testDUSZA2.setLore(testlore);

        testlore.clear();
        testDUSZA3.setName("&8* &f&lDusza &4Obrazen &8*");
        testlore.add(" ");
        testlore.add("&8>> &eBonus dodatkowych obrazen: &c8%");
        testlore.add(" ");
        testDUSZA3.addGlowing();
        testDUSZA3.setLore(testlore);

        testlore.clear();
        testDUSZA4.setName("&8* &f&lDusza &aObrony &8*");
        testlore.add(" ");
        testlore.add("&8>> &eBonus dodatkowej obrony: &c8%");
        testlore.add(" ");
        testDUSZA4.addGlowing();
        testDUSZA4.setLore(testlore);

        testlore.clear();
        testDUSZA5.setName("&8* &f&lDusza &5Dropu &8*");
        testlore.add(" ");
        testlore.add("&8>> &eBonus dodatkowego dropu '&e&lSEJF Z BONAMI&e'");
        testlore.add("&8>> &eSzansa: &c0.0001%!");
        testlore.add("&8>> &4&lARTEFAKT");
        testlore.add(" ");
        testDUSZA5.addGlowing();
        testDUSZA5.setLore(testlore);

        player.getInventory().addItem(testDUSZA1.toItemStack(), testDUSZA2.toItemStack(), testDUSZA3.toItemStack(), testDUSZA4.toItemStack(), testDUSZA5.toItemStack());
    }

}
