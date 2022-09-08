package rpg.rpgcore.akcesoria;

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

import java.util.UUID;

public class AKCESORIAPlayerInteract implements Listener {

    private final RPGCORE rpgcore;

    public AKCESORIAPlayerInteract(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public int getIntFromString(String string) {
        String string1 = string.replaceAll("%", "");
        String[] items = string1.split(" ");
        for (final String item : items) {
            try {
                return Integer.parseInt(item);
            } catch (NumberFormatException e) {
                continue;
            }
        }
        return 0;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void akcesoriaPlayerInteract(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }


        final int lvl = rpgcore.getUserManager().find(uuid).getLvl();
        final AkcesoriaUser user = rpgcore.getAkcesoriaManager().find(uuid).getAkcesoriaUser();

        if (eventItem.getType() == Material.ITEM_FRAME) {

            if (eventItem.getItemMeta().getDisplayName() == null) {
                return;
            }

            if (eventItem.getItemMeta().getDisplayName().contains("Tarcza")) {

                if (user.getTarcza().length() == 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazona &cTarcze"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore().get(3))) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setTarcza(Utils.serializeItem(eventItem));
                    /*rpgcore.getPlayerManager().updatePlayerDef(uuid, rpgcore.getPlayerManager().getPlayerDef(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Obrona"));
                    rpgcore.getPlayerManager().updatePlayerBlok(uuid, rpgcore.getPlayerManager().getPlayerBlok(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Blok Ciosu"));
                    rpgcore.getPlayerManager().updatePlayerDamage(uuid, rpgcore.getPlayerManager().getPlayerDamage(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Obrazenia"));*/
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
            return;
        }

        if (eventItem.getType() == Material.STORAGE_MINECART) {
            if (eventItem.getItemMeta().getDisplayName() == null) {
                return;
            }

            if (eventItem.getItemMeta().getDisplayName().contains("Medalion")) {

                if (user.getMedalion().length() == 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazony &cMedalion"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore().get(3))) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setMedalion(Utils.serializeItem(eventItem));
                //rpgcore.getPlayerManager().updatePlayerPrzeszywka(uuid, rpgcore.getPlayerManager().getPlayerPrzeszywka(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Przeszycie Bloku"));
                //rpgcore.getPlayerManager().updatePlayerDamage(uuid, rpgcore.getPlayerManager().getPlayerDamage(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Obrazenia"));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
            return;
        }

        if (eventItem.getType() == Material.LEASH) {
            if (eventItem.getItemMeta().getDisplayName() == null) {
                return;
            }

            if (eventItem.getItemMeta().getDisplayName().contains("Pas")) {

                if (user.getPas().length() == 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazony &cPas"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore().get(3))) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setPas(Utils.serializeItem(eventItem));
                //rpgcore.getPlayerManager().updatePlayerKryt(uuid, rpgcore.getPlayerManager().getPlayerKryt(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Cios Krytyczny"));
                //rpgcore.getPlayerManager().updatePlayerSrednie(uuid, rpgcore.getPlayerManager().getPlayerSrednie(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Srednie Obrazenia"));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
            return;
        }

        if (eventItem.getType() == Material.HOPPER_MINECART) {
            if (eventItem.getItemMeta().getDisplayName() == null) {
                return;
            }

            if (eventItem.getItemMeta().getDisplayName().contains("Kolczyki")) {

                if (user.getKolczyki().length() == 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazone &cKolczyki"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore().get(3))) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setKolczyki(Utils.serializeItem(eventItem));
                //rpgcore.getPlayerManager().updatePlayerHP(uuid, rpgcore.getPlayerManager().getPlayerHP(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Dodatkowe HP"));
                //rpgcore.getPlayerManager().updatePlayerSilnyNaLudzi(uuid, rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Silny przeciwko Ludziom"));
                //player.setMaxHealth(player.getMaxHealth() + (double) rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 13, "Dodatkowe HP") * 2);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
            return;
        }

        if (eventItem.getType() == Material.GOLD_NUGGET) {
            if (eventItem.getItemMeta().getDisplayName() == null) {
                return;
            }

            if (eventItem.getItemMeta().getDisplayName().contains("Sygnet")) {

                if (user.getSygnet().length() == 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazony &cSygnet"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore().get(3))) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setSygnet(Utils.serializeItem(eventItem));
                //rpgcore.getPlayerManager().updatePlayerBlok(uuid, rpgcore.getPlayerManager().getPlayerBlok(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Blok Ciosu"));
                //rpgcore.getPlayerManager().updatePlayerHP(uuid, rpgcore.getPlayerManager().getPlayerHP(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Dodatkowe HP"));
                //player.setMaxHealth(player.getMaxHealth() + (double) rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 14, "Dodatkowe HP") * 2);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
            return;
        }

        if (eventItem.getType() == Material.MINECART) {
            if (eventItem.getItemMeta().getDisplayName() == null) {
                return;
            }

            if (eventItem.getItemMeta().getDisplayName().contains("Energia")) {

                if (user.getEnergia().length() == 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazona &cEnergie"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore().get(4))) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setEnergia(Utils.serializeItem(eventItem));
                //rpgcore.getPlayerManager().updatePlayerDef(uuid, rpgcore.getPlayerManager().getPlayerDef(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Obrona"));
                //rpgcore.getPlayerManager().updatePlayerBlok(uuid, rpgcore.getPlayerManager().getPlayerBlok(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Blok Ciosu"));
                //rpgcore.getPlayerManager().updatePlayerMinusSrednie(uuid, rpgcore.getPlayerManager().getPlayerMinusSrednie(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Srednie Obrazenia"));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
            return;
        }

        if (eventItem.getType() == Material.WATCH) {
            if (eventItem.getItemMeta().getDisplayName() == null) {
                return;
            }

            if (eventItem.getItemMeta().getDisplayName().contains("Zegarek")) {

                if (user.getZegarek().length() == 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazony &cZegarek"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore().get(3))) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setZegarek(Utils.serializeItem(eventItem));
                //rpgcore.getPlayerManager().updatePlayerDamage(uuid, rpgcore.getPlayerManager().getPlayerDamage(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Obrazenia"));
                //rpgcore.getPlayerManager().updatePlayerSilnyNaLudzi(uuid, rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Silny przeciwko Ludziom"));
                //rpgcore.getPlayerManager().updatePlayerMinusDef(uuid, rpgcore.getPlayerManager().getPlayerMinusDef(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToSet, "Obrona"));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
        }


    }

}
