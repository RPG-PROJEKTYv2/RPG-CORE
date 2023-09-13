package rpg.rpgcore.commands.admin.vanish;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

public class VanishListener implements Listener {
    private final RPGCORE rpgcore;

    public VanishListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWorldChange(final PlayerChangedWorldEvent e) {
        final Player p = e.getPlayer();
        final UUID uuid = p.getUniqueId();
        if (!rpgcore.getVanishManager().isVanished(uuid)) return;
        p.sendMessage("channged the world, hiding again!");
        rpgcore.getVanishManager().hidePlayer(p);
    }
}
