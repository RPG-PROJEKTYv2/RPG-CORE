package rpg.rpgcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class PlayerQuitListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerQuitListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuitListener(final PlayerQuitEvent e) {

        final Player p = e.getPlayer();
        final String name = p.getName();

        rpgcore.getVanishManager().getVanishList().remove(p.getUniqueId());

        e.setQuitMessage(Utils.quitMessage(name));
    }

}
