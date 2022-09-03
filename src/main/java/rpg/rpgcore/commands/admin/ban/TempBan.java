package rpg.rpgcore.commands.admin.ban;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
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
            final User user = rpgcore.getUserManager().find(args[0]);
            final UUID uuidPlayerToTempBan = user.getId();

            if (uuidPlayerToTempBan == null) {
                sender.sendMessage(Utils.BANPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            if (args[0].equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("zbanowac"));
                return false;
            }

            if (user.isBanned()) {
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

        if (args.length == 4) {
            if (!(args[2].equalsIgnoreCase("y") || args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("mm") || args[2].equalsIgnoreCase("s"))) {
                player.sendMessage(Utils.poprawneUzycie("tempban <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
                return false;
            }
            final String jednostka = args[2];
            final User user = rpgcore.getUserManager().find(args[0]);
            final UUID uuidPlayerToTempBan = user.getId();

            if (uuidPlayerToTempBan == null) {
                sender.sendMessage(Utils.BANPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            if (args[0].equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("zbanowac"));
                return false;
            }

            if (user.isBanned()) {
                sender.sendMessage(Utils.ALREADYBANNED);
                return false;
            }
            int time = 0;
            try {
                time = Integer.parseInt(args[1]);
            } catch (final NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.BANPREFIX + "&cMusisz podac liczbe calkowita"));
            }

            if (args[3].equalsIgnoreCase("-s")) {
                rpgcore.getBanManager().tempBanPlayer(player.getName(), uuidPlayerToTempBan, time, jednostka, true, "Brak Powodu!");
                return false;
            }

            args[0] = "";
            args[1] = "";
            args[2] = "";

            rpgcore.getBanManager().tempBanPlayer(player.getName(), uuidPlayerToTempBan, time, jednostka, false, String.valueOf(args[3]));
            return false;
        }

        if (args.length > 4) {
            boolean silent = false;
            if (!(args[2].equalsIgnoreCase("y") || args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("mm") || args[2].equalsIgnoreCase("s"))) {
                player.sendMessage(Utils.poprawneUzycie("tempban <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
                return false;
            }
            final String jednostka = args[2];
            final User user = rpgcore.getUserManager().find(args[0]);
            final UUID uuidPlayerToTempBan = user.getId();

            if (uuidPlayerToTempBan == null) {
                sender.sendMessage(Utils.BANPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            if (args[0].equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("zbanowac"));
                return false;
            }

            if (user.isBanned()) {
                sender.sendMessage(Utils.ALREADYBANNED);
                return false;
            }
            int time = 0;
            try {
                time = Integer.parseInt(args[1]);
            } catch (final NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.BANPREFIX + "&cMusisz podac liczbe calkowita"));
            }

            if (args[3].equalsIgnoreCase("-s")) {
                silent = true;
                args[3] = "";
            }
            args[0] = "";
            args[1] = "";
            args[2] = "";
            final StringBuilder reason = new StringBuilder();
            for (final String arg : args) {
                if (!(arg.equalsIgnoreCase(""))) {
                    reason.append(arg).append(" ");
                }
            }

            rpgcore.getBanManager().tempBanPlayer(player.getName(), uuidPlayerToTempBan, time, jednostka, silent, String.valueOf(reason));
            return false;
        }
        player.sendMessage(Utils.poprawneUzycie("tempban <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
        return false;
    }
}
