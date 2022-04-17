package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class Akcesoria implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Akcesoria(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;
        final UUID uuid = player.getUniqueId();

        if (args.length == 0) {
            if (!(rpgcore.getAkcesoriaManager().getAkcesoriaMap().containsKey(uuid))) {
                rpgcore.getAkcesoriaManager().createAkcesoriaGUINew(uuid);
            }
            player.openInventory(rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid));
            return false;
        }


        return false;
    }
}
