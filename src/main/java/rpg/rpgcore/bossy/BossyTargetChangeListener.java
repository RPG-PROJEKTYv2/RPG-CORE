package rpg.rpgcore.bossy;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import rpg.rpgcore.utils.ChanceHelper;

import java.util.List;
import java.util.stream.Collectors;

public class BossyTargetChangeListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(final EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (e.getEntity().getCustomName() != null && e.getEntity().getCustomName().contains("BOSS")) {
            if (ChanceHelper.getChance(40)) {
                final List<Player> targets = e.getEntity().getNearbyEntities(6, 6, 6).stream().filter(entity -> entity instanceof Player).map(entity -> (Player) entity).collect(Collectors.toList());
                if (targets.size() > 1) {
                    if (!(e.getEntity() instanceof Creature)) return;
                    final Creature entity = (Creature) e.getEntity();
                    entity.setTarget(targets.get(ChanceHelper.getRandInt(0, targets.size() - 1)));
                }
            }
        }
    }
}
