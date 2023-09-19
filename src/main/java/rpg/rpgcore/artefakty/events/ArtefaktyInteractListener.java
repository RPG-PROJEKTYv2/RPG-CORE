package rpg.rpgcore.artefakty.events;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class ArtefaktyInteractListener implements Listener {
    private final RPGCORE rpgcore;

    public ArtefaktyInteractListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (eventItem == null) {
            return;
        }

        if (eventItem.getType() == Material.COMPASS && eventItem.getItemMeta().hasDisplayName() && Utils.removeColor(eventItem.getItemMeta().getDisplayName()).equals("Bifrost")) {
            e.setCancelled(true);
            if (Utils.getTagString(eventItem, "owner_name").equals(player.getName()) && Utils.getTagString(eventItem, "owner_uuid").equals(uuid.toString())) {
                if (eventItem.getItemMeta().getLore().stream().anyMatch(s -> s.contains("1 min"))) {
                    if (rpgcore.getCooldownManager().hasBiFrost2Cooldown(uuid)) {
                        player.sendMessage(Utils.format("&4&lArtefakty &8>> &cMusisz poczekac &4" + rpgcore.getCooldownManager().getBiFrost2Cooldown(uuid)));
                        return;
                    }
                    rpgcore.getTeleporterNPC().openTeleporterEXPOWISKA(player);
                    rpgcore.getCooldownManager().givePlayerBiFrost2Cooldown(uuid);
                    player.sendMessage(Utils.format("&4&lArtefakty &8>> &aPomyslnie uzyto &b&lBifrostu"));
                    return;
                }
                if (rpgcore.getCooldownManager().hasBiFrostCooldown(uuid)) {
                    player.sendMessage(Utils.format("&4&lArtefakty &8>> &cMusisz poczekac &4" + rpgcore.getCooldownManager().getBiFrostCooldown(uuid)));
                    return;
                }
                rpgcore.getTeleporterNPC().openTeleporterEXPOWISKA(player);
                rpgcore.getCooldownManager().givePlayerBiFrostCooldown(uuid);
                player.sendMessage(Utils.format("&4&lArtefakty &8>> &aPomyslnie uzyto &b&lBifrostu"));
                return;
            } else {
                player.sendMessage(Utils.format("&4&lArtefakty &8>> &cTen artefakt nie nalezy do ciebie!"));
                return;
            }
        }
        if (eventItem.getType() == Material.INK_SACK && eventItem.getDurability() == 1 &&
                eventItem.getItemMeta().hasDisplayName() && Utils.removeColor(eventItem.getItemMeta().getDisplayName()).equals("Krwisty Legendarny Rog")) {
            e.setCancelled(true);

            if (Utils.getTagString(eventItem, "owner_name").equals(player.getName()) && Utils.getTagString(eventItem, "owner_uuid").equals(uuid.toString())) {
                if (rpgcore.getCooldownManager().hasRogCooldown(uuid)) {
                    player.sendMessage(Utils.format("&4&lArtefakty &8>> &cMusisz poczekac &4" + rpgcore.getCooldownManager().getRogCooldown(uuid)));
                    return;
                }
                if (!player.getWorld().getName().contains("map")) {
                    player.sendMessage(Utils.format("&4&lArtefakty &8>> &cNie mozesz tego tutaj uzyc!"));
                    rpgcore.getCooldownManager().givePlayerRogCooldown(uuid);
                    return;
                }
                final String map = player.getWorld().getName().replace("map", "");
                for (int i = 0; i < ChanceHelper.getRandInt(15, 30); i++) {
                    final double x = ChanceHelper.getRandDouble(-0.5, 0.5);
                    final double z = ChanceHelper.getRandDouble(-0.5, 0.5);
                    RPGCORE.getMythicMobs().getMobManager().spawnMob(map + "-MOB3", player.getLocation().clone().add(x, 5, z));
                }
                rpgcore.getCooldownManager().givePlayerRogCooldown(uuid);
                player.sendMessage(Utils.format("&4&lArtefakty &8>> &aPomyslnie uzyto &4&lKrwistego Legendarnego Rogu!"));
                return;
            } else {
                player.sendMessage(Utils.format("&4&lArtefakty &8>> &cTen artefakt nie nalezy do ciebie!"));
                return;
            }
        }
        if (eventItem.getType() == Material.WATCH && eventItem.getItemMeta().hasDisplayName() && Utils.removeColor(eventItem.getItemMeta().getDisplayName()).equals("Serce Yothuna")) {
            e.setCancelled(true);
            if (Utils.getTagString(eventItem, "owner_name").equals(player.getName()) && Utils.getTagString(eventItem, "owner_uuid").equals(uuid.toString())) {
                if (rpgcore.getCooldownManager().hasSerceCooldown(uuid)) {
                    player.sendMessage(Utils.format("&4&lArtefakty &8>> &cMusisz poczekac &4" + rpgcore.getCooldownManager().getSerceCooldown(uuid)));
                    return;
                }
                if (!player.getLocation().getWorld().getName().equals("50-60map") && !player.getLocation().getWorld().getName().equals("60-70map") &&
                        !player.getLocation().getWorld().getName().equals("70-80map") && !player.getLocation().getWorld().getName().equals("80-90map") &&
                        !player.getLocation().getWorld().getName().equals("90-100map") && !player.getLocation().getWorld().getName().equals("100-110map") &&
                        !player.getLocation().getWorld().getName().equals("110-120map") && !player.getLocation().getWorld().getName().equals("120-130map")) {
                    player.sendMessage(Utils.format("&4&lArtefakty &8>> &cNie mozesz uzywac tego artefaktu na mapach bez pvp!"));
                    rpgcore.getCooldownManager().givePlayerSerceCooldown(uuid);
                    return;
                }
                for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                    if (entity instanceof Player) {
                        ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 0));
                        ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
                        ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 3));
                        entity.sendMessage(Utils.format("&4&lArtefakty &8>> &cGracz &4" + player.getName() + " &cnalozyl na Ciebie efekt artefaktu &4Serca Yothuna&c!"));
                    }
                }
                rpgcore.getCooldownManager().givePlayerSerceCooldown(uuid);
                player.sendMessage(Utils.format("&4&lArtefakty &8>> &aPomyslnie uzyto &c&lSerca Yothuna!"));
                return;
            } else {
                player.sendMessage(Utils.format("&4&lArtefakty &8>> &cTen artefakt nie nalezy do ciebie!"));
                return;
            }
        }
        if (eventItem.getType() == Material.LAVA_BUCKET && eventItem.getItemMeta().hasDisplayName() && Utils.removeColor(eventItem.getItemMeta().getDisplayName()).equals("Eliksir Potegi")) {
            e.setCancelled(true);
            if (Utils.getTagString(eventItem, "owner_name").equals(player.getName()) && Utils.getTagString(eventItem, "owner_uuid").equals(uuid.toString())) {
                if (rpgcore.getCooldownManager().hasEliksirPotegiCooldown(uuid)) {
                    player.sendMessage(Utils.format("&4&lArtefakty &8>> &cMusisz poczekac &4" + rpgcore.getCooldownManager().getEliksirPotegiCooldown(uuid)));
                    return;
                }
                if (rpgcore.getKociolkiManager().find(uuid).isEliksirPotegi()) {
                    rpgcore.getBonusesManager().find(uuid).getBonusesUser().setSrednieobrazenia(rpgcore.getBonusesManager().find(uuid).getBonusesUser().getSrednieobrazenia() - DoubleUtils.round(10, 2));
                }
                rpgcore.getKociolkiManager().find(uuid).setEliksirPotegi(true);
                rpgcore.getKociolkiManager().find(uuid).setEliksirPotegiTime(300);
                rpgcore.getBonusesManager().find(uuid).getBonusesUser().setSrednieobrazenia(rpgcore.getBonusesManager().find(uuid).getBonusesUser().getSrednieobrazenia() + DoubleUtils.round(10, 2));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataBonuses(uuid, rpgcore.getBonusesManager().find(uuid));
                    rpgcore.getMongoManager().saveDataKociolki(uuid, rpgcore.getKociolkiManager().find(uuid));
                });
                rpgcore.getCooldownManager().givePlayerEliksirPotegiCooldown(uuid);
                player.sendMessage(Utils.format("&4&lArtefakty &8>> &aPomyslnie nalozono efekt &6&lEliksiru Potegi &a!"));
                return;
            } else {
                player.sendMessage(Utils.format("&4&lArtefakty &8>> &cTen artefakt nie nalezy do ciebie!"));
                return;
            }
        }
        if (eventItem.getType() == Material.WATER_BUCKET && eventItem.getItemMeta().hasDisplayName() && Utils.removeColor(eventItem.getItemMeta().getDisplayName()).equals("Eliksir Obroncy")) {
            e.setCancelled(true);
            if (Utils.getTagString(eventItem, "owner_name").equals(player.getName()) && Utils.getTagString(eventItem, "owner_uuid").equals(uuid.toString())) {
                if (rpgcore.getCooldownManager().hasEliksirObronncyCooldown(uuid)) {
                    player.sendMessage(Utils.format("&4&lArtefakty &8>> &cMusisz poczekac &4" + rpgcore.getCooldownManager().getEliksirObronncyCooldown(uuid)));
                    return;
                }
                if (rpgcore.getKociolkiManager().find(uuid).isEliksirObroncy()) {
                    rpgcore.getBonusesManager().find(uuid).getBonusesUser().setSredniadefensywa(rpgcore.getBonusesManager().find(uuid).getBonusesUser().getSredniadefensywa() - DoubleUtils.round(10, 2));
                }
                rpgcore.getKociolkiManager().find(uuid).setEliksirObroncy(true);
                rpgcore.getKociolkiManager().find(uuid).setEliksirObroncyTime(300);
                rpgcore.getBonusesManager().find(uuid).getBonusesUser().setSredniadefensywa(rpgcore.getBonusesManager().find(uuid).getBonusesUser().getSredniadefensywa() + DoubleUtils.round(10, 2));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataBonuses(uuid, rpgcore.getBonusesManager().find(uuid));
                    rpgcore.getMongoManager().saveDataKociolki(uuid, rpgcore.getKociolkiManager().find(uuid));
                });
                rpgcore.getCooldownManager().givePlayerEliksirObronncyCooldown(uuid);
                player.sendMessage(Utils.format("&4&lArtefakty &8>> &aPomyslnie nalozono efekt &6&lEliksiru Obroncy &a!"));
            } else {
                player.sendMessage(Utils.format("&4&lArtefakty &8>> &cTen artefakt nie nalezy do ciebie!"));
            }
        }
    }
}
