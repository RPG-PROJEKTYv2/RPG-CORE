package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;


public class Pomoc implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Pomoc(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (args.length == 0) {

            player.openInventory(rpgcore.getPomocManager().pomocGUIMAIN());
            return false;
        }
        player.sendMessage("poprawne uzycie /pomoc");
        return false;
    }
}
