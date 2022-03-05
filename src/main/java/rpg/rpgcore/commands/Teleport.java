package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Alerts;
import rpg.rpgcore.utils.Colorize;

public class Teleport implements CommandExecutor {

    private final RPGCORE rpgcore;
    private final Colorize colorize;
    private final Alerts alerts;

    public Teleport(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.alerts = rpgcore.getAlerts();
        this.colorize = rpgcore.getColorize();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)){
            colorize.sendMessage(sender,alerts.nieGracz());
            return false;
        }

        final Player p = (Player) sender;

        if (!(p.hasPermission("rpg-core.tp"))){
            colorize.sendMessage(p,alerts.permisje("rpg-core.tp"));
            return false;
        }

        if (args.length == 1){
            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null){
                colorize.sendMessage(p, alerts.offline(args[0]));
                return false;
            }

            rpgcore.getTeleportManager().teleportToTarget(p,target);
            return true;
        }

        if (args.length == 2){
            if (!(p.hasPermission("rpg-core.tp.player-to-player"))){
                colorize.sendMessage(p,alerts.permisje("rpg-core.tp.player-to-player"));
                return false;
            }
            final Player playerToTeleport = Bukkit.getPlayer(args[0]);
            final Player target = Bukkit.getPlayer(args[1]);
            if (target == null || playerToTeleport == null){
                colorize.sendMessage(p, alerts.offline(args[0]));
                return false;
            }
            rpgcore.getTeleportManager().playerToTeleport(p,playerToTeleport,target);
            return true;
        }
        alerts.poprawneUzycie(p,"/tp [gracz]");

        return false;
    }
}
