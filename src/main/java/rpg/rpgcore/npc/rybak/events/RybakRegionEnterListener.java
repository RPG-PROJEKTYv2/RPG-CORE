package rpg.rpgcore.npc.rybak.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Set;

public class RybakRegionEnterListener implements Listener {
    private final RPGCORE rpgcore;

    public RybakRegionEnterListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRegionEnter(final PlayerMoveEvent e) {
        if (!e.getPlayer().getLocation().getWorld().getName().equals("Rybak")) return;
        final Player player = e.getPlayer();

        if (e.getTo().getY() < 145) {
            player.teleport(new Location(player.getWorld(), -10.5, 157.5, -133.5, -90, 0));
            return;
        }

        final Set<ProtectedRegion> from = RPGCORE.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(e.getFrom()).getRegions();
        final Set<ProtectedRegion> to = RPGCORE.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(e.getTo()).getRegions();

        if (from == null || to == null) return;
        if (to.equals(from)) return;
        if (to.isEmpty()) return;



        final ProtectedRegion region = to.stream().findFirst().get();

        if ((region.getId().equals("rybak-most1") || region.getId().equals("rybak-wyspa2")) && !rpgcore.getRybakNPC().find(player.getUniqueId()).getStaruszekUser().isDone()) {
            player.teleport(new Location(player.getWorld(), -7.5, 157, -177.5, 0, 0));
            player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie ukonczyles jeszcze wszystkich moich misji!"));
        }


    }
}
