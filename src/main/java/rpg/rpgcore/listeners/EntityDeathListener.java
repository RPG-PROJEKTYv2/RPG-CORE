package rpg.rpgcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class EntityDeathListener implements Listener {

    private final RPGCORE rpgcore;

    public EntityDeathListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent e) {
        e.setDroppedExp(0);
        e.getDrops().clear();
        final Player killer = e.getEntity().getKiller();

        if (e.getEntity().getCustomName() == null){
            killer.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen mobek nie dal ci zadnego expa"));
            return;
        }

        String mobName = Utils.removeColor(e.getEntity().getCustomName());
        killer.sendMessage("Mob - " + mobName);
        rpgcore.getLvlManager().updateExp(killer, mobName);


    }
}