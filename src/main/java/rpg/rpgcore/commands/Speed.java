package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

public class Speed implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.speed"))) {
            player.sendMessage(Utils.permisje("rpg.speed"));
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(Utils.poprawneUzycie("speed <1-5>"));
            return false;
        }
        try {
            float speed = Integer.parseInt(args[0]);
            if (!(speed >= 1 && speed <= 5)) {
                player.sendMessage(Utils.poprawneUzycie("speed <1-5>"));
                return false;
            }
            if (player.isFlying()) {
                player.setFlySpeed(speed / 10);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ustawiono &cFLY &7speed na &c" + speed));
                return false;
            }
            player.setWalkSpeed(speed / 10);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ustawiono &cWALK &7speed na &c" + speed));
            return false;
        } catch (final NumberFormatException e) {
            player.sendMessage(Utils.poprawneUzycie("speed <1-5>"));
            return false;
        }
    }
}
