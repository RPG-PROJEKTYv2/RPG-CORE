package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.UUID;

public class KickCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public KickCommand(RPGCORE rpgcore) {
        super("kick");
        this.setRankLevel(RankType.KIDMOD);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        if (args.length < 1) {
            sender.sendMessage(Utils.format("&cUsage: /kick <player> [reason]"));
            return;
        }
        final String senderName = sender.getName();
        if (args.length == 1) {
            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return;
            }
            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("wyrzucic"));
                return;
            }
            final User user = this.rpgcore.getUserManager().find(args[0]);
            final UUID uuidPlayerToKick = user.getId();

            final Player playerToKick = Bukkit.getPlayer(uuidPlayerToKick);

            if (playerToKick == null) {
                sender.sendMessage(Utils.offline(args[0]));
                return;
            }

            rpgcore.getBanManager().kickPlayer(senderName, playerToKick, " Brak powodu", false);

            return;
        }

        final String playerToKickName = args[0];
        args[0] = "";

        if (!rpgcore.getUserManager().isUserName(playerToKickName)) {
            sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
            return;
        }
        if (playerToKickName.equalsIgnoreCase(senderName)) {
            sender.sendMessage(Utils.theSenderCannotBeTarget("wyrzucic"));
            return;
        }

        final User user = this.rpgcore.getUserManager().find(playerToKickName);
        final UUID uuidPlayerToKick = user.getId();
        final Player playerToKick = Bukkit.getPlayer(uuidPlayerToKick);

        if (playerToKick == null) {
            sender.sendMessage(Utils.offline(args[0]));
            return;
        }

        boolean silent = false;
        if (args[1].equalsIgnoreCase("-s")) {
            silent = true;
            args[1] = "";
        }

        final StringBuilder reason = new StringBuilder();
        for (final String arg : args) {
            if (!(arg.equalsIgnoreCase(""))) {
                reason.append(" ").append(arg);
            }
        }

        final String finalReason = String.valueOf(reason);

        rpgcore.getBanManager().kickPlayer(senderName, playerToKick, finalReason, silent);
    }
}
