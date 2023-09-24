package rpg.rpgcore.npc.rzemieslnik;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.ArrayList;
import java.util.List;

public class RzemieslnikManager {

    public void openGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&f&lRzemieslnik"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).toItemStack());
        }
        gui.setItem(12, new ItemBuilder(Material.SIGN).setName("&eWytworz &7>> &4&lLOSOWY BON 5%").setLore(this.setLoreBON(player)).addGlowing().toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.IRON_INGOT).setName("&eWytworz &7>> &7&lStal Kowalska").setLore(this.setLoreSTAL(player)).addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }

    private List<String> setLoreBON(final Player player) {
        final List<String> lore = new ArrayList<>();
        final double kasa = RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getKasa();
        int item;
        String prefix;
        lore.add("");
        lore.add("&7Kliknij aby wytworzyc losowy bon sposrod:");
        lore.add("&f* &cBon Srednich Obrazen 5%");
        lore.add("&f* &2Bon Sredniej Defensywy 5%");
        lore.add("&f* &5Bon Szansy Na Cios Krytyczny 5%");
        lore.add("");
        lore.add("&7&m----------{&f&l Wymagane Przedmioty &7&m}----------");
        item = Utils.getPlayerInventoryItemCount(player, GlobalItem.getItem("I_FRAGMENT_BONA",1));
        prefix = (item >= 3 ? "&3&m" : "&3");
        lore.add("  &8- " + prefix + "&3&lFragment Tajemniczego Bona &8x3 (" + item + "/3)");
        prefix = (kasa >= 200_000_000 ? "&a&m" : "&c");
        lore.add("  &8- " + prefix + "200 000 000 $");
        lore.add("&7&m----------{&f&l Wymagane Przedmioty &7&m}----------");
        return lore;
    }
    private List<String> setLoreSTAL(final Player player) {
        final List<String> lore = new ArrayList<>();
        final double kasa = RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getKasa();
        int item;
        String prefix;
        lore.add("");
        lore.add("&7Kliknij aby wytworzyc &7&lStal Kowalska");
        lore.add("");
        lore.add("&7&m----------{&f&l Wymagane Przedmioty &7&m}----------");
        item = Utils.getPlayerInventoryItemCount(player, GlobalItem.getItem("I_FRAGMENT_STALI", 1));
        prefix = (item >= 4 ? "&8&l&m" : "&8&l");
        lore.add("  &8- " + prefix + "Fragment Stali Kowalskiej &8x4 (" + item + "/4)");
        item = Utils.getPlayerInventoryItemCount(player, GlobalItem.getItem("I10", 1));
        prefix = (item >= 1 ? "&a&l&m" : "&a&l");
        lore.add("  &8- " + prefix + "Podrecznik Kowala &8x1 (" + item + "/1)");
        prefix = (kasa >= 3_000_000 ? "&a&m" : "&c");
        lore.add("  &8- " + prefix + "3 000 000 $");
        lore.add("&7&m----------{&f&l Wymagane Przedmioty &7&m}----------");
        return lore;
    }
}
