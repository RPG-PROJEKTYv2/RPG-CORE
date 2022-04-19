package rpg.rpgcore.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInteractListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        ItemStack itemToSet;

        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getItem().equals(rpgcore.getBaoManager().getItemDoZmianki())) {
                if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) < 80) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c80 &7poziom, zeby uzywac &4&lKsiegi Magii"));
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                player.openInventory(rpgcore.getBaoManager().ksiegaMagiiGUI(player.getUniqueId()));
                return;
            }

            if (e.getItem().getType() == Material.ITEM_FRAME) {

                if (e.getItem().getItemMeta().getDisplayName() == null) {
                    return;
                }

                if (e.getItem().getItemMeta().getDisplayName().contains("Tarcza")) {

                    if (rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).getItem(10).getType() != Material.BARRIER){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazona &cTarcze"));
                        return;
                    }
                    itemToSet = e.getItem();

                    if (e.getItem().getAmount() != 1){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                        return;
                    }

                    if (rpgcore.getPlayerManager().getPlayerLvl(uuid) < rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Wymagany Poziom")){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                        return;
                    }
                    rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).setItem(10, itemToSet);
                    this.itemMapToRemove.put(0, itemToSet);
                    player.getInventory().removeItem(this.itemMapToRemove.get(0));
                    this.itemMapToRemove.clear();
                    rpgcore.getPlayerManager().updatePlayerDef(uuid, rpgcore.getPlayerManager().getPlayerDef(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Obrona"));
                    rpgcore.getPlayerManager().updatePlayerBlok(uuid, rpgcore.getPlayerManager().getPlayerBlok(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Blok Ciosu"));
                    rpgcore.getPlayerManager().updatePlayerDamage(uuid, rpgcore.getPlayerManager().getPlayerDamage(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Obrazenia"));
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + itemToSet.getItemMeta().getDisplayName()));
                    return;
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
                return;
            }

            if (e.getItem().getType() == Material.STORAGE_MINECART) {
                if (e.getItem().getItemMeta().getDisplayName() == null) {
                    return;
                }

                if (e.getItem().getItemMeta().getDisplayName().contains("Naszyjnik")) {

                    if (rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).getItem(11).getType() != Material.BARRIER){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazony &cNaszyjnik"));
                        return;
                    }
                    itemToSet = e.getItem();

                    if (e.getItem().getAmount() != 1){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                        return;
                    }

                    if (rpgcore.getPlayerManager().getPlayerLvl(uuid) < rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Wymagany Poziom")){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                        return;
                    }
                    rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).setItem(11, itemToSet);
                    this.itemMapToRemove.put(0, itemToSet);
                    player.getInventory().removeItem(this.itemMapToRemove.get(0));
                    this.itemMapToRemove.clear();
                    rpgcore.getPlayerManager().updatePlayerPrzeszywka(uuid, rpgcore.getPlayerManager().getPlayerPrzeszywka(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Przeszycie Bloku"));
                    rpgcore.getPlayerManager().updatePlayerDamage(uuid, rpgcore.getPlayerManager().getPlayerDamage(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Obrazenia"));
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + itemToSet.getItemMeta().getDisplayName()));
                    return;
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
                return;
            }

            if (e.getItem().getType() == Material.POWERED_MINECART) {
                if (e.getItem().getItemMeta().getDisplayName() == null) {
                    return;
                }

                if (e.getItem().getItemMeta().getDisplayName().contains("Bransoleta")) {

                    if (rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).getItem(12).getType() != Material.BARRIER){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazona &cBransolete"));
                        return;
                    }
                    itemToSet = e.getItem();

                    if (e.getItem().getAmount() != 1){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                        return;
                    }

                    if (rpgcore.getPlayerManager().getPlayerLvl(uuid) < rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Wymagany Poziom")){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                        return;
                    }
                    rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).setItem(12, itemToSet);
                    this.itemMapToRemove.put(0, itemToSet);
                    player.getInventory().removeItem(this.itemMapToRemove.get(0));
                    this.itemMapToRemove.clear();
                    rpgcore.getPlayerManager().updatePlayerKryt(uuid, rpgcore.getPlayerManager().getPlayerKryt(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Cios Krytyczny"));
                    rpgcore.getPlayerManager().updatePlayerSrednie(uuid, rpgcore.getPlayerManager().getPlayerSrednie(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Srednie Obrazenia"));
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + itemToSet.getItemMeta().getDisplayName()));
                    return;
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
                return;
            }

            if (e.getItem().getType() == Material.HOPPER_MINECART) {
                if (e.getItem().getItemMeta().getDisplayName() == null) {
                    return;
                }

                if (e.getItem().getItemMeta().getDisplayName().contains("Kolczyki")) {

                    if (rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).getItem(13).getType() != Material.BARRIER){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazone &cKolczyki"));
                        return;
                    }
                    itemToSet = e.getItem();

                    if (e.getItem().getAmount() != 1){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                        return;
                    }

                    if (rpgcore.getPlayerManager().getPlayerLvl(uuid) < rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Wymagany Poziom")){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                        return;
                    }
                    rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).setItem(13, itemToSet);
                    this.itemMapToRemove.put(0, itemToSet);
                    player.getInventory().removeItem(this.itemMapToRemove.get(0));
                    this.itemMapToRemove.clear();
                    rpgcore.getPlayerManager().updatePlayerHP(uuid, rpgcore.getPlayerManager().getPlayerHP(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Dodatkowe HP"));
                    rpgcore.getPlayerManager().updatePlayerSilnyNaLudzi(uuid, rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Silny przeciwko Ludziom"));
                    player.setMaxHealth(player.getMaxHealth() + (double) rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 13, "Dodatkowe HP") * 2);
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + itemToSet.getItemMeta().getDisplayName()));
                    return;
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
                return;
            }

            if (e.getItem().getType() == Material.EXPLOSIVE_MINECART) {
                if (e.getItem().getItemMeta().getDisplayName() == null) {
                    return;
                }

                if (e.getItem().getItemMeta().getDisplayName().contains("Pierscien")) {

                    if (rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).getItem(14).getType() != Material.BARRIER){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazony &cPierscien"));
                        return;
                    }
                    itemToSet = e.getItem();

                    if (e.getItem().getAmount() != 1){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                        return;
                    }

                    if (rpgcore.getPlayerManager().getPlayerLvl(uuid) < rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Wymagany Poziom")){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                        return;
                    }
                    rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).setItem(14, itemToSet);
                    this.itemMapToRemove.put(0, itemToSet);
                    player.getInventory().removeItem(this.itemMapToRemove.get(0));
                    this.itemMapToRemove.clear();
                    rpgcore.getPlayerManager().updatePlayerBlok(uuid, rpgcore.getPlayerManager().getPlayerBlok(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Blok Ciosu"));
                    rpgcore.getPlayerManager().updatePlayerHP(uuid, rpgcore.getPlayerManager().getPlayerHP(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Dodatkowe HP"));
                    player.setMaxHealth(player.getMaxHealth() + (double) rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 14, "Dodatkowe HP") * 2);
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + itemToSet.getItemMeta().getDisplayName()));
                    return;
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
                return;
            }

            if (e.getItem().getType() == Material.MINECART) {
                if (e.getItem().getItemMeta().getDisplayName() == null) {
                    return;
                }

                if (e.getItem().getItemMeta().getDisplayName().contains("Energia")) {

                    if (rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).getItem(15).getType() != Material.BARRIER){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazona &cEnergie"));
                        return;
                    }
                    itemToSet = e.getItem();

                    if (e.getItem().getAmount() != 1){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                        return;
                    }

                    if (rpgcore.getPlayerManager().getPlayerLvl(uuid) < rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Wymagany Poziom")){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                        return;
                    }
                    rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).setItem(15, itemToSet);
                    this.itemMapToRemove.put(0, itemToSet);
                    player.getInventory().removeItem(this.itemMapToRemove.get(0));
                    this.itemMapToRemove.clear();
                    rpgcore.getPlayerManager().updatePlayerDef(uuid, rpgcore.getPlayerManager().getPlayerDef(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Obrona"));
                    rpgcore.getPlayerManager().updatePlayerBlok(uuid, rpgcore.getPlayerManager().getPlayerBlok(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Blok Ciosu"));
                    rpgcore.getPlayerManager().updatePlayerMinusSrednie(uuid, rpgcore.getPlayerManager().getPlayerMinusSrednie(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Srednie Obrazenia"));
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + itemToSet.getItemMeta().getDisplayName()));
                    return;
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
                return;
            }

            if (e.getItem().getType() == Material.WATCH) {
                if (e.getItem().getItemMeta().getDisplayName() == null) {
                    return;
                }

                if (e.getItem().getItemMeta().getDisplayName().contains("Zegarek")) {

                    if (rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).getItem(16).getType() != Material.BARRIER){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazony &cZegarek"));
                        return;
                    }
                    itemToSet = e.getItem();

                    if (e.getItem().getAmount() != 1){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                        return;
                    }

                    if (rpgcore.getPlayerManager().getPlayerLvl(uuid) < rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Wymagany Poziom")){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                        return;
                    }
                    rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).setItem(16, itemToSet);
                    this.itemMapToRemove.put(0, itemToSet);
                    player.getInventory().removeItem(this.itemMapToRemove.get(0));
                    this.itemMapToRemove.clear();
                    rpgcore.getPlayerManager().updatePlayerDamage(uuid, rpgcore.getPlayerManager().getPlayerDamage(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Obrazenia"));
                    rpgcore.getPlayerManager().updatePlayerSilnyNaLudzi(uuid, rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Silny przeciwko Ludziom"));
                    rpgcore.getPlayerManager().updatePlayerMinusDef(uuid, rpgcore.getPlayerManager().getPlayerMinusDef(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Obrona"));
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + itemToSet.getItemMeta().getDisplayName()));
                    return;
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
                return;
            }

            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock() == null) {
                return;
            }

            if (e.getItem().equals(rpgcore.getBaoManager().getItemDoZmianki())) {
                if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) < 80) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c80 &7poziom, zeby uzywac &4&lKsiegi Magii"));
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                player.openInventory(rpgcore.getBaoManager().ksiegaMagiiGUI(player.getUniqueId()));
                return;
            }
        }

    }
}
