package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Teleport implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Teleport(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player p = (Player) sender;

        if (!(p.hasPermission("rpg-core.tp"))) {
            p.sendMessage(Utils.permisje("rpg-core.tp"));
            return false;
        }

        if (args.length == 1) {
            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                p.sendMessage(Utils.offline(args[0]));
                return false;
            }

            if (p == target) {
                p.sendMessage(Utils.SERVERNAME + Utils.format("&cNie mozesz sie przeteleportowac do samego siebie!"));
                return false;
            }

            rpgcore.getTeleportManager().teleportToTarget(p, target);
            return true;
        }

        if (args.length == 2) {
            if (!(p.hasPermission("rpg-core.tp.player-to-player"))) {
                p.sendMessage(Utils.permisje("rpg-core.tp.player-to-player"));
                return false;
            }

            final Player playerToTeleport = Bukkit.getPlayer(args[0]);
            final Player target = Bukkit.getPlayer(args[1]);

            if (target == null || playerToTeleport == null) {
                p.sendMessage(Utils.offline(args[0]));
                return false;
            }
            rpgcore.getTeleportManager().playerToTeleport(p, playerToTeleport, target);
            return true;
        }
        p.sendMessage(Utils.poprawneUzycie("tp [gracz]"));

        return false;
    }
}
