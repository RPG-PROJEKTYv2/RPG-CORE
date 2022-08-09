package rpg.rpgcore.commands.admin.teleport;

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

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player p = (Player) sender;

        if (!(p.hasPermission("rpg.tp"))) {
            p.sendMessage(Utils.permisje("rpg.tp"));
            return false;
        }

        if (args.length == 1) {

            if (!(rpgcore.getPlayerManager().getPlayersNames().contains(args[0]))) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

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
            if (!(p.hasPermission("rpg.tp.player-to-player"))) {
                p.sendMessage(Utils.permisje("rpg.tp.player-to-player"));
                return false;
            }

            if (!(rpgcore.getPlayerManager().getPlayersNames().contains(args[0])) && !(rpgcore.getPlayerManager().getPlayersNames().contains(args[1]))) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
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
