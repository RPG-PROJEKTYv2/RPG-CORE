package rpg.rpgcore.commands.admin.adminpanel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.AkceItems;
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
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            }
        }

        gui.setItem(10, new ItemBuilder(Material.CHEST).setName("&cSkrzynki - &fMob/Boss/Dungeony/Inne").addGlowing().toItemStack().clone());
        gui.setItem(11, new ItemBuilder(Material.JUKEBOX).setName("&b&lNiesamowite Przedmioty &8&l& &6&lAkcesorium").addGlowing().toItemStack().clone());
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
                "&8* &f/rozdaj <all/jeden>",
                "&8* &f/pserver <podserver>",
                "&8* &f/wedka ?",
                "&8* &f/miecze ?"
        )).addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openNpctyGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&c&lNpcty &f- ADMINISTRACJA"));
        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            }
        }
        gui.setItem(12, new ItemBuilder(Material.MILK_BUCKET).setName("&fPrzyrodnik").addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.MILK_BUCKET).setName("&fMetinolog").addGlowing().toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.MILK_BUCKET).setName("&fLowca").addGlowing().toItemStack().clone());
        gui.setItem(21, new ItemBuilder(Material.BUCKET).setName("&fLesnik").addGlowing().toItemStack().clone());
        gui.setItem(22, new ItemBuilder(Material.BUCKET).setName("&fAlchemik").addGlowing().toItemStack().clone());

        gui.setItem(26, Utils.powrot());

        player.openInventory(gui);
    }
    public void openPrzyrodnikGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&c&lPrzyrodnik &f- ADMINISTRACJA"));
        for (final PrzyrodnikItems przyrodnikItems : PrzyrodnikItems.values()) {
            gui.setItem(gui.firstEmpty(), przyrodnikItems.getItemStack());
        }
        gui.setItem(17, Utils.powrot());
        player.openInventory(gui);
    }
    public void openMetinologGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&c&lMetinolog &f- ADMINISTRACJA"));
        for (final MetinologItems metinologItems : MetinologItems.values()) {
            gui.setItem(gui.firstEmpty(), metinologItems.getItemStack());
        }
        gui.setItem(17, Utils.powrot());
        player.openInventory(gui);
    }
    public void openLowcaGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&c&lLowca &f- ADMINISTRACJA"));
        for (final LowcaItems lowcaItems : LowcaItems.values()) {
            gui.setItem(gui.firstEmpty(), lowcaItems.getItem());
        }
        gui.setItem(17, Utils.powrot());
        player.openInventory(gui);
    }
    public void openLesnikGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&c&lLesnik &f- ADMINISTRACJA"));
        for (final LesnikItems lesnikItems : LesnikItems.values()) {
            gui.setItem(gui.firstEmpty(), lesnikItems.getItem());
        }
        gui.setItem(8, Utils.powrot());
        player.openInventory(gui);
    }
    public void openAlchemikGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&c&lAlchemik &f- ADMINISTRACJA"));
        for (final AlchemikItems alchemikItems : AlchemikItems.values()) {
            gui.setItem(gui.firstEmpty(), alchemikItems.getItemStack());
        }
        gui.setItem(17, Utils.powrot());
        player.openInventory(gui);
    }
    public void openPrzedmiotySpecjalneGui(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&e&lPrzedmioty S &f- ADMINISTRACJA"));
        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            }
        }
        gui.setItem(12, new ItemBuilder(Material.BOOK).setName("&fPodreczniki wyszkolenia").addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.BUCKET).setName("&6Bossy & Klucze").addGlowing().toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.DIAMOND).setName("&cInne").addGlowing().toItemStack().clone());
        gui.setItem(22, new ItemBuilder(Material.JUKEBOX).setName("&6&lDungeony items").addGlowing().toItemStack().clone());
        gui.setItem(26, Utils.powrot());
        player.openInventory(gui);
    }
    public void openPrzedmiotySpecjalneGuiPodreczniki(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&f&lPodreczniki &f- ADMINISTRACJA"));
        for (final WyszkolenieItems wyszkolenieItems : WyszkolenieItems.values()) {
            gui.setItem(gui.firstEmpty(), wyszkolenieItems.getItem());
        }
        gui.setItem(26, Utils.powrot());
        player.openInventory(gui);
    }
    public void openPrzedmiotySpecjalneGuiBossyKlucze(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lBossy & Klucze &f- ADMINISTRACJA"));
        for (final Bossy bossy : Bossy.values()) {
            gui.setItem(gui.firstEmpty(), bossy.getItemStack());
        }
        gui.setItem(26, Utils.powrot());
        player.openInventory(gui);
    }

    public void openDungeonItems(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lDungeony &f- ADMINISTRACJA"));
        for (final Dungeony dungeons : Dungeony.values()) {
            gui.setItem(gui.firstEmpty(), dungeons.getItemStack());
        }
        gui.setItem(26, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openPrzedmiotySpecjalneInne(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&c&lInne &f- ADMINISTRACJA"));
        gui.setItem(0, Przepustki.getItem("I1", 1));
        gui.setItem(1, Przepustki.getItem("I2", 1));
        gui.setItem(2, Przepustki.getItem("I3", 1));
        gui.setItem(3, Przepustki.getItem("I4", 1));
        gui.setItem(4, GlobalItem.I50.getItemStack());

        gui.setItem(6, GlobalItem.I_OCZYSZCZENIE.getItemStack());
        gui.setItem(7, GlobalItem.I10.getItemStack());
        gui.setItem(8, GlobalItem.I_METAL.getItemStack());
        gui.setItem(9, GlobalItem.I_FRAGMENT_STALI.getItemStack());
        gui.setItem(10, GlobalItem.I_CZASTKA_MAGII.getItemStack());
        gui.setItem(11, new ItemBuilder(Material.ENCHANTED_BOOK).setName("&f&kaa &5Magiczna Receptura &f&kaa").setLore(Arrays.asList(
                "&7Receptura ta umozliwi Ci wytwarzanie",
                "&5&lKsiega Magii"
        )).toItemStack());
        gui.setItem(12, GlobalItem.I_KAMIENBAO.getItemStack());
        gui.setItem(13, GlobalItem.I_KSIEGAMAGII.getItemStack());
        gui.setItem(14, GlobalItem.I_KSIEGAMAGII_PLUS.getItemStack());

        gui.setItem(15, GlobalItem.RUDA_MITHRYLU.getItemStack());
        gui.setItem(16, GlobalItem.I_ODLAMEK_ZAKLETEJ_DUSZY.getItemStack());

        gui.setItem(18, GlobalItem.ZNISZCZONE_RUBINOWE_SERCE.getItemStack());
        gui.setItem(19, GlobalItem.ZNISZCZONE_SZAFIROWE_SERCE.getItemStack());
        gui.setItem(20, GlobalItem.SZAFIROWE_SERCE.getItemStack());
        gui.setItem(21, GlobalItem.RUBINOWE_SERCE.getItemStack());
        gui.setItem(23, NereusItems.I1.getItemStack());
        gui.setItem(26, Utils.powrot());
        player.openInventory(gui);
    }
    public void openRybakItemsGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lRybak &f- ADMINISTRACJA"));
        gui.setItem(0, new ItemBuilder(RybakItems.getStaraWedka(player.getName())).toItemStack().clone());
        for (final RybakItems rank : RybakItems.values()) {
            gui.setItem(gui.firstEmpty(), rank.getItemStack());
        }
        gui.setItem(26, Utils.powrot());
        player.openInventory(gui);
    }
    public void openAllCaseGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&2&lSkrzynki &f- ADMINISTRACJA"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            }
        }

        gui.setItem(12, new ItemBuilder(Material.CHEST).setName("&c&lSkrzynki &f- MOBY").addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.ENDER_CHEST).setName("&c&lSkrzynki &f- BOSSY").addGlowing().toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.FURNACE).setName("&c&lSkrzynki &f- DUNGEONY").addGlowing().toItemStack().clone());
        gui.setItem(22, new ItemBuilder(Material.DIAMOND).setName("&c&lSkrzynki &f- INNE").addGlowing().toItemStack().clone());

        gui.setItem(26, Utils.powrot());

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
        gui.setItem(6, Skrzynki.getItem("I_LODOWY_CHEST",1));
        gui.setItem(7, Skrzynki.getItem("I14",1));
        gui.setItem(8, Skrzynki.getItem("I16",1));


        gui.setItem(26, Utils.powrot());
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
        gui.setItem(26, Utils.powrot());
        player.openInventory(gui);
    }
    public void openDungeonyCaseGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lDungeony &f- ADMINISTRACJA"));
        for (final Dungeony dungeony : Dungeony.values()) {
            gui.setItem(gui.firstEmpty(), dungeony.getItemStack());
        }
        gui.setItem(26, Utils.powrot());
        player.openInventory(gui);
    }
    public void openInneCaseGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&2&lSkrzynki &4&lINNE &f- ADMINISTRACJA"));
        for (final SkrzynkiOther skrzynkiOther : SkrzynkiOther.values()) {
            gui.setItem(gui.firstEmpty(), skrzynkiOther.getItemStack());
        }
        gui.setItem(17, Utils.powrot());
        player.openInventory(gui);
    }

    public void openUlepszaczeGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 45, Utils.format("&f&lUlepszacze &f- ADMINISTRACJA"));
        for (final Ulepszacze ulepszacze : Ulepszacze.values()) {
            gui.setItem(gui.firstEmpty(), ulepszacze.getItem());
        }
        gui.setItem(44, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
        player.openInventory(gui);
    }


    public void openBonyGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&c&lBony &f- ADMINISTRACJA"));
        for (final BonType bonType : BonType.values()) {
            gui.setItem(gui.firstEmpty(), bonType.getBon());
        }
        gui.setItem(26, Utils.powrot());
        player.openInventory(gui);
    }
    public void openItemShopGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lItemShop &f- ADMINISTRACJA"));

        gui.setItem(0, ItemShop.IS9.getItems().get(0).clone());
        gui.setItem(1, ItemShop.IS10.getItems().get(0).clone());
        gui.setItem(2, ItemShop.IS11.getItems().get(0).clone());
        gui.setItem(3, ItemShop.IS22.getItems().get(0).clone());

        gui.setItem(5, ItemShop.IS23.getItems().get(0).clone());
        gui.setItem(6, ItemShop.IS21.getItems().get(0).clone());
        gui.setItem(7, ItemShop.IS19.getItems().get(0).clone());
        gui.setItem(8, ItemShop.IS20.getItems().get(0).clone());

        gui.setItem(9, ItemShop.IS12.getItems().get(0).clone());
        gui.setItem(10, ItemShop.IS13.getItems().get(0).clone());
        gui.setItem(11, ItemShop.IS14.getItems().get(0).clone());
        gui.setItem(12, ItemShop.IS15.getItems().get(0).clone());

        gui.setItem(14, GlobalItem.I52.getItemStack().clone());
        gui.setItem(15, GlobalItem.I53.getItemStack().clone());
        gui.setItem(16, GlobalItem.I54.getItemStack().clone());
        gui.setItem(17, GlobalItem.I55.getItemStack().clone());

        gui.setItem(18, ItemShop.IS1.getItems().get(0).clone());
        gui.setItem(19, ItemShop.IS2.getItems().get(0).clone());
        gui.setItem(20, ItemShop.IS3.getItems().get(0).clone());
        gui.setItem(21, ItemShop.IS4.getItems().get(0).clone());
        gui.setItem(22, ItemShop.IS5.getItems().get(0).clone());
        gui.setItem(23, ItemShop.IS6.getItems().get(0).clone());
        gui.setItem(24, ItemShop.IS7.getItems().get(0).clone());
        gui.setItem(25, ItemShop.IS8.getItems().get(0).clone());

        gui.setItem(26, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
        player.openInventory(gui);
    }

    public void openGuiAKCENIES(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&b&lNIES &8&l& &6&lAKCE &f- ADMINISTRACJA"));
        gui.setItem(3, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNIESAMOWITE PRZEDMIOTY").toItemStack().clone());
        gui.setItem(5, new ItemBuilder(Material.IRON_BLOCK).setName("&6&lAKCESORIUM").toItemStack().clone());
        gui.setItem(8, Utils.powrot());
        player.openInventory(gui);
    }
    public void openAkcesoriumGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&6&lAkcesorium &f- ADMINISTRACJA"));
        for (final AkceItems akceItems : AkceItems.values()) {
            gui.setItem(gui.firstEmpty(), akceItems.getAkceItems());
        }
        gui.setItem(17, Utils.powrot());
        player.openInventory(gui);
    }
    public void openNiesamowiteGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&b&lNiesamowite &f- ADMINISTRACJA"));
        for (final NiesyItems niesyItems : NiesyItems.values()) {
            gui.setItem(gui.firstEmpty(), niesyItems.getNiesy());
        }
        gui.setItem(17, Utils.powrot());
        player.openInventory(gui);
    }

    public void openGornikItemsGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lGornik &f- ADMINISTRACJA"));
        for (final GornikItems gornikItems : GornikItems.values()) {
            gui.setItem(gui.firstEmpty(), gornikItems.getItemStack());
        }
        gui.setItem(gui.firstEmpty(), GornikItems.getKilof(player.getUniqueId()).clone());
        gui.setItem(gui.firstEmpty(), new ItemBuilder(GornikItems.getKilof(player.getUniqueId()).clone()).setType(Material.IRON_PICKAXE).toItemStack().clone());
        gui.setItem(gui.firstEmpty(), new ItemBuilder(GornikItems.getKilof(player.getUniqueId()).clone()).setType(Material.GOLD_PICKAXE).toItemStack().clone());
        gui.setItem(gui.firstEmpty(), new ItemBuilder(GornikItems.getKilof(player.getUniqueId()).clone()).setType(Material.DIAMOND_PICKAXE).toItemStack().clone());

        gui.setItem(22, Utils.powrot().clone());

        player.openInventory(gui);
    }
}
