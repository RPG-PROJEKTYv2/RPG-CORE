package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.BossBarUtil;

public class BossBarTask implements Runnable{
    public BossBarTask(final RPGCORE rpgcore) {
        rpgcore.getServer().getScheduler().runTaskTimer(rpgcore, this, 20L, 20L);
    }

    @Override
    public void run() {
        for(String s : BossBarUtil.getPlayers()) {
            Player o = Bukkit.getPlayer(s);
            if(o != null) BossBarUtil.teleportBar(o);
        }
    }
}
