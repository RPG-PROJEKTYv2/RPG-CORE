package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class TopkiTask implements Runnable {
    private final RPGCORE rpgcore;
    public TopkiTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(this.rpgcore, this, 20L, 600_000L);
    }

    @Override
    public void run() {
        this.rpgcore.getTopkiManager().updateTopki();
        Bukkit.getServer().broadcastMessage(Utils.format("&3&lTopki zostaly odswiezone!"));
    }
}
