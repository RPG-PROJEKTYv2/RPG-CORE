package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Message implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Message(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.msg"))) {
            player.sendMessage(Utils.permisje("rpg.msg"));
            return false;
        }

        if (args.length >= 2) {

            final StringBuilder message = new StringBuilder();

            if (Bukkit.getPlayer(args[0]) == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie odnaleziono podanego gracza"));
                return false;
            }

            final Player target = Bukkit.getPlayer(args[0]);

//            if (player == target) {
//                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie mozesz napisac sam do siebie"));
//                return false;
//            }

            if (!(target.isOnline())) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany gracz jest &coffline"));
                return false;
            }

            args[0] = "";

            for (final String arg : args) {
                if (!(arg.equalsIgnoreCase(""))) {
                    message.append(" ").append(arg);
                }
            }

            rpgcore.getMsgManager().sendMessages(player, target, String.valueOf(message));

            return false;
        }

        player.sendMessage(Utils.poprawneUzycie("msg <gracz> <wiadomosc>"));
        return false;
    }
}
