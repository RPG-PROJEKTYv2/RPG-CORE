package rpg.rpgcore.commands.player.craftingi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Arrays;

public class CraftingiManager {
    public void openCraftingiGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&e&lCraftingi"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());
            }
        }

        gui.setItem(10, new ItemBuilder(Material.WATER_BUCKET).setName("&f&lAnielskie Akcesorium").addGlowing().toItemStack());
        gui.setItem(12, new ItemBuilder(Material.LAVA_BUCKET).setName("&4&lDiabelskie Akcesorium").addGlowing().toItemStack());
        gui.setItem(14, new ItemBuilder(Material.BEACON).setName("&e&lSwietlny Ekwipunek").addGlowing().toItemStack());
        gui.setItem(16, new ItemBuilder(Material.ARROW).setName("&3&lInne").addGlowing().toItemStack());

        player.openInventory(gui);
    }

    public void openCraftingAnielskie(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&e&lCraftingi &8>> &f&lAnielskie Akce"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());
            }
        }

        gui.setItem(10, new ItemBuilder(Material.ITEM_FRAME).setName("&f&lAnielska Tarcza").setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Zwiekszona Defensywa: &c1% - 17%",
                "&7Szansa na Blok: &c1% - 10%",
                "&7Dodatkowe Serca: &c+1 - 5",
                "",
                "&cWymagany poziom: &61 - 30",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &610x &eZloto",
                "&8- &610x &bBrylant",
                "&8- &610x &aSzmaragd",
                "&8- &610x &cPyl",
                "&8- &610x &7Kamien",
                "&8- &610x &8Stal",
                "&8- &610x &7Proch",
                "&8- &650 000&2$"
        )).toItemStack());

        gui.setItem(12, new ItemBuilder(Material.WATCH).setName("&f&lAnielski Diadem").setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Srednie Obrazenia: &c1% - 8%",
                "&7Silny przeciwko potworom: &c1% - 10%",
                "&7Dodatkowy EXP: &c1% - 2%",
                "",
                "&cWymagany poziom: &61 - 30",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &616x &eZloto",
                "&8- &616x &bBrylant",
                "&8- &616x &aSzmaragd",
                "&8- &616x &cPyl",
                "&8- &616x &7Kamien",
                "&8- &616x &8Stal",
                "&8- &616x &7Proch",
                "&8- &6150 000&2$"
        )).toItemStack());

        gui.setItem(14, new ItemBuilder(Material.STORAGE_MINECART).setName("&f&lAnielski Naszyjnik").setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Dodatkowe Obrazenia: &c+1 - 20",
                "&7Szansa na Cios Krytyczny: &c1% - 10%",
                "&7Zwiekszone Obrazenia: &c1% - 7%",
                "",
                "&cWymagany poziom: &61 - 30",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &624x &eZloto",
                "&8- &624x &bBrylant",
                "&8- &624x &aSzmaragd",
                "&8- &624x &cPyl",
                "&8- &624x &7Kamien",
                "&8- &624x &8Stal",
                "&8- &624x &7Proch",
                "&8- &6200 000&2$"
        )).toItemStack());

        gui.setItem(16, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&f&lAnielski Pierscien").setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Szansa na Przeszycie Bloku: &c1% - 6%",
                "&7Wzmocnienie Ciosu Krytycznego: &c1% - 5%",
                "&7Zwiekszona Szybkosc Ruchu, &c+1 - 10",
                "",
                "&cWymagany poziom: &61 - 30",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &616x &eZloto",
                "&8- &616x &bBrylant",
                "&8- &616x &aSzmaragd",
                "&8- &616x &cPyl",
                "&8- &616x &7Kamien",
                "&8- &616x &8Stal",
                "&8- &616x &7Proch",
                "&8- &6100 000&2$"
        )).toItemStack());

        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());

        player.openInventory(gui);
    }

    public void openCraftingDiabelskie(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&e&lCraftingi &8>> &4&lDiabelskie Akce"));

        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());
        }

        gui.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(24, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(18, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(26, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());


        gui.setItem(10, new ItemBuilder(Material.ITEM_FRAME).setName("&4&lDiabelska Tarcza").setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Zwiekszona Defensywa: &c5% - 35%",
                "&7Szansa na Blok: &c3% - 20%",
                "&7Dodatkowe Serca: &c+1 - 10",
                "",
                "&cWymagany poziom: &630 - 60",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &632x &eZloto",
                "&8- &632x &bBrylant",
                "&8- &632x &aSzmaragd",
                "&8- &632x &cPyl",
                "&8- &632x &7Kamien",
                "&8- &632x &8Stal",
                "&8- &632x &7Proch",
                "&8- &6200 000&2$"
        )).toItemStack());

        gui.setItem(12, new ItemBuilder(Material.WATCH).setName("&4&lDiabelski Diadem").setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Srednie Obrazenia: &c3% - 15%",
                "&7Silny przeciwko potworom: &c3% - 15%",
                "&7Dodatkowy EXP: &c1% - 3%",
                "",
                "&cWymagany poziom: &630 - 60",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &648x &eZloto",
                "&8- &648x &bBrylant",
                "&8- &648x &aSzmaragd",
                "&8- &648x &cPyl",
                "&8- &648x &7Kamien",
                "&8- &648x &8Stal",
                "&8- &648x &7Proch",
                "&8- &6400 000&2$"
        )).toItemStack());
        
        gui.setItem(13, new ItemBuilder(Material.HOPPER_MINECART).setName("&4&lDiabelskie Kolczyki").setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Silny przeciwko ludziom: &c3% - 18%",
                "&7Odpornosc na Ludzi: &c2% - 15%",
                "&7Zmniejszona Szybkosc Ruchu: -&c10 - 30",
                "",
                "&cWymagany poziom: &630 - 60",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &656x &eZloto",
                "&8- &656x &bBrylant",
                "&8- &656x &aSzmaragd",
                "&8- &656x &cPyl",
                "&8- &656x &7Kamien",
                "&8- &656x &8Stal",
                "&8- &656x &7Proch",
                "&8- &6400 000&2$"
        )).toItemStack());
        

        gui.setItem(14, new ItemBuilder(Material.STORAGE_MINECART).setName("&4&lDiabelski Naszyjnik").setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Dodatkowe Obrazenia: &c+12 - 60",
                "&7Szansa na Cios Krytyczny: &c5% - 18%",
                "&7Zwiekszone Obrazenia: &c5% - 18%",
                "",
                "&cWymagany poziom: &630 - 60",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &664x &eZloto",
                "&8- &664x &bBrylant",
                "&8- &664x &aSzmaragd",
                "&8- &664x &cPyl",
                "&8- &664x &7Kamien",
                "&8- &664x &8Stal",
                "&8- &664x &7Proch",
                "&8- &6500 000&2$"
        )).toItemStack());

        gui.setItem(16, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&4&lDiabelski Pierscien").setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Szansa na Przeszycie Bloku: &c4% - 12%",
                "&7Wzmocnienie Ciosu Krytycznego: &c3% - 10%",
                "&7Zwiekszona Szybkosc Ruchu, &c+15 - 30",
                "",
                "&cWymagany poziom: &630 - 60",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &648x &eZloto",
                "&8- &648x &bBrylant",
                "&8- &648x &aSzmaragd",
                "&8- &648x &cPyl",
                "&8- &648x &7Kamien",
                "&8- &648x &8Stal",
                "&8- &648x &7Proch",
                "&8- &6300 000&2$"
        )).toItemStack());

        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());

        player.openInventory(gui);
    }

    public void openCratingSwietlnaZbroja(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&e&lCraftingi &8>> &e&lSwietlne EQ"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());
            }
        }

        gui.setItem(10, new ItemBuilder(Material.DIAMOND_HELMET).setName("&e&lSwietlny Helm").setLore(Arrays.asList(
                "&7Obrona: &f1 - 75",
                "&7Ciernie: &f1 - 20",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &640x &eZloto",
                "&8- &640x &bBrylant",
                "&8- &640x &aSzmaragd",
                "&8- &640x &cPyl",
                "&8- &640x &7Kamien",
                "&8- &640x &8Stal",
                "&8- &640x &7Proch",
                "&8- &65 000 000&2$"
        )).toItemStack());

        gui.setItem(12, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("&e&lSwietlny Napiersnik").setLore(Arrays.asList(
                "&7Obrona: &f1 - 75",
                "&7Ciernie: &f1 - 20",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &664x &eZloto",
                "&8- &664x &bBrylant",
                "&8- &664x &aSzmaragd",
                "&8- &664x &cPyl",
                "&8- &664x &7Kamien",
                "&8- &664x &8Stal",
                "&8- &664x &7Proch",
                "&8- &65 000 000&2$"
        )).toItemStack());

        gui.setItem(14, new ItemBuilder(Material.DIAMOND_LEGGINGS).setName("&e&lSwietlne Nogawice").setLore(Arrays.asList(
                "&7Obrona: &f1 - 75",
                "&7Ciernie: &f1 - 20",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &656x &eZloto",
                "&8- &656x &bBrylant",
                "&8- &656x &aSzmaragd",
                "&8- &656x &cPyl",
                "&8- &656x &7Kamien",
                "&8- &656x &8Stal",
                "&8- &656x &7Proch",
                "&8- &65 000 000&2$"
        )).toItemStack());

        gui.setItem(16, new ItemBuilder(Material.DIAMOND_BOOTS).setName("&e&lSwietlne Buty").setLore(Arrays.asList(
                "&7Obrona: &f1 - 75",
                "&7Ciernie: &f1 - 20",
                "",
                "&7Potrzebne przedmioty:",
                "&8- &632x &eZloto",
                "&8- &632x &bBrylant",
                "&8- &632x &aSzmaragd",
                "&8- &632x &cPyl",
                "&8- &632x &7Kamien",
                "&8- &632x &8Stal",
                "&8- &632x &7Proch",
                "&8- &65 000 000&2$"
        )).toItemStack());

        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());

        player.openInventory(gui);
    }

    public void openCraftingiInne(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&e&lCraftingi &8>> &3&lInne"));

        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());
        }

        gui.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(24, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(18, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        gui.setItem(26, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());

        gui.setItem(10, new ItemBuilder(GlobalItem.getItem("I50", 1).clone()).setLoreCrafting(GlobalItem.getItem("I50", 1).clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Potrzebne przedmioty:",
                "&8- &632x &eZloto",
                "&8- &632x &bBrylant",
                "&8- &632x &aSzmaragd",
                "&8- &632x &cPyl",
                "&8- &632x &7Kamien",
                "&8- &632x &8Stal",
                "&8- &632x &7Proch",
                "&8- &62 500 000&2$"
        )).toItemStack());
        
        gui.setItem(11, new ItemBuilder(GlobalItem.getItem("I51", 1).clone()).setLoreCrafting(GlobalItem.getItem("I51", 1).clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Potrzebne przedmioty:",
                "&8- &632x &eZloto",
                "&8- &632x &bBrylant",
                "&8- &632x &aSzmaragd",
                "&8- &632x &cPyl",
                "&8- &632x &7Kamien",
                "&8- &632x &8Stal",
                "&8- &632x &7Proch",
                "&8- &62 500 000&2$"
        )).toItemStack());
        
        gui.setItem(13, new ItemBuilder(GlobalItem.getItem("I_METAL", 1).clone()).setLoreCrafting(GlobalItem.getItem("I_METAL", 1).clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Potrzebne przedmioty:",
                "&8- &63x &8&lFragment Magicznej Stali",
                "&8- &616x &eZloto",
                "&8- &616x &cPyl",
                "&8- &616x &7Kamien",
                "&8- &616x &8Stal",
                "&8- &68 000 000&2$"
        )).toItemStack());
        gui.setItem(14, new ItemBuilder(GlobalItem.getItem("I_OCZYSZCZENIE", 1).clone()).setLoreCrafting(GlobalItem.getItem("I_OCZYSZCZENIE", 1).clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Potrzebne przedmioty:",
                "&8- &61x &8&lFragment Magicznej Stali",
                "&8- &616x &bBrylant",
                "&8- &616x &aSzmaragd",
                "&8- &616x &7Proch",
                "&8- &61 000 000&2$"
        )).toItemStack());
        
        gui.setItem(16, new ItemBuilder(GlobalItem.getItem("I_KAMIENBAO", 1).clone()).setLoreCrafting(GlobalItem.getItem("I_KAMIENBAO", 1).clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Potrzebne przedmioty:",
                "&8- &61x &d&lCzastka Magii",
                "&8- &632x &eZloto",
                "&8- &632x &bBrylant",
                "&8- &632x &aSzmaragd",
                "&8- &632x &cPyl",
                "&8- &632x &7Proch",
                "&8- &67 500 000&2$"
        )).toItemStack());

        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());


        player.openInventory(gui);
    }


}
