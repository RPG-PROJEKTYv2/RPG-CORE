package rpg.rpgcore.npc.teleporter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
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
    private final ItemBuilder expowisko1 = new ItemBuilder(Material.IRON_FENCE);
    private final ItemBuilder expowisko2 = new ItemBuilder(Material.RED_MUSHROOM);
    private final ItemBuilder expowisko3 = new ItemBuilder(Material.IRON_BLOCK);
    private final ItemBuilder expowisko4 = new ItemBuilder(Material.SEA_LANTERN);
    private final ItemBuilder expowisko5 = new ItemBuilder(Material.NETHER_BRICK);
    private final ItemBuilder expowisko6 = new ItemBuilder(Material.ICE);
    private final ItemBuilder expowisko7 = new ItemBuilder(Material.MONSTER_EGG, 1, (short)54);
    private final ItemBuilder expowisko8 = new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.SKELETON.ordinal());
    private final ItemBuilder expowisko9 = new ItemBuilder(Material.BLAZE_POWDER);
    private final ItemBuilder expowisko10 = new ItemBuilder(Material.WATER_BUCKET);
    private final ItemBuilder expowisko11 = new ItemBuilder(Material.POTION, 1, (short)16388).addFlag(ItemFlag.HIDE_POTION_EFFECTS);
    private final ItemBuilder expowisko12 = new ItemBuilder(Material.GRASS);
    private final ItemBuilder brakdostepu = new ItemBuilder(Material.BARRIER);

    public Inventory teleporterMAIN(Player player) {
        Inventory gui = Bukkit.createInventory(null, 2 * 9, Utils.format("&9&lTELEPORTER"));

        fillInventory.setName(" ");
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }

        // expowisko 1
        expowisko1.setName("&eGrota Wygnanców");
        this.loreEXPOWISKA("&cOFF", "&f1", "4");
        expowisko1.addGlowing();
        expowisko1.setLore(itemLore);
        gui.setItem(0, expowisko1.toItemStack());

        // expowisko 2
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 9) {
            expowisko2.setName("&cCzerwony Las");
            this.loreEXPOWISKA("&cOFF", "&f10", "4");
            expowisko2.addGlowing();
            expowisko2.setLore(itemLore);
            gui.setItem(1, expowisko2.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a2. &8]");
            this.loreBRAKDOSTEPU("10");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(1, brakdostepu.toItemStack());
        }

        // expowisko 3
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 19) {
            expowisko3.setName("&7Goryla Wyspa");
            this.loreEXPOWISKA("&cOFF", "&f20", "5");
            expowisko3.addGlowing();
            expowisko3.setLore(itemLore);
            gui.setItem(2, expowisko3.toItemStack());
        }  else {
            brakdostepu.setName("&2Expowisko &8[ &a3. &8]");
            this.loreBRAKDOSTEPU("20");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(2, brakdostepu.toItemStack());
        }

        // expowisko 4
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 29) {
            expowisko4.setName("&3Opuszczony Port");
            this.loreEXPOWISKA("&cOFF", "&f30", "5");
            expowisko4.addGlowing();
            expowisko4.setLore(itemLore);
            gui.setItem(3, expowisko4.toItemStack());
        }   else {
            brakdostepu.setName("&2Expowisko &8[ &a4. &8]");
            this.loreBRAKDOSTEPU("30");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(3, brakdostepu.toItemStack());
        }

        // expowisko 5
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 39) {
            expowisko5.setName("&5Zatopiona Twierdza");
            this.loreEXPOWISKA("&cOFF", "&f40", "5");
            expowisko5.addGlowing();
            expowisko5.setLore(itemLore);
            gui.setItem(4, expowisko5.toItemStack());
        }  else {
            brakdostepu.setName("&2Expowisko &8[ &a5. &8]");
            this.loreBRAKDOSTEPU("40");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(4, brakdostepu.toItemStack());
        }

        // expowisko 6
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 49) {
            expowisko6.setName("&bLodowa Kraina");
            this.loreEXPOWISKA("&cOFF", "&f50", "5");
            expowisko6.addGlowing();
            expowisko6.setLore(itemLore);
            gui.setItem(5, expowisko6.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a6. &8]");
            this.loreBRAKDOSTEPU("50");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(5, brakdostepu.toItemStack());
        }

        // expowisko 7
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 59) {
            expowisko7.setName("&6Góra Olbrzymów");
            this.loreEXPOWISKA("&aON", "&f60", "6");
            expowisko7.addGlowing();
            expowisko7.setLore(itemLore);
            gui.setItem(6, expowisko7.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a7. &8]");
            this.loreBRAKDOSTEPU("60");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(6, brakdostepu.toItemStack());
        }

        // expowisko 8
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 69) {
            expowisko8.setName("&fKrólestwo Duchów");
            this.loreEXPOWISKA("&aON", "&f70", "6");
            expowisko8.addGlowing();
            expowisko8.setLore(itemLore);
            gui.setItem(7, expowisko8.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a8. &8]");
            this.loreBRAKDOSTEPU("70");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(7, brakdostepu.toItemStack());
        }

        // expowisko 9
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 79) {
            expowisko9.setName("&4Płomienny Las");
            this.loreEXPOWISKA("&aON", "&f80", "6");
            expowisko9.addGlowing();
            expowisko9.setLore(itemLore);
            gui.setItem(8, expowisko9.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a9. &8]");
            this.loreBRAKDOSTEPU("80");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(8, brakdostepu.toItemStack());
        }

        // expowisko 10
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 89) {
            expowisko10.setName("&3Atlantyda");
            this.loreEXPOWISKA("&aON", "&f90", "6");
            expowisko10.addGlowing();
            expowisko10.setLore(itemLore);
            gui.setItem(9, expowisko10.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a10. &8]");
            this.loreBRAKDOSTEPU("90");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(9, brakdostepu.toItemStack());
        }

        // expowisko 11
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 99) {
            expowisko11.setName("&8Przeklęta Jaskinia");
            this.loreEXPOWISKA("&aON", "&f100", "6");
            expowisko11.addGlowing();
            expowisko11.setLore(itemLore);
            gui.setItem(10, expowisko11.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a11. &8]");
            this.loreBRAKDOSTEPU("100");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(10, brakdostepu.toItemStack());
        }
        // expowisko 12
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) > 109) {
            expowisko12.setName("&aPolana");
            this.loreEXPOWISKA("&aON", "&f110", "6");
            expowisko12.addGlowing();
            expowisko12.setLore(itemLore);
            gui.setItem(11, expowisko12.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a12. &8]");
            this.loreBRAKDOSTEPU("110");
            brakdostepu.addGlowing();
            brakdostepu.setLore(itemLore);
            gui.setItem(11, brakdostepu.toItemStack());
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
