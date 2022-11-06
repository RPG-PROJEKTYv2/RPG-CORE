package rpg.rpgcore.dungeons.zamekNieskonczonosci.events;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;

public class ZamekNieskonczonosciMoveListener implements Listener {
    private final RPGCORE rpgcore;
    public ZamekNieskonczonosciMoveListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(final PlayerMoveEvent e) {
        final Player player = e.getPlayer();
        if (player.getWorld().getName().equalsIgnoreCase("zamekNieskonczonosci") && rpgcore.getZamekNieskonczonosciManager().status == DungeonStatus.ONGOING) {
            if (rpgcore.getZamekNieskonczonosciManager().phase == 0) {
                final Chunk chunk = player.getLocation().getChunk();
                if ((chunk.getX() >= -1 && chunk.getX() <= 1) && (chunk.getZ() >= 0 && chunk.getZ() <= 2)) {
                    if (player.getLocation().getBlockY() <= 9) {
                        player.teleport(rpgcore.getZamekNieskonczonosciManager().parkour);
                        return;
                    }
                }
                if (Bukkit.getWorld("zamekNieskonczonosci").getBlockAt(1, 12, 44).isBlockIndirectlyPowered() && Bukkit.getWorld("zamekNieskonczonosci").getBlockAt(5, 12, 44).isBlockIndirectlyPowered()) {
                    if (rpgcore.getPartyManager().findPartyByMember(player.getUniqueId()) == null) {
                        rpgcore.getZamekNieskonczonosciManager().endDungeonByPass(Bukkit.getWorld("zamekNieskonczonosci").getPlayers());
                        return;
                    }
                    for (Player p : Bukkit.getWorld("zamekNieskonczonosci").getPlayers()) {
                        p.teleport(rpgcore.getZamekNieskonczonosciManager().phase1StartLocation);
                    }
                    rpgcore.getZamekNieskonczonosciManager().phase = 1;
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getZamekNieskonczonosciManager().startPhase1(rpgcore.getPartyManager().findPartyByMember(player.getUniqueId())), 1L);
                }
            }
        }
    }
}
