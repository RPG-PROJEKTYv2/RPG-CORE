package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class BanManager {

    private final HashMap<UUID, String> banInfo = new HashMap<>();

    public void unBanPlayer(final Player playerToUnBan) {
        System.out.println("UNBAM");
    }

    public void banPlayer(final String sederName, final UUID uuidPlayerToBan, final String reason, final boolean silent, final String date, final String broadcast) {

        final Player playerToBan = Bukkit.getPlayer(uuidPlayerToBan);

        if (playerToBan != null) {
            playerToBan.kickPlayer(Utils.banMessage(sederName, reason, date));
        }
        if (silent) {
            Bukkit.getServer().broadcastMessage(broadcast);
        }
    }

    public void kickPlayer(final String sederName, final Player playerToKick, final String reason, final boolean silent, final String broadcast) {

        if (playerToKick != null) {
            playerToKick.kickPlayer(Utils.kickMessage(sederName, reason));
        }

        if (silent) {
            Bukkit.getServer().broadcastMessage(broadcast);
        }
    }

    public void setBanInfo(final UUID uuid, final String banInfo) {
        this.banInfo.put(uuid, banInfo);
    }

    public String getBanInfo(final UUID uuid) {
        return this.banInfo.get(uuid);
    }

}
