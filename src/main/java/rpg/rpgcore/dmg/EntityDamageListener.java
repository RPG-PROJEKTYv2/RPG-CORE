package rpg.rpgcore.dmg;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

import java.util.stream.Collectors;

public class EntityDamageListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(final EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.DROWNING || e.getCause() == EntityDamageEvent.DamageCause.FALL
                || e.getCause() == EntityDamageEvent.DamageCause.LAVA || e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING
                || e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            e.setCancelled(true);
        }
        if (e.getEntity() instanceof Player) {
            final Player victim = (Player) e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.POISON) {
                if (Utils.customDungeonWorlds.contains(victim.getWorld())) return;
                final PotionEffect effect = victim.getActivePotionEffects().stream().filter(potion -> potion.getType() == PotionEffectType.POISON).collect(Collectors.toList()).get(0);
                final int amplifier = effect.getAmplifier();

                double damage = 0;
                switch (amplifier) {
                    case 0:
                        damage = victim.getMaxHealth() / 50 * 2;
                        break;
                    case 1:
                        damage = victim.getMaxHealth() / 40 * 2;
                }


                e.setDamage(EntityDamageEvent.DamageModifier.BASE, damage);
                RPGCORE.getInstance().getDamageManager().sendDamagePacket("&2", damage, victim, victim);
                return;
            }

            if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                if (Utils.customDungeonWorlds.contains(victim.getWorld())) return;
                double damage = DoubleUtils.round(victim.getMaxHealth() / 25 * 2, 2);
                e.setDamage(EntityDamageEvent.DamageModifier.BASE, damage);
                RPGCORE.getInstance().getDamageManager().sendDamagePacket("&6", damage, victim, victim);
            }
        }
    }

}
