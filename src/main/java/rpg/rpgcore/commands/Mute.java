package rpg.rpgcore.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class Mute implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Mute(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender.hasPermission("rpg.mute"))) {
            sender.sendMessage(Utils.permisje("rpg.mute"));
            return false;
        }

        final String senderName = sender.getName();
        final String muteExpiry = "Permamentny";


        if (args.length == 1){

            final UUID uuidPlayerToMute = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (uuidPlayerToMute == null) {
                sender.sendMessage(Utils.BANPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("wyciszyc"));
                return false;
            }

            if (rpgcore.getPlayerManager().isMuted(uuidPlayerToMute)) {
                sender.sendMessage(Utils.ALREADYMUTED);
                return false;
            }

            rpgcore.getMuteManager().mutePlayer(senderName, uuidPlayerToMute, " Brak powodu", false, muteExpiry);

            return false;
        }



        if (args.length >= 2) {

            final UUID uuidPlayerToMute = rpgcore.getPlayerManager().getPlayerUUID(args[0]);
            args[0] = "";

            if (uuidPlayerToMute == null) {
                sender.sendMessage(Utils.MUTEPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("wyciszyc"));
                return false;
            }

            if (rpgcore.getPlayerManager().isMuted(uuidPlayerToMute)) {
                sender.sendMessage(Utils.ALREADYMUTED);
                return false;
            }

            boolean silent = false;
            if (args[1].equalsIgnoreCase("-s")) {
                silent = true;
                args[1] = "";
            }

            final StringBuilder reason = new StringBuilder();

            for (final String arg : args) {
                if (!(arg.equalsIgnoreCase(""))) {
                    reason.append(" ").append(arg);
                }
            }

            if (reason.length() == 0) {
                reason.append("Brak Powodu!");
            }

            final String finalReason = String.valueOf(reason);

            rpgcore.getMuteManager().mutePlayer(senderName, uuidPlayerToMute, finalReason, silent, muteExpiry);

            return false;
        }

        sender.sendMessage(Utils.poprawneUzycie("mute <gracz> [-s] [powod]"));
        return false;
    }
}
