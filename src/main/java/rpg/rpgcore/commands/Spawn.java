package rpg.rpgcore.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Alerts;

import java.text.DecimalFormat;

public class Spawn implements CommandExecutor {

    private final Alerts alerts = new Alerts();

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        alerts.nieGracz(sender,"&c");

        final Player p = (Player) sender;

        alerts.permisje(p,"rpg.spawn");

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("set")){

                alerts.permisje(p,"rpg.spawn.set");

                final Location loc = p.getLocation();

                final DecimalFormat df = new DecimalFormat(".00");

                final double x = loc.getX();
                final double y = loc.getY();
                final double z = loc.getZ();
                final World w = loc.getWorld();
                final float pitch = loc.getPitch();
                final float yaw = loc.getYaw();

                final Location locSpawn = new Location(w,x,y,z,yaw,pitch);

            }
        }

        return false;
    }

}
