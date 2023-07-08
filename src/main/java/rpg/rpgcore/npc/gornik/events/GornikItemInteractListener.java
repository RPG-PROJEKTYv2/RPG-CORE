package rpg.rpgcore.npc.gornik.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.objects.GornikUser;
import rpg.rpgcore.utils.Utils;

import java.util.stream.Collectors;

public class GornikItemInteractListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (!e.getItem().getType().toString().contains("_PICKAXE")) return;
        if (!e.getItem().hasItemMeta() || !e.getItem().getItemMeta().hasDisplayName() || !e.getItem().getItemMeta().getDisplayName().contains("Kilof Gornika")) return;
        if (Utils.getTagInt(e.getItem(), "lvl") < 30) return;
        final Player player = e.getPlayer();
        if (!Utils.getTagString(e.getItem(), "owner").equals(player.getName())) {
            player.sendMessage(Utils.format("&6&lGornik &8>> &cTen kilof nie nalezy do Ciebie!"));
            return;
        }
        if (!Utils.getTagString(e.getItem(), "owner-uuid").equals(player.getUniqueId().toString())) {
            player.sendMessage(Utils.format("&6&lGornik &8>> &cTen kilof nie nalezy do Ciebie!"));
            return;
        }
        if (!e.getPlayer().getWorld().getName().equals("Kopalnia")) {
            player.sendMessage(Utils.format("&6&lGornik &8>> &cNie mozesz tego tutaj uzyc!"));

            return;
        }
        if (RPGCORE.getInstance().getCooldownManager().hasPlayerPickaxeAbilityCooldown(player.getUniqueId())) {
            player.sendMessage(Utils.format("&6&lGornik &8>> &cMusisz odczekac jeszcze &6" + RPGCORE.getInstance().getCooldownManager().getPlayerPickaxeAbilityCooldown(player.getUniqueId()) + "&c!"));
            return;
        }
        e.setCancelled(true);
        e.setUseItemInHand(Event.Result.DENY);
        e.setUseInteractedBlock(Event.Result.DENY);
        final GornikUser user = RPGCORE.getInstance().getGornikNPC().find(e.getPlayer().getUniqueId());
        final PotionEffect effect = new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 1);

        player.addPotionEffect(effect);
        player.sendMessage(Utils.format("&6&lGornik &8>> &7Uzywasz mocy swojego &6Kilofa&7!"));
        for (final Player p : player.getNearbyEntities(10, 10, 10).stream().filter(entity -> entity instanceof Player).map(entity -> (Player) entity).collect(Collectors.toList())) {
            p.addPotionEffect(effect);
            p.sendMessage(Utils.format("&6&lGornik &8>> &6" + player.getName() + " &7uzyl mocy swojego &6Kilofa &7i zagwarantowal Ci efekt &eHaste II &7na &e60 &7sekund!"));
        }

        user.activatePickaxeAbility();
        RPGCORE.getInstance().getCooldownManager().givePlayerPickaxeAbilityCooldown(player.getUniqueId());
     }
}
