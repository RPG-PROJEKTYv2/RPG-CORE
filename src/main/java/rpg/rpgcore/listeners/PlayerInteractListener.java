package rpg.rpgcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class PlayerInteractListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInteractListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(final PlayerInteractEvent e){
        final Player player = e.getPlayer();
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) < 75) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c75 &7poziom, zeby uzywac &6STOLU MAGII"));
            e.setCancelled(true);
            return;
        }

    }
}
