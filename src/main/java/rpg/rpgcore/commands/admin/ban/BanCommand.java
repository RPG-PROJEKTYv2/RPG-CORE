package rpg.rpgcore.commands.admin.ban;

import org.bukkit.command.CommandSender;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.UUID;


public class BanCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public BanCommand(RPGCORE rpgcore) {
        super("ban");
        this.setRankLevel(RankType.MOD);
        this.rpgcore = rpgcore;
    }
    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        if (args.length < 1) {
            sender.sendMessage(Utils.format(Utils.poprawneUzycie("ban <gracz> [-s] <powod>")));
            return;
        }
        final String senderName = sender.getName();
        final String banExpiry = "Pernamentny";

        if (args.length == 1) {
            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.BANPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return;
            }

            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("zbanowac"));
                return;
            }
            final User user = rpgcore.getUserManager().find(args[0]);
            final UUID uuidPlayerToBan = user.getId();


            if (user.isBanned()) {
                sender.sendMessage(Utils.ALREADYBANNED);
                return;
            }

            rpgcore.getBanManager().banPlayer(senderName, uuidPlayerToBan, " Brak powodu", false, banExpiry);

            return;
        }

        if (!rpgcore.getUserManager().isUserName(args[0])) {
            sender.sendMessage(Utils.BANPREFIX + Utils.NIEMATAKIEGOGRACZA);
            return;
        }
        if (args[0].equalsIgnoreCase(senderName)) {
            sender.sendMessage(Utils.theSenderCannotBeTarget("zbanowac"));
            return;
        }
        final User user = rpgcore.getUserManager().find(args[0]);
        final UUID uuidPlayerToBan = user.getId();
        args[0] = "";
        if (user.isBanned()) {
            sender.sendMessage(Utils.ALREADYBANNED);
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
                reason.append(" ").append(arg);
            }
        }

        if (reason.length() == 0) {
            reason.append("Brak Powodu!");
        }

        final String finalReason = String.valueOf(reason);
        rpgcore.getBanManager().banPlayer(senderName, uuidPlayerToBan, finalReason, silent, banExpiry);
    }
}
