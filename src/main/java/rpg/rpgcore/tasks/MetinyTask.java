package rpg.rpgcore.tasks;

import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.metiny.MetinyHelper;
import rpg.rpgcore.utils.Utils;

public class MetinyTask implements Runnable{
    private final RPGCORE rpgcore;

    public MetinyTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().runTaskTimer(this.rpgcore, this, 20L, 6000L);
    }

    @Override
    public void run() {
        MetinyHelper.respAllMetins();
        this.rpgcore.getServer().broadcastMessage(Utils.format("&b&lMetiny zostaly zresetowane!"));
    }
}
