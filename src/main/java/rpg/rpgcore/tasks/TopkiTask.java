package rpg.rpgcore.tasks;

import rpg.rpgcore.RPGCORE;

public class TopkiTask implements Runnable {
    private final RPGCORE rpgcore;
    public TopkiTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(this.rpgcore, this, 20L, 1_800_000L);
    }

    @Override
    public void run() {
        this.rpgcore.getTopkiManager().updateTopki();
    }
}
