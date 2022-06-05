package rpg.rpgcore.msg;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Map;
import java.util.UUID;

public class Reply implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Reply(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;
        final UUID playerUUID = player.getUniqueId();

        if (!(player.hasPermission("rpg.reply"))) {
            player.sendMessage(Utils.permisje("rpg.reply"));
            return false;
        }

        if (args.length >= 1) {
            if (rpgcore.getMsgManager().isInMessageMapAsKey(playerUUID)) {
                final UUID targetUUID = rpgcore.getMsgManager().getTargetUUID(playerUUID);

                if (Bukkit.getPlayer(targetUUID) == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie odnaleziono podanego gracza"));
                    return false;
                }

                final Player target = Bukkit.getPlayer(targetUUID);
                final StringBuilder message = new StringBuilder();

                for (final String s : args) {
                    if (!(s.equalsIgnoreCase(""))){
                        message.append(" ").append(s);
                    }
                }

                rpgcore.getMsgManager().sendMessages(player, target, String.valueOf(message));
                return false;
            } else if (rpgcore.getMsgManager().isInMessageMapAsValue(playerUUID)) {
                UUID targetUUID = null;

                for (Map.Entry<UUID, UUID> entry : rpgcore.getMsgManager().getMessageMap().entrySet()){
                    if (entry.getValue() == playerUUID) {
                        targetUUID = entry.getKey();
                        break;
                    }
                }

                if (Bukkit.getPlayer(targetUUID) == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie odnaleziono podanego gracza"));
                    return false;
                }

                final Player target = Bukkit.getPlayer(targetUUID);
                final StringBuilder message = new StringBuilder();

                for (final String s : args) {
                    if (!(s.equalsIgnoreCase(""))){
                        message.append(" ").append(s);
                    }
                }

                rpgcore.getMsgManager().sendMessages(player, target, String.valueOf(message));
                return false;
            } else {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Jeszcze do nikogo nie napisales!"));
                return false;
            }
        }

        player.sendMessage(Utils.poprawneUzycie("r <wiadomosc>"));
        return false;
    }
}
