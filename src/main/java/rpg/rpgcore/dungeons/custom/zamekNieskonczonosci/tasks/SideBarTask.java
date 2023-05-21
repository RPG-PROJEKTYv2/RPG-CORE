package rpg.rpgcore.dungeons.custom.zamekNieskonczonosci.tasks;

import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;

public class SideBarTask implements Runnable {
    private final RPGCORE rpgcore;

    public SideBarTask(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        int i = this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, this, 20L, 20L);
        rpgcore.getZamekNieskonczonosciManager().taskList.add(i);
    }

    @Override
    public void run() {
        for (Player player : rpgcore.getZamekNieskonczonosciManager().players) {
            rpgcore.getZamekNieskonczonosciManager().createNewScreboardForPlayer(player);
        }
    }
}
