package rpg.rpgcore.dungeons.maps.koloseum.tasks;

import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class KoloseumTask implements Runnable {
    private final RPGCORE rpgcore;

    public KoloseumTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(this.rpgcore, this, 20L, 20L);
    }

    private final List<DungeonStatus> doNotRun = Arrays.asList(DungeonStatus.ENDED, DungeonStatus.OPENED_GATE, DungeonStatus.CLOSED_GATE, DungeonStatus.RESETTING);

    @Override
    public void run() {
        if (doNotRun.contains(rpgcore.getKoloseumManager().getDungeonStatus())) return;
        ((TextHologramLine) rpgcore.getKoloseumManager().getHologram().getLines().get(4)).setText(Utils.format("&7Ilosc graczy: &c" + rpgcore.getKoloseumManager().getDungeon().getPlayers().size()));
        if (rpgcore.getKoloseumManager().getDungeon().getPlayers().isEmpty()) {
            rpgcore.getKoloseumManager().resetDungeon();
            return;
        }
        switch (rpgcore.getKoloseumManager().getDungeonStatus()) {
            case ETAP_1:
                if (rpgcore.getKoloseumManager().getCounter() >= 240) {
                    rpgcore.getKoloseumManager().startPhase2();
                }
                break;
            case ETAP_2:
                if (rpgcore.getKoloseumManager().getCounter() >= 1) {
                    rpgcore.getKoloseumManager().startPhase3();
                }
                break;
            case ETAP_3:
                if (rpgcore.getKoloseumManager().getCounter() >= 4) {
                    rpgcore.getKoloseumManager().startPhase4();
                }
                break;
            case ETAP_4:
                if (rpgcore.getKoloseumManager().getCounter() >= 1) {
                    rpgcore.getKoloseumManager().startPhase5();
                }
                break;
            case ETAP_5:
                if (rpgcore.getKoloseumManager().getCounter() >= 240) {
                    rpgcore.getKoloseumManager().startPhase6();
                }
                break;
            case ETAP_6:
                if (rpgcore.getKoloseumManager().getCounter() >= 2) {
                    rpgcore.getKoloseumManager().startPhase7();
                }
                break;
            case ETAP_7:
                if (rpgcore.getKoloseumManager().getCounter() >= 6) {
                    rpgcore.getKoloseumManager().startPhase8();
                }
                break;
            case BOSS:
                if (rpgcore.getKoloseumManager().getCounter() >= 1) {
                    rpgcore.getKoloseumManager().endDungeon();
                }
                break;
        }
    }
}
