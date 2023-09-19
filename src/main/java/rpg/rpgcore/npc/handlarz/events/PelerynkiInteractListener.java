package rpg.rpgcore.npc.handlarz.events;

import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PelerynkiInteractListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();

        if (eventItem == null || eventItem.getType() != Material.LEATHER || !eventItem.hasItemMeta() || !eventItem.getItemMeta().hasDisplayName() || !Utils.removeColor(eventItem.getItemMeta().getDisplayName()).contains("Przekleta Smocza Skora")) {
            return;
        }
        e.setUseItemInHand(Event.Result.DENY);
        e.setCancelled(true);
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            this.switchType(player, eventItem);
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            this.useItem(eventItem, player);
        }
    }


    private void switchType(final Player player, final ItemStack itemStack) {
        final String type = Utils.getTagString(itemStack, "type");
        String subtitle;
        if (type.equals("exp")) {
            Utils.setTagString(itemStack, "type", "afk");
            final ItemMeta meta = itemStack.getItemMeta();
            final List<String> lore = Arrays.asList(
                    "&7Tryb: &cAFK",
                    "&7Cooldown: &c0.1 sekundy",
                    "&7Zasieg Dzialania: &c" + Utils.getTagInt(itemStack, "range") + " blokow",
                    "",
                    "&6Umiejetnosc: Zmiana Trybu &e&lLMB",
                    "&7Zmienia tryb na &aExpienie &7lub &cAFK",
                    "",
                    "&6Umiejetnosc: Uzycie przedmiotu &e&lRMB",
                    "&7Sprawia, ze potwory w zasiegu jej dzialania",
                    "&7obiora Cie za swoj cel i beda Cie atakowac"
            );
            meta.setLore(Utils.format(lore));
            itemStack.setItemMeta(meta);
            subtitle = "&c&lAFK";
        } else {
            Utils.setTagString(itemStack, "type", "exp");
            final ItemMeta meta = itemStack.getItemMeta();
            final List<String> lore = Arrays.asList(
                    "&7Tryb: &aExpienie",
                    (itemStack.getItemMeta().getDisplayName().contains("+") ? "&7Cooldown: &c45 sekund" : "&7Cooldown: &c1 minuta"),
                    "&7Zasieg Dzialania: &c" + Utils.getTagInt(itemStack, "range") + " blokow",
                    "",
                    "&6Umiejetnosc: Zmiana Trybu &e&lLMB",
                    "&7Zmienia tryb na &aExpienie &7lub &cAFK",
                    "",
                    "&6Umiejetnosc: Uzycie przedmiotu &e&lRMB",
                    "&7Teleportuje wszystkie moby w zasiegu jej",
                    "&7dzialania do twojej lokalizacji"
            );
            meta.setLore(Utils.format(lore));
            itemStack.setItemMeta(meta);
            subtitle = "&a&lExpienie";
        }
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(player, "&8&l[&4&lArtefakty&8&l]", subtitle, 5, 10));
    }

    private void useItem(final ItemStack item, final Player player) {
        final String type = Utils.getTagString(item, "type");
        final UUID uuid = player.getUniqueId();
        if (type.equals("exp")) {
            if (item.getItemMeta().getDisplayName().contains("+")) {
                if (RPGCORE.getInstance().getCooldownManager().hasPelerynkaPCooldownExp(uuid)) {
                    player.sendMessage(Utils.format("&4&lArtefakty &8>> &cMusisz poczekac &4" + RPGCORE.getInstance().getCooldownManager().getPelerynkaPCooldownExp(uuid)));
                    return;
                }
                if (!player.getWorld().getName().contains("map")) {
                    player.sendMessage(Utils.format("&4&lArtefakty &8>> &cNie mozesz tego tutaj uzyc!"));
                    RPGCORE.getInstance().getCooldownManager().givePelerynkaPCooldownExp(uuid);
                    return;
                }
                if (RPGCORE.getInstance().getHandlarzNPC().hasLocation(player.getUniqueId())) {
                    if (player.getLocation().getWorld().equals(RPGCORE.getInstance().getHandlarzNPC().getLocation(player.getUniqueId()).getWorld())) {
                        if (player.getLocation().distance(RPGCORE.getInstance().getHandlarzNPC().getLocation(player.getUniqueId())) <= 0.5) {
                            player.sendMessage(Utils.format("&4&lArtefakty &8>> &cNie mozesz tego uzywac do stania AFK!"));
                            RPGCORE.getInstance().getCooldownManager().givePelerynkaPCooldownExp(uuid);
                            return;
                        }
                    }
                }
            } else {
                if (RPGCORE.getInstance().getCooldownManager().hasPelerynkaCooldownExp(uuid)) {
                    player.sendMessage(Utils.format("&4&lArtefakty &8>> &cMusisz poczekac &4" + RPGCORE.getInstance().getCooldownManager().getPelerynkaCooldownExp(uuid)));
                    return;
                }
                if (!player.getWorld().getName().contains("map")) {
                    player.sendMessage(Utils.format("&4&lArtefakty &8>> &cNie mozesz tego tutaj uzyc!"));
                    RPGCORE.getInstance().getCooldownManager().givePelerynkaCooldownExp(uuid);
                    return;
                }

                if (RPGCORE.getInstance().getHandlarzNPC().hasLocation(player.getUniqueId())) {
                    if (player.getLocation().getWorld().equals(RPGCORE.getInstance().getHandlarzNPC().getLocation(player.getUniqueId()).getWorld())) {
                        if (player.getLocation().distance(RPGCORE.getInstance().getHandlarzNPC().getLocation(player.getUniqueId())) <= 0.5) {
                            player.sendMessage(Utils.format("&4&lArtefakty &8>> &cNie mozesz tego uzywac do stania AFK!"));
                            RPGCORE.getInstance().getCooldownManager().givePelerynkaCooldownExp(uuid);
                            return;
                        }
                    }
                }
            }

            final int range = Utils.getTagInt(item, "range");
            for (Entity entity : player.getNearbyEntities(range, range, range)) {
                if (entity instanceof Creature) {
                    if (entity.getCustomName().contains("Mistyczny Kowal")) continue;
                    final Creature creature = (Creature) entity;
                    if (Utils.removeColor(entity.getCustomName()).contains("[BOSS]") || Utils.removeColor(entity.getCustomName()).contains("[MINIBOSS]")) continue;
                    creature.teleport(player);
                }
            }
            if (item.getItemMeta().getDisplayName().contains("+"))
                RPGCORE.getInstance().getCooldownManager().givePelerynkaPCooldownExp(uuid);
            else RPGCORE.getInstance().getCooldownManager().givePelerynkaCooldownExp(uuid);
            player.sendMessage(Utils.format("&4&lArtefakty &8>> &aPomyslnie uzyto " + item.getItemMeta().getDisplayName() + "&a!"));
            RPGCORE.getInstance().getHandlarzNPC().addLocation(player.getUniqueId(), player.getLocation());
        } else {
            final int range = Utils.getTagInt(item, "range");

            if (RPGCORE.getInstance().getCooldownManager().hasPelerynkaCooldownAfk(uuid)) return;
            for (Entity entity : player.getNearbyEntities(range, range, range)) {
                if (entity instanceof Creature) {
                    if (entity.getCustomName().contains("Mistyczny Kowal")) continue;
                    if (Utils.removeColor(entity.getCustomName()).contains("[BOSS]") || Utils.removeColor(entity.getCustomName()).contains("[MINIBOSS]")) return;
                    final Creature creature = (Creature) entity;
                    creature.setTarget(player);
                }
            }
        }
        RPGCORE.getInstance().getCooldownManager().givePelerynkaCooldownAfk(uuid);
    }

}
