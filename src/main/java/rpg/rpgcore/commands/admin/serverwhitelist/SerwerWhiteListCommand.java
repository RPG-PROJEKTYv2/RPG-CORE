package rpg.rpgcore.commands.admin.serverwhitelist;

import org.bukkit.command.CommandSender;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class SerwerWhiteListCommand extends CommandAPI {
    public SerwerWhiteListCommand() {
        super("serwerwhitelist");
        this.setAliases(Arrays.asList("swl", "servwerwl", "wls"));
        this.setRankLevel(RankType.HA);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        if (args.length == 0) {
            sender.sendMessage(Utils.poprawneUzycie("serwerwl <on/off/status/add/remove> [gracz]"));
            return;
        }
        if (args.length == 1) {
            if (args[0].equals("on")) {
                RPGCORE.getInstance().getSerwerWhiteListManager().getWhitelist().setEnabled(true);
                RPGCORE.getInstance().getSerwerWhiteListManager().save();
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono &7whiteliste na serwerze!"));
                return;
            }
            if (args[0].equals("off")) {
                RPGCORE.getInstance().getSerwerWhiteListManager().getWhitelist().setEnabled(false);
                RPGCORE.getInstance().getSerwerWhiteListManager().save();
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7whiteliste na serwerze!"));
                return;
            }
            if (args[0].equals("status")) {
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&7Whitelista na serwerze jest " +
                        (RPGCORE.getInstance().getSerwerWhiteListManager().getWhitelist().isEnabled() ? "&a&lWlaczona" : "&c&lWylaczona") + "&7!"));
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&7Graczy na whitelist: &a" + RPGCORE.getInstance().getSerwerWhiteListManager().getWhitelist().getWhitelisted().size()));
                for (final UUID uuid : RPGCORE.getInstance().getSerwerWhiteListManager().getWhitelist().getWhitelisted()) {
                    final User user = RPGCORE.getInstance().getUserManager().find(uuid);
                    if (user == null) continue;
                    sender.sendMessage(Utils.format("  &8- &6" + user.getName()));
                }
                return;
            }
            sender.sendMessage(Utils.poprawneUzycie("serwerwl <on/off/status/add> [gracz]"));
            return;
        }
        if (args.length == 2) {
            if (args[0].equals("add")) {
                final User user = RPGCORE.getInstance().getUserManager().find(args[1]);

                if (user == null) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza o nicku &7" + args[1] + "&c!"));
                    return;
                }

                if (RPGCORE.getInstance().getSerwerWhiteListManager().isWhiteListed(user.getId())) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz jest juz na whitelist!"));
                    return;
                }
                RPGCORE.getInstance().getSerwerWhiteListManager().addPlayer(user.getId());
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aDodano gracza &6" + args[1] + " &ado whitelisty!"));
                return;
            }
            if (args[0].equals("remove")) {
                final User user = RPGCORE.getInstance().getUserManager().find(args[1]);

                if (user == null) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza o nicku &7" + args[1] + "&c!"));
                    return;
                }

                if (!RPGCORE.getInstance().getSerwerWhiteListManager().isWhiteListed(user.getId())) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz nie jest na whitelist!"));
                    return;
                }
                RPGCORE.getInstance().getSerwerWhiteListManager().removePlayer(user.getId());
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aUsunieto gracza &6" + args[1] + " &az whitelisty!"));
            }
        }
    }
}
