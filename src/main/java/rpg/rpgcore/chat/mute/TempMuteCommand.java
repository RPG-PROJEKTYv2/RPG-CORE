package rpg.rpgcore.chat.mute;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.UUID;

public class TempMuteCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public TempMuteCommand(final RPGCORE rpgcore) {
        super("tempmute");
        this.setRankLevel(RankType.HELPER);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 3) {
            player.sendMessage(Utils.poprawneUzycie("tempMute <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
            return;
        }
        if (args.length == 3) {

            if (!(args[2].equalsIgnoreCase("y") || args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("mm") || args[2].equalsIgnoreCase("s"))) {
                player.sendMessage(Utils.poprawneUzycie("tempmute <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
                return;
            }
            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.MUTEPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return;
            }

            if (args[0].equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("wyciszyc"));
                return;
            }
            final User user = rpgcore.getUserManager().find(args[0]);
            final UUID uuidPlayerToTempMute = user.getId();
            if (user.isMuted()) {
                sender.sendMessage(Utils.ALREADYMUTED);
                return;
            }
            int time = 0;
            try {
                time = Integer.parseInt(args[1]);
            } catch (final NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.MUTEPREFIX + "&cMusisz podac liczbe calkowita"));
            }
            rpgcore.getMuteManager().tempMutePlayer(player.getName(), uuidPlayerToTempMute, time, args[2], false, "Brak Powodu!");
            return;
        }

        if (args.length == 4) {
            if (!(args[2].equalsIgnoreCase("y") || args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("mm") || args[2].equalsIgnoreCase("s"))) {
                player.sendMessage(Utils.poprawneUzycie("tempMute <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
                return;
            }
            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.MUTEPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return;
            }

            if (args[0].equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("wyciszyc"));
                return;
            }
            final String jednostka = args[2];
            final User user = rpgcore.getUserManager().find(args[0]);
            final UUID uuidPlayerToTempMute = user.getId();
            if (user.isMuted()) {
                sender.sendMessage(Utils.ALREADYMUTED);
                return;
            }
            int time = 0;
            try {
                time = Integer.parseInt(args[1]);
            } catch (final NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.MUTEPREFIX + "&cMusisz podac liczbe calkowita"));
            }

            if (args[3].equalsIgnoreCase("-s")) {
                rpgcore.getMuteManager().tempMutePlayer(player.getName(), uuidPlayerToTempMute, time, jednostka, true, "Brak Powodu!");
                return;
            }

            args[0] = "";
            args[1] = "";
            args[2] = "";

            rpgcore.getMuteManager().tempMutePlayer(player.getName(), uuidPlayerToTempMute, time, jednostka, false, String.valueOf(args[3]));
            return;
        }
        boolean silent = false;
        if (!(args[2].equalsIgnoreCase("y") || args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("mm") || args[2].equalsIgnoreCase("s"))) {
            player.sendMessage(Utils.poprawneUzycie("tempMute <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
            return;
        }
        if (!rpgcore.getUserManager().isUserName(args[0])) {
            sender.sendMessage(Utils.MUTEPREFIX + Utils.NIEMATAKIEGOGRACZA);
            return;
        }

        if (args[0].equalsIgnoreCase(sender.getName())) {
            sender.sendMessage(Utils.theSenderCannotBeTarget("wyciszyc"));
            return;
        }
        final String jednostka = args[2];
        final User user = rpgcore.getUserManager().find(args[0]);
        final UUID uuidPlayerToTempMute = user.getId();
        if (user.isMuted()) {
            sender.sendMessage(Utils.ALREADYMUTED);
            return;
        }
        int time = 0;
        try {
            time = Integer.parseInt(args[1]);
        } catch (final NumberFormatException e) {
            player.sendMessage(Utils.format(Utils.MUTEPREFIX + "&cMusisz podac liczbe calkowita"));
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

        rpgcore.getMuteManager().tempMutePlayer(player.getName(), uuidPlayerToTempMute, time, jednostka, silent, String.valueOf(reason));

    }
}
