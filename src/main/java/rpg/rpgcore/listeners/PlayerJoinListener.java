package rpg.rpgcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerJoinListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoinListener(AsyncPlayerPreLoginEvent e) {

        final UUID playerUUID = e.getUniqueId();
        final String playerName = e.getName();

        if (playerName == null || playerUUID == null) {
            System.out.println("Blad ktos mial pusty nick albo uuid!");
            return;
        }
        if (!(rpgcore.getPlayerManager().getPlayers().contains(playerUUID))) {
            rpgcore.getSQLManager().createPlayer(playerName, playerUUID);
        }

    }
}
