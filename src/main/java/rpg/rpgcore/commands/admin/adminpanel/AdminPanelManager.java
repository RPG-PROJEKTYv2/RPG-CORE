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
import rpg.rpgcore.utils.globalitems.expowiska.*;
import rpg.rpgcore.utils.globalitems.npc.*;
import rpg.rpgcore.wyszkolenie.enums.WyszkolenieItems;

import java.util.Arrays;

public class AdminPanelManager {

    public void openAdminPanelGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 45, Utils.format("&4&lPanel Administracyjny"));
        for (int i = 0; i < 45; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
            }
        }

        gui.setItem(10, new ItemBuilder(Material.CHEST).setName("&cSkrzynki - &fMob/Boss/Dungeony/Inne").addGlowing().toItemStack().clone());
        gui.setItem(11, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&bNiesamowite Przedmioty").addGlowing().toItemStack().clone());
        gui.setItem(12, new ItemBuilder(Material.SIGN).setName("&cBony").addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.SNOW_BALL).setName("&fUlepszacze").addGlowing().toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.GOLD_INGOT).setName("&6Itemshop").addGlowing().toItemStack().clone());
        gui.setItem(15, new ItemBuilder(Material.NETHER_STAR).setName("&ePrzedmioty Specjalne").addGlowing().toItemStack().clone());
        gui.setItem(16, new ItemBuilder(Material.BEDROCK).setName("&cNpcty - &fitemy").addGlowing().toItemStack().clone());

        gui.setItem(28, new ItemBuilder(Material.STONE_PICKAXE).setName("&6Itemy Gornika").addGlowing().toItemStack().clone());
        gui.setItem(29, new ItemBuilder(Material.FISHING_ROD).setName("&6Itemy Rybaka").addGlowing().toItemStack().clone());
        gui.setItem(30, new ItemBuilder(Material.GOLD_AXE).setName("&6Itemy Drwala").addGlowing().toItemStack().clone());

        gui.setItem(35, new ItemBuilder(Material.IRON_FENCE).setName(" ").toItemStack());
        gui.setItem(34, new ItemBuilder(Material.IRON_FENCE).setName(" ").toItemStack());
        gui.setItem(43, new ItemBuilder(Material.IRON_FENCE).setName(" ").toItemStack());

        gui.setItem(44, new ItemBuilder(Material.FIREBALL).setName("&b&lKomendy Administracyjne").setLore(Arrays.asList("",
                "&8* &f/level <gracz> setlvl/setexp/setprocent <wartosc>",
                "&8* &f/rozdaj <all/jeden>",
                "&8* &f/pserver <podserver>"
        )).addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openNpctyGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&c&lNpcty &f- ADMINISTRACJA"));
        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").toItemStack());
            }
        }
        gui.setItem(12, new ItemBuilder(Material.MILK_BUCKET).setName("&fPrzyrodnik").addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.MILK_BUCKET).setName("&fMetinolog").addGlowing().toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.MILK_BUCKET).setName("&fLowca").addGlowing().toItemStack().clone());
        gui.setItem(22, new ItemBuilder(Material.BUCKET).setName("&fLesnik").addGlowing().toItemStack().clone());

        gui.setItem(26, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());

        player.openInventory(gui);
    }
    public void openPrzyrodnikGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&c&lPrzyrodnik &f- ADMINISTRACJA"));
        for (final PrzyrodnikItems przyrodnikItems : PrzyrodnikItems.values()) {
            gui.setItem(gui.firstEmpty(), przyrodnikItems.getItemStack());
        }
        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openMetinologGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&c&lMetinolog &f- ADMINISTRACJA"));
        for (final MetinologItems metinologItems : MetinologItems.values()) {
            gui.setItem(gui.firstEmpty(), metinologItems.getItemStack());
        }
        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openLowcaGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&c&lLowca &f- ADMINISTRACJA"));
        for (final LowcaItems lowcaItems : LowcaItems.values()) {
            gui.setItem(gui.firstEmpty(), lowcaItems.getItem());
        }
        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openLesnikGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&c&lLesnik &f- ADMINISTRACJA"));
        for (final LesnikItems lesnikItems : LesnikItems.values()) {
            gui.setItem(gui.firstEmpty(), lesnikItems.getItem());
        }
        gui.setItem(8, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openPrzedmiotySpecjalneGui(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 36, Utils.format("&e&lPrzedmioty S &f- ADMINISTRACJA"));
        for (final WyszkolenieItems wyszkolenieItems : WyszkolenieItems.values()) {
            gui.setItem(gui.firstEmpty(), wyszkolenieItems.getItem());
        }

        gui.setItem(18, Bossy.getItem("I1", 1));
        gui.setItem(19, Bossy.getItem("I2", 1));
        gui.setItem(20, Bossy.getItem("I3", 1));
        gui.setItem(21, Bossy.getItem("I3_1", 1));
        gui.setItem(22, Bossy.getItem("I4", 1));
        gui.setItem(23, Bossy.getItem("I5", 1));
        gui.setItem(24, Bossy.getItem("I5_1", 1));
        gui.setItem(25, Bossy.getItem("I5_2", 1));

        gui.setItem(26, Przepustki.getItem("I1", 1));
        gui.setItem(27, Przepustki.getItem("I2", 1));
        gui.setItem(28, Przepustki.getItem("I3", 1));
        gui.setItem(29, Przepustki.getItem("I4", 1));

        gui.setItem(30, Dungeony.getItem("I_KLUCZ_ARENA_PRZEKLETYCH_WOJOWNIKOW", 1));
        gui.setItem(31, Dungeony.getItem("I_KLUCZ_PIEKIELNA_KRYJOWKA", 1));
        gui.setItem(32, new ItemBuilder(Material.ENCHANTED_BOOK).setName("&f&kaa &5Magiczna Receptura &f&kaa").setLore(Arrays.asList(
                "&7Receptura ta umozliwi Ci wytwarzanie",
                "&5&lKsiega Magii"
        )).toItemStack());


        gui.setItem(35, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());

        player.openInventory(gui);
    }
    public void openRybakItemsGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&6&lRybak &f- ADMINISTRACJA"));
        gui.setItem(0, new ItemBuilder(RybakItems.getWedka(player.getName(), 1)).toItemStack().clone());
        for (final RybakItems rank : RybakItems.values()) {
            gui.setItem(gui.firstEmpty(), rank.getRybak());
        }
        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
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
        gui.setItem(5, Skrzynki.getItem("I12",1));
        gui.setItem(6, Skrzynki.getItem("I14",1));
        gui.setItem(7, Skrzynki.getItem("I16",1));


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
        gui.setItem(5, Skrzynki.getItem("I11",1));
        gui.setItem(6, Skrzynki.getItem("I13",1));
        gui.setItem(7, Skrzynki.getItem("I15",1));
        gui.setItem(8, Skrzynki.getItem("I17",1));
        gui.setItem(9, Skrzynki.getItem("I19",1));
        gui.setItem(10, Skrzynki.getItem("I21",1));
        gui.setItem(11, Skrzynki.getItem("I23",1));
        gui.setItem(12, Skrzynki.getItem("I25",1));
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
        gui.setItem(6, GlobalItem.getItem("I21",1));

        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }

    public void openUlepszaczeGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&f&lUlepszacze &f- ADMINISTRACJA"));

        gui.setItem(0, Ulepszacze.getItem("I_SZATANAJEMNIKA", 1));
        gui.setItem(1, Ulepszacze.getItem("I_OKOGOBLINA", 1));
        gui.setItem(2, Ulepszacze.getItem("I_SKORAGORYLA", 1));
        gui.setItem(3, Ulepszacze.getItem("I_ZLAMANAKOSC", 1));
        gui.setItem(4, Ulepszacze.getItem("I_LZAOCEANU", 1));
        gui.setItem(5, Ulepszacze.getItem("I_MROZNYPAZUR", 1));
        gui.setItem(6, Ulepszacze.getItem("I_OGNISTYPYL", 1));
        gui.setItem(7, Ulepszacze.getItem("I_TRUJACAROSLINA", 1));
        gui.setItem(8, Ulepszacze.getItem("I_JADPTASZNIKA", 1));
        gui.setItem(9, Ulepszacze.getItem("I_MROCZNYMATERIAL", 1));
        gui.setItem(10, Ulepszacze.getItem("I_SZAFIROWESERCE", 1));
        gui.setItem(11, Ulepszacze.getItem("I_ZAKLETYLOD", 1));
        gui.setItem(12, Ulepszacze.getItem("I_NIEBIANSKIMATERIAL", 1));

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
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lItemShop &f- ADMINISTRACJA"));

        gui.setItem(0, ItemShop.getByName("Vip_3d").getItems().get(0));
        gui.setItem(1, ItemShop.getByName("Vip_7d").getItems().get(0));
        gui.setItem(2, ItemShop.getByName("Vip_14d").getItems().get(0));

        gui.setItem(6, ItemShop.getByName("DAR_KLEOPATRY").getItems().get(0));
        gui.setItem(7, ItemShop.getByName("PELERYNKA").getItems().get(0));
        gui.setItem(8, ItemShop.getByName("PELERYNKA+").getItems().get(0));

        gui.setItem(9, ItemShop.getByName("Elita_3d").getItems().get(0));
        gui.setItem(10, ItemShop.getByName("Elita_7d").getItems().get(0));
        gui.setItem(11, ItemShop.getByName("Elita_14d").getItems().get(0));
        gui.setItem(12, ItemShop.getByName("Elita_30d").getItems().get(0));

        gui.setItem(14, GlobalItem.getItem("I52",1 ));
        gui.setItem(15, GlobalItem.getItem("I53",1 ));
        gui.setItem(16, GlobalItem.getItem("I54",1 ));
        gui.setItem(17, GlobalItem.getItem("I55",1 ));

        gui.setItem(18, ItemShop.getByName("HS_5").getItems().get(0));
        gui.setItem(19, ItemShop.getByName("HS_10").getItems().get(0));
        gui.setItem(20, ItemShop.getByName("HS_15").getItems().get(0));
        gui.setItem(21, ItemShop.getByName("HS_30").getItems().get(0));
        gui.setItem(22, ItemShop.getByName("HS_55").getItems().get(0));
        gui.setItem(23, ItemShop.getByName("HS_130").getItems().get(0));
        gui.setItem(24, ItemShop.getByName("HS_280").getItems().get(0));
        gui.setItem(25, ItemShop.getByName("HS_585").getItems().get(0));

        gui.setItem(26, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
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
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&6&lGornik &f- ADMINISTRACJA"));
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
