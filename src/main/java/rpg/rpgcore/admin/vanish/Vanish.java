package rpg.rpgcore.admin.vanish;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class Vanish implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Vanish(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }
        final Player p = (Player) sender;
        final UUID uuid = p.getUniqueId();

        if (!(p.hasPermission("rpg.vanish"))) {
            p.sendMessage(Utils.permisje("rpg.vanish"));
            return false;
        }

        if (args.length == 0) {

            if (!(rpgcore.getVanishManager().isVisible(uuid))) {
                rpgcore.getVanishManager().hidePlayer(p);
                return false;
            }
            rpgcore.getVanishManager().showPlayer(p);
            return false;
        }
        if (args.length == 1) {

            if (!(p.hasPermission("rpg.vanish.others"))) {
                p.sendMessage(Utils.permisje("rpg.vanish.others"));
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

            final UUID targetUUID = target.getUniqueId();

            if (!(rpgcore.getVanishManager().isVisible(targetUUID))) {
                rpgcore.getVanishManager().hidePlayer(p, target);
                return false;
            }
            rpgcore.getVanishManager().showPlayer(p, target);
            return false;


        }

        return false;
    }
}
