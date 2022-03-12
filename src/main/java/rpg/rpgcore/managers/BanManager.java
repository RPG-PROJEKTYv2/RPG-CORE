package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Date;
import java.util.UUID;

public class BanManager {

    private final RPGCORE rpgcore;

    public BanManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private void addToPunishmentHistory(final UUID uuid, final String punishment) {

        final StringBuilder newPunishment = new StringBuilder();

        newPunishment.append(rpgcore.getPlayerManager().getPlayerPunishmentHistory(uuid));
        newPunishment.append(punishment).append(",");

        rpgcore.getPlayerManager().updatePlayerPunishmentHistory(uuid, String.valueOf(newPunishment));

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().setPunishmentHistory(uuid, String.valueOf(newPunishment)));

    }

    public void unBanPlayer(final String senderName, final UUID uuidToUnBan, final boolean silent) {

        final String nameOfPlayerToBan = rpgcore.getPlayerManager().getPlayerName(uuidToUnBan);

        if (!(silent)) {
            Bukkit.getServer().broadcastMessage(Utils.unBanBroadcast(nameOfPlayerToBan, senderName));
        }

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().unBanPlayer(uuidToUnBan));
    }

    public void banPlayer(final String banSender, final UUID uuidPlayerToBan, final String reason, final boolean silent, final String banExpiry) {

        final String dateOfBan = Utils.dateFormat.format(new Date());

        final Player playerToBan = Bukkit.getPlayer(uuidPlayerToBan);
        final String nameOfPlayerToBan = rpgcore.getPlayerManager().getPlayerName(uuidPlayerToBan);

        if (playerToBan != null) {
            playerToBan.kickPlayer(Utils.banMessage(banSender, reason, banExpiry, dateOfBan));
        }
        if (!(silent)) {
            Bukkit.getServer().broadcastMessage(Utils.banBroadcast(nameOfPlayerToBan, banSender, String.valueOf(reason), banExpiry));
        }

        final String banInfo = banSender + ";" + reason + ";" + banExpiry + ";" + dateOfBan;
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().banPlayer(uuidPlayerToBan, banInfo));

        this.addToPunishmentHistory(uuidPlayerToBan, "Ban;" + banInfo);
    }

    public void kickPlayer(final String sederName, final Player playerToKick, final String reason, final boolean silent) {

        final String dateOfBan = Utils.dateFormat.format(new Date());

        final String playerName = playerToKick.getName();
        final UUID playerUUID = playerToKick.getUniqueId();

        playerToKick.kickPlayer(Utils.kickMessage(sederName, reason));

        if (!(silent)) {
            Bukkit.getServer().broadcastMessage(Utils.kickBroadcast(playerName, sederName, reason));
        }

        final String kick = sederName + ";" + reason + ";" + dateOfBan;
        this.addToPunishmentHistory(playerUUID, "Kick;" + kick);
    }

}
