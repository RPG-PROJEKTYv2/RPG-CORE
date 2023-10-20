package rpg.rpgcore.npc.straganiarz;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Ulepszacze;

import java.util.Arrays;

public class StraganiarzManager {

    public void openGUI1(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lStraganiarz &7- MENU"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).toItemStack());
        }
        gui.setItem(12, new ItemBuilder(Material.GOLD_SWORD).setName("&6&lStragan ULEPSZACZY").setLore(Arrays.asList("", "&7Kliknij aby przejsc dalej...")).toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.GOLD_PICKAXE).setName("&6&lStragan MATERIALOW").setLore(Arrays.asList("", "&7Kliknij aby przejsc dalej...")).toItemStack().clone());
        player.openInventory(gui);
    }
    public void openGUI2(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 36, Utils.format("&6&lStraganiarz &7- ULEPSZACZE"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).toItemStack());
        }
        gui.setItem(10, new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(11, new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(12, new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(15, new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(16, new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());

        gui.setItem(20, new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(21, new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(22, new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(23, new ItemBuilder(Ulepszacze.I_SZAFIROWYSKRAWEK.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(24, new ItemBuilder(Ulepszacze.I_ZAKLETYLOD.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());

        gui.setItem(31, new ItemBuilder(Ulepszacze.I_NIEBIANSKIMATERIAL.getItem().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)").toItemStack().clone());

        gui.setItem(35, Utils.powrot());
        player.openInventory(gui);
    }
    public void openGUI3(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lStraganiarz &7- MATERIALY"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).toItemStack());
        }
        gui.setItem(10, new ItemBuilder(GlobalItem.I12.getItemStack().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(11, new ItemBuilder(GlobalItem.I13.getItemStack().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(12, new ItemBuilder(GlobalItem.I14.getItemStack().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(13, new ItemBuilder(GlobalItem.I15.getItemStack().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(14, new ItemBuilder(GlobalItem.I16.getItemStack().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(15, new ItemBuilder(GlobalItem.I17.getItemStack().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());
        gui.setItem(16, new ItemBuilder(GlobalItem.I18.getItemStack().clone()).addLoreLine("", "&7Kliknij PPM aby rozstakowac &e(&c64x1 - 64&e)", "&7Kliknij LPM aby zestakowac &e(&c64 - 64x1&e)", "").toItemStack().clone());

        gui.setItem(26, Utils.powrot());
        player.openInventory(gui);
    }

}
