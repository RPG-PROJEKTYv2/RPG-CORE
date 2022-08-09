package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Fly implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Fly(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private void fly(Player p) {
        if (!(p.isFlying())) {
            p.setAllowFlight(true);
            p.setFlying(true);
            p.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczano &7fly"));
        } else {
            p.setAllowFlight(false);
            p.setFlying(false);
            p.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7fly"));
        }
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender.hasPermission("rpg.fly"))) {
            sender.sendMessage(Utils.permisje("rpg.fly"));
            return false;
        }

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.NIEGRACZ);
                return false;
            }

            final Player p = (Player) sender;

            this.fly(p);

            return false;
        }

        if (args.length == 1) {

            if (!(sender.hasPermission("rpg.fly.others"))) {
                sender.sendMessage(Utils.permisje("rpg.fly.others"));
                return false;
            }

            if (args[0].equals(sender.getName())) {
                sender.sendMessage(Utils.format("&cNie mozesz w ten sposob!!"));
                return false;
            }

            final Player target = Bukkit.getPlayer(args[0]);

            if (!(rpgcore.getPlayerManager().getPlayersNames().contains(args[0]))) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            if (target == null) {
                sender.sendMessage(Utils.offline(args[0]));
                return false;
            }

            this.fly(target);

            return false;

        }

        return false;

    }
}
