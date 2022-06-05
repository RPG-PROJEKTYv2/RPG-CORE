package rpg.rpgcore.admin.ban;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Date;
import java.util.UUID;

public class UnBan implements CommandExecutor {

    private final RPGCORE rpgcore;

    public UnBan(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender.hasPermission("rpg.unban"))) {
            sender.sendMessage(Utils.permisje("rpg.unban"));
            return false;
        }

        final String senderName = sender.getName();

        if (args.length == 1) {

            final UUID uuidToUnBan = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (uuidToUnBan == null) {
                sender.sendMessage(Utils.format(Utils.NIEMATAKIEGOGRACZA));
                return false;
            }

            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("odbanowac"));
                return false;
            }

            if (!(rpgcore.getPlayerManager().isBanned(uuidToUnBan))) {
                sender.sendMessage(Utils.NOALREADYBANNED);
                return false;
            }

            rpgcore.getBanManager().unBanPlayer(senderName, uuidToUnBan, false);

            final Date dataUb = new Date();
            final String unbanInfo = senderName + ";" + Utils.dateFormat.format(dataUb);

            this.addToPunishmentHistory(uuidToUnBan, "UnBan;" + unbanInfo);

            return false;
        }

        if (args.length == 2) {

            if (!(sender.hasPermission("rpg.unban.silent"))) {
                sender.sendMessage(Utils.permisje("rpg.unban.silent"));
                return false;
            }

            final UUID uuidToUnBan = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (uuidToUnBan == null) {
                sender.sendMessage(Utils.format(Utils.NIEMATAKIEGOGRACZA));
                return false;
            }

            if (args[0].equalsIgnoreCase(senderName)) {
                sender.sendMessage(Utils.theSenderCannotBeTarget("odbanowac"));
                return false;
            }

            if (!(rpgcore.getPlayerManager().isBanned(uuidToUnBan))) {
                sender.sendMessage(Utils.NOALREADYBANNED);
                return false;
            }

            boolean silent = false;
            if (args[1].equalsIgnoreCase("-s")) {
                silent = true;
                args[1] = "";
            }

            rpgcore.getBanManager().unBanPlayer(senderName, uuidToUnBan, silent);

            final Date dataUb = new Date();
            final String unbanInfo = senderName + ";" + Utils.dateFormat.format(dataUb);

            this.addToPunishmentHistory(uuidToUnBan, "UnBan;" + unbanInfo);

            return false;
        }
        sender.sendMessage(Utils.poprawneUzycie("unban [gracz]"));
        return false;
    }


    private void addToPunishmentHistory(final UUID uuid, final String punishment) {

        final StringBuilder newPunishment = new StringBuilder();

        newPunishment.append(rpgcore.getPlayerManager().getPlayerPunishmentHistory(uuid));
        newPunishment.append(punishment).append(",");

        rpgcore.getPlayerManager().updatePlayerPunishmentHistory(uuid, String.valueOf(newPunishment));

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().setPunishmentHistory(uuid, String.valueOf(newPunishment)));

    }
}
