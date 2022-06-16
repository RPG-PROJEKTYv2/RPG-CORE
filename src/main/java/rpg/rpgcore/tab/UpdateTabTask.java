package rpg.rpgcore.tab;

import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;

public class UpdateTabTask implements Runnable{

    private final RPGCORE rpgcore;

    public UpdateTabTask(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        rpgcore.getServer().getScheduler().runTaskTimer(rpgcore, this, 20L, 60L);
    }

    @Override
    public void run() {
        for (Player player : rpgcore.getServer().getOnlinePlayers()) {
            TabManager.update(player.getUniqueId());
        }
    }
}
