package rpg.rpgcore.commands;

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

        //TODO do przerobienia

        final String senderName = sender.getName();
        final StringBuilder reason = new StringBuilder();

        if (args.length == 1) {

            final UUID uuidTarget = rpgcore.getPlayerUUID(args[0]);
            if (uuidTarget != null) {
                final Player target = Bukkit.getPlayer(uuidTarget);

                if (target == null) {
                    sender.sendMessage(Utils.SERVERNAME + Utils.offline(args[0]));
                    return true;
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

                reason.append("Brak powodu");

                rpgcore.getBanManager().kickPlayer(senderName, target, String.valueOf(reason), silent, "&a&lGracz&7 " + target.getName() + "&a&lzostal wyrzucony z serwera przez&7 " + senderName);

                return true;
            }
            sender.sendMessage(Utils.SERVERNAME + Utils.GRACZOFFLINE);
            return true;
        }

        if (args.length >= 2) {

            final UUID uuidTarget = rpgcore.getPlayerUUID(args[0]);
            if (uuidTarget != null) {
                final Player target = Bukkit.getPlayer(uuidTarget);

                if (target == null) {
                    sender.sendMessage(Utils.SERVERNAME + Utils.offline(args[0]));
                    return true;
                }

                for (String arg : args) {
                    if (((!(arg.equalsIgnoreCase(args[0]))))) {
                        reason.append(" ").append(arg);
                    }
                }
                final String fianlReason = String.valueOf(reason).replace("-s", "");

                rpgcore.getBanManager().kickPlayer(senderName, target, String.valueOf(reason), fianlReason.contains("-s"), "&a&lGracz&7 " + target.getName() + "&a&lzostal wyrzucony z serwera przez&7 " + senderName);
                return true;
            }
            sender.sendMessage(Utils.SERVERNAME + Utils.format("&cNie znaleziono podanego gracz"));
            return true;
        }

        sender.sendMessage(Utils.poprawneUzycie("kick [gracz] [powod] "));

        return false;
    }
}
