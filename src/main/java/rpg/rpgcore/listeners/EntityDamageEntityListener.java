package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.AkcesoriaHelper;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class EntityDamageEntityListener implements Listener {

    private final RPGCORE rpgcore;

    public EntityDamageEntityListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(final EntityDamageByEntityEvent e) {



        // ATAKUJACY JEST GRACZEM
        if (e.getDamager() instanceof Player) {

            final Player attacker = (Player) e.getDamager();

            if (e.getEntity() instanceof Player) {

                //... Victim jest Graczem

                final Player victim = (Player) e.getEntity();

                final double attackerDmg = this.calculateAttackerDmgToPlayer(attacker);
                e.setDamage(attackerDmg);
                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket("&c&l", attackerDmg, victim.getLocation(), attacker));

            } else {
                // ... Victim jest Mobem

                final LivingEntity victim = (LivingEntity) e.getEntity();
                final double attackerDmg = this.calculateAttackerDmgToEntity(attacker);
                e.setDamage(attackerDmg);
                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket("&c&l", attackerDmg, victim.getLocation(), attacker));
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

    private double calculateAttackerDmgToPlayer(final Player attacker) {
        final ItemStack weapon = attacker.getItemInHand();
        final UUID uuid = attacker.getUniqueId();
        double dmg = 0;
        double mnoznik = 1;

        // MIECZ
        if (weapon != null) {
            dmg += Utils.getSharpnessLevel(weapon);
        }

        // Akcesoria
        if (rpgcore.getAkcesoriaManager().isTarczaEquiped(uuid)) {
            dmg += rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 10, "Obrazenia");
        }
        if (rpgcore.getAkcesoriaManager().isNaszyjnikEquiped(uuid)) {
            dmg += rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 11, "Obrazenia");
        }
        if (rpgcore.getAkcesoriaManager().isBransoletaEquiped(uuid)) {
            mnoznik += rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 12, "Srednie Obrazenia");
        }
        if (rpgcore.getAkcesoriaManager().isKolczykiEquiped(uuid)) {
            mnoznik += rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 13, "Silny przeciwko Ludziom");
        }
        if (rpgcore.getAkcesoriaManager().isEnergiaEquiped(uuid)) {
            mnoznik -= rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 15, "Srednie Obrazenia");
        }
        if (rpgcore.getAkcesoriaManager().isZegarekEquiped(uuid)) {
            dmg += rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 16, "Obrazenia");
        }

        return dmg * (mnoznik / 100);
    }

    private double calculateAttackerDmgToEntity(final Player attacker) {
        final ItemStack weapon = attacker.getItemInHand();
        final UUID uuid = attacker.getUniqueId();
        double dmg = 0;
        double mnoznik = 1;

        // MIECZ DMG
        if (!weapon.getType().equals(Material.AIR)) {
            dmg += Utils.getSharpnessLevel(weapon);
            dmg += Utils.getObrazeniaMobyLevel(weapon);
        }

        // Akcesoria
        if (rpgcore.getAkcesoriaManager().isTarczaEquiped(uuid)) {
            dmg += rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 10, "Obrazenia");
        }
        if (rpgcore.getAkcesoriaManager().isNaszyjnikEquiped(uuid)) {
            dmg += rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 11, "Obrazenia");
        }
        if (rpgcore.getAkcesoriaManager().isBransoletaEquiped(uuid)) {
            mnoznik += rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 12, "Srednie Obrazenia");
        }
        if (rpgcore.getAkcesoriaManager().isEnergiaEquiped(uuid)) {
            mnoznik -= rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 15, "Srednie Obrazenia");
        }
        if (rpgcore.getAkcesoriaManager().isZegarekEquiped(uuid)) {
            dmg += rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 16, "Obrazenia");
            mnoznik += rpgcore.getAkcesoriaManager().getAkcesoriaBonus(uuid, 16, "Silny przeciwko potworom");
        }

        // BAO
        if (!rpgcore.getBaoManager().getBaoBonusy(uuid).contains("Brak Bonusu")) {
            final String[] bonusy = rpgcore.getBaoManager().getBaoBonusy(uuid).split(",");
            final String[] wartosci = rpgcore.getBaoManager().getBaoBonusyWartosci(uuid).split(",");

            if (bonusy[0].equals("Srednie obrazenia") || bonusy[0].equals("Srednia defensywa przeciwko potworom")) {
                mnoznik += Double.parseDouble(wartosci[0]);
            }
            if (bonusy[3].equals("Dodatkowe Obrazenia")) {
                dmg += Double.parseDouble(wartosci[3]);
            }

        }



        return dmg * (mnoznik / 100);
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
