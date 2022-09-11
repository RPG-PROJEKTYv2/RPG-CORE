package rpg.rpgcore.chat.mute;

import org.bukkit.command.CommandSender;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class UnMuteCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public UnMuteCommand(final RPGCORE rpgcore){
        super("unmute");
        this.setRankLevel(RankType.MOD);
        this.rpgcore = rpgcore;
    }
    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        if (args.length < 1) {
            sender.sendMessage(Utils.poprawneUzycie("unmute <gracz>"));
            return;
        }
        final String senderName = sender.getName();

        if (args.length == 1) {
            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.MUTEPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return;
            }
            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("odciszyc"));
                return;
            }
            final UUID uuidToUnMute = rpgcore.getUserManager().find(args[0]).getId();

            if (!rpgcore.getUserManager().find(uuidToUnMute).isMuted()) {
                sender.sendMessage(Utils.ALREADYMUTED);
                return;
            }
            rpgcore.getMuteManager().unMutePlayer(senderName, uuidToUnMute, false);

            final Date dataUb = new Date();
            final String unmuteInfo = senderName + ";" + Utils.dateFormat.format(dataUb);

            this.addToPunishmentHistory(uuidToUnMute, "UnMuteCommand;" + unmuteInfo);
            return;
        }

        if (args.length == 2) {
            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.MUTEPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return;
            }
            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("odciszyc"));
                return;
            }
            final UUID uuidToUnMute = rpgcore.getUserManager().find(args[0]).getId();
            if (!rpgcore.getUserManager().find(uuidToUnMute).isMuted()) {
                sender.sendMessage(Utils.ALREADYMUTED);
                return;
            }

            boolean silent = false;
            if (args[1].equalsIgnoreCase("-s")) {
                silent = true;
                args[1] = "";
            }

            rpgcore.getMuteManager().unMutePlayer(senderName, uuidToUnMute, silent);

            final Date dataUb = new Date();
            final String unmuteInfo = senderName + ";" + Utils.dateFormat.format(dataUb);

            this.addToPunishmentHistory(uuidToUnMute, "UnMuteCommand;" + unmuteInfo);
        }
    }

    private void addToPunishmentHistory(final UUID uuid, final String punishment) {
        final User user = rpgcore.getUserManager().find(uuid);
        final StringBuilder newPunishment = new StringBuilder();

        newPunishment.append(user.getPunishmentHistory());
        newPunishment.append(punishment).append(",");

        user.setPunishmentHistory(String.valueOf(newPunishment));

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().setPunishmentHistory(uuid, String.valueOf(newPunishment)));

    }
}
