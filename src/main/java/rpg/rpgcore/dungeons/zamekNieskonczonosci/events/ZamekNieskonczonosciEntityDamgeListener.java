package rpg.rpgcore.dungeons.zamekNieskonczonosci.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;

public class ZamekNieskonczonosciEntityDamgeListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(final EntityDamageEvent e) {
        if (e.getEntity().getWorld().getName().equalsIgnoreCase("zamekNieskonczonosci")) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(final EntityDamageByEntityEvent e) {
        if (!e.getDamager().getWorld().getName().equalsIgnoreCase("zamekNieskonczonosci")) {
            return;
        }
        if (!(e.getDamager() instanceof Player)) {
            final Entity entity = e.getDamager();
            if (entity.getCustomName().contains("Sluga Ksiecia Mroku")) {
                if (!(e.getEntity() instanceof Player)) return;
                final Player player = (Player) e.getEntity();
                if (ChanceHelper.getChance(10)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5, 3));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 5, 4));
                    player.setHealth(player.getHealth() - player.getMaxHealth() / 5);
                    return;
                }
                if (ChanceHelper.getChance(20)) {
                    if (player.getFireTicks() > 0) {
                        e.setDamage(EntityDamageEvent.DamageModifier.BASE, e.getDamage() * 2);
                    }
                    player.setFireTicks(80);
                    player.sendMessage(Utils.format("&cSluga Ksiecia Mroku: &6&lPLON!"));
                }
                if (ChanceHelper.getChance(35)) {
                    player.setVelocity(new Vector(player.getEyeLocation().getX() * -1, 2, player.getEyeLocation().getZ() * -1));
                    player.sendMessage(Utils.format("&cSluga Ksiecia Mroku: &fOdejdz!"));
                }
                return;
            }
            return;
        }

        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            e.setCancelled(true);
            return;
        }

        assert e.getDamager() instanceof Player;
        assert e.getEntity() instanceof LivingEntity;
        final Player player = (Player) e.getDamager();
        final Entity entity = e.getEntity();
        if (entity.getCustomName() != null && entity.getCustomName().contains("Sluga Ksiecia Mroku")) {
            final String entityName = Utils.removeColor(entity.getCustomName());
            final String entityPlayerName = entityName.substring(entityName.indexOf("-") + 2, entityName.lastIndexOf(" "));
            final int hitNumber = Integer.parseInt(entityName.substring(entityName.lastIndexOf(" ") + 1));
            if (!player.getName().equals(entityPlayerName)) {
                e.setCancelled(true);
                if (((LivingEntity) entity).getHealth() < ((LivingEntity) entity).getMaxHealth()) {
                    ((LivingEntity) entity).setHealth(((LivingEntity) entity).getHealth() + 1);
                }
                player.sendMessage(Utils.format("&cSluga Ksiecia Mroku: &fhaha... nie mozesz mnie trafic!"));
                return;
            }
            if (hitNumber - 1 > 0) {
                ((LivingEntity) entity).damage(1);
                entity.setCustomName(Utils.format("&cSluga Ksiecia Mroku &7- &f" + player.getName() + " " + (hitNumber - 1)));
                e.setCancelled(true);
            } else {
                ((LivingEntity) entity).damage(1);
                entity.setCustomName(Utils.format("&cSluga Ksiecia Mroku &7- &f" + RPGCORE.getInstance().getZamekNieskonczonosciManager().getRandomPlayerName(RPGCORE.getInstance().getZamekNieskonczonosciManager().players) + " 5"));
                e.setCancelled(true);
            }


            if (((LivingEntity) entity).getHealth() <= 0) {
                for (Player p : RPGCORE.getInstance().getZamekNieskonczonosciManager().players) {
                    p.sendMessage(Utils.format("&cSluga Ksiecia Mroku: &fWybacz mi Panie... &4Zawiodlem Cie!"));
                }
                RPGCORE.getInstance().getZamekNieskonczonosciManager().miniBossesKilled++;
                entity.remove();

                if (RPGCORE.getInstance().getZamekNieskonczonosciManager().miniBossesKilled == 2) {
                    RPGCORE.getInstance().getZamekNieskonczonosciManager().startKsiazeBossFight();
                }
                return;
            }
            RPGCORE.getInstance().getNmsManager().sendActionBar(player, Utils.format("&cSluga Ksiecia Mroku" + " &f" + ((LivingEntity) entity).getHealth() + "&c/&f" + ((LivingEntity) entity).getMaxHealth()));
        }
    }
}
