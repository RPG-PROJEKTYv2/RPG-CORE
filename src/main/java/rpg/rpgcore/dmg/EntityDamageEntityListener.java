package rpg.rpgcore.dmg;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.metiny.MetinyHelper;
import rpg.rpgcore.utils.Utils;


public class EntityDamageEntityListener implements Listener {

    private final RPGCORE rpgcore;

    public EntityDamageEntityListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(final EntityDamageByEntityEvent e) {

        if (e.getEntity().getType().equals(EntityType.ENDER_CRYSTAL)) {
            if (e.getDamager() instanceof Player) {
                e.setCancelled(true);
                if (e.getEntity().getCustomName() == null) {
                    return;
                }
                if (!rpgcore.getCooldownManager().hasMetinyCooldown(e.getDamager().getUniqueId())) {
                    MetinyHelper.attackMetin(Integer.parseInt(e.getEntity().getCustomName()), 1, e.getEntity(), ((Player) e.getDamager()).getPlayer());
                    rpgcore.getCooldownManager().givePlayerMetinyCooldown(e.getDamager().getUniqueId());
                    return;
                }
                return;
            }
        }

        // ATAKUJACY JEST GRACZEM
        if (e.getDamager() instanceof Player) {

            final Player attacker = (Player) e.getDamager();

            if (e.getEntity() instanceof Player) {

                //... Victim jest Graczem

                final Player victim = (Player) e.getEntity();

                final double attackerDmg = rpgcore.getDamageManager().calculateAttackerDmgToPlayer(attacker);
                final double victimDef = rpgcore.getDamageManager().calculateDef(victim, "ludzie");

                double finalDmg = Double.parseDouble(String.format("%.2f", attackerDmg - victimDef));
                if (finalDmg < 0) {
                    finalDmg = 0;
                }
                attacker.sendMessage("Final dmg - " + finalDmg);
                victim.sendMessage("Def: " + victimDef);
                e.setDamage(EntityDamageEvent.DamageModifier.BASE, finalDmg);
                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket("&c&l", e.getFinalDamage(), victim.getLocation(), attacker));

            } else {
                // ... Victim jest Mobem

                if (attacker.getItemInHand() != null && attacker.getItemInHand().getType() != Material.AIR) {
                    if (RPGCORE.getInstance().getUserManager().find(attacker.getUniqueId()).getLvl() < Utils.getTagInt(attacker.getItemInHand(), "lvl")) {
                        attacker.sendMessage(Utils.format("&8[&c✘&8] &cNie posiadasz wymaganego poziomu, aby uzywac tego przedmiotu!"));
                        e.setCancelled(true);
                        return;
                    }
                }

                final LivingEntity victim = (LivingEntity) e.getEntity();
                final double attackerDmg = rpgcore.getDamageManager().calculateAttackerDmgToEntity(attacker, victim);
                e.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
                e.setDamage(EntityDamageEvent.DamageModifier.BASE, attackerDmg);
                attacker.sendMessage("Base - " + e.getDamage(EntityDamageEvent.DamageModifier.BASE));
                attacker.sendMessage("Armor - " + e.getDamage(EntityDamageEvent.DamageModifier.ARMOR));
                attacker.sendMessage("Resistance - " + e.getDamage(EntityDamageEvent.DamageModifier.RESISTANCE));
                attacker.sendMessage("Dmg event - " + e.getDamage());
                attacker.sendMessage("Dmg final - " + e.getFinalDamage());
                if (victim.getCustomName().contains("Ksiaze Mroku")) {
                    if (((Monster) victim).getTarget() != attacker) {
                        ((Monster) victim).setTarget(attacker);
                    }
                }

                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket("&c&l", e.getFinalDamage(), victim.getLocation(), attacker));
            }
            if (e.getDamage() < ((LivingEntity) e.getEntity()).getHealth()) {
                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamageActionBarPacket(attacker, e.getFinalDamage(), (LivingEntity) e.getEntity()));
            }
        }

        // ATAKUJACY JEST GRACZEM

    }

    private void updateCustomName(final Entity entity, final double dmg) {
        if ((entity.getCustomName() != null && entity.getCustomName().contains("Podwodny Wladca")) || entity.getPassenger().getCustomName().contains("Podwodny Wladca")) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (entity.isInsideVehicle()) {
                livingEntity.setCustomName(Utils.format("&6&lPodwodny Wladca &c" + (int) livingEntity.getHealth() + "&7/&c" + (int) livingEntity.getMaxHealth() + " ❤"));
            } else {
                livingEntity = (LivingEntity) entity.getPassenger();
                livingEntity.damage(dmg);
                livingEntity.setCustomName(Utils.format("&6&lPodwodny Wladca &c" + (int) livingEntity.getHealth() + "&7/&c" + (int) livingEntity.getMaxHealth() + " ❤"));
            }
        }
    }

























    /*if (damager instanceof Player) {
            final Player p = (Player) damager;
            double dmgZMiecza;

            // SETOWANIE NAZWY MOBOW Z RYBAKA

            if (p.getItemInHand() == null) {
                e.setDamage(rpgcore.getDamageManager().calculateDamage(p.getUniqueId(), 0.0));
                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket(e.getDamage(), entity.getLocation(), p));
                if ((entity.getCustomName() != null && entity.getCustomName().contains("Podwodny Wladca")) || entity.getPassenger().getCustomName().contains("Podwodny Wladca")) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (entity.isInsideVehicle()) {
                        livingEntity.setCustomName(Utils.format("&6&lPodwodny Wladca &c" + (int) livingEntity.getHealth() + "&7/&c" + (int) livingEntity.getMaxHealth() + " ❤"));
                    } else {
                        livingEntity = (LivingEntity) entity.getPassenger();
                        livingEntity.damage(e.getDamage());
                        livingEntity.setCustomName(Utils.format("&6&lPodwodny Wladca &c" + (int) livingEntity.getHealth() + "&7/&c" + (int) livingEntity.getMaxHealth() + " ❤"));
                    }
                }
                return;
            }

            // ... BIJE MIECZEM

            if (String.valueOf(p.getItemInHand().getType()).contains("SWORD")) {
                for (int j = 0; j < p.getItemInHand().getItemMeta().getLore().size(); j++) {
                    if (p.getItemInHand().getItemMeta().getLore().get(j).trim().contains("Obrazenia: ")) {
                        dmgZMiecza = Double.parseDouble(Utils.removeColor(p.getItemInHand().getItemMeta().getLore().get(j).trim().replace("Obrazenia: ", "")));
                        e.setDamage(rpgcore.getDamageManager().calculateDamage(p.getUniqueId(), dmgZMiecza));
                    }
                }

            } else {
                e.setDamage(rpgcore.getDamageManager().calculateDamage(p.getUniqueId(), 0.0));
                //TODO WALI COS NULLPOINTEREM W IFIE I TRZEBA SPRAWDZIC CO

            }
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket(e.getDamage(), entity.getLocation(), p));
        }*/

}
