package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import rpg.rpgcore.RPGCORE;

public class EntityDamageEntityListener implements Listener {

    private final RPGCORE rpgcore;

    public EntityDamageEntityListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageByEntityEvent e){
        //TODO zrobic wyswietlanie dmg po uderzeniu moba, obstawiam ze na armorstandach!
        if (e.getDamager() instanceof Player){
            final Player damager = (Player) e.getDamager();
            final Entity mob = e.getEntity();
            damager.sendMessage("DMG - " + e.getDamage());
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket(e.getDamage(), mob.getLocation()));
        }
    }


}
