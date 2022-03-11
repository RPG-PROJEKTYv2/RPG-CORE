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

        if (!(rpgcore.getPlayers().contains(playerUUID))) {
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().createPlayer(playerName, playerUUID, "false"));
            e.setJoinMessage(Utils.firstJoinMessage(playerName));
        }

        e.setJoinMessage(Utils.joinMessage(playerName));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerPreLoginListener(final AsyncPlayerPreLoginEvent e) {
        final UUID uuid = e.getUniqueId();

        if (rpgcore.isBanned(uuid)) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, "nie ma tak");
        }

    }

}
