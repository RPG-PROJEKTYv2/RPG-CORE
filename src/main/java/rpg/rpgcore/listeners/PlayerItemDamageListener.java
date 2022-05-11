package rpg.rpgcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class PlayerItemDamageListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onitemDamage(PlayerItemDamageEvent e) {
        e.setCancelled(true);
        return;
    }
}
