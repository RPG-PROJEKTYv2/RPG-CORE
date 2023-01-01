package rpg.rpgcore.tasks;

import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.icetower.IceTowerManager;
import rpg.rpgcore.dungeons.icetower.ResetType;
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
        IceTowerManager.resetIceTower(ResetType.NORMAL);
        this.rpgcore.getServer().broadcastMessage(Utils.format("&b&lMetiny zostaly zresetowane!"));
    }
}
