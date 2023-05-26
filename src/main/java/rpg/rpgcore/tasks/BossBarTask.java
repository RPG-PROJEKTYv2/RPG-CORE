package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.BossBarUtil;

import java.util.UUID;

public class BossBarTask implements Runnable{
    private final RPGCORE rpgcore;
    public BossBarTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, this, 20L, 20L);
    }

    @Override
    public void run() {
        for (final UUID uuid : BossBarUtil.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) {
                BossBarUtil.removeFromBarMap(uuid);
                continue;
            }
            if (!rpgcore.getCooldownManager().hasBossBarCooldown(uuid)) {
                BossBarUtil.removeBar(player);
                continue;
            }
            BossBarUtil.teleportBar(player);
        }
    }
}
