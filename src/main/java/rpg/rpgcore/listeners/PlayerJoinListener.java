package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerJoinListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoinListener(final PlayerJoinEvent e) {

        final Player p = e.getPlayer();
        final UUID playerUUID = p.getUniqueId();
        final String playerName = p.getName();
        final int playerLvl = rpgcore.getPlayerManager().getPlayerLvl(playerUUID);
        final double playerExp = rpgcore.getPlayerManager().getPlayerExp(playerUUID) / rpgcore.getLvlManager().getExpForLvl(p.getLevel());

        if (!(rpgcore.getPlayerManager().getPlayers().contains(playerUUID))) {
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().createPlayer(playerName, playerUUID, "false"));
            e.setJoinMessage(Utils.firstJoinMessage(playerName));
        }
        p.setLevel(playerLvl);
        p.setExp((float) playerExp);
        for (Player rest : Bukkit.getOnlinePlayers()){
            rpgcore.getLvlManager().updateLvlBelowName(rest, playerName, playerLvl);
        }
        e.setJoinMessage(Utils.joinMessage(playerName));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerPreLoginListener(final AsyncPlayerPreLoginEvent e) {
        final UUID uuid = e.getUniqueId();

        if (rpgcore.getPlayerManager().getPlayers().contains(uuid)) {
            if (rpgcore.getPlayerManager().isBanned(uuid)) {

                final String[] banInfo = rpgcore.getPlayerManager().getPlayerBanInfo(uuid).split(";");

                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Utils.banMessage(banInfo[0], banInfo[1], banInfo[2], banInfo[3]));
            }
        }

    }

}
