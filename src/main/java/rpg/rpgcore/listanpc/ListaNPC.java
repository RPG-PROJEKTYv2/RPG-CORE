package rpg.rpgcore.listanpc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class ListaNPC implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!player.hasPermission("rpg.listanpc")) {
            player.sendMessage(Utils.permisje("rpg.listanpc"));
            return false;
        }

        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("listanpc"));
            return false;
        }

        RPGCORE.getInstance().getListaNPCManager().openMainGUI(player);
        return false;
    }
}
