package rpg.rpgcore.chat.mute;


import org.bukkit.command.CommandSender;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class MuteCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public MuteCommand(final RPGCORE rpgcore) {
        super("mute");
        this.setAliases(Arrays.asList("wycisz"));
        this.setRankLevel(RankType.MOD);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        if (args.length < 1) {
            sender.sendMessage(Utils.poprawneUzycie("mute <gracz> [-s] [powod]"));
            return;
        }
        final String senderName = sender.getName();
        final String muteExpiry = "Permamentny";
        if (args.length == 1) {
            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.MUTEPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return;
            }
            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("wyciszyc"));
                return;
            }
            final User user = rpgcore.getUserManager().find(args[0]);
            final UUID uuidPlayerToMute = user.getId();
            if (user.isMuted()) {
                sender.sendMessage(Utils.ALREADYMUTED);
                return;
            }
            rpgcore.getMuteManager().mutePlayer(senderName, uuidPlayerToMute, " Brak powodu", false, muteExpiry);
            return;
        }
        if (!rpgcore.getUserManager().isUserName(args[0])) {
            sender.sendMessage(Utils.MUTEPREFIX + Utils.NIEMATAKIEGOGRACZA);
            return;
        }
        if (args[0].equalsIgnoreCase(senderName)) {
            sender.sendMessage(Utils.theSenderCannotBeTarget("wyciszyc"));
            return;
        }
        final User user = rpgcore.getUserManager().find(args[0]);
        final UUID uuidPlayerToMute = user.getId();
        args[0] = "";
        if (user.isMuted()) {
            sender.sendMessage(Utils.ALREADYMUTED);
            return;
        }
        boolean silent = false;
        if (args[1].equalsIgnoreCase("-s")) {
            silent = true;
            args[1] = "";
        }

        final StringBuilder reason = new StringBuilder();
        for (final String arg : args) {
            if (!(arg.equalsIgnoreCase(""))) {
                reason.append(arg).append(" ");
            }
        }
        if (reason.length() == 0) {
            reason.append("Brak Powodu!");
        }
        final String finalReason = String.valueOf(reason);
        rpgcore.getMuteManager().mutePlayer(senderName, uuidPlayerToMute, finalReason, silent, muteExpiry);
    }
}
