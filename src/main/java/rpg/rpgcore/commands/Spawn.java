package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Spawn implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Spawn(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player p = (Player) sender;

        if (!(p.hasPermission("rpg.spawn"))) {
            p.sendMessage(Utils.permisje("rpg.spawn"));
            return false;
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("set")) {

                if (!(p.hasPermission("rpg.spawn.set"))) {
                    p.sendMessage(Utils.permisje("rpg.spawn.set"));
                    return false;
                }

                final double x = p.getLocation().getX();
                final double y = p.getLocation().getY();
                final double z = p.getLocation().getZ();
                final World w = p.getLocation().getWorld();

                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().setSpawn(p.getLocation()));

                p.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie ustawiono nowego spawna! Na kordach: " + " &7x: " + Utils.df.format(x) + " &7y: " + Utils.df.format(y) + " &7z: " + Utils.df.format(z) + " &7w swiecie " + w.getName()));

                return false;

            }

            final Player target = Bukkit.getPlayer(args[0]);

            if (target != null) {

                if (!(p.hasPermission("rpg.spawn.otherplayer"))) {
                    p.sendMessage(Utils.permisje("rpg.spawn.otherplayer"));
                    return false;
                }

                target.teleport(rpgcore.getSpawnManager().getSpawn());
                target.playSound(target.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                p.sendMessage(Utils.format(Utils.SERVERNAME + "&aZostales przeteleportowany na spawna przez administratora &7" + p.getName()));
            }


            return false;
        }

        if (args.length != 0) {
            p.sendMessage(Utils.poprawneUzycie("spawn"));
        }

        p.teleport(rpgcore.getSpawnManager().getSpawn());
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.format(Utils.SERVERNAME + "&aPrzeteleportowano na spawna!"));

        return false;
    }

}
