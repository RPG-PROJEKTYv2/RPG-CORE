package rpg.rpgcore.dungeons.custom.zamekNieskonczonosci.events;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.custom.zamekNieskonczonosci.enums.ZamekNieskonczonosciLocations;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Random;

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
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            if (e.getEntity() instanceof Player) {
                e.setDamage(0);
                e.setCancelled(true);
                ((Player) e.getEntity()).damage(20);
                return;
            }
        }
        if (!(e.getDamager() instanceof Player)) {
            final Entity entity = e.getDamager();
            if (entity.getCustomName() == null) return;
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
                    player.setVelocity(new Vector(player.getEyeLocation().getX() * -1 / 20, 2, player.getEyeLocation().getZ() * -1 / 20));
                    player.sendMessage(Utils.format("&cSluga Ksiecia Mroku: &fOdejdz!"));
                }
                return;
            }
            if (entity.getCustomName().contains("Ksiaze Mroku")) {
                if (!(e.getEntity() instanceof Player)) return;
                final Player player = (Player) e.getEntity();
                if (ChanceHelper.getChance(0)) {
                    if (player.getHealth() - player.getMaxHealth()/5 <= 0) {
                        player.setHealth(0);
                    } else {
                        player.setHealth(player.getHealth() - (player.getMaxHealth() / 5));
                    }
                    return;
                }
                if (ChanceHelper.getChance(0)) {
                    entity.setVelocity(new Vector(0, 3, 0));
                    int b = RPGCORE.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(RPGCORE.getInstance(), () -> this.spawnCircleParticles(entity.getLocation(), 7, Effect.CRIT), 1L, 5L);
                    RPGCORE.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getServer().getScheduler().cancelTask(b), 30L);
                    RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
                        entity.setVelocity(new Vector(0, -3, 0));
                        for (Entity en : entity.getNearbyEntities(7, 2, 7)) {
                            if (!(en instanceof Player)) continue;
                            if ((((Player) en).getMaxHealth() / 2) - ((Player) en).getHealth() <= 0) {
                                ((Player) en).setHealth(0);
                                return;
                            }
                            ((Player) en).setHealth((((Player) en).getMaxHealth() / 2) - ((Player) en).getHealth());
                        }
                    }, 20L);
                    return;
                }

                if (ChanceHelper.getChance(99)) {
                    this.castSuperAbbility(entity);
                    return;
                }
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

        if (entity.getCustomName() == null) return;
        if (entity.getCustomName().contains("Sluga Ksiecia Mroku")) {
            final String entityName = Utils.removeColor(entity.getCustomName());
            final String entityPlayerName = entityName.substring(entityName.indexOf("-") + 2, entityName.lastIndexOf(" "));
            final int hitNumber = Integer.parseInt(entityName.substring(entityName.lastIndexOf(" ") + 1));
            e.setCancelled(true);
            if (!player.getName().equals(entityPlayerName)) {
                if (((LivingEntity) entity).getHealth() < ((LivingEntity) entity).getMaxHealth()) {
                    ((LivingEntity) entity).setHealth(((LivingEntity) entity).getHealth() + 1);
                }
                player.sendMessage(Utils.format("&cSluga Ksiecia Mroku: &fhaha... nie mozesz mnie trafic!"));
                return;
            }
            if (hitNumber - 1 > 0) {
                ((LivingEntity) entity).damage(1);
                entity.setCustomName(Utils.format("&cSluga Ksiecia Mroku &7- &f" + player.getName() + " " + (hitNumber - 1)));
            } else {
                ((LivingEntity) entity).damage(1);
                entity.setCustomName(Utils.format("&cSluga Ksiecia Mroku &7- &f" + RPGCORE.getInstance().getZamekNieskonczonosciManager().getRandomPlayerName(RPGCORE.getInstance().getZamekNieskonczonosciManager().players) + " 5"));
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
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getNmsManager().sendActionBar(player, Utils.format("&cSluga Ksiecia Mroku" + " &f" + ((LivingEntity) entity).getHealth() + "&c/&f" + ((LivingEntity) entity).getMaxHealth())));
        }
    }

    private void spawnCircleParticles(final Location loc, final float radius, final Effect effect) {
        float angle = 0f;

        while (angle < Math.PI * 2) {
            double x = (radius * Math.sin(angle));
            double z = (radius * Math.cos(angle));
            angle += 0.1;

            loc.getWorld().playEffect(new Location(loc.getWorld(), loc.getX() + x, 6, loc.getZ() + z), effect, 2);
        }
    }

    private void castSuperAbbility(final Entity entity) {
        final Location loc = ZamekNieskonczonosciLocations.getLocationByName("SuperAbility_" + (new Random().nextInt(4) + 1));
        System.out.println(loc);
        if (loc == null) return;
        entity.teleport(loc);
        int a = RPGCORE.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(RPGCORE.getInstance(), () -> {
            for (Player p : RPGCORE.getInstance().getZamekNieskonczonosciManager().players) {
                p.setFireTicks(10);
                final Fireball fireball = (Fireball) p.getWorld().spawnEntity(new Location(loc.getWorld(), loc.getX(), loc.getY() - 2, loc.getZ(), loc.getYaw(), loc.getPitch()), EntityType.FIREBALL);
                fireball.setDirection(new Vector(p.getEyeLocation().getX() * -1, p.getEyeLocation().getY() * -1, p.getEyeLocation().getZ() * -1));
            }
        }, 1L, 180L);
        RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
            RPGCORE.getInstance().getServer().getScheduler().cancelTask(a);
            entity.teleport(ZamekNieskonczonosciLocations.getLocationByName("BackLocation"));
        }, 550L);
    }
}
