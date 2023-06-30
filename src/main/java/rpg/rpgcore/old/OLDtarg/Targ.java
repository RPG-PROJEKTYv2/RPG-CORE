package rpg.rpgcore.old.OLDtarg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Targ implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Targ(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.OLDtarg"))) {
            player.sendMessage(Utils.permisje("rpg.OLDtarg"));
            return false;
        }

        if (args.length == 0) {
            player.openInventory(rpgcore.getTargManager().openTargGUI(1));
            return false;
        }


        player.sendMessage(Utils.poprawneUzycie("OLDtarg"));
        return false;
    }
}
