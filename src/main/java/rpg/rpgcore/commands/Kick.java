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

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player p = (Player) sender;
        final String playerName = p.getName();
        if (!(p.hasPermission("rpg.kick"))) {
            p.sendMessage(Utils.permisje("rpg.kick"));
            return false;
        }

        if (args.length == 1) {

            final UUID uuidTarget = rpgcore.getPlayerManager().getPlayerUUID(args[0]);
            if (uuidTarget != null) {
                final Player target = Bukkit.getPlayer(uuidTarget);

                if (target == null) {
                    p.sendMessage(Utils.SERVERNAME + Utils.offline(args[0]));
                    return true;
                }

                //TODO do dodania sprawdzanie czy zawiera -s

                rpgcore.getBanManager().kickPlayer(playerName, target, false);
                return true;
            }
            p.sendMessage(Utils.SERVERNAME + Utils.format("&cNie znaleziono podanego gracz"));
            return true;
        }

        if (args.length >= 2) {

            final UUID uuidTarget = rpgcore.getPlayerManager().getPlayerUUID(args[0]);
            if (uuidTarget != null) {
                final Player target = Bukkit.getPlayer(uuidTarget);

                if (target == null) {
                    p.sendMessage(Utils.SERVERNAME + Utils.offline(args[0]));
                    return true;
                }

                //TODO do dodania sprawdzanie czy zawiera -s

                final StringBuilder reason = new StringBuilder();

                for (String arg : args) {
                    if ((!(arg.equalsIgnoreCase(args[0])))) {
                        reason.append(" ").append(arg);
                    }
                }

                rpgcore.getBanManager().kickPlayer(playerName, target, reason, false);
                return true;
            }
            p.sendMessage(Utils.SERVERNAME + Utils.format("&cNie znaleziono podanego gracz"));
            return true;
        }

        p.sendMessage(Utils.SERVERNAME + Utils.poprawneUzycie("kick [gracz] [powod] "));

        return false;
    }
}
