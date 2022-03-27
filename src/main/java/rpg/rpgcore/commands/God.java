package rpg.rpgcore.commands;

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

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.god"))) {
            player.sendMessage(Utils.permisje("rpg.god"));
            return false;
        }

        if (args.length == 0) {
            if (rpgcore.getGodManager().containsPlayer(player.getUniqueId())) {
                rpgcore.getGodManager().getGodList().remove(player.getUniqueId());
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7goda"));
                return false;
            }
            rpgcore.getGodManager().getGodList().add(player.getUniqueId());
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono &7goda"));

            return false;
        }

        return false;
    }
}
