package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Back implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Back(RPGCORE rpgcore) {this.rpgcore = rpgcore;}

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (args.length > 0){
            player.sendMessage(Utils.poprawneUzycie("back"));
            return false;
        }
        if (!(rpgcore.getTeleportManager().getBeforeTeleportLocation().containsKey(player.getUniqueId()))) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNiestety nie znam miesjca, do ktorego moglbys sie cofnac"));
            return false;
        }

        player.teleport(rpgcore.getTeleportManager().getBeforeTeleportLocation().get(player.getUniqueId()));
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aTeleportowano do poprzedniej lokalizacji"));

        return false;
    }
}
