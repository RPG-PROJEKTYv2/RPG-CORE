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


        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("help") ||
                    args[0].equalsIgnoreCase("pomoc")) {
                sendHelp(sender);
                return false;
            }

            reason.append("Brak Powodu");

            final UUID uuidplayerToBan = rpgcore.getPlayerUUID(args[0]);

            if (uuidplayerToBan == null) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            boolean silent = false;
            int i = 0;
            for (String test : args) {
                if (test.equalsIgnoreCase("-s")) {
                    silent = true;
                    args[i] = "";
                    break;
                }
                i += 1;
            }

            final String playerToBanName = rpgcore.getPlayerName(uuidplayerToBan);
            final String date = "data";

            rpgcore.getBanManager().banPlayer(senderName, uuidplayerToBan, String.valueOf(reason), silent, date, "Gracz &c" + playerToBanName + " &7zostal zablokowany na serwerze przez &c" + senderName + ". &7Wygasa: &4Nigdy&7. Powod: &c" + reason);


            return false;
        }

        if (args.length >= 2) {

            final UUID uuidplayerToBan = rpgcore.getPlayerUUID(args[0]);

            if (uuidplayerToBan == null) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            for (String arg : args) {
                if (((!(arg.equalsIgnoreCase(args[0]))))) {
                    reason.append(" ").append(arg);
                }
            }

            final String playerToBanName = rpgcore.getPlayerName(uuidplayerToBan);
            String fianlReason = String.valueOf(reason);
            final boolean silent = fianlReason.contains("-s");
            fianlReason = fianlReason.replace("-s", "");

            final String date = "data";

            rpgcore.getBanManager().banPlayer(senderName, uuidplayerToBan, fianlReason, silent, date, "Gracz &c" + playerToBanName + " &7zostal zablokowany na serwerze przez &c" + senderName + ". &7Wygasa: &4Nigdy&7. Powod: &c" + fianlReason);

            return true;
        }
        sendHelp(sender);
        return false;
    }
}
