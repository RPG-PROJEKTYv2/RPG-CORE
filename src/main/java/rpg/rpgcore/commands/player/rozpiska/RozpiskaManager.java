package rpg.rpgcore.commands.player.rozpiska;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public class RozpiskaManager {

    public void openROZPISKAGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&7Rozpiska - menu"));
        ItemBuilder itemGUI = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short)11);
        gui.setItem(0, itemGUI.setName("&cExpowisko &8* &f(&a1-10&f)").addGlowing().toItemStack().clone());
        gui.setItem(1, itemGUI.setName("&cExpowisko &8* &f(&a10-20&f)").addGlowing().toItemStack().clone());
        gui.setItem(2, itemGUI.setName("&cExpowisko &8* &f(&a20-30&f)").addGlowing().toItemStack().clone());
        gui.setItem(3, itemGUI.setName("&cExpowisko &8* &f(&a30-40&f)").addGlowing().toItemStack().clone());
        gui.setItem(4, itemGUI.setName("&cExpowisko &8* &f(&a40-50&f)").addGlowing().toItemStack().clone());
        gui.setItem(5, itemGUI.setName("&cExpowisko &8* &f(&a50-60&f)").addGlowing().toItemStack().clone());
        gui.setItem(6, itemGUI.setName("&cExpowisko &8* &f(&a60-70&f)").addGlowing().toItemStack().clone());
        gui.setItem(7, itemGUI.setName("&cExpowisko &8* &f(&a70-80&f)").addGlowing().toItemStack().clone());
        gui.setItem(8, itemGUI.setName("&cExpowisko &8* &f(&a80-90&f)").addGlowing().toItemStack().clone());
        gui.setItem(9, itemGUI.setName("&cExpowisko &8* &f(&a90-100&f)").addGlowing().toItemStack().clone());
        gui.setItem(10, itemGUI.setName("&cExpowisko &8* &f(&a100-110&f)").addGlowing().toItemStack().clone());
        gui.setItem(11, itemGUI.setName("&cExpowisko &8* &f(&a110-120&f)").addGlowing().toItemStack().clone());
        gui.setItem(12, itemGUI.setName("&cExpowisko &8* &f(&a120-130&f)").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    // do rozpiski:
    // 1. Skrzynki kazda
    // 3. Przyrodnik
    // 2. Ulepszacz
    // 4. Zywica
    // 6. Rubinowe Serce
    // 5. Niesik
    // 9. fragmenty metali
    // 10. czastki magii.
    // 7. Sakwy [expo tam 60...]
    // 8. przepustki
    public void openFIRSTexp(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&7Rozpiska - &f(&a1-10&f)"));
        gui.setItem(0, new ItemBuilder(Material.MONSTER_EGG).setName("&6Rozbojnik").setLore(Arrays.asList("", "&7Skrzynka: &6X%")).addGlowing().toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.FIREWORK_CHARGE).setName("&c&lDowodca Rozbojnikow").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.RABBIT_HIDE).setName("&9&lMetin Rozbojnikow").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());

        gui.setItem(8, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openSECONDexp(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&7Rozpiska - &f(&a10-20&f)"));
        gui.setItem(10, new ItemBuilder(Material.CHEST).setName("&fSkrzynia Goblina").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(11, new ItemBuilder(Material.RABBIT_HIDE).setName("&aOko Goblina").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(12, new ItemBuilder(Material.WATCH).setName("&2Ucho Goblina").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNIESAMOWITY PRZEDMIOT").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());

        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openTHIRDexp(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&7Rozpiska - &f(&a20-30&f)"));
        gui.setItem(10, new ItemBuilder(Material.CHEST).setName("&fSkrzynia Goblina").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(11, new ItemBuilder(Material.RABBIT_HIDE).setName("&aOko Goblina").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(12, new ItemBuilder(Material.WATCH).setName("&2Ucho Goblina").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNIESAMOWITY PRZEDMIOT").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());

        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
}
