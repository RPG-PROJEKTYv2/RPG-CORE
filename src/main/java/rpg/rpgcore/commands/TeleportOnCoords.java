package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Alerts;
import rpg.rpgcore.utils.Colorize;

public class TeleportOnCoords implements CommandExecutor {

    private final RPGCORE rpgcore;
    private final Colorize colorize;
    private final Alerts alerts;

    public TeleportOnCoords(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.alerts = rpgcore.getAlerts();
        this.colorize = rpgcore.getColorize();
    }

    public boolean onCommand(CommandSender sender, Command cdm, String label, String[] args) {

        if (!(sender instanceof Player)){
            colorize.sendMessage(sender,alerts.nieGracz());
            return false;
        }

        final Player p = (Player) sender;

        if (!(p.hasPermission("rpg-core.tpcoords"))){
            colorize.sendMessage(p,alerts.permisje("rpg-core.tpcoords"));
            return false;
        }

        if (args.length == 3){

            final String x = args[0];
            final String y = args[1];
            final String z = args[2];

            if (x == null || y == null || z == null) {
                alerts.poprawneUzycie(p,"/tpcoords [x] [y] [z] [swiat / puste]");
                return false;
            }
            rpgcore.getTeleportManager().teleportToLocation(p,Double.parseDouble(x),Double.parseDouble(y),Double.parseDouble(z));

            return true;
        }

        if (args.length == 4){

            if (!(p.hasPermission("rpg-core.tpcoords.to-other-world"))){
                colorize.sendMessage(p,alerts.permisje("rpg-core.tpcoords.to-other-world"));
                return false;
            }

            final String x = args[0];
            final String y = args[1];
            final String z = args[2];
            final World world = Bukkit.getWorld(args[3]);

            if (x == null || y == null || z == null || world == null) {
                alerts.poprawneUzycie(p,"/tpcoords [x] [y] [z] [swiat / puste]");
                return false;
            }
            rpgcore.getTeleportManager().teleportToLocation(p,Double.parseDouble(x),Double.parseDouble(y),Double.parseDouble(z),world);

            return true;
        }

        alerts.poprawneUzycie(p,"/tpcoords [x] [y] [z] [swiat / puste]");

        return false;
    }
}
