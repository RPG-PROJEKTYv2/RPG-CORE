package rpg.rpgcore.dungeons.maps.tajemniczePiaski.events;

import org.bukkit.Location;
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
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

public class TajemniczePiaskiEntityDamageListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(final EntityDamageByEntityEvent e) {
        if (!e.getDamager().getWorld().getName().equalsIgnoreCase("Dungeon80-90")) return;
        if (e.getDamager() instanceof Player) {
            final String name = Utils.removeColor(e.getEntity().getName());
            if (name.equals("[BOSS] Cesarz Pustyni") || name.equals("[MiniBOSS] Pustynny Przyzywacz") || name.equals("[MiniBOSS] Pustynny Tarczownik")) {
                if (ChanceHelper.getChance(10)) {
                    e.getEntity().teleport(e.getDamager());
                    e.getDamager().sendMessage(Utils.format(e.getEntity().getCustomName() + " &dteleportowal sie do Ciebie!"));
                }
            }
            return;
        }
        final String name = e.getDamager().getCustomName();
        if (name == null) return;
        if (!(e.getEntity() instanceof Player)) return;
        final Player player = (Player) e.getEntity();
        final LivingEntity entity = (LivingEntity) e.getDamager();
        switch (Utils.removeColor(name)) {
            case "[MiniBOSS] Pustynny Tarczownik":
                if (ChanceHelper.getChance(5)) {
                    entity.setHealth(entity.getMaxHealth());
                    player.sendMessage(Utils.format(name + " &awyleczyl sie!"));
                }
                if (ChanceHelper.getChance(10)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 4));
                    player.sendMessage(Utils.format(name + " &2otrul Cie!"));
                }
                if (ChanceHelper.getChance(10)) {
                    double pushX = player.getLocation().getDirection().normalize().getX() * -3.5;
                    double pushY = player.getLocation().getDirection().normalize().getY() * -6;
                    double pushZ = player.getLocation().getDirection().normalize().getZ() * -3.5;
                    player.setVelocity(new Vector(pushX, pushY, pushZ));
                    player.sendMessage(Utils.format(name + " &7odrzucil Cie!"));
                }
                if (player.getActivePotionEffects().stream().anyMatch(potion -> potion.getType() == PotionEffectType.POISON)) {
                    e.setDamage(EntityDamageEvent.DamageModifier.BASE, DoubleUtils.round(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.15, 2));
                }
                return;
            case "[MiniBOSS] Pustynny Przyzywacz":
                if (ChanceHelper.getChance(15)) {
                    double health = player.getHealth() * 15 / 100;
                    entity.setHealth(Math.min(entity.getHealth() + health, entity.getMaxHealth()));
                    player.setHealth(player.getHealth() - health);
                    player.sendMessage(Utils.format(name + " &cwykradl 15% twojego zdrowia!"));
                }
                if (ChanceHelper.getChance(10)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 3));
                    player.sendMessage(Utils.format(name + " &7spowolnil Cie!"));
                }
                if (ChanceHelper.getChance(10)) {
                    final Location location = entity.getLocation();

                    for (int i = 0; i < 8; i++)
                        RPGCORE.getMythicMobs().getMobManager().spawnMob(
                                "TAJEMNICZE-PIASKI-MOB2",
                                location.clone().add(ChanceHelper.getRandDouble(-0.5, 0.5), 3, ChanceHelper.getRandDouble(-0.5, 0.5))
                        );

                    player.sendMessage(Utils.format(name + " &eprzywolal swoja armie!"));
                }
                return;
            case "[BOSS] Cesarz Pustyni":
                if (ChanceHelper.getChance(10)) {
                    entity.setHealth(Math.min(entity.getHealth() + (player.getHealth() / 5), entity.getMaxHealth()));
                    player.setHealth(player.getHealth() - (player.getHealth() / 5));
                    player.sendMessage(Utils.format(name + " &cwykradl 20% twojego zdrowia!"));
                }
                if (ChanceHelper.getChance(10)) {
                    final Location location = entity.getLocation();

                    for (int i = 0; i < 8; i++)
                        RPGCORE.getMythicMobs().getMobManager().spawnMob(
                                "TAJEMNICZE-PIASKI-MOB2",
                                location.clone().add(ChanceHelper.getRandDouble(-0.5, 0.5), 3, ChanceHelper.getRandDouble(-0.5, 0.5))
                        );

                    player.sendMessage(Utils.format(name + " &eprzywolal swoja armie!"));
                }
                if (ChanceHelper.getChance(15)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 4));
                    player.sendMessage(Utils.format(name + " &2otrul Cie!"));
                }
                if (ChanceHelper.getChance(5)) {
                    entity.setHealth(Math.min(entity.getHealth() + ((entity.getHealth() * 15) / 100), entity.getMaxHealth()));
                    player.sendMessage(Utils.format(name + " &awyleczyl sie o 15% brakujacego zdrowia!"));
                }
                if (player.getActivePotionEffects().stream().anyMatch(potion -> potion.getType() == PotionEffectType.POISON)) {
                    e.setDamage(EntityDamageEvent.DamageModifier.BASE, DoubleUtils.round(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.15, 2));
                }
        }
    }
}
