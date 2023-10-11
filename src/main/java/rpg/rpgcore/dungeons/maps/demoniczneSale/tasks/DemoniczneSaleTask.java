package rpg.rpgcore.dungeons.maps.demoniczneSale.tasks;

import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Dungeony;

import java.util.Arrays;
import java.util.List;

public class DemoniczneSaleTask implements Runnable {
    private final RPGCORE rpgcore;

    public DemoniczneSaleTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(this.rpgcore, this, 20L, 20L);
    }

    private final List<DungeonStatus> doNotRun = Arrays.asList(DungeonStatus.ENDED, DungeonStatus.OPENED_GATE, DungeonStatus.CLOSED_GATE, DungeonStatus.RESETTING);

    @Override
    public void run() {
        if (doNotRun.contains(rpgcore.getDemoniczneSaleManager().getDungeonStatus())) return;
        ((TextHologramLine) rpgcore.getDemoniczneSaleManager().getHologram().getLines().get(4)).setText(Utils.format("&7Ilosc graczy: &c" + rpgcore.getDemoniczneSaleManager().getDungeon().getPlayers().size()));
        if (rpgcore.getDemoniczneSaleManager().getDungeon().getPlayers().isEmpty()) {
            rpgcore.getDemoniczneSaleManager().resetDungeon();
            return;
        }
        rpgcore.getDemoniczneSaleManager().incrementTime();
        if (rpgcore.getDemoniczneSaleManager().getTime() >= 7_200 && rpgcore.getDemoniczneSaleManager().getDungeonStatus() == DungeonStatus.ETAP_1) {
            rpgcore.getDemoniczneSaleManager().startPhase2();
            return;
        }
        switch (rpgcore.getDemoniczneSaleManager().getDungeonStatus()) {
            case ETAP_1:
                for (Player player : rpgcore.getDemoniczneSaleManager().getDungeon().getPlayers()) {
                    if (player.getInventory().containsAtLeast(Dungeony.I_DEMONICZNY_SARKOFAG.getItemStack(), 1)) {
                        rpgcore.getDemoniczneSaleManager().deactivateSpawners();
                        break;
                    }
                }
                break;
            case ETAP_2:
                if (rpgcore.getDemoniczneSaleManager().getCounter() == 1) {

                    rpgcore.getDemoniczneSaleManager().startPhase3();
                }
                break;
            case BOSS:
                if (rpgcore.getDemoniczneSaleManager().getCounter() == 1) {
                    rpgcore.getDemoniczneSaleManager().endDungeon();
                }
                break;
        }
    }
}
