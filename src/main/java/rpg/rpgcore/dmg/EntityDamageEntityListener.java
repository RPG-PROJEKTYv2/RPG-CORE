package rpg.rpgcore.dmg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.artefakty.Artefakty;
import rpg.rpgcore.metiny.MetinyHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
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
                final Player attacker = (Player) e.getDamager();
                e.setCancelled(true);

                if (attacker.getItemInHand() != null && attacker.getItemInHand().getType() != Material.AIR) {
                    if (RPGCORE.getInstance().getUserManager().find(attacker.getUniqueId()).getLvl() < Utils.getTagInt(attacker.getItemInHand(), "lvl")) {
                        attacker.sendMessage(Utils.format("&8[&c✘&8] &cNie posiadasz wymaganego poziomu, aby uzywac tego przedmiotu!"));
                        return;
                    }
                }

                if (e.getEntity().getCustomName() == null) {
                    System.out.println(1);
                    return;
                }

                if (!rpgcore.getCooldownManager().hasMetinyCooldown(attacker.getUniqueId())) {
                    final Location loc = e.getEntity().getLocation();
                    MetinyHelper.attackMetin(
                            new Location(loc.getWorld(),
                                    DoubleUtils.round(loc.getX(), 2),
                                    DoubleUtils.round(loc.getY(), 2),
                                    DoubleUtils.round(loc.getZ(), 2)),
                            e.getEntity(), attacker);
                    rpgcore.getCooldownManager().givePlayerMetinyCooldown(attacker.getUniqueId());
                    return;
                }
                return;
            }
        }

        if (e.getEntityType() == EntityType.ARMOR_STAND) return;

        final String entityName = Utils.removeColor(e.getEntity().getName());
        if (entityName.equalsIgnoreCase("Magazynier") || entityName.equalsIgnoreCase("Kupiec") || entityName.equalsIgnoreCase("Metinolog") || entityName.equalsIgnoreCase("Przyrodnik") ||
            entityName.equalsIgnoreCase("Kolekcjoner") || entityName.equalsIgnoreCase("Lowca") || entityName.equalsIgnoreCase("Dungeony") || entityName.equalsIgnoreCase("ItemShop") ||
            entityName.equalsIgnoreCase("Pomocnik Gornika") || entityName.equalsIgnoreCase("Duszolog") || entityName.equalsIgnoreCase("TELEPORTER") || entityName.equalsIgnoreCase("Rybak") ||
            entityName.equalsIgnoreCase("Kowal") || entityName.equalsIgnoreCase("Trener") || entityName.equalsIgnoreCase("Medyk") || entityName.equalsIgnoreCase("Gornik") ||
            entityName.equalsIgnoreCase("Lesnik") || entityName.equalsIgnoreCase("Zmianki") || entityName.equalsIgnoreCase("Wyslannik") || entityName.equalsIgnoreCase("Zaginiony Wladca") ||
            entityName.equalsIgnoreCase("czarownica") || entityName.equalsIgnoreCase("Bremu")) {
            e.setCancelled(true);
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.THORNS) {
            if (e.getDamager() instanceof Player) {
                e.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
                e.setDamage(EntityDamageEvent.DamageModifier.BASE, 0);
                if (e.getEntity() instanceof Player) {
                    final Player victim = (Player) e.getDamager();
                    final Player attacker = (Player) e.getEntity();

                    if (victim.getInventory().contains(Artefakty.getArtefakt("Egzekutor", victim))) {
                        if (ChanceHelper.getChance(5.0)) {
                            if (!rpgcore.getCooldownManager().hasEgzekutorCooldown(victim.getUniqueId())) {
                                rpgcore.getKociolkiManager().find(victim.getUniqueId()).setEgzekutor(true);
                                rpgcore.getKociolkiManager().find(victim.getUniqueId()).setEgzekutorTime(5);
                                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataKociolki(victim.getUniqueId(), rpgcore.getKociolkiManager().find(victim.getUniqueId())));
                                rpgcore.getCooldownManager().givePlayerEgzekutorCooldown(victim.getUniqueId());
                                victim.sendMessage(Utils.format("&4&lArtefakty &8>> &aPomyslnie aktywowano &c&lEgzekutora &a!"));
                            }
                        }
                    }
                    boolean przebicie = false;
                    if (!ChanceHelper.getChance(rpgcore.getDamageManager().calculatePrzebicie(victim))) {
                        if (ChanceHelper.getChance(rpgcore.getDamageManager().calculateVictimBlok(attacker, victim))) {
                            e.setCancelled(true);
                            if (ChanceHelper.getChance(15.0)) {
                                final int trueDmg = rpgcore.getBonusesManager().find(victim.getUniqueId()).getBonusesUser().getTruedamage();
                                attacker.setHealth(attacker.getHealth() - trueDmg);
                                attacker.sendMessage(Utils.format("&cGracz " + victim.getName() + " zadal ci obrazenia o wartosci &f" + (trueDmg / 2.0) + "&c❤ twojego prawdziwego zdrowia!"));
                                victim.sendMessage(Utils.format("&aZadales/-as graczu " + attacker.getName() + " obrazenia o wartosci &f" + (trueDmg / 2.0) + "&c❤ &ajego prawdziwego zdrowia!"));
                            }
                            victim.sendMessage(Utils.format("&cTwoj atak zostal zablokowany przez gracza &4" + attacker.getName() + "&c!"));
                            attacker.sendMessage(Utils.format("&aZablokowales/-as cios gracza " + victim.getName() + "&a!"));
                            return;
                        }
                    } else {
                        przebicie = true;
                        victim.sendMessage(Utils.format("&cTwoj atak przebil pancerz gracza &4" + attacker.getName() + "&c!"));
                        attacker.sendMessage(Utils.format("&aGracz " + victim.getName() + " przebil twoj pancerz!"));
                    }

                    double playerDamage = DoubleUtils.round(rpgcore.getDamageManager().calculateAttackerDmgToPlayer(attacker, victim), 2);
                    double wartoscDefa  = rpgcore.getDamageManager().calculatePlayerDef(victim);

                    if (rpgcore.getKociolkiManager().find(victim.getUniqueId()).isEgzekutor()) {
                        playerDamage *= 1.75;
                        attacker.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60, 1));
                        attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 3));
                        attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 1));
                        attacker.sendMessage(Utils.format("&4&lArtefakty &8>> &7Gracz &c" + victim.getName() + " &7nalozyl na Ciebie efekt &c&lEgzekutora&7!"));
                    }

                    if (attacker.hasPotionEffect(PotionEffectType.WEAKNESS)) {
                        for (PotionEffect effect : attacker.getActivePotionEffects()) {
                            if (effect.getType().equals(PotionEffectType.WEAKNESS)) {
                                if (effect.getAmplifier() == 0) {
                                    wartoscDefa *= 0.9;
                                } else if (effect.getAmplifier() == 1) {
                                    wartoscDefa *= 0.8;
                                }
                            }
                        }
                    }
                    if (przebicie) {
                        wartoscDefa *= 0.5;
                    }

                    final double redukcja = DoubleUtils.round( wartoscDefa / (wartoscDefa + 40), 2);

                    final double finalDmg = DoubleUtils.round((1 - redukcja) * playerDamage, 2);

                    final double finalThornsDamage = DoubleUtils.round(finalDmg * 0.00125 * rpgcore.getDamageManager().calculatePlayerThorns(victim), 2);

                    if (finalThornsDamage > 0) {
                        e.setDamage(EntityDamageEvent.DamageModifier.BASE, finalThornsDamage);
                    } else {
                        e.setDamage(EntityDamageEvent.DamageModifier.BASE, 0);
                    }
                    Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket("&c&l", e.getFinalDamage(), e.getDamager().getLocation(), (Player) e.getDamager()));
                    if (finalThornsDamage < ((LivingEntity) e.getEntity()).getHealth()) {
                        Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamageActionBarPacket((Player) e.getDamager(), e.getFinalDamage(), (LivingEntity) e.getEntity()));
                    }
                    return;
                } else if (e.getEntity() instanceof Creature || e.getEntity() instanceof Monster) {
                    final double mnoznik = rpgcore.getDamageManager().calculatePlayerThornsDmg((Player) e.getDamager(), e.getEntity());
                    final double playerDamage = DoubleUtils.round(rpgcore.getDamageManager().calculateAttackerDmgToEntity((Player) e.getDamager(), e.getEntity()), 2);
                    final double finalDmg = DoubleUtils.round(playerDamage * mnoznik, 2);
                    if (mnoznik > 0) {
                        e.setDamage(finalDmg);
                    } else {
                        e.setDamage(EntityDamageEvent.DamageModifier.BASE, 0);
                    }
                    Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket("&c&l", e.getFinalDamage(), e.getDamager().getLocation(), (Player) e.getDamager()));
                    if (finalDmg < ((LivingEntity) e.getEntity()).getHealth()) {
                        Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamageActionBarPacket((Player) e.getDamager(), e.getFinalDamage(), (LivingEntity) e.getEntity()));
                    }
                    return;
                }
            }
        }


        // ATAKUJACY JEST GRACZEM
        if (e.getDamager() instanceof Player) {

            final Player attacker = (Player) e.getDamager();

            if (attacker.getItemInHand() != null && attacker.getItemInHand().getType() != Material.AIR) {
                if (RPGCORE.getInstance().getUserManager().find(attacker.getUniqueId()).getLvl() < Utils.getTagInt(attacker.getItemInHand(), "lvl")) {
                    attacker.sendMessage(Utils.format("&8[&c✘&8] &cNie posiadasz wymaganego poziomu, aby uzywac tego przedmiotu!"));
                    e.setCancelled(true);
                    return;
                }
            }

            if (e.getEntity() instanceof Player) {
                if (e.getCause() == EntityDamageEvent.DamageCause.THORNS) {
                    e.setCancelled(true);
                    return;
                }

                if (rpgcore.getCooldownManager().hasPvpCooldown(attacker.getUniqueId())) {
                    e.setDamage(0);
                    e.setDamage(EntityDamageEvent.DamageModifier.BASE, 0);
                    return;
                }

                //... Victim jest Graczem

                final Player victim = (Player) e.getEntity();

                if (!e.getDamager().getLocation().getWorld().getName().equals("50-60map") && !e.getDamager().getLocation().getWorld().getName().equals("60-70map") &&
                        !e.getDamager().getLocation().getWorld().getName().equals("70-80map") && !e.getDamager().getLocation().getWorld().getName().equals("80-90map") &&
                        !e.getDamager().getLocation().getWorld().getName().equals("90-100map") && !e.getDamager().getLocation().getWorld().getName().equals("100-110map") &&
                        !e.getDamager().getLocation().getWorld().getName().equals("110-120map") && !e.getDamager().getLocation().getWorld().getName().equals("120-130map")) {
                    e.setCancelled(true);
                    return;
                }

                if (rpgcore.getGodManager().containsPlayer(victim.getUniqueId())) {
                    e.setCancelled(true);
                    return;
                }

                if (attacker.getInventory().contains(Artefakty.getArtefakt("Egzekutor", attacker))) {
                    if (ChanceHelper.getChance(5.0)) {
                        if (!rpgcore.getCooldownManager().hasEgzekutorCooldown(attacker.getUniqueId())) {
                            rpgcore.getKociolkiManager().find(attacker.getUniqueId()).setEgzekutor(true);
                            rpgcore.getKociolkiManager().find(attacker.getUniqueId()).setEgzekutorTime(5);
                            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataKociolki(attacker.getUniqueId(), rpgcore.getKociolkiManager().find(attacker.getUniqueId())));
                            rpgcore.getCooldownManager().givePlayerEgzekutorCooldown(attacker.getUniqueId());
                            attacker.sendMessage(Utils.format("&4&lArtefakty &8>> &aPomyslnie aktywowano &c&lEgzekutora &a!"));
                        }
                    }
                }
                boolean przebicie = false;
                if (!ChanceHelper.getChance(rpgcore.getDamageManager().calculatePrzebicie(attacker))) {
                    if (ChanceHelper.getChance(rpgcore.getDamageManager().calculateVictimBlok(victim, attacker))) {
                        e.setCancelled(true);
                        if (ChanceHelper.getChance(15.0)) {
                            final int trueDmg = rpgcore.getBonusesManager().find(attacker.getUniqueId()).getBonusesUser().getTruedamage();
                            victim.setHealth(victim.getHealth() - trueDmg);
                            victim.sendMessage(Utils.format("&cGracz " + attacker.getName() + " zadal ci obrazenia o wartosci &f" + (trueDmg / 2.0) + "&c❤ twojego prawdziwego zdrowia!"));
                            attacker.sendMessage(Utils.format("&aZadales/-as graczu " + victim.getName() + " obrazenia o wartosci &f" + (trueDmg / 2.0) + "&c❤ &ajego prawdziwego zdrowia!"));
                        }
                        attacker.sendMessage(Utils.format("&cTwoj atak zostal zablokowany przez gracza &4" + victim.getName() + "&c!"));
                        victim.sendMessage(Utils.format("&aZablokowales/-as cios gracza " + attacker.getName() + "&a!"));
                        rpgcore.getCooldownManager().givePvpCooldown(attacker.getUniqueId());
                        return;
                    }
                } else {
                    przebicie = true;
                    attacker.sendMessage(Utils.format("&cTwoj atak przebil pancerz gracza &4" + victim.getName() + "&c!"));
                    victim.sendMessage(Utils.format("&aGracz " + attacker.getName() + " przebil twoj pancerz!"));
                }


                double attackerDmg = rpgcore.getDamageManager().calculateAttackerDmgToPlayer(attacker, victim);
                double wartoscDefa  = rpgcore.getDamageManager().calculatePlayerDef(victim);


                if (rpgcore.getKociolkiManager().find(attacker.getUniqueId()).isEgzekutor()) {
                    attackerDmg *= 1.75;
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60, 1));
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 3));
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 1));
                    victim.sendMessage(Utils.format("&4&lArtefakty &8>> &7Gracz &c" + attacker.getName() + " &7nalozyl na Ciebie efekt &c&lEgzekutora&7!"));
                }

                if (victim.hasPotionEffect(PotionEffectType.WEAKNESS)) {
                    for (PotionEffect effect : victim.getActivePotionEffects()) {
                        if (effect.getType().equals(PotionEffectType.WEAKNESS)) {
                            if (effect.getAmplifier() == 0) {
                                wartoscDefa *= 0.9;
                            } else if (effect.getAmplifier() == 1) {
                                wartoscDefa *= 0.8;
                            }
                        }
                    }
                }

                if (przebicie) {
                    wartoscDefa *= 0.5;
                }

                final double redukcja = DoubleUtils.round( wartoscDefa / (wartoscDefa + 40), 2);

                double finalDmg = DoubleUtils.round((1 - redukcja) * attackerDmg, 2);
                if (finalDmg < 0) {
                    finalDmg = 0;
                }
                e.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
                e.setDamage(EntityDamageEvent.DamageModifier.RESISTANCE, 0);
                e.setDamage(EntityDamageEvent.DamageModifier.BASE, finalDmg);
                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket("&c&l", e.getFinalDamage(), victim.getLocation(), attacker));
                rpgcore.getCooldownManager().givePvpCooldown(attacker.getUniqueId());

            } else {
                // ... Victim jest Mobem

                if (e.getEntity() instanceof ItemFrame) {
                    e.setCancelled(true);
                    return;
                }

                final LivingEntity victim = (LivingEntity) e.getEntity();
                final double attackerDmg = rpgcore.getDamageManager().calculateAttackerDmgToEntity(attacker, victim);
                e.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
                e.setDamage(EntityDamageEvent.DamageModifier.BASE, attackerDmg);
                /*attacker.sendMessage("Base - " + e.getDamage(EntityDamageEvent.DamageModifier.BASE));
                attacker.sendMessage("Armor - " + e.getDamage(EntityDamageEvent.DamageModifier.ARMOR));
                attacker.sendMessage("Resistance - " + e.getDamage(EntityDamageEvent.DamageModifier.RESISTANCE));
                attacker.sendMessage("Dmg event - " + e.getDamage());
                attacker.sendMessage("Dmg final - " + e.getFinalDamage());*/
                if (victim.getCustomName() != null && victim.getCustomName().contains("Ksiaze Mroku")) {
                    if (((Monster) victim).getTarget() != attacker) {
                        ((Monster) victim).setTarget(attacker);
                    }
                }

                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamagePacket("&c&l", e.getFinalDamage(), victim.getLocation(), attacker));
                rpgcore.getCooldownManager().givePvpCooldown(attacker.getUniqueId());
            }
            if (e.getDamage() < ((LivingEntity) e.getEntity()).getHealth()) {
                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getDamageManager().sendDamageActionBarPacket(attacker, e.getFinalDamage(), (LivingEntity) e.getEntity()));
            }
        } else if (e.getDamager() instanceof Creature || e.getDamager() instanceof Monster) {
            // Victim jest Graczem
            if (e.getEntity() instanceof Player) {
                final Player victim = (Player) e.getEntity();
                if (rpgcore.getGodManager().containsPlayer(victim.getUniqueId())) {
                    e.setCancelled(true);
                    return;
                }
                double finalDmg = rpgcore.getDamageManager().calculateEntityDamageToPlayer(e.getDamager(), victim);

                if (finalDmg < 0) {
                    finalDmg = 0;
                }
                e.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
                e.setDamage(EntityDamageEvent.DamageModifier.BASE, finalDmg);
            }
        }
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
