package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerJoinListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerLoginEvent e) {
        final Player p = e.getPlayer();
        final String nick = p.getDisplayName();
        final UUID uuid = p.getUniqueId();

        if (!(p.hasPlayedBefore())) {
            //TODO Naprawienie czy gracz istnieje w tabeli
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().createPlayer(nick, uuid));
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().getPlayer(uuid));

    }

}
