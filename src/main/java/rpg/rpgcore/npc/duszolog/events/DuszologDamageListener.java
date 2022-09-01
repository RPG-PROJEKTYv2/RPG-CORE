package rpg.rpgcore.npc.duszolog.events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import rpg.rpgcore.RPGCORE;

public class DuszologDamageListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(final EntityDamageByEntityEvent e) {
        if (e.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
            ArmorStand as = (ArmorStand) e.getEntity();
            if (as.getHelmet().equals(RPGCORE.getInstance().getDuszologNPC().getHelm())) {
                e.setCancelled(true);
            }
        }
    }
}
