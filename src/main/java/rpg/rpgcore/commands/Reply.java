package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Reply implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Reply(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.reply"))) {
            player.sendMessage(Utils.permisje("rpg.reply"));
            return false;
        }



        player.sendMessage(Utils.poprawneUzycie("r <wiadomosc>"));
        return false;
    }
}
