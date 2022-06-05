package rpg.rpgcore.admin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class Kick implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Kick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender.hasPermission("rpg.kick"))) {
            sender.sendMessage(Utils.permisje("rpg.kick"));
            return false;
        }

        final String senderName = sender.getName();
        if (args.length == 1) {

            final UUID uuidPlayerToKick = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (!(rpgcore.getPlayerManager().getPlayers().contains(uuidPlayerToKick))) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            final Player playerToKick = Bukkit.getPlayer(uuidPlayerToKick);

            if (playerToKick == null) {
                sender.sendMessage(Utils.offline(args[0]));
                return false;
            }

            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("wyrzucic"));
                return false;
            }

            rpgcore.getBanManager().kickPlayer(senderName, playerToKick, " Brak powodu", false);

            return false;
        }

        if (args.length >= 2) {

            final String playerToKickName = args[0];
            args[0] = "";

            final UUID uuidPlayerToKick = rpgcore.getPlayerManager().getPlayerUUID(playerToKickName);

            if (!(rpgcore.getPlayerManager().getPlayers().contains(uuidPlayerToKick))) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            final Player playerToKick = Bukkit.getPlayer(uuidPlayerToKick);

            if (playerToKick == null) {
                sender.sendMessage(Utils.offline(args[0]));
                return false;
            }

            if (playerToKickName.equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("wyrzucic"));
                return false;
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

            return false;
        }

        sender.sendMessage(Utils.poprawneUzycie("kick [gracz] [powod] "));

        return false;
    }
}
