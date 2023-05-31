package rpg.rpgcore.dungeons.maps.icetower.tasks;

import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;

import java.util.Arrays;
import java.util.List;

public class IceTowerTask implements Runnable {
    private final RPGCORE rpgcore;

    public IceTowerTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, this, 20L, 20L);
    }

    private final List<DungeonStatus> doNotRun = Arrays.asList(DungeonStatus.ENDED, DungeonStatus.RESETTING);

    @Override
    public void run() {
        if (doNotRun.contains(rpgcore.getIceTowerManager().getStatus())) return;
        if (rpgcore.getIceTowerManager().getStatus() == DungeonStatus.ETAP_1 || rpgcore.getIceTowerManager().getStatus() == DungeonStatus.ETAP_2 ||
                rpgcore.getIceTowerManager().getStatus() == DungeonStatus.ETAP_3 || rpgcore.getIceTowerManager().getStatus() == DungeonStatus.ETAP_4 ||
                rpgcore.getIceTowerManager().getStatus() == DungeonStatus.BOSS || rpgcore.getIceTowerManager().getStatus() == DungeonStatus.UPGRADING) {
            if (rpgcore.getIceTowerManager().getDungeonWorld().getPlayers().isEmpty()) {
                rpgcore.getIceTowerManager().resetDungeon();
                return;
            }
        }
        if (rpgcore.getIceTowerManager().getStatus() == DungeonStatus.STARTED && !rpgcore.getIceTowerManager().getDamaged().asMap().containsKey("kamien")) {
            rpgcore.getIceTowerManager().resetDungeon();
            return;
        }
        rpgcore.getIceTowerManager().updatePlayerCount();
        rpgcore.getIceTowerManager().updateTime();
        switch (rpgcore.getIceTowerManager().getStatus()) {
            case ETAP_1:
                if (rpgcore.getIceTowerManager().getCount() >= 60) {
                    rpgcore.getIceTowerManager().startPrePhase();
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getIceTowerManager().startPhase2(), 300L);
                }
                break;
                case ETAP_2:
                    if (rpgcore.getIceTowerManager().getCount() >= 60) {
                        rpgcore.getIceTowerManager().startPrePhase();
                        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getIceTowerManager().startPhase3(), 300L);
                    }
                    break;
            case ETAP_3:
                if (rpgcore.getIceTowerManager().getCount() >= 8) {
                    rpgcore.getIceTowerManager().startPrePhase();
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getIceTowerManager().startPhase4(), 300L);
                }
                break;
            case ETAP_4:
                if (rpgcore.getIceTowerManager().getCount() >= 75) {
                    rpgcore.getIceTowerManager().startPrePhase();
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getIceTowerManager().startPhaseBOSS(), 300L);
                }
                break;
            case BOSS:
                if (rpgcore.getIceTowerManager().getCount() >= 1) {
                    rpgcore.getIceTowerManager().endIceTower();
                }
                break;
        }
    }
}
