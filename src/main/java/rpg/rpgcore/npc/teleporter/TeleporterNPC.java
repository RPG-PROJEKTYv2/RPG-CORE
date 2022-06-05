package rpg.rpgcore.npc.teleporter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;

public class TeleporterNPC {

    private final RPGCORE rpgcore;

    public TeleporterNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final ItemBuilder fillInventory = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ArrayList<String> itemLore = new ArrayList<>();
    private final ItemBuilder expowisko1 = new ItemBuilder(Material.GRASS);
    private final ItemBuilder expowisko2 = new ItemBuilder(Material.STONE);
    private final ItemBuilder expowisko3 = new ItemBuilder(Material.WOOD_PICKAXE);
    private final ItemBuilder expowisko4 = new ItemBuilder(Material.SPONGE);
    private final ItemBuilder expowisko5 = new ItemBuilder(Material.SEA_LANTERN);
    private final ItemBuilder expowisko6 = new ItemBuilder(Material.HAY_BLOCK);
    private final ItemBuilder expowisko7 = new ItemBuilder(Material.COAL_ORE);
    private final ItemBuilder brakdostepu = new ItemBuilder(Material.BARRIER);

    public Inventory teleporterMAIN(Player player) {
        Inventory gui = Bukkit.createInventory(null, 4 * 9, Utils.format("&9&lTELEPORTER"));

        fillInventory.setName(" ");
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }

        // expowisko 1
        expowisko1.setName("&4MAPA PIERWSZA");
        this.loreEXPOWISKA("&cOFF", "&f1", "4");
        expowisko1.addGlowing();
        expowisko1.setLore(itemLore);
        gui.setItem(10, expowisko1.toItemStack());

        // expowisko 2
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 9) {
            expowisko2.setName("&4MAPA DRUGA");
            this.loreEXPOWISKA("&cOFF", "&f10", "4");
            expowisko2.addGlowing();
            expowisko2.setLore(itemLore);
            gui.setItem(11, expowisko2.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a2. &8]");
            this.loreBRAKDOSTEPU("10");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(11, brakdostepu.toItemStack());
        }

        // expowisko 3
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 19) {
            expowisko3.setName("&4MAPA TRZECIA");
            this.loreEXPOWISKA("&cOFF", "&f20", "5");
            expowisko3.addGlowing();
            expowisko3.setLore(itemLore);
            gui.setItem(12, expowisko3.toItemStack());
        }  else {
            brakdostepu.setName("&2Expowisko &8[ &a3. &8]");
            this.loreBRAKDOSTEPU("20");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(12, brakdostepu.toItemStack());
        }

        // expowisko 4
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 29) {
            expowisko4.setName("&4MAPA CZWARTA");
            this.loreEXPOWISKA("&cOFF", "&f30", "5");
            expowisko4.addGlowing();
            expowisko4.setLore(itemLore);
            gui.setItem(13, expowisko4.toItemStack());
        }   else {
            brakdostepu.setName("&2Expowisko &8[ &a4. &8]");
            this.loreBRAKDOSTEPU("30");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(13, brakdostepu.toItemStack());
        }

        // expowisko 5
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 39) {
            expowisko5.setName("&4MAPA PIATA");
            this.loreEXPOWISKA("&cOFF", "&f40", "5");
            expowisko5.addGlowing();
            expowisko5.setLore(itemLore);
            gui.setItem(14, expowisko5.toItemStack());
        }  else {
            brakdostepu.setName("&2Expowisko &8[ &a5. &8]");
            this.loreBRAKDOSTEPU("40");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(14, brakdostepu.toItemStack());
        }

        // expowisko 6
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 49) {
            expowisko6.setName("&4MAPA SZOSTA");
            this.loreEXPOWISKA("&cOFF", "&f50", "5");
            expowisko6.addGlowing();
            expowisko6.setLore(itemLore);
            gui.setItem(15, expowisko6.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a6. &8]");
            this.loreBRAKDOSTEPU("50");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(15, brakdostepu.toItemStack());
        }

        // expowisko 7
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 59) {
            expowisko7.setName("&4MAPA SIODMA");
            this.loreEXPOWISKA("&aON", "&f60", "6");
            expowisko7.addGlowing();
            expowisko7.setLore(itemLore);
            gui.setItem(16, expowisko7.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a7. &8]");
            this.loreBRAKDOSTEPU("60");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(16, brakdostepu.toItemStack());
        }

        return gui;
    }

    private void loreEXPOWISKA(final String lorePVP, final String lorePOZIOM, final String loreLiczbaTP) {
        this.itemLore.clear();
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eInformacje:");
        this.itemLore.add("&8* &bWymagany poziom: &f" + lorePOZIOM);
        this.itemLore.add("&8* &4PvP: " + lorePVP );
        this.itemLore.add("&8* &3Liczba teleportow: &9" + loreLiczbaTP);
        this.itemLore.add(" ");
        this.itemLore.add("&8* &9Status: &a&lODBLOKOWANE");
        this.itemLore.add(" ");
    }
    private void loreBRAKDOSTEPU(final String lorePOZIOM) {
        this.itemLore.clear();
        this.itemLore.add(" ");
        this.itemLore.add("&8* &9Status: &4&lZABLOKOWANE");
        this.itemLore.add("&8* &bWymagany poziom: &f" + lorePOZIOM);
        this.itemLore.add(" ");
    }
}
