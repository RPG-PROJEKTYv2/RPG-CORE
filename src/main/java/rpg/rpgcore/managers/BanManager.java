package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Calendar;
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
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().banPlayer(uuidPlayerToBan, "true"));

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

    //                      TEMPBAN                      \\

    public void tempBanPlayer(final String adminName, final UUID uuidPlayerToTempBan, final int time, final String jednostka, final boolean silent, final String reason) {

        final Date tempBanDate = new Date();
        Calendar cal = Calendar.getInstance();
        switch (jednostka){
            case "y":
                cal.add(Calendar.YEAR, time);
                break;
            case "m":
                cal.add(Calendar.MONTH, time);
                break;
            case "d":
                cal.add(Calendar.DATE, time);
                break;
            case "h":
                cal.add(Calendar.HOUR, time);
                break;
            case "mm":
                cal.add(Calendar.MINUTE, time);
                break;
            case "s":
                cal.add(Calendar.SECOND, time);
                break;
        }
        final Date tempBanExpireDate = new Date(cal.getTime().getTime());
        final Player playerToTempBan = Bukkit.getPlayer(uuidPlayerToTempBan);
        final String nameOfThePlayerToTempBan = rpgcore.getPlayerManager().getPlayerName(uuidPlayerToTempBan);

        if (playerToTempBan != null) {
            playerToTempBan.kickPlayer(Utils.banMessage(adminName, reason, String.valueOf(tempBanExpireDate), String.valueOf(tempBanDate)));
        }
        if (!(silent)) {
            Bukkit.getServer().broadcastMessage(Utils.banBroadcast(nameOfThePlayerToTempBan, adminName, String.valueOf(reason), Utils.convertDatesToTimeLeft(tempBanDate, tempBanExpireDate)));
        }

        final String tempBanInfo = adminName + ";" + reason + ";" + tempBanExpireDate + ";" + tempBanDate;
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().banPlayer(uuidPlayerToTempBan, "true"));

        this.addToPunishmentHistory(uuidPlayerToTempBan, "TempBan;" + tempBanInfo);
    }


}
