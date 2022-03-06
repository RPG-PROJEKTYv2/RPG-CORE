package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Alerts;
import rpg.rpgcore.utils.Colorize;

import java.text.DecimalFormat;

public class Spawn implements CommandExecutor {

    private final RPGCORE rpgcore;
    private final Colorize colorize;
    private final Alerts alerts;

    public Spawn(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.colorize = rpgcore.getColorize();
        this.alerts = rpgcore.getAlerts();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if(!(sender instanceof Player)){
            colorize.sendMessage(sender, alerts.nieGracz());
            return false;
        }

        final Player p = (Player) sender;

        if (!(p.hasPermission("rpg.spawn"))) {
            colorize.sendMessage(p, alerts.permisje("rpg.spawn"));
            return false;
        }

        if (args.length == 1) {


            if (args[0].equalsIgnoreCase("set")){

                if (!(p.hasPermission("rpg.spawn.set"))) {
                    colorize.sendMessage(p, alerts.permisje("rpg.spawn.set"));
                    return false;
                }

                final Location loc = p.getLocation();

                final DecimalFormat df = new DecimalFormat(".00");

                final double x = loc.getX();
                final double y = loc.getY();
                final double z = loc.getZ();
                final World w = loc.getWorld();
                final float pitch = loc.getPitch();
                final float yaw = loc.getYaw();

                final Location locSpawn = new Location(w,x,y,z,yaw,pitch);

                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().setSpawn(locSpawn));

                colorize.sendMessage(p, rpgcore.nazwaserwera + "&aPomyslnie ustawiono nowego spawna! Na kordach: " + " &7x: " + df.format(x) + " &7y: " + df.format(y) + " &7z: " + df.format(z) + " &7w swiecie " + w.getName());

                return false;
            } else if (Bukkit.getPlayer(args[0]) != null){
                if (!(p.hasPermission("rpg.spawn.otherplayer"))){
                    colorize.sendMessage(p, alerts.permisje("rpg.spawn.otherplayer"));
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);
                target.teleport(rpgcore.getSpawnManager().getSpawn());
                target.playSound(target.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                colorize.sendMessage(p, rpgcore.nazwaserwera + "&aZostales przeteleportowany na spawna przez administratora &7" + p.getName());
            }



            return false;
        } else if (args.length != 0){
            alerts.poprawneUzycie(p, "spawn");
        }

        p.teleport(rpgcore.getSpawnManager().getSpawn());
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        colorize.sendMessage(p, rpgcore.nazwaserwera + "&aPrzeteleportowano na spawna!");

        return false;
    }

}
