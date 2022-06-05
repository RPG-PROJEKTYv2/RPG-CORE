package rpg.rpgcore.admin.teleport;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class TeleportCoords implements CommandExecutor {

    private final RPGCORE rpgcore;

    public TeleportCoords(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player p = (Player) sender;

        if (!(p.hasPermission("rpg.tpcoords"))) {
            p.sendMessage(Utils.permisje("rpg.tpcoords"));
            return false;
        }

        if (args.length == 3) {

            try {

                final double x = Double.parseDouble(args[0]);
                final double y = Double.parseDouble(args[1]);
                final double z = Double.parseDouble(args[2]);

                rpgcore.getTeleportManager().teleportToLocation(p, x, y, z);

            } catch (final NumberFormatException e) {

                p.sendMessage(Utils.poprawneUzycie("tpcoords [x] [y] [z] [swiat / puste] (używaj kropek nie przecinków!)"));

            }
            return false;
        }

        if (args.length == 4) {

            if (!(p.hasPermission("rpg.tpcoords.to-other-world"))) {
                p.sendMessage(Utils.permisje("rpg.tpcoords.to-other-world"));
                return false;
            }

            try {

                final double x = Double.parseDouble(args[0]);
                final double y = Double.parseDouble(args[1]);
                final double z = Double.parseDouble(args[2]);
                final World world = Bukkit.getWorld(args[3]);

                rpgcore.getTeleportManager().teleportToLocation(p, x, y, z, world);

            } catch (final NumberFormatException e) {

                p.sendMessage(Utils.poprawneUzycie("tpcoords [x] [y] [z] [swiat / puste] (uzywaj kropek nie przecinkow!)"));

            }

            return true;
        }

        p.sendMessage(Utils.poprawneUzycie("tpcoords [x] [y] [z] [swiat / puste]"));

        return false;
    }
}
