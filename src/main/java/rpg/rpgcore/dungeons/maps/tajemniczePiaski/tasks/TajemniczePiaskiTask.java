package rpg.rpgcore.dungeons.maps.tajemniczePiaski.tasks;

import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.dungeons.maps.tajemniczePiaski.objects.RdzenPiaszczystychWydm;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class TajemniczePiaskiTask implements Runnable {
    private final RPGCORE rpgcore;

    public TajemniczePiaskiTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(this.rpgcore, this, 20L, 20L);
    }

    private final List<DungeonStatus> doNotRun = Arrays.asList(DungeonStatus.ENDED, DungeonStatus.OPENED_GATE, DungeonStatus.CLOSED_GATE, DungeonStatus.RESETTING);

    @Override
    public void run() {
        if (doNotRun.contains(rpgcore.getTajemniczePiaskiManager().getDungeonStatus())) return;
        ((TextHologramLine) rpgcore.getTajemniczePiaskiManager().getHologram().getLines().get(4)).setText(Utils.format("&7Ilosc graczy: &c" + rpgcore.getTajemniczePiaskiManager().getDungeon().getPlayers().size()));
        if (rpgcore.getTajemniczePiaskiManager().getDungeon().getPlayers().isEmpty()) {
            rpgcore.getTajemniczePiaskiManager().resetDungeon();
            return;
        }
        if (rpgcore.getTajemniczePiaskiManager().getDungeonStatus() != DungeonStatus.ETAP_2) {
            for (Player player : rpgcore.getTajemniczePiaskiManager().getDungeon().getPlayers()) {
                for (final RdzenPiaszczystychWydm rdzen : rpgcore.getTajemniczePiaskiManager().getRdzenieLocation().values()) {
                    rdzen.getHologram().getVisibilitySettings().setIndividualVisibility(player, VisibilitySettings.Visibility.HIDDEN);
                }
            }
        }
        switch (rpgcore.getTajemniczePiaskiManager().getDungeonStatus()) {
            case ETAP_1:
                if (rpgcore.getTajemniczePiaskiManager().getCounter() >= 256) {
                    rpgcore.getTajemniczePiaskiManager().startPhase2();
                }
                break;
            case ETAP_2:
                if (rpgcore.getTajemniczePiaskiManager().getCounter() >= 7) {
                    rpgcore.getTajemniczePiaskiManager().startPhase3();
                }
                break;
            case ETAP_3:
                if (rpgcore.getTajemniczePiaskiManager().getCounter() >= 2) {
                    rpgcore.getTajemniczePiaskiManager().startPhase4();
                }
                break;
            case ETAP_4:
                if (rpgcore.getTajemniczePiaskiManager().getCounter() >= 6) {
                    rpgcore.getTajemniczePiaskiManager().startPhase5();
                }
                break;
            case BOSS:
                if (rpgcore.getTajemniczePiaskiManager().getCounter() >= 1) {
                    rpgcore.getTajemniczePiaskiManager().endDungeon();
                }
        }
    }
}
