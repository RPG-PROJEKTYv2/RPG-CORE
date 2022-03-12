package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class BanManager {

    private final RPGCORE rpgcore;

    public BanManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    //TODO dodanie funkcji na rozczytywanie banInfo oraz jego tworzenie

    public void unBanPlayer(final String senderName, final UUID uuidToUnBan, final boolean silent) {

        final String nameOfPlayerToBan = rpgcore.getPlayerName(uuidToUnBan);

        if (!(silent)) {
            Bukkit.getServer().broadcastMessage(Utils.unBanBroadcast(nameOfPlayerToBan, senderName));
        }

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().unBanPlayer(uuidToUnBan));
    }

    public void banPlayer(final String sederName, final UUID uuidPlayerToBan, final String reason, final boolean silent, final String banExpiry) {

        final Player playerToBan = Bukkit.getPlayer(uuidPlayerToBan);
        final String nameOfPlayerToBan = rpgcore.getPlayerName(uuidPlayerToBan);

        if (playerToBan != null) {
            //TODO dodanie pobierania aktualniej godziny i daty
            playerToBan.kickPlayer(Utils.banMessage(sederName, reason, banExpiry, "do dodania"));
        }
        if (!(silent)) {
            Bukkit.getServer().broadcastMessage(Utils.banBroadcast(nameOfPlayerToBan, sederName, String.valueOf(reason), banExpiry));
        }

        final String banInfo = "true";

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().banPlayer(uuidPlayerToBan, banInfo));
    }

    public void kickPlayer(final String sederName, final Player playerToKick, final String reason, final boolean silent, final String broadcast) {

        //TODO do przerobienia

        if (playerToKick != null) {
            playerToKick.kickPlayer(Utils.kickMessage(sederName, reason));
        }

        if (!(silent)) {
            Bukkit.getServer().broadcastMessage(broadcast);
        }
    }

}
