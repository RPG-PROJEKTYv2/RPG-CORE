package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class TempMute implements CommandExecutor {

    private final RPGCORE rpgcore;
    
    public TempMute(final RPGCORE rpgcore) {
        this.rpgcore= rpgcore;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.tempmute"))) {
            player.sendMessage(Utils.permisje("rpg.tempmute"));
            return false;
        }

        if (args.length == 3) {

            if (!(args[2].equalsIgnoreCase("y") || args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("mm") || args[2].equalsIgnoreCase("s"))) {
                player.sendMessage(Utils.poprawneUzycie("tempmute <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
                return false;
            }

            final UUID uuidPlayerToTempMute = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (uuidPlayerToTempMute == null) {
                sender.sendMessage(Utils.MUTEPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            /*if (args[0].equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("wyciszyc"));
                return false;
            }*/

            if (rpgcore.getPlayerManager().isMuted(uuidPlayerToTempMute)) {
                sender.sendMessage(Utils.ALREADYMUTED);
                return false;
            }
            int time = 0;
            try {
                time = Integer.parseInt(args[1]);
            } catch (final NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.MUTEPREFIX + "&cMusisz podac liczbe calkowita"));
            }
            rpgcore.getMuteManager().tempMutePlayer(player.getName(), uuidPlayerToTempMute, time, args[2], false, "Brak Powodu!");
            return false;
        }

        if (args.length == 4) {
            if (!(args[2].equalsIgnoreCase("y") || args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("mm") || args[2].equalsIgnoreCase("s"))) {
                player.sendMessage(Utils.poprawneUzycie("tempMute <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
                return false;
            }
            final String jednostka = args[2];

            final UUID uuidPlayerToTempMute = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (uuidPlayerToTempMute == null) {
                sender.sendMessage(Utils.MUTEPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            if (args[0].equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("zMuteowac"));
                return false;
            }

            if (rpgcore.getPlayerManager().isMuted(uuidPlayerToTempMute)) {
                sender.sendMessage(Utils.ALREADYMUTED);
                return false;
            }
            int time = 0;
            try {
                time = Integer.parseInt(args[1]);
            } catch (final NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.MUTEPREFIX + "&cMusisz podac liczbe calkowita"));
            }

            if (args[3].equalsIgnoreCase("-s")) {
                rpgcore.getMuteManager().tempMutePlayer(player.getName(), uuidPlayerToTempMute, time, jednostka, true, "Brak Powodu!");
                return false;
            }

            args[0] = "";
            args[1] = "";
            args[2] = "";

            rpgcore.getMuteManager().tempMutePlayer(player.getName(), uuidPlayerToTempMute, time, jednostka, false, String.valueOf(args[3]));
            return false;
        }

        if (args.length > 4) {
            boolean silent = false;
            if (!(args[2].equalsIgnoreCase("y") || args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("mm") || args[2].equalsIgnoreCase("s"))) {
                player.sendMessage(Utils.poprawneUzycie("tempMute <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
                return false;
            }
            final String jednostka = args[2];
            final UUID uuidPlayerToTempMute = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (uuidPlayerToTempMute == null) {
                sender.sendMessage(Utils.MUTEPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            if (args[0].equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("zMuteowac"));
                return false;
            }

            if (rpgcore.getPlayerManager().isMuted(uuidPlayerToTempMute)) {
                sender.sendMessage(Utils.ALREADYMUTED);
                return false;
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
            return false;
        }

        player.sendMessage(Utils.poprawneUzycie("tempMute <gracz> <liczba> <y/m/d/h/mm/s> [-s] [powod]"));
        return false;
    }
}
