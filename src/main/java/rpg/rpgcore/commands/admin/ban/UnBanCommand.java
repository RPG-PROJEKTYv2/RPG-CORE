package rpg.rpgcore.commands.admin.ban;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class UnBanCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public UnBanCommand(RPGCORE rpgcore) {
        super("unban");
        this.setRankLevel(RankType.MOD);
        this.setAliases(Arrays.asList("ub"));
        this.rpgcore = rpgcore;
    }
    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        if (args.length < 1) {
            sender.sendMessage(Utils.poprawneUzycie("unban [gracz]"));
            return;
        }
        final String senderName = sender.getName();

        if (args.length == 1) {
            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.format(Utils.NIEMATAKIEGOGRACZA));
                return;
            }
            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("odbanowac"));
                return;
            }

            final User user = this.rpgcore.getUserManager().find(args[0]);
            final UUID uuidToUnBan = user.getId();

            if (!user.isBanned()) {
                sender.sendMessage(Utils.NOALREADYBANNED);
                return;
            }

            rpgcore.getBanManager().unBanPlayer(senderName, uuidToUnBan, false);

            final Date dataUb = new Date();
            final String unbanInfo = senderName + ";" + Utils.dateFormat.format(dataUb);

            this.addToPunishmentHistory(uuidToUnBan, "UnBanCommand;" + unbanInfo);

            return;
        }

        if (args.length == 2) {
            if (sender instanceof Player && !(rpgcore.getUserManager().isUser(((Player) sender).getUniqueId()) && rpgcore.getUserManager().find(((Player) sender).getUniqueId()).getRankUser().isHighStaff())) {
                sender.sendMessage(Utils.poprawneUzycie("unban [gracz]"));
                return;
            }
            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.format(Utils.NIEMATAKIEGOGRACZA));
                return;
            }
            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("odbanowac"));
                return;
            }
            final User user = this.rpgcore.getUserManager().find(args[0]);
            final UUID uuidToUnBan = user.getId();


            if (!user.isBanned()) {
                sender.sendMessage(Utils.NOALREADYBANNED);
                return;
            }

            boolean silent = false;
            if (args[1].equalsIgnoreCase("-s")) {
                silent = true;
                args[1] = "";
            }

            rpgcore.getBanManager().unBanPlayer(senderName, uuidToUnBan, silent);

            final Date dataUb = new Date();
            final String unbanInfo = senderName + ";" + Utils.dateFormat.format(dataUb);

            this.addToPunishmentHistory(uuidToUnBan, "UnBanCommand;" + unbanInfo);
        }
    }

    private void addToPunishmentHistory(final UUID uuid, final String punishment) {
        final User user = this.rpgcore.getUserManager().find(uuid);
        final StringBuilder newPunishment = new StringBuilder();

        newPunishment.append(user.getPunishmentHistory());
        newPunishment.append(punishment).append(",");

        user.setPunishmentHistory(String.valueOf(newPunishment));

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().setPunishmentHistory(uuid, String.valueOf(newPunishment)));

    }
}
