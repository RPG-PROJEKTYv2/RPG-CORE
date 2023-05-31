package rpg.rpgcore.dungeons.maps.piekielnyPrzedsionek.tasks;

import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class PiekielnyPrzedsionekTask implements Runnable {
    private final RPGCORE rpgcore;

    public PiekielnyPrzedsionekTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(this.rpgcore, this, 20L, 20L);
    }

    private final List<DungeonStatus> doNotRun = Arrays.asList(DungeonStatus.ENDED, DungeonStatus.OPENED_GATE, DungeonStatus.CLOSED_GATE, DungeonStatus.RESETTING);

    @Override
    public void run() {
        if (doNotRun.contains(rpgcore.getPrzedsionekManager().getDungeonStatus())) return;
        ((TextHologramLine) rpgcore.getPrzedsionekManager().getDungeonHologram().getLines().get(4)).setText(Utils.format("&7Ilosc graczy: &c" + rpgcore.getPrzedsionekManager().getDungeonWorld().getPlayers().size()));
        if (rpgcore.getPrzedsionekManager().getDungeonWorld().getPlayers().isEmpty()) {
            rpgcore.getPrzedsionekManager().resetDungeon();
            return;
        }
        switch (rpgcore.getPrzedsionekManager().getDungeonStatus()) {
            case ETAP_1:
                if (rpgcore.getPrzedsionekManager().getCounter() >= 96) {
                    rpgcore.getPrzedsionekManager().startPhase2();
                }
                break;
            case ETAP_2:
                if (rpgcore.getPrzedsionekManager().getCounter() >= 12) {
                    rpgcore.getPrzedsionekManager().startPhase3();
                }
                break;
            case ETAP_3:
                if (rpgcore.getPrzedsionekManager().getCounter() >= 144) {
                    rpgcore.getPrzedsionekManager().startPhase4();
                }
                break;
            case BOSS:
                if (rpgcore.getPrzedsionekManager().getCounter() >= 1) {
                    rpgcore.getPrzedsionekManager().endDungeon();
                }
                break;
        }
    }
}
