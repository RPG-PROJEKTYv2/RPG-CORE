package rpg.rpgcore.tasks;

import rpg.rpgcore.RPGCORE;

public class ZmiankiTask implements Runnable{
    private final RPGCORE rpgcore;
    public ZmiankiTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(this.rpgcore, this, 0L, 20L);
    }

    @Override
    public void run() {

    }
}
