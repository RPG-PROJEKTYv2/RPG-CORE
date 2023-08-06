package rpg.rpgcore.npc.mistyczny_kowal;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.*;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Ulepszacze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MistycznyKowalManager {

    public void openGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&d&lMistyczny Kowal"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 11).toItemStack());
        }
        gui.setItem(11, new ItemBuilder(Material.DIAMOND_SWORD).setName("&9&lMithrylowy Sztylet").setLore(Arrays.asList(
                "&7Obrazenia: &c35",
                "&7Obrazenia na potwory: &c15",
                "",
                "&3Silny Na Ludzi: &f+XX%",
                "&3Silny Na Potwory: &f-XX%",
                "",
                "&cWymagany Poziom: &665"
        )).toItemStack());

        gui.setItem(13, new ItemBuilder(Material.WORKBENCH).setName("&eWytworz &9&lMithrylowy Miecz").setLore(this.getLore(player)).addGlowing().toItemStack().clone());

        gui.setItem(15, new ItemBuilder(Material.DIAMOND_SWORD).setName("&9&lMithrylowe Ostrze").setLore(Arrays.asList(
                "&7Obrazenia: &c35",
                "&7Obrazenia na potwory: &c15",
                "",
                "&3Silny Na Potwory: &f+XX%",
                "&3Silny Na Ludzi: &f-XX%",
                "",
                "&cWymagany Poziom: &665"
        )).toItemStack());

        player.openInventory(gui);
    }

    private List<String> getLore(final Player player) {
        final List<String> lore = new ArrayList<>();
        final double kasa = RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getKasa();
        String prefix;
        int itemCount;
        lore.add("&7&m----------{&f&l Wymagane Przedmioty &7&m}----------");
        itemCount = Utils.getPlayerInventoryItemCount(player, GlobalItem.RUDA_MITHRYLU.getItemStack());
        prefix = (itemCount >= 5 ? "&2&m" : "&2");
        lore.add("  &8- " + prefix + "Ruda Mithrylu&8 x5 (" + itemCount + "/5)");
        itemCount = Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_SZATAROZBOJNIKA.getItem());
        prefix = (itemCount >= 16 ? "&8&m" : "&8");
        lore.add("  &8- " + prefix + "Szata Rozbojnika&8 x16 (" + itemCount + "/16)");
        itemCount = Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_OKOGOBLINA.getItem());
        prefix = (itemCount >= 16 ? "&a&m" : "&a");
        lore.add("  &8- " + prefix + "Oko Goblina&8 x16 (" + itemCount + "/16)");
        itemCount = Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_SKORAGORYLA.getItem());
        prefix = (itemCount >= 16 ? "&f&m" : "&f");
        lore.add("  &8- " + prefix + "Skora Goryla&8 x16 (" + itemCount + "/16)");
        itemCount = Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_ZLAMANAKOSC.getItem());
        prefix = (itemCount >= 16 ? "&7&m" : "&7");
        lore.add("  &8- " + prefix + "Zlamana Kosc&8 x16 (" + itemCount + "/16)");
        itemCount = Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_LZAOCEANU.getItem());
        prefix = (itemCount >= 16 ? "&b&m" : "&b");
        lore.add("  &8- " + prefix + "Lza Oceanu&8 x16 (" + itemCount + "/16)");
        itemCount = Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_WILCZEFUTRO.getItem());
        prefix = (itemCount >= 16 ? "&7&m" : "&7");
        lore.add("  &8- " + prefix + "Wilcze Futro&8 x16 (" + itemCount + "/16)");
        itemCount = Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_OGNISTYPYL.getItem());
        prefix = (itemCount >= 16 ? "&c&m" : "&c");
        lore.add("  &8- " + prefix + "Ognisty Pyl&8 x16 (" + itemCount + "/16)");
        itemCount = Utils.getPlayerInventoryItemCount(player, GlobalItem.I12.getItemStack());
        prefix = (itemCount >= 24 ? "&e&m" : "&e");
        lore.add("  &8- " + prefix + "Zloto&8 x24 (" + itemCount + "/24)");
        itemCount = Utils.getPlayerInventoryItemCount(player, GlobalItem.I13.getItemStack());
        prefix = (itemCount >= 24 ? "&b&m" : "&b");
        lore.add("  &8- " + prefix + "Brylant&8 x24 (" + itemCount + "/24)");
        itemCount = Utils.getPlayerInventoryItemCount(player, GlobalItem.I14.getItemStack());
        prefix = (itemCount >= 24 ? "&a&m" : "&a");
        lore.add("  &8- " + prefix + "Szmaragd&8 x24 (" + itemCount + "/24)");
        itemCount = Utils.getPlayerInventoryItemCount(player, GlobalItem.I15.getItemStack());
        prefix = (itemCount >= 20 ? "&c&m" : "&c");
        lore.add("  &8- " + prefix + "Pyl&8 x20 (" + itemCount + "/20)");
        itemCount = Utils.getPlayerInventoryItemCount(player, GlobalItem.I16.getItemStack());
        prefix = (itemCount >= 20 ? "&7&m" : "&7");
        lore.add("  &8- " + prefix + "Kamien&8 x20 (" + itemCount + "/20)");
        itemCount = Utils.getPlayerInventoryItemCount(player, GlobalItem.I17.getItemStack());
        prefix = (itemCount >= 16 ? "&8&m" : "&8");
        lore.add("  &8- " + prefix + "Stal&8 x16 (" + itemCount + "/16)");
        itemCount = Utils.getPlayerInventoryItemCount(player, GlobalItem.I18.getItemStack());
        prefix = (itemCount >= 16 ? "&7&m" : "&7");
        lore.add("  &8- " + prefix + "Proch&8 x16 (" + itemCount + "/16)");
        prefix = (kasa >= 10_000_000 ? "&a&m" : "&c");
        lore.add("  &8- " + prefix + "10 000 000 $");
        lore.add("&7&m----------{&f&l Wymagane Przedmioty &7&m}----------");

        return lore;
    }

    public void getRandomMiecz(final Player player) {
        final int szczescie = RPGCORE.getInstance().getBonusesManager().find(player.getUniqueId()).getBonusesUser().getSzczescie();
        final SwordType swordType = (ChanceHelper.getChance(50) ? SwordType.KS : SwordType.TYRA);

        /*
            60-55% - 5%
            54-45% -  25%
            44-25% - 30%
            24-2% - 50%
         */

        int randomPercent = 1;

        if (ChanceHelper.getChance(MobDropHelper.getDropChance(szczescie, 5))) {
            randomPercent = ChanceHelper.getRandInt(55, 60);
        } else if (ChanceHelper.getChance(MobDropHelper.getDropChance(szczescie, 25))) {
            randomPercent = ChanceHelper.getRandInt(45, 54);
        } else if (ChanceHelper.getChance(MobDropHelper.getDropChance(szczescie, 30))) {
            randomPercent = ChanceHelper.getRandInt(25, 44);
        } else if (ChanceHelper.getChance(MobDropHelper.getDropChance(szczescie, 75))) {
            randomPercent = ChanceHelper.getRandInt(2, 24);
        }


        final ItemStack item = GlobalItem.getPercentSword(swordType, randomPercent);
        player.getInventory().addItem(item);
        player.sendMessage(Utils.format("&7&lMistyczny Kowal &8>> &aPomyslnie wytworzylem twoj miecz!"));
        Bukkit.getServer().broadcastMessage(Utils.format("&7&lMistyczny Kowal &8>> &fGracz &5" + player.getName() + " &fwytworzyl " + item.getItemMeta().getDisplayName() + " (" + randomPercent + "%)&a!"));
    }
}
