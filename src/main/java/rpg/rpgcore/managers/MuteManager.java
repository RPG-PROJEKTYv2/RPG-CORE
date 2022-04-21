package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class MuteManager {

    private final RPGCORE rpgcore;

    public MuteManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private void addToPunishmentHistory(final UUID uuid, final String punishment) {

        final StringBuilder newPunishment = new StringBuilder();

        newPunishment.append(rpgcore.getPlayerManager().getPlayerPunishmentHistory(uuid));
        newPunishment.append(punishment).append(",");

        rpgcore.getPlayerManager().updatePlayerPunishmentHistory(uuid, String.valueOf(newPunishment));

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().setPunishmentHistory(uuid, String.valueOf(newPunishment)));

    }

    public void unMutePlayer(final String senderName, final UUID uuidToUnMute, final boolean silent) {

        final String nameOfPlayerToUnMute = rpgcore.getPlayerManager().getPlayerName(uuidToUnMute);

        if (!(silent)) {
            Bukkit.getServer().broadcastMessage(Utils.unMuteBroadcast(nameOfPlayerToUnMute, senderName));
        }

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().unMutePlayer(uuidToUnMute));
    }

    public void mutePlayer(final String muteSender, final UUID uuidPlayerToMute, final String reason, final boolean silent, final String muteExpiry) {
        final Date muteDate = new Date();
        final Player playerToMute = Bukkit.getPlayer(uuidPlayerToMute);
        final String nameOfPlayerToMute = rpgcore.getPlayerManager().getPlayerName(uuidPlayerToMute);

        if (playerToMute != null) {
            Utils.youAreMuted(playerToMute, muteSender, reason, muteExpiry);
        }

        if (!(silent)) {
            rpgcore.getServer().broadcastMessage(Utils.muteBroadcast(nameOfPlayerToMute, muteSender, String.valueOf(reason), muteExpiry));
        }


        final String muteInfo = muteSender + ";" + reason + ";" + muteExpiry + ";" + Utils.dateFormat.format(muteDate);
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().mutePlayer(uuidPlayerToMute, muteInfo));

        this.addToPunishmentHistory(uuidPlayerToMute, "Mute;" + muteInfo);
    }


    public void tempMutePlayer(final String adminName, final UUID uuidPlayerToTempMute, final int time, final String jednostka, final boolean silent, final String reason) {

        final Date tempMuteDate = new Date();
        Calendar cal = Calendar.getInstance();
        switch (jednostka) {
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
        final Date tempMuteExpireDate = new Date(cal.getTime().getTime());
        final Player playerToTempMute = Bukkit.getPlayer(uuidPlayerToTempMute);
        final String nameOfThePlayerToTempMute = rpgcore.getPlayerManager().getPlayerName(uuidPlayerToTempMute);
        final String expiryDateInString = Utils.convertDatesToTimeLeft(tempMuteDate, tempMuteExpireDate);

        if (playerToTempMute != null) {
            Utils.youAreMuted(playerToTempMute, adminName, reason, expiryDateInString);
        }
        if (!(silent)) {
            rpgcore.getServer().broadcastMessage(Utils.muteBroadcast(nameOfThePlayerToTempMute, adminName, String.valueOf(reason), expiryDateInString));
        }

        final String tempMuteInfo = adminName + ";" + reason + ";" + Utils.dateFormat.format(tempMuteExpireDate) + ";" + Utils.dateFormat.format(tempMuteDate);
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().mutePlayer(uuidPlayerToTempMute, tempMuteInfo));

        this.addToPunishmentHistory(uuidPlayerToTempMute, "TempMute;" + tempMuteInfo);
    }

}
