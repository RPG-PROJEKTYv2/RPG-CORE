package rpg.rpgcore.admin.god;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class God implements CommandExecutor {

    private final RPGCORE rpgcore;

    public God(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player p = (Player) sender;

        if (!(p.hasPermission("rpg.god"))) {
            p.sendMessage(Utils.permisje("rpg.god"));
            return false;
        }

        if (args.length == 0) {

            rpgcore.getGodManager().changeStatusGod(p);

            return false;
        }

        if (args.length == 1) {

            if (!(p.hasPermission("rpg.god.others"))) {
                p.sendMessage(Utils.permisje("rpg.god.others"));
                return false;
            }

            if (!(rpgcore.getPlayerManager().getPlayersNames().contains(args[0]))) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                p.sendMessage(Utils.offline(args[0]));
                return false;
            }

            rpgcore.getGodManager().changeStatusGod(target);

            return false;
        }

        p.sendMessage(Utils.poprawneUzycie("god [gracz / puste]"));

        return false;
    }
}
