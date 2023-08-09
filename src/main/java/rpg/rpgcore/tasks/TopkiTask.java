package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class TopkiTask implements Runnable {
    private final RPGCORE rpgcore;
    public TopkiTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleAsyncRepeatingTask(this.rpgcore, this, 20L, 6_000L);
    }

    @Override
    public void run() {
        this.rpgcore.getTopkiManager().updateTopki();
        this.rpgcore.getGuildManager().updateTopki();
        Bukkit.getServer().broadcastMessage(Utils.format("&3&lTopki zostaly odswiezone!"));
    }
}
