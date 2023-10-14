package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class ItemShopCommand extends CommandAPI {
    public ItemShopCommand() {
        super("itemshop");
        this.setAliases(Arrays.asList("sklep", "is"));
        this.setRankLevel(RankType.HA);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        if (args.length < 2) {
            sender.sendMessage(Utils.poprawneUzycie("itemshop <ilosc_hs> <broadcast? tak/nie> <gracz>"));
            return;
        }
        if (args.length == 3) {
            final Player player = Bukkit.getPlayer(args[2]);
            if (player == null) {
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cGracz o nicku &4" + args[2] + " &cnie jest online!"));
                return;
            }
            final int iloscHS = Integer.parseInt(args[0]);
            if (args[1].equals("tak")) broadcast(player, iloscHS);
            give(player, iloscHS);
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aPakiet &e" + iloscHS + "&6&lH&4&lS &azostal pomyslnie nadany dla gracza &2" + player.getName() + "&a!"));
            log(sender.getName(), player.getName(), iloscHS, args[1]);
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz nadac Pakietu konsoli!"));
            return;
        }
        final Player player = (Player) sender;
        final int iloscHS = Integer.parseInt(args[0]);
        if (args[1].equals("tak")) broadcast(player, iloscHS);
        give(player, iloscHS);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPakiet &e" + iloscHS + "&6&lH&4&lS &azostal pomyslnie nadany dla Ciebie!"));
        log(sender.getName(), player.getName(), iloscHS, args[1]);
    }

    private void give(final Player player, final int iloscHS) {
        player.getInventory().addItem(GlobalItem.getHells(iloscHS).clone());
        RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(player, RPGCORE.getInstance().getNmsManager().makeTitle("&8&l[&6&lItemShop&8&l]", 5, 20, 5),
                RPGCORE.getInstance().getNmsManager().makeSubTitle("&7Pomyslnie zakupiles pakiet &e" + iloscHS + "&6&lH&4&lS&7! &6Dziekujemy!", 5, 20, 5));
    }

    private void log(final String senderName, final String playerName, final int iloscHS, final String broadcast) {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () ->
                RPGCORE.getDiscordBot().sendChannelMessage("itemshop-logs", EmbedUtil.create("**Zakup**", "**Gracz:** `" + playerName + "`" + "\n" +
                        "**Pakiet:** " + iloscHS + "HS\n" +
                        "**Nadajacy:** " + senderName + "\n" +
                        "**Broadcast:** " + broadcast.toUpperCase(Locale.ROOT) + "\n" +
                        "**Data:** " + Utils.dateFormat.format(new Date()), Color.ORANGE)));
    }

    private void broadcast(final Player player, final int iloscHS) {
        Bukkit.getServer().broadcastMessage(" ");
        Bukkit.getServer().broadcastMessage(Utils.format("               &6&lItem&2&lShop            "));
        Bukkit.getServer().broadcastMessage(Utils.format("  &aGracz &6" + player.getName() + " &azakupil pakiet &e" + iloscHS + "&6&lH&4&lS &a!"));
        Bukkit.getServer().broadcastMessage(Utils.format("  &6Dziekujemy za wspacie serwera. &cZespol Hellrpg.pl ❤"));
        Bukkit.getServer().broadcastMessage(Utils.format("  &6Rangi &7oraz &4&lHell&6&lS'y &7mozesz zakupic"));
        Bukkit.getServer().broadcastMessage(Utils.format("  &7na naszej stronie &3www.hellrpg.pl &7lub discordzie &3dc.hellrpg.pl"));
        Bukkit.getServer().broadcastMessage(Utils.format("               &6&lItem&2&lShop            "));
        Bukkit.getServer().broadcastMessage(" ");
    }
}
