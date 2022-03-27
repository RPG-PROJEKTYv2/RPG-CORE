package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

public class Heal implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player p = (Player) sender;

        if (!(p.hasPermission("rpg.heal"))) {
            p.sendMessage(Utils.permisje("rpg.heal"));
            return false;
        }

        if (args.length == 0) {

            p.setHealth(20.0);
            p.setFoodLevel(20);
            p.sendMessage(Utils.format("&aZostales uleczony!"));

            return false;
        }

        if (args.length == 1) {

            if (!(p.hasPermission("rpg.heal.others"))) {
                p.sendMessage(Utils.permisje("rpg.heal.others"));
                return false;
            }

            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                p.sendMessage(Utils.offline(args[0]));
                return false;
            }

            target.setHealth(20.0);
            target.setFoodLevel(20);
            target.sendMessage(Utils.format("&aZostales uleczony przez dobrego czleka!"));
            p.sendMessage(Utils.format("&aUleczono gracza " + p.getName()));

            return false;
        }

        p.sendMessage(Utils.poprawneUzycie("heal [gracz / puste]"));

        return false;
    }
}
