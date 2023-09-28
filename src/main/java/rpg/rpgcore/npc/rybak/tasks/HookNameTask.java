package rpg.rpgcore.npc.rybak.tasks;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Set;

public class HookNameTask implements Runnable {

    private final RPGCORE rpgcore;
    private final Player player;
    private final PlayerFishEvent e;
    private final int taskId;

    public HookNameTask(final RPGCORE rpgcore, final Player player, final PlayerFishEvent e) {
        this.rpgcore = rpgcore;
        this.player = player;
        this.e = e;
        this.taskId = this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(this.rpgcore, this, 1L, 1L);
    }

    @Override
    public void run() {
        if (!player.isOnline()) {
            this.rpgcore.getServer().getScheduler().cancelTask(this.taskId);
            this.rpgcore.getRybakNPC().getTimeMap().remove(player.getUniqueId());
            e.setCancelled(true);
            return;
        }
        if (e.getHook().isDead()) {
            this.rpgcore.getServer().getScheduler().cancelTask(this.taskId);
            this.rpgcore.getRybakNPC().getTimeMap().remove(player.getUniqueId());
            e.setCancelled(true);
            return;
        }

        final long time = this.rpgcore.getRybakNPC().getTimeMap().get(player.getUniqueId());

        e.getHook().setCustomNameVisible(true);
        if (time <= 0L && e.getState() == PlayerFishEvent.State.FISHING) {
            e.getHook().setCustomName(Utils.format("&c" + player.getName() + " &7| &ePlynie..."));
        } else if (time <= 0L && e.getState() == PlayerFishEvent.State.CAUGHT_ENTITY || e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            e.getHook().setCustomName(Utils.format("&c" + player.getName() + " &7| &aWylow!!!"));
        } else {
            e.getHook().setCustomName(Utils.format("&c" + player.getName() + " &7| &6Przyplynie za: " + this.getPrefix(time) + Utils.durationToString(time, true)));
            this.rpgcore.getRybakNPC().getTimeMap().replace(player.getUniqueId(), this.rpgcore.getRybakNPC().getTimeMap().get(player.getUniqueId()) - 50L);
        }
    }

    private String getPrefix(final long time) {
        if (time < 5_000L) return "&c";
        if (time < 10_000L) return "&e";
        if (time < 13_000L) return "&a";
        return "&2";
    }
}
