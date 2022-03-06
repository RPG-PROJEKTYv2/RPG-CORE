package rpg.rpgcore.commands;

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

    public boolean onCommand(CommandSender sender, Command cdm, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player p = (Player) sender;

        if (!(p.hasPermission("rpg-core.tpcoords"))) {
            p.sendMessage(Utils.permisje("rpg-core.tpcoords"));
            return false;
        }

        if (args.length == 3) {

            final String x = args[0];
            final String y = args[1];
            final String z = args[2];

            if (x == null || y == null || z == null) {
                p.sendMessage(Utils.poprawneUzycie("/tpcoords [x] [y] [z] [swiat / puste]"));
                return false;
            }
            rpgcore.getTeleportManager().teleportToLocation(p, Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));

            return true;
        }

        if (args.length == 4) {

            if (!(p.hasPermission("rpg-core.tpcoords.to-other-world"))) {
                p.sendMessage(Utils.permisje("rpg-core.tpcoords.to-other-world"));
                return false;
            }

            final String x = args[0];
            final String y = args[1];
            final String z = args[2];
            final World world = Bukkit.getWorld(args[3]);

            if (x == null || y == null || z == null || world == null) {
                p.sendMessage(Utils.poprawneUzycie("/tpcoords [x] [y] [z] [swiat / puste]"));
                return false;
            }
            rpgcore.getTeleportManager().teleportToLocation(p, Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z), world);

            return true;
        }

        p.sendMessage(Utils.poprawneUzycie("/tpcoords [x] [y] [z] [swiat / puste]"));

        return false;
    }
}
