package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class Back implements CommandExecutor {
//jdsfd
    private final RPGCORE rpgcore;

    public Back(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;
        final UUID uuid = player.getUniqueId();

        if (!(player.hasPermission("rpg.back"))) {
            player.sendMessage(Utils.permisje("rpg.back"));
            return false;
        }

        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("back"));
            return false;
        }

        if (rpgcore.getTeleportManager().getBeforeTeleportLocation(uuid) == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNiestety nie znam miesjca, do ktorego moglbys sie cofnac"));
            return false;
        }

        player.teleport(rpgcore.getTeleportManager().getBeforeTeleportLocation(uuid));
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPrzeteleportowano do poprzedniej lokalizacji"));

        return false;
    }
}
