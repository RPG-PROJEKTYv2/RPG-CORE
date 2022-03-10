package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class UnBan implements CommandExecutor {

    private final RPGCORE rpgcore;

    public UnBan(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (args.length == 1) {

            final UUID uuidTarget = rpgcore.getPlayerUUID(args[0]);

            if (uuidTarget == null) {
                sender.sendMessage(Utils.format(Utils.NIEMATAKIEGOGRACZA));
                return false;
            }

            final Player playerToUnBan = Bukkit.getPlayer(uuidTarget);

            rpgcore.getBanManager().unBanPlayer(playerToUnBan);

            return false;
        }
        sender.sendMessage(Utils.poprawneUzycie("unban [gracz]"));
        return false;
    }
}
