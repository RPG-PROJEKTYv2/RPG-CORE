package rpg.rpgcore.msg;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

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
        final UUID playerUUID = player.getUniqueId();

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
            final UUID targetUUID = target.getUniqueId();

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
            if (rpgcore.getMsgManager().isInMessageMapAsKey(playerUUID)) {
                rpgcore.getMsgManager().updateMessageMap(playerUUID, targetUUID);
                return false;
            }

            rpgcore.getMsgManager().putInMessageMap(playerUUID, targetUUID);
            return false;
        }

        player.sendMessage(Utils.poprawneUzycie("msg <gracz> <wiadomosc>"));
        return false;
    }
}
