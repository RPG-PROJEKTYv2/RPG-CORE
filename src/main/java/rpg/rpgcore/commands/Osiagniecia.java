package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Osiagniecia implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Osiagniecia(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.os"))) {
            player.sendMessage(Utils.permisje("rpg.os"));
            return false;
        }

        if (args.length == 0) {
            player.openInventory(rpgcore.getOsManager().osGuiMain());
            return false;
        }

        player.sendMessage(Utils.poprawneUzycie("os"));
        return false;
    }
}
