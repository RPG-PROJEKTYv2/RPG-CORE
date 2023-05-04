package rpg.rpgcore.bossy.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bossy.objects.BossyUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;

public class KrysztalowyWladca110_120Listener implements Listener {
    private final BossyUser bossyUser;
    private final RPGCORE rpgcore;

    public KrysztalowyWladca110_120Listener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.bossyUser = rpgcore.getBossyManager().getBossyUser();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) return;
        if (e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR) return;

        final Player player = e.getPlayer();
        final Location loc = e.getClickedBlock().getLocation();
        if (player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.MAGMA_CREAM)) {
            final User user = rpgcore.getUserManager().find(player.getUniqueId());
            if (user.getRankUser().isHighStaff()) {
                if (this.bossyUser.isRdzen110_120Location(e.getClickedBlock().getLocation())) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTa lokalizacja jest już zapisana!"));
                    return;
                }
                this.bossyUser.addRdzen110_120Location(loc);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie dodano blok do lokalizacji Rdzenia 110-120"));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aW: &6" + loc.getWorld().getName() + " &aX: &6" + loc.getBlockX() + " &aY: &6" + loc.getBlockY() + " &aZ: &6" + loc.getBlockZ()));
                this.bossyUser.save();
                return;
            }
            return;
        }

        if (player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.FIREBALL)) {
            final User user = rpgcore.getUserManager().find(player.getUniqueId());
            if (user.getRankUser().isHighStaff()) {
                if (!this.bossyUser.isRdzen110_120Location(e.getClickedBlock().getLocation())) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTa lokalizacja nie jest zapisana!"));
                    return;
                }
                this.bossyUser.removeRdzen110_120Location(loc);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPomyslnie usunieto blok z lokalizacji Rdzenia 110-120"));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cW: &6" + loc.getWorld().getName() + " &cX: &6" + loc.getBlockX() + " &cY: &6" + loc.getBlockY() + " &cZ: &6" + loc.getBlockZ()));
                this.bossyUser.save();
                return;
            }
            return;
        }

        if (!this.bossyUser.isRdzen110_120Location(loc)) return;
        if (!player.getInventory().containsAtLeast(Bossy.I4.getItemStack(), 1)) return;
        if (rpgcore.getCooldownManager().hasOdlamkiCooldown(player.getUniqueId())) return;
        e.setCancelled(true);
        e.setUseItemInHand(Event.Result.DENY);
        player.getInventory().removeItem(new ItemBuilder(Bossy.I4.getItemStack().clone()).setAmount(1).toItemStack());
        this.bossyUser.incrementOdlamki110_120();
        player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &bPomyslnie zaladowano Fragment Krysztalu! &3(" + this.bossyUser.getOdlamki110_120() + "/250)"));
        rpgcore.getCooldownManager().givePlayerOdlamkiCooldown(player.getUniqueId());
        if (this.bossyUser.getOdlamki110_120() == 125) {
            Bukkit.broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &bLod zaczyna sie powoli topic..."));
            this.bossyUser.save();
            return;
        }
        if (this.bossyUser.getOdlamki110_120() == 250) {
            Bukkit.broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &1&lKrysztalowy Wladca &bobudzil sie ze snu i jest gotowy do walki!"));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn 110-120-BOSS 1 110-120map,-0.5,77,-155.5");
            this.bossyUser.resetOdlamki110_120();
            this.bossyUser.save();
        }
    }
}
