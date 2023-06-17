package rpg.rpgcore.bossy.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class PodwodnyStraznikEntityInteractListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final PlayerInteractAtEntityEvent e) {
        final Player player = e.getPlayer();
        if (!e.getRightClicked().getName().contains("Podwodny Straznik ")) return;
        player.sendMessage(Utils.format("&cPostep mobow: &6" + RPGCORE.getInstance().getBossyManager().getBossyUser().getMobsCount100_110() + "&c/&610 000"));
    }
}
