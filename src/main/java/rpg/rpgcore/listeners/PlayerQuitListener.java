package rpg.rpgcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import rpg.rpgcore.utils.Utils;

public class PlayerQuitListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuitListener(PlayerQuitEvent e) {

        e.setQuitMessage(null);

        final Player p = e.getPlayer();
        final String name = p.getName();

        e.setQuitMessage(Utils.quitMessage(name));
    }

}
