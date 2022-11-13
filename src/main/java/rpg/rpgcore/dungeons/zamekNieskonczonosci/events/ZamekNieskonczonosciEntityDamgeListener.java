package rpg.rpgcore.dungeons.zamekNieskonczonosci.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import rpg.rpgcore.RPGCORE;
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
        final Entity entity = e.getEntity();
        if (!(e.getDamager() instanceof Player)) {
            //
        }

        if (entity instanceof Player) {
            e.setCancelled(true);
            return;
        }

        final Player player = (Player) e.getDamager();
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
                    RPGCORE.getInstance().getZamekNieskonczonosciManager().startPhase2();
                }
                return;
            }
            RPGCORE.getInstance().getNmsManager().sendActionBar(player, Utils.format("&cSluga Ksiecia Mroku" + " &f" + ((LivingEntity) entity).getHealth() + "&c/&f" + ((LivingEntity) entity).getMaxHealth()));
        }
    }
}
