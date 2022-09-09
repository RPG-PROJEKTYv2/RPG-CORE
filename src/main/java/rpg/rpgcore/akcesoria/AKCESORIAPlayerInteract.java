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
import rpg.rpgcore.bonuses.BonusesUser;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AKCESORIAPlayerInteract implements Listener {

    private final RPGCORE rpgcore;

    public AKCESORIAPlayerInteract(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public int getIntFromString(final List<String> lore) {
        for (final String item : lore) {
            try {
                if (Utils.removeColor(item).contains("Wymagany Poziom: ")) {
                    return Integer.parseInt(Utils.removeColor(item).replace("Wymagany Poziom: ", ""));
                }
            } catch (NumberFormatException e) {
                continue;
            }
        }
        return 0;
    }

    public int getIntFromString(String string) {
        String string1 = string.replaceAll("%", "");
        String[] items = string1.split(" ");
        for (final String item : items) {
            try {
                return Integer.parseInt(Utils.removeColor(item));
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

        if (eventItem == null) {
            return;
        }

        if (eventItem.getType() == Material.ITEM_FRAME) {

            if (eventItem.getItemMeta().getDisplayName() == null) {
                return;
            }

            if (eventItem.getItemMeta().getDisplayName().contains("Tarcza")) {
                e.setCancelled(true);

                if (user.getTarcza().length() > 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazona &cTarcze"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore())) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setTarcza(Utils.serializeItem(eventItem));
                BonusesUser bonusUser = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
                bonusUser.setSredniadefensywa(bonusUser.getSredniadefensywa() + getIntFromString(eventItem.getItemMeta().getLore().get(0)));
                bonusUser.setBlokciosu(bonusUser.getBlokciosu() + getIntFromString(eventItem.getItemMeta().getLore().get(1)));
                bonusUser.setDodatkoweobrazenia(bonusUser.getDodatkoweobrazenia() + getIntFromString(eventItem.getItemMeta().getLore().get(2)));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium!**",
                        "**Typ: **`"  + eventItem.getType() + "`\n"
                                + "**Akcesorium:** " + eventItem.getItemMeta(), Color.getHSBColor(114, 90, 47)));
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
            return;
        }

        if (eventItem.getType() == Material.FIREBALL) {
            if (eventItem.getItemMeta().getDisplayName() == null) {
                return;
            }

            if (eventItem.getItemMeta().getDisplayName().contains("Medalion")) {
                e.setCancelled(true);

                if (user.getMedalion().length() > 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazony &cMedalion"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore())) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setMedalion(Utils.serializeItem(eventItem));
                BonusesUser bonusUser = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
                bonusUser.setPrzeszyciebloku(bonusUser.getPrzeszyciebloku() + getIntFromString(eventItem.getItemMeta().getLore().get(0)));
                bonusUser.setDodatkoweobrazenia(bonusUser.getDodatkoweobrazenia() + getIntFromString(eventItem.getItemMeta().getLore().get(1)));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium!**",
                        "**Typ: **`"  + eventItem.getType() + "`\n"
                                + "**Akcesorium:** " + eventItem.getItemMeta(), Color.getHSBColor(114, 90, 47)));
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
                e.setCancelled(true);

                if (user.getPas().length() > 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazony &cPas"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore())) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setPas(Utils.serializeItem(eventItem));
                BonusesUser bonusUser = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
                bonusUser.setSzansanakryta(bonusUser.getSzansanakryta() + getIntFromString(eventItem.getItemMeta().getLore().get(0)));
                bonusUser.setSrednieobrazenia(bonusUser.getSrednieobrazenia() + getIntFromString(eventItem.getItemMeta().getLore().get(1)));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium!**",
                        "**Typ: **`"  + eventItem.getType() + "`\n"
                                + "**Akcesorium:** " + eventItem.getItemMeta(), Color.getHSBColor(114, 90, 47)));
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
                e.setCancelled(true);

                if (user.getKolczyki().length() > 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazone &cKolczyki"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore())) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setKolczyki(Utils.serializeItem(eventItem));
                BonusesUser bonusUser = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
                bonusUser.setDodatkowehp(bonusUser.getDodatkowehp() + getIntFromString(eventItem.getItemMeta().getLore().get(0)));
                bonusUser.setSilnynaludzi(bonusUser.getSilnynaludzi() + getIntFromString(eventItem.getItemMeta().getLore().get(1)));
                player.setMaxHealth((double) bonusUser.getDodatkowehp() * 2);
                player.setHealth(player.getMaxHealth());
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium!**",
                        "**Typ: **`"  + eventItem.getType() + "`\n"
                                + "**Akcesorium:** " + eventItem.getItemMeta(), Color.getHSBColor(114, 90, 47)));
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
                e.setCancelled(true);

                if (user.getSygnet().length() > 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazony &cSygnet"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore())) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setSygnet(Utils.serializeItem(eventItem));
                BonusesUser bonusUser = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
                bonusUser.setDodatkowehp(bonusUser.getDodatkowehp() + getIntFromString(eventItem.getItemMeta().getLore().get(0)));
                bonusUser.setSilnynapotwory(bonusUser.getSilnynapotwory() + getIntFromString(eventItem.getItemMeta().getLore().get(1)));
                player.setMaxHealth((double) bonusUser.getDodatkowehp() * 2);
                player.setHealth(player.getMaxHealth());
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium!**",
                        "**Typ: **`"  + eventItem.getType() + "`\n"
                                + "**Akcesorium:** " + eventItem.getItemMeta(), Color.getHSBColor(114, 90, 47)));
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
                e.setCancelled(true);

                if (user.getEnergia().length() > 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazona &cEnergie"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore())) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setEnergia(Utils.serializeItem(eventItem));
                BonusesUser bonusUser = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
                bonusUser.setSredniadefensywa(bonusUser.getSredniadefensywa() + getIntFromString(eventItem.getItemMeta().getLore().get(0)));
                bonusUser.setBlokciosu(bonusUser.getBlokciosu() + getIntFromString(eventItem.getItemMeta().getLore().get(1)));
                bonusUser.setMinussrednieobrazenia(bonusUser.getMinussrednieobrazenia() + getIntFromString(eventItem.getItemMeta().getLore().get(2)));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium!**",
                        "**Typ: **`"  + eventItem.getType() + "`\n"
                                + "**Akcesorium:** " + eventItem.getItemMeta(), Color.getHSBColor(114, 90, 47)));
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
                e.setCancelled(true);

                if (user.getZegarek().length() > 0) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazony &cZegarek"));
                    return;
                }

                if (eventItem.getAmount() != 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                    return;
                }

                if (lvl < getIntFromString(eventItem.getItemMeta().getLore())) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                    return;
                }
                user.setZegarek(Utils.serializeItem(eventItem));
                BonusesUser bonusUser = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
                bonusUser.setDefnamoby(bonusUser.getDefnamoby() + getIntFromString(eventItem.getItemMeta().getLore().get(0)));
                bonusUser.setSilnynapotwory(bonusUser.getSilnynapotwory() + getIntFromString(eventItem.getItemMeta().getLore().get(1)));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + eventItem.getItemMeta().getDisplayName()));
                player.setItemInHand(null);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium!**",
                        "**Typ: **`"  + eventItem.getType() + "`\n"
                                + "**Akcesorium:** " + eventItem.getItemMeta(), Color.getHSBColor(114, 90, 47)));
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
        }


    }

}
