package rpg.rpgcore.tasks;

import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.icetower.IceTowerManager;
import rpg.rpgcore.dungeons.icetower.ResetType;

public class AntyAfktIceTowerTask implements Runnable{
    public AntyAfktIceTowerTask(final RPGCORE rpgcore) {
        rpgcore.getServer().getScheduler().runTaskTimer(rpgcore, this, 20L, 20L);
    }

    @Override
    public void run() {
        if (RPGCORE.getInstance().getIceTowerManager().getAntyAfk() != -1) {
            if (RPGCORE.getInstance().getIceTowerManager().getAntyAfk() > 0) {
                RPGCORE.getInstance().getIceTowerManager().setAntyAfk(RPGCORE.getInstance().getIceTowerManager().getAntyAfk() - 1);
                if (RPGCORE.getInstance().getIceTowerManager().getAntyAfk() == 0) {
                    IceTowerManager.resetIceTower(ResetType.BYPASS);
                }
            }
        }
    }
}
