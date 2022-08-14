package rpg.rpgcore.dungeons.niebiosa.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class NiebiosaPortalEntry implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPortalEntry(final PlayerPortalEvent e) {
        e.getPortalTravelAgent().setCanCreatePortal(false);
        if (e.getFrom().getWorld().getName().equals("60-70exp")) {
            if (!RPGCORE.getInstance().getNiebiosaManager().isActive()) {
                /*e.getPlayer().teleport(RPGCORE.getInstance().getSpawnManager().getSpawn());
                e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cCos poszlo nie tak. Teleportuje na &6spawn."));*/
                return;
            }
            e.setCancelled(true);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mv tp " + e.getPlayer().getName() + " 10-20exp");
        }
    }
}
