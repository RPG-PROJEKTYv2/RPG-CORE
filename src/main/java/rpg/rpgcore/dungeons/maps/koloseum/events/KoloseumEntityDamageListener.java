package rpg.rpgcore.dungeons.maps.koloseum.events;

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

public class KoloseumEntityDamageListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(final EntityDamageByEntityEvent e) {
        if (!e.getDamager().getWorld().getName().equalsIgnoreCase("Dungeon70-80")) return;
        if (e.getDamager() instanceof Player) {
            final String name = Utils.removeColor(e.getEntity().getName());
            if (name.equals("[BOSS] Czempion Areny") || name.equals("[MiniBOSS] Wyznawca Ateny") || name.equals("[MiniBOSS] Wyznawca Posejdona") || name.equals("[MiniBOSS] Wyznawca Zeusa") || name.equals("[MiniBOSS] Wyznawca Hadesa")) {
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

        switch (Utils.removeColor(name)) {
            case "[MiniBOSS] Wyznawca Ateny":
                if (ChanceHelper.getChance(15)) {
                    player.setVelocity(new Vector(0, 2.5, 0));
                    player.sendMessage(Utils.format(name + " &fpodrzucil Cie!"));
                }
                if (ChanceHelper.getChance(10)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 4));
                    player.sendMessage(Utils.format(name + " &2otrul Cie!"));
                }
                if (player.getActivePotionEffects().stream().anyMatch(potion -> potion.getType() == PotionEffectType.POISON)) {
                    e.setDamage(EntityDamageEvent.DamageModifier.BASE, DoubleUtils.round(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.15, 2));
                }
                return;
            case "[MiniBOSS] Wyznawca Posejdona":
            case "[MiniBOSS] Wyznawca Zeusa":
            case "[MiniBOSS] Wyznawca Hadesa":
                if (ChanceHelper.getChance(15)) {
                    player.setVelocity(new Vector(0, 2.5, 0));
                    player.sendMessage(Utils.format(name + " &fpodrzucil Cie!"));
                }
                if (ChanceHelper.getChance(15)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 4));
                    player.sendMessage(Utils.format(name + " &2otrul Cie!"));
                }
                if (ChanceHelper.getChance(5)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 4));
                    player.teleport(RPGCORE.getInstance().getKoloseumManager().getSpawnLocation());
                    player.sendMessage(Utils.format(name + " &7ogluszyl Cie!"));
                }
                if (ChanceHelper.getChance(5)) {
                    final LivingEntity entity = (LivingEntity) e.getDamager();

                    entity.setHealth(Math.min(entity.getHealth() + (player.getHealth() / 5), entity.getMaxHealth()));
                    player.setHealth(player.getHealth() - (player.getHealth() / 5));
                    player.sendMessage(Utils.format(name + " &cwykradl 20% twojego zdrowia!"));
                }
                if (player.getActivePotionEffects().stream().anyMatch(potion -> potion.getType() == PotionEffectType.POISON)) {
                    e.setDamage(EntityDamageEvent.DamageModifier.BASE, DoubleUtils.round(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.15, 2));
                }
                return;
            case "[BOSS] Czempion Areny":
                if (ChanceHelper.getChance(15)) {
                    final LivingEntity entity = (LivingEntity) e.getDamager();

                    entity.setHealth(Math.min(entity.getHealth() + (player.getHealth() / 5), entity.getMaxHealth()));
                    player.setHealth(player.getHealth() - (player.getHealth() / 5));
                    player.sendMessage(Utils.format(name + " &cwykradl 20% twojego zdrowia!"));
                }
                if (ChanceHelper.getChance(5)) {
                    e.setDamage(EntityDamageEvent.DamageModifier.BASE, DoubleUtils.round(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.5, 2));
                    player.sendMessage(Utils.format(name + " &czmniejszyl twoja &aodpornosc &co &650%&c!"));
                }
                if (ChanceHelper.getChance(10)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 8));
                    player.sendMessage(Utils.format(name + " &bzamrozil Cie!"));
                }
                if (ChanceHelper.getChance(15)) {
                    final Location location = e.getDamager().getLocation();

                    for (int i = 0; i < 8; i++) RPGCORE.getMythicMobs().getMobManager().spawnMob(
                            "KOLOSEUM-MOB1",
                            location.clone().add(ChanceHelper.getRandDouble(-0.5, 0.5), 3, ChanceHelper.getRandDouble(-0.5, 0.5))
                    );

                    player.sendMessage(Utils.format(name + " &eprzywolal swoja armie!"));
                }
        }
    }
}
