package rpg.rpgcore.commands.admin.adminpanel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.ItemShop;
import rpg.rpgcore.utils.globalitems.NiesyItems;
import rpg.rpgcore.utils.globalitems.expowiska.Skrzynki;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

import java.util.Arrays;

public class AdminPanelManager {

    public void openAdminPanelGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 36, Utils.format("&4&lPanel Administracyjny"));
        gui.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(33, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(35, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());

        gui.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());

        gui.setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(15, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(17, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());

        gui.setItem(18, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(24, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(26, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());

        gui.setItem(28, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(29, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(30, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(31, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(32, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
        gui.setItem(34, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());

        gui.setItem(10, new ItemBuilder(Material.CHEST).setName("&cSkrzynki - &fMob/Boss/Dungeony/Inne").addGlowing().toItemStack().clone());
        gui.setItem(11, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&bNiesamowite Przedmioty").addGlowing().toItemStack().clone());
        gui.setItem(12, new ItemBuilder(Material.SIGN).setName("&cBony").addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.GOLD_INGOT).setName("&6Itemshop").addGlowing().toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.SNOW_BALL).setName("&fUlepszacze").addGlowing().toItemStack().clone());
        gui.setItem(16, new ItemBuilder(Material.FIREBALL).setName("&b&lKomendy Administracyjne").setLore(Arrays.asList("",
                "&8* &f/level <gracz> setlvl <wartosc>",
                "&8* &f/rozdaj <all/jeden>"
        )).addGlowing().toItemStack().clone());
        gui.setItem(19, new ItemBuilder(Material.STONE_PICKAXE).setName("&6Itemy Gornika").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }

    public void openAllCaseGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&2&lSkrzynki &f- ADMINISTRACJA"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName("").toItemStack());
            }
        }

        gui.setItem(12, new ItemBuilder(Material.CHEST).setName("&c&lSkrzynki &f- MOBY").addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.ENDER_CHEST).setName("&c&lSkrzynki &f- BOSSY").addGlowing().toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.FURNACE).setName("&c&lSkrzynki &f- DUNGEONY").addGlowing().toItemStack().clone());
        gui.setItem(22, new ItemBuilder(Material.DIAMOND).setName("&c&lSkrzynki &f- INNE").addGlowing().toItemStack().clone());

        gui.setItem(26, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());

        player.openInventory(gui);
    }
    public void openMobyCaseGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&2&lSkrzynki &4&lMOBY &f- ADMINISTRACJA"));
        gui.setItem(0, Skrzynki.getItem("I2",1));
        gui.setItem(1, Skrzynki.getItem("I4",1));
        gui.setItem(2, Skrzynki.getItem("I6",1));
        gui.setItem(3, Skrzynki.getItem("I8",1));
        gui.setItem(4, Skrzynki.getItem("I10",1));
        gui.setItem(26, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openBossyCaseGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&2&lSkrzynki &4&lBOSSY &f- ADMINISTRACJA"));
        gui.setItem(0, Skrzynki.getItem("I1",1));
        gui.setItem(1, Skrzynki.getItem("I3",1));
        gui.setItem(2, Skrzynki.getItem("I5",1));
        gui.setItem(3, Skrzynki.getItem("I7",1));
        gui.setItem(4, Skrzynki.getItem("I9",1));
        gui.setItem(26, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openDungeonyCaseGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&2&lSkrzynki &4&lDUNGEONY &f- ADMINISTRACJA"));
        gui.setItem(0, new ItemBuilder(Material.CHEST).setName("&c&lSKRZYNIA DUNGEONU PIERWSZEGO").addGlowing().toItemStack().clone());
        gui.setItem(26, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openInneCaseGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&2&lSkrzynki &4&lINNE &f- ADMINISTRACJA"));
        gui.setItem(0, GlobalItem.getItem("I1",1));
        gui.setItem(1, GlobalItem.getItem("I2",1));
        gui.setItem(2, GlobalItem.getItem("I3",1));
        gui.setItem(3, GlobalItem.getItem("I4",1));
        gui.setItem(4, GlobalItem.getItem("I5",1));
        gui.setItem(5, GlobalItem.getItem("I6",1));
        gui.setItem(6, GlobalItem.getItem("I22",1));
        gui.setItem(7, GlobalItem.getItem("I21",1));

        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }

    public void openUlepszaczeGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&f&lUlepszacze &f- ADMINISTRACJA"));

        gui.setItem(0, GlobalItem.getItem("I_SZATANAJEMNIKA", 1));
        gui.setItem(1, GlobalItem.getItem("I_OKOGOBLINA", 1));
        gui.setItem(2, GlobalItem.getItem("I_SKORAGORYLA", 1));
        gui.setItem(3, GlobalItem.getItem("I_PROCHYZJAWY", 1));
        gui.setItem(4, GlobalItem.getItem("I_LZAOCEANU", 1));
        gui.setItem(5, GlobalItem.getItem("I_MROZNYPAZUR", 1));
        gui.setItem(6, GlobalItem.getItem("I_OGNISTYPYL", 1));
        gui.setItem(7, GlobalItem.getItem("I_TRUJACAROSLINA", 1));
        gui.setItem(8, GlobalItem.getItem("I_JADPTASZNIKA", 1));
        gui.setItem(9, GlobalItem.getItem("I_MROCZNYMATERIAL", 1));
        gui.setItem(10, GlobalItem.getItem("I_SZAFIROWESERCE", 1));
        gui.setItem(11, GlobalItem.getItem("I_SERCEDEMONA", 1));
        gui.setItem(12, GlobalItem.getItem("I_NIEBIANSKIMATERIAL", 1));

        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
        player.openInventory(gui);
    }


    public void openBonyGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&c&lBony &f- ADMINISTRACJA"));
        for (final BonType bonType : BonType.values()) {
            gui.setItem(gui.firstEmpty(), bonType.getBon());
        }
        gui.setItem(26, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }


    public void openItemShopGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 45, Utils.format("&6&lItemShop &f- ADMINISTRACJA"));
        gui.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(18, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(22, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(24, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(26, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(29, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(31, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(33, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(35, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(45, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(47, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(49, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(51, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(53, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());

        gui.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(11, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(15, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(17, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(36, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(38, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(40, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(42, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(44, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(46, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(48, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(50, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(52, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());

        gui.setItem(10, ItemShop.IS18.getItems().get(0));
        gui.setItem(19, ItemShop.IS17.getItems().get(0));
        gui.setItem(28, ItemShop.IS16.getItems().get(0));
        gui.setItem(37, ItemShop.IS15.getItems().get(0));

        gui.setItem(12, ItemShop.IS14.getItems().get(0));
        gui.setItem(21, ItemShop.IS13.getItems().get(0));
        gui.setItem(30, ItemShop.IS12.getItems().get(0));
        gui.setItem(39, ItemShop.IS19.getItems().get(0));

        gui.setItem(14, ItemShop.IS11.getItems().get(0));
        gui.setItem(23, ItemShop.IS10.getItems().get(0));
        gui.setItem(32, ItemShop.IS9.getItems().get(0));
        gui.setItem(41, ItemShop.IS20.getItems().get(0));

        gui.setItem(16, GlobalItem.getItem("I52", 1));
        gui.setItem(25, GlobalItem.getItem("I53", 1));
        gui.setItem(34, GlobalItem.getItem("I54", 1));
        gui.setItem(43, GlobalItem.getItem("I55", 1));

        
        gui.setItem(35, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
        player.openInventory(gui);
    }


    public void openNiesamowiteGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&b&lNiesamowite &f- ADMINISTRACJA"));
        for (final NiesyItems niesyItems : NiesyItems.values()) {
            gui.setItem(gui.firstEmpty(), niesyItems.getNiesy());
        }
        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }

    public void openGornikItemsGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&6&lGORNIK &f- &4&lADMINISTRACJA"));
        for (final GornikItems item : GornikItems.values()) {
            if (item.getName().equals("KILOF") || item.getName().equals("ZMIOTKA_T0") || item.getName().equals("ZMIOTKA_T1")
                    || item.getName().equals("ZMIOTKA_T2") || item.getName().equals("ZMIOTKA_T3") || item.getName().equals("ZMIOTKA_T4")) {
                continue;
            }
            gui.setItem(gui.firstEmpty(), item.getItemStack().clone());
        }

        gui.setItem(45, GornikItems.getKilof(player.getName()).clone());
        gui.setItem(46, GornikItems.getZmiotka("T0", 50, player.getName()).clone());
        gui.setItem(47, GornikItems.getZmiotka("T1", 50, player.getName()).clone());
        gui.setItem(48, GornikItems.getZmiotka("T2", 50, player.getName()).clone());
        gui.setItem(49, GornikItems.getZmiotka("T3", 50, player.getName()).clone());
        gui.setItem(50, GornikItems.getZmiotka("T4", 50, player.getName()).clone());



        gui.setItem(53, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());

        player.openInventory(gui);
    }
}
