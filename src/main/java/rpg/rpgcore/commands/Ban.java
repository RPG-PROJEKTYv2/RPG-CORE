package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;


public class Ban implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Ban(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private void sendHelp(final CommandSender sender) {
        sender.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-_-_-{&4&lBAN&8}-_-_-_-_-_-_-_-_-_-_-"));
        sender.sendMessage(Utils.format("&c/ban <gracz> [-s] [powod] &7- blokuje gracza na zawsze za podany powod, [-s] jesli ma sie nie pokazywac na chacie"));
        sender.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-_-_-{&4&lBAN&8}-_-_-_-_-_-_-_-_-_-_-"));
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender.hasPermission("rpg.ban"))) {
            sender.sendMessage(Utils.permisje("rpg.ban"));
            return false;
        }

        final StringBuilder reason = new StringBuilder();
        final String senderName = sender.getName();
        final String banExpiry = "Pernamentny";

        if (args.length == 1) {

            final UUID uuidPlayerToBan = rpgcore.getPlayerUUID(args[0]);

            if (uuidPlayerToBan == null) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("zbanowac"));
                return false;
            }

            if (rpgcore.isBanned(uuidPlayerToBan)) {
                sender.sendMessage(Utils.ALREADYBANNED);
                return false;
            }

            rpgcore.getBanManager().banPlayer(senderName, uuidPlayerToBan, " Brak powodu", false, banExpiry);

            return false;
        }

        if (args.length >= 2) {

            final UUID uuidPlayerToBan = rpgcore.getPlayerUUID(args[0]);
            args[0] = "";

            if (uuidPlayerToBan == null) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("zbanowac"));
                return false;
            }

            if (rpgcore.isBanned(uuidPlayerToBan)) {
                sender.sendMessage(Utils.ALREADYBANNED);
                return false;
            }

            boolean silent = false;
            if (args[1].equalsIgnoreCase("-s")) {
                silent = true;
                args[1] = "";
            }

            for (final String arg : args) {
                if (!(arg.equalsIgnoreCase(""))) {
                    reason.append(" ").append(arg);
                }
            }

            final String finalReason = String.valueOf(reason);

            rpgcore.getBanManager().banPlayer(senderName, uuidPlayerToBan, finalReason, silent, banExpiry);

            return false;
        }

        sendHelp(sender);

        return false;
    }
}
