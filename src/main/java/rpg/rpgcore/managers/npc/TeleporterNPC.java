package rpg.rpgcore.managers.npc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;

public class TeleporterNPC {

    private Inventory gui;

    private final ItemBuilder fillInventory = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ArrayList<String> itemLore = new ArrayList<>();
    private final ItemBuilder expowisko1 = new ItemBuilder(Material.GRASS);
    private final ItemBuilder expowisko2 = new ItemBuilder(Material.STONE);
    private final ItemBuilder expowisko3 = new ItemBuilder(Material.WOOD_PICKAXE);
    private final ItemBuilder expowisko4 = new ItemBuilder(Material.SPONGE);
    private final ItemBuilder expowisko5 = new ItemBuilder(Material.SEA_LANTERN);
    private final ItemBuilder expowisko6 = new ItemBuilder(Material.HAY_BLOCK);
    private final ItemBuilder expowisko7 = new ItemBuilder(Material.COAL_ORE);

    public Inventory teleporterMAIN() {
        this.gui = Bukkit.createInventory(null, 5 * 9, Utils.format("&9&lTELEPORTER"));

        fillInventory.setName(" ");
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }

        // expowisko 1
        expowisko1.setName("&2Expowisko &8[ &a1. &8]");
        this.loreEXPOWISKA("&cOFF", "&f1", "4", "&cNAZWA 1");
        expowisko1.addGlowing();
        expowisko1.setLore(itemLore);
        gui.setItem(10, expowisko1.toItemStack());

        // expowisko 2
        expowisko2.setName("&2Expowisko &8[ &a2. &8]");
        this.loreEXPOWISKA("&cOFF", "&f10", "4", "&cNAZWA 2");
        expowisko2.addGlowing();
        expowisko2.setLore(itemLore);
        gui.setItem(11, expowisko2.toItemStack());

        // expowisko 3
        expowisko3.setName("&2Expowisko &8[ &a3. &8]");
        this.loreEXPOWISKA("&cOFF", "&f20", "5", "&cNAZWA 3");
        expowisko3.addGlowing();
        expowisko3.setLore(itemLore);
        gui.setItem(12, expowisko3.toItemStack());

        // expowisko 4
        expowisko4.setName("&2Expowisko &8[ &a4. &8]");
        this.loreEXPOWISKA("&cOFF", "&f30", "5", "&cNAZWA 4");
        expowisko4.addGlowing();
        expowisko4.setLore(itemLore);
        gui.setItem(13, expowisko4.toItemStack());

        // expowisko 5
        expowisko5.setName("&2Expowisko &8[ &a5. &8]");
        this.loreEXPOWISKA("&cOFF", "&f40", "5", "&cNAZWA 5");
        expowisko5.addGlowing();
        expowisko5.setLore(itemLore);
        gui.setItem(14, expowisko5.toItemStack());

        // expowisko 6
        expowisko6.setName("&2Expowisko &8[ &a6. &8]");
        this.loreEXPOWISKA("&cOFF", "&f50", "5", "&cNAZWA 6");
        expowisko6.addGlowing();
        expowisko6.setLore(itemLore);
        gui.setItem(15, expowisko6.toItemStack());

        // expowisko 7
        expowisko7.setName("&2Expowisko &8[ &a7. &8]");
        this.loreEXPOWISKA("&aON", "&f60", "6", "&cNAZWA 7");
        expowisko7.addGlowing();
        expowisko7.setLore(itemLore);
        gui.setItem(16, expowisko7.toItemStack());

        return this.gui;
    }

    private void loreEXPOWISKA(final String lorePVP, final String lorePOZIOM, final String loreLiczbaTP, final String loreNAZWA) {
        this.itemLore.clear();
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eInformacje:");
        this.itemLore.add("&8* &fNazwa: " + loreNAZWA);
        this.itemLore.add("&8* &4PvP: " + lorePVP );
        this.itemLore.add("&8* &bWymagany poziom: &f" + lorePOZIOM);
        this.itemLore.add("&8* &3Liczba teleportow: &9" + loreLiczbaTP);
        this.itemLore.add(" ");
    }
}
