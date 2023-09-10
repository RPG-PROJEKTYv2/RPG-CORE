package rpg.rpgcore.klasy.events;

import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.klasy.enums.KlasyMain;
import rpg.rpgcore.klasy.enums.KlasySide;
import rpg.rpgcore.klasy.objects.Klasa;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KlasyInteractListener implements Listener {
    private final RPGCORE rpgcore;

    private final List<PotionEffect> effects = Arrays.asList(
            new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1, 1),
            new PotionEffect(PotionEffectType.POISON, 100, 3),
            new PotionEffect(PotionEffectType.BLINDNESS, 100, 3),
            new PotionEffect(PotionEffectType.SLOW, 100, 3),
            new PotionEffect(PotionEffectType.SLOW_DIGGING, 1, 1),
            new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1, 1)
    );

    private final List<String> allowedPvP = Arrays.asList(
            "50-60map",
            "DemonTower",
            "60-70map",
            "70-80map",
            "80-90map",
            "90-100map",
            "100-110map",
            "110-120map",
            "120-130map",
            "Dungeon60-70",
            "Dungeon70-80",
            "Dungeon80-90",
            "Dungeon90-100",
            "Dungeon100-110",
            "Dungeon110-120",
            "Dungeon120-130");

    public KlasyInteractListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        final ItemStack item = e.getItem();
        if (item.getType() != Material.DOUBLE_PLANT) return;
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || item.getItemMeta().getDisplayName().contains("Zywica") || item.getItemMeta().getDisplayName().contains("Czek"))
            return;
        final Klasa klasa = rpgcore.getKlasyManager().find(e.getPlayer().getUniqueId());
        if (klasa.getMainKlasa() == KlasyMain.NIE_WYBRANO) {
            e.getPlayer().sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Musisz wybrac klase!"));
            return;
        }
        if (klasa.getPodKlasa() == KlasySide.NIE_WYBRANO) {
            e.getPlayer().sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Musisz wybrac podklase!"));
            return;
        }
        if (!Utils.removeColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(klasa.getMainKlasa().getName() + " - " + klasa.getPodKlasa().getName()))
            return;
        e.setCancelled(true);
        e.setUseItemInHand(Event.Result.DENY);
        e.setUseInteractedBlock(Event.Result.DENY);
        final Player player = e.getPlayer();
        switch (klasa.getMainKlasa()) {
            case WOJOWNIK:
                switch (klasa.getPodKlasa()) {
                    case BERSERK:
                        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                            if (klasa.hasLeftClickCooldown()) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Musisz odczekac jeszcze &c" + Utils.durationToString(klasa.getCdLMB(), false) + "&7!"));
                                return;
                            }
                            klasa.setCdLMB(System.currentTimeMillis() + 180_000L);
                            rpgcore.getKlasyManager().getBerserkLMB().asMap().put(player.getUniqueId(), System.currentTimeMillis() + 10_000L);
                            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie uzyles/-as &cSzalu Bojowego&a!"));
                            break;
                        }
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (klasa.hasRightClickCooldown()) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Musisz odczekac jeszcze &c" + Utils.durationToString(klasa.getCdRMB(), false) + "&7!"));
                                return;
                            }
                            klasa.setCdRMB(System.currentTimeMillis() + 300_000L);
                            rpgcore.getKlasyManager().getBerserkRMB().add(player.getUniqueId());
                            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie uzyles/-as &4Brutala&a!"));
                            break;
                        }
                        break;
                    case PALADYN:
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (klasa.hasRightClickCooldown()) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Musisz odczekac jeszcze &c" + Utils.durationToString(klasa.getCdRMB(), false) + "&7!"));
                                return;
                            }
                            klasa.setCdRMB(System.currentTimeMillis() + 300_000L);
                            final double addHealth = player.getMaxHealth() * 0.35;
                            player.setHealth(Math.min(player.getHealth() + addHealth, player.getMaxHealth()));
                            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie uzyles/-as &a&lOwocu Zycia&a!"));
                            break;
                        }
                        break;
                    default:
                        return;
                }
                break;
            case ZABOJCA:
                switch (klasa.getPodKlasa()) {
                    case NINJA:
                        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                            if (klasa.hasLeftClickCooldown()) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Musisz odczekac jeszcze &c" + Utils.durationToString(klasa.getCdLMB(), false) + "&7!"));
                                return;
                            }
                            klasa.setCdLMB(System.currentTimeMillis() + 180_000L);
                            if (!allowedPvP.contains(player.getLocation().getWorld().getName())) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &cNie mozesz uzyc tej umiejetnosci w tym miejscu!"));
                                return;
                            }
                            final PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 100, 3, true, true);
                            final PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 100, 5, true, true);
                            for (final Player target : player.getNearbyEntities(5, 5, 5).stream().filter(entity -> entity instanceof Player).map(entity -> (Player) entity).collect(Collectors.toList())) {
                                target.addPotionEffect(blind);
                                target.addPotionEffect(slow);
                                target.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &cZostales trafiony efektem &7&lZaslony Dymnej &cgracza &6" + player.getName() + "&c!"));
                            }
                            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie uzyles/-as &7&lZaslony Dymnej&a!"));
                            return;
                        }
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (klasa.hasRightClickCooldown()) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Musisz odczekac jeszcze &c" + Utils.durationToString(klasa.getCdRMB(), false) + "&7!"));
                                return;
                            }
                            klasa.setCdRMB(System.currentTimeMillis() + 180_000L);
                            rpgcore.getKlasyManager().getNinjaRMB().asMap().put(player.getUniqueId(), System.currentTimeMillis() + 10_000L);
                            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie uzyles/-as &fJe-stem Szybki&a!"));
                            break;
                        }
                        break;
                    case SKRYTOBOJCA:
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (klasa.hasRightClickCooldown()) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Musisz odczekac jeszcze &c" + Utils.durationToString(klasa.getCdRMB(), false) + "&7!"));
                                return;
                            }
                            klasa.setCdRMB(System.currentTimeMillis() + 120_000L);
                            if (!allowedPvP.contains(player.getLocation().getWorld().getName())) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &cNie mozesz uzyc tej umiejetnosci w tym miejscu!"));
                                return;
                            }
                            final List<Double> targetHealth = new ArrayList<>();
                            for (Player target : player.getNearbyEntities(5, 5, 5).stream().filter(entity -> entity instanceof Player).map(entity -> (Player) entity).collect(Collectors.toList())) {
                                target.setVelocity(new Vector(0, 0.2, 0));
                                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 2));
                                targetHealth.add(target.getHealth() * 0.1);
                            }
                            double toAdd = 0;
                            if (!targetHealth.isEmpty()) for (int i = 0; i < 3; i++)
                                toAdd += targetHealth.get(ChanceHelper.getRandInt(0, targetHealth.size() - 1));

                            player.setHealth(Math.min(player.getHealth() + toAdd, player.getMaxHealth()));
                            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie uzyles/-as &eBurza Ostrzy&a!"));
                            break;
                        }
                        break;
                }
                break;
            case CZARODZIEJ:
                switch (klasa.getPodKlasa()) {
                    case NEKROMANTA:
                        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &cTa umiejetnosc jest jeszcze niedostepna"));
                            return;
                        }
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (klasa.hasRightClickCooldown()) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Musisz odczekac jeszcze &c" + Utils.durationToString(klasa.getCdRMB(), false) + "&7!"));
                                return;
                            }
                            klasa.setCdRMB(System.currentTimeMillis() + 300_000L);
                            if (!allowedPvP.contains(player.getLocation().getWorld().getName())) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &cNie mozesz uzyc tej umiejetnosci w tym miejscu!"));
                                return;
                            }
                            final Fireball fireball = player.launchProjectile(Fireball.class);
                            fireball.setVelocity(player.getLocation().getDirection().multiply(2));
                            fireball.setIsIncendiary(false);
                            fireball.setYield(0);
                            fireball.setBounce(false);
                            fireball.setShooter(player);
                            fireball.setMetadata("fireball", new FixedMetadataValue(rpgcore, true));
                            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie uzyles/-as &cOddechu Smoka&a!"));
                            break;
                        }
                        break;
                    case MAG_ZYWIOLOW:
                        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                            if (klasa.hasLeftClickCooldown()) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Musisz odczekac jeszcze &c" + Utils.durationToString(klasa.getCdLMB(), false) + "&7!"));
                                return;
                            }
                            klasa.setCdLMB(System.currentTimeMillis() + 300_000L);
                            if (!allowedPvP.contains(player.getLocation().getWorld().getName())) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &cNie mozesz uzyc tej umiejetnosci w tym miejscu!"));
                                return;
                            }
                            for (Player target : player.getNearbyEntities(10, 10, 10).stream().filter(entity -> entity instanceof Player).map(entity -> (Player) entity).collect(Collectors.toList())) {
                                final PotionEffect effect = this.effects.get(ChanceHelper.getRandInt(0, this.effects.size() - 1));
                                if (effect.getType() == PotionEffectType.FIRE_RESISTANCE) {
                                    target.setFireTicks(100);
                                    target.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Zostales &epodpalony &7przez &6" + player.getName() + "&7!"));
                                    continue;
                                }
                                if (effect.getType() == PotionEffectType.SLOW_DIGGING) {
                                    target.setWalkSpeed(0);
                                    rpgcore.getServer().getScheduler().runTaskLaterAsynchronously(rpgcore, () -> target.setWalkSpeed(0.2F), 100L);
                                    target.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Zostales &bzamrozony &7przez &6" + player.getName() + "&7!"));
                                    continue;
                                }
                                if (effect.getType() == PotionEffectType.INCREASE_DAMAGE) {
                                    final double health = target.getHealth() * 0.1;
                                    target.setHealth(target.getHealth() - health);
                                    player.setHealth(Math.min(player.getHealth() + health, player.getMaxHealth()));
                                    target.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &6" + player.getName() + " &7ukradl Ci &c" + DoubleUtils.round(health / 2, 0) + "❤&7!"));
                                    continue;
                                }
                                target.addPotionEffect(effect);
                                target.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &6" + player.getName() + " &7uzywa na tobie &5Puszki Pandory&7!"));
                            }
                            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie uzyles/-as &5Puszki Pandory&a!"));
                            return;
                        }
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (klasa.hasRightClickCooldown()) {
                                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &7Musisz odczekac jeszcze &c" + Utils.durationToString(klasa.getCdRMB(), false) + "&7!"));
                                return;
                            }
                            klasa.setCdRMB(System.currentTimeMillis() + 120_000L);
                            rpgcore.getKlasyManager().getMagRMB().add(player.getUniqueId());
                            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie uzyles/-as &a&lTrzesienia Ziemi&a!"));
                            break;
                        }
                        break;
                }
                break;
            default:
                break;
        }
    }
}
