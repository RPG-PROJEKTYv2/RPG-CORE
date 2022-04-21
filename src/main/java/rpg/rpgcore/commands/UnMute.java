package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class UnMute implements CommandExecutor {

    private final RPGCORE rpgcore;

    public UnMute(final RPGCORE rpgcore){
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender.hasPermission("rpg.unmute"))) {
            sender.sendMessage(Utils.permisje("rpg.core"));
            return false;
        }

        final String senderName = sender.getName();

        if (args.length == 1) {

            final UUID uuidToUnMute = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (uuidToUnMute == null) {
                sender.sendMessage(Utils.format(Utils.NIEMATAKIEGOGRACZA));
                return false;
            }

            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("odciszyc"));
                return false;
            }

            if (!(rpgcore.getPlayerManager().isMuted(uuidToUnMute))) {
                sender.sendMessage(Utils.ALREADYMUTED);
                return false;
            }

            rpgcore.getMuteManager().unMutePlayer(senderName, uuidToUnMute, false);

            return false;
        }

        if (args.length == 2) {

            if (!(sender.hasPermission("rpg.unMute.silent"))) {
                sender.sendMessage(Utils.permisje("rpg.unMute.silent"));
                return false;
            }

            final UUID uuidToUnMute = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (uuidToUnMute == null) {
                sender.sendMessage(Utils.format(Utils.NIEMATAKIEGOGRACZA));
                return false;
            }

            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("odciszyc"));
                return false;
            }

            if (!(rpgcore.getPlayerManager().isMuted(uuidToUnMute))) {
                sender.sendMessage(Utils.ALREADYMUTED);
                return false;
            }

            boolean silent = false;
            if (args[1].equalsIgnoreCase("-s")) {
                silent = true;
                args[1] = "";
            }

            rpgcore.getMuteManager().unMutePlayer(senderName, uuidToUnMute, silent);

            return false;
        }


        sender.sendMessage(Utils.poprawneUzycie("unmute <gracz>"));
        return false;
    }
}
