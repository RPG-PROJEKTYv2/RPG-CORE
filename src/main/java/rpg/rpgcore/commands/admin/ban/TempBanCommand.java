package rpg.rpgcore.commands.admin.ban;

import org.bukkit.command.CommandSender;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.UUID;

public class TempBanCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public TempBanCommand(RPGCORE rpgcore) {
        super("tempban");
        this.setRankLevel(RankType.KIDMOD);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {

        if (args.length < 3) {
            sender.sendMessage(Utils.poprawneUzycie("tempban <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
            return;
        }
        if (args.length == 3) {
            if (!(args[2].equalsIgnoreCase("y") || args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("mm") || args[2].equalsIgnoreCase("s"))) {
                sender.sendMessage(Utils.poprawneUzycie("tempban <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
                return;
            }

            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.BANPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return;
            }
            if (args[0].equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("zbanowac"));
                return;
            }
            final User user = rpgcore.getUserManager().find(args[0]);
            final UUID uuidPlayerToTempBan = user.getId();
            if (user.isBanned()) {
                sender.sendMessage(Utils.ALREADYBANNED);
                return;
            }
            int time = 0;
            try {
                time = Integer.parseInt(args[1]);
            } catch (final NumberFormatException e) {
                sender.sendMessage(Utils.format(Utils.BANPREFIX + "&cMusisz podac liczbe calkowita"));
            }
            rpgcore.getBanManager().tempBanPlayer(sender.getName(), uuidPlayerToTempBan, time, args[2], false, "Brak Powodu!");
            return;
        }

        if (args.length == 4) {
            if (!(args[2].equalsIgnoreCase("y") || args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("mm") || args[2].equalsIgnoreCase("s"))) {
                sender.sendMessage(Utils.poprawneUzycie("tempban <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
                return;
            }
            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.BANPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return;
            }
            if (args[0].equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("zbanowac"));
                return;
            }
            final String jednostka = args[2];
            final User user = rpgcore.getUserManager().find(args[0]);
            final UUID uuidPlayerToTempBan = user.getId();
            if (user.isBanned()) {
                sender.sendMessage(Utils.ALREADYBANNED);
                return;
            }
            int time = 0;
            try {
                time = Integer.parseInt(args[1]);
            } catch (final NumberFormatException e) {
                sender.sendMessage(Utils.format(Utils.BANPREFIX + "&cMusisz podac liczbe calkowita"));
            }

            if (args[3].equalsIgnoreCase("-s")) {
                rpgcore.getBanManager().tempBanPlayer(sender.getName(), uuidPlayerToTempBan, time, jednostka, true, "Brak Powodu!");
                return;
            }

            args[0] = "";
            args[1] = "";
            args[2] = "";

            rpgcore.getBanManager().tempBanPlayer(sender.getName(), uuidPlayerToTempBan, time, jednostka, false, String.valueOf(args[3]));
            return;
        }

        boolean silent = false;
        if (!(args[2].equalsIgnoreCase("y") || args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("mm") || args[2].equalsIgnoreCase("s"))) {
            sender.sendMessage(Utils.poprawneUzycie("tempban <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
            return;
        }
        if (!rpgcore.getUserManager().isUserName(args[0])) {
            sender.sendMessage(Utils.BANPREFIX + Utils.NIEMATAKIEGOGRACZA);
            return;
        }
        if (args[0].equalsIgnoreCase(sender.getName())) {
            sender.sendMessage(Utils.theSenderCannotBeTarget("zbanowac"));
            return;
        }
        final String jednostka = args[2];
        final User user = rpgcore.getUserManager().find(args[0]);
        final UUID uuidPlayerToTempBan = user.getId();
        if (user.isBanned()) {
            sender.sendMessage(Utils.ALREADYBANNED);
            return;
        }
        int time = 0;
        try {
            time = Integer.parseInt(args[1]);
        } catch (final NumberFormatException e) {
            sender.sendMessage(Utils.format(Utils.BANPREFIX + "&cMusisz podac liczbe calkowita"));
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

        rpgcore.getBanManager().tempBanPlayer(sender.getName(), uuidPlayerToTempBan, time, jednostka, silent, String.valueOf(reason));
    }
}
