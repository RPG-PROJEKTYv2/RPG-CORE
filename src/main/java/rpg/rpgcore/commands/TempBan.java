package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class TempBan implements CommandExecutor {

    private final RPGCORE rpgcore;

    public TempBan(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.tempban"))) {
            player.sendMessage(Utils.permisje("rpg.tempban"));
            return false;
        }

        if (args.length == 3) {

            if (!(args[2].equalsIgnoreCase("y") || args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("mm") || args[2].equalsIgnoreCase("s"))) {
                player.sendMessage(Utils.poprawneUzycie("tempban <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
                return false;
            }

            final UUID uuidPlayerToTempBan = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (uuidPlayerToTempBan == null) {
                sender.sendMessage(Utils.BANPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            if (args[0].equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("zbanowac"));
                return false;
            }

            if (rpgcore.getPlayerManager().isBanned(uuidPlayerToTempBan)) {
                sender.sendMessage(Utils.ALREADYBANNED);
                return false;
            }
            int time = 0;
            try {
                time = Integer.parseInt(args[1]);
            } catch (final NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.BANPREFIX + "&cMusisz podac liczbe calkowita"));
            }
            rpgcore.getBanManager().tempBanPlayer(player.getName(), uuidPlayerToTempBan, time, args[2], false, "Brak Powodu!");
            return false;
        }
        player.sendMessage(Utils.poprawneUzycie("tempban <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
        return false;
    }
}
