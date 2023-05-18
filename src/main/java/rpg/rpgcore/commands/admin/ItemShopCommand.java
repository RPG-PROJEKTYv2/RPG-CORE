package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.ItemShop;

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
        if (args.length < 1) {
            sender.sendMessage(Utils.poprawneUzycie("itemshop <nazwa_pakietu/list> <broadcast? tak/nie> [gracz]"));
            return;
        }
        if (args[0].equals("list")) {
            ItemShop.listAll(sender);
            return;
        }
        if (args.length < 2) {
            sender.sendMessage(Utils.poprawneUzycie("itemshop <nazwa_pakietu/list> <broadcast? tak/nie> [gracz]"));
            return;
        }
        if (args.length == 3) {
            final Player player = Bukkit.getPlayer(args[2]);
            if (player == null) {
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cGracz o nicku &4" + args[2] + " &cnie jest online!"));
                return;
            }
            final ItemShop is = ItemShop.getByName(args[0]);
            if (is == null) {
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono podanego pakietu!"));
                return;
            }
            if (args[1].equals("tak")) broadcast(player, is);
            give(player, is);
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aPakiet &2" + is.getName() + " &azostal pomyslnie nadany dla gracza &2" + player.getName() + "&a!"));
            log(sender.getName(), player.getName(), is, args[1]);
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz nadac Pakietu konsoli!"));
            return;
        }
        final Player player = (Player) sender;
        final ItemShop is = ItemShop.getByName(args[0]);
        if (is == null) {
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono podanego pakietu!"));
            return;
        }
        if (args[1].equals("tak")) broadcast(player, is);
        give(player, is);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPakiet &2" + is.getName() + " &azostal pomyslnie nadany dla Ciebie!"));
        log(sender.getName(), player.getName(), is, args[1]);
    }

    private void give(final Player player, final ItemShop is) {
        for (ItemStack item : is.getItems()) {
            player.getInventory().addItem(item);
        }
        final String pakietName = is.getName().replaceAll("_", " ").replace("HS", Utils.format("&4&lH&6&lS")).replace("Vip", Utils.format("&e&lVip"))
                .replace("Svip", Utils.format("&6&lS&e&lvip")).replace("ELITA", Utils.format("&5&lELITA"));
        RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(player, RPGCORE.getInstance().getNmsManager().makeTitle("&8&l[&6&lItemShop&8&l]", 5, 20, 5),
                RPGCORE.getInstance().getNmsManager().makeSubTitle("&7Pomyslnie zakupiles pakiet &6" + pakietName + "&7! &6Dziekujemy!", 5, 20, 5));
    }

    private void log(final String senderName, final String playerName, final ItemShop is, final String broadcast) {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () ->
                RPGCORE.getDiscordBot().sendChannelMessage("itemshop-logs", EmbedUtil.create("**Zakup**", "**Gracz:** `" + playerName + "`" + "\n" +
                        "**Pakiet:** " + is.getName().replaceAll("_", " ") + "\n" +
                        "**Nadajacy:** " + senderName + "\n" +
                        "**Broadcast:** " + broadcast.toUpperCase(Locale.ROOT) + "\n" +
                        "**Data:** " + Utils.dateFormat.format(new Date()), Color.ORANGE)));
    }

    private void broadcast(final Player player, final ItemShop is) {
        final String pakietName = is.getName().replaceAll("_", " ").replace("HS", Utils.format("&4&lH&6&lS")).replace("Vip", Utils.format("&e&lVip"))
                .replace("Svip", Utils.format("&6&lS&e&lvip")).replace("ELITA", Utils.format("&5&lELITA"));
        Bukkit.getServer().broadcastMessage(" ");
        Bukkit.getServer().broadcastMessage(Utils.format("               &6&lItem&2&lShop            "));
        Bukkit.getServer().broadcastMessage(Utils.format("  &aGracz &6" + player.getName() + " &azakupil pakiet &6" + pakietName + " &a!"));
        Bukkit.getServer().broadcastMessage(Utils.format("  &6Dziekujemy za wspacie servera. &cZespol Hellrpg.pl ❤"));
        Bukkit.getServer().broadcastMessage(Utils.format("  &6Rangi &7oraz &4&lHell&6&lS'y &7mozesz zakupic"));
        Bukkit.getServer().broadcastMessage(Utils.format("  &7na naszej stronie &3www.hellrpg.pl &7lub discordzie &3dc.hellrpg.pl"));
        Bukkit.getServer().broadcastMessage(Utils.format("               &6&lItem&2&lShop            "));
        Bukkit.getServer().broadcastMessage(" ");
    }
}
