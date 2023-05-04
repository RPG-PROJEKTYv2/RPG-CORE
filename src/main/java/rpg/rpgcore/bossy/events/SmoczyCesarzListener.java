package rpg.rpgcore.bossy.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bossy.BossyManager;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;

public class SmoczyCesarzListener implements Listener {
    private final BossyManager bossyManager = RPGCORE.getInstance().getBossyManager();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) return;
        if (e.getClickedBlock() == null) return;
        final Location loc = e.getClickedBlock().getLocation();
        if (!loc.getWorld().getName().equals("120-130map")) return;
        final Player player = e.getPlayer();
        if (!e.getClickedBlock().getType().equals(Material.BARRIER)) return;
        if (RPGCORE.getInstance().getCooldownManager().hasKlejnoty120_130Cooldown(player.getUniqueId())) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cMusisz jeszcze chwile odczekac..."));
            return;
        }
        if (new ItemBuilder(player.getItemInHand().clone()).setAmount(1).toItemStack().equals(Bossy.I5_1.getItemStack())) {
            if (bossyManager.isKlejnot120_130_1(loc)) {
                if (bossyManager.getKlejnoty120_130()[0]) {
                    player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cTo miejsce jest juz zajete!"));
                    return;
                }
                player.getInventory().removeItem(new ItemBuilder(Bossy.I5_1.getItemStack().clone()).setAmount(1).toItemStack());
                bossyManager.spawnKlejnot(player, 1);
                RPGCORE.getInstance().getCooldownManager().givePlayerKlejnoty120_130Cooldown(player.getUniqueId());
            } else if (bossyManager.isKlejnot120_130_2(loc)) {
                if (bossyManager.getKlejnoty120_130()[1]) {
                    player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cTo miejsce jest juz zajete!"));
                    return;
                }
                player.getInventory().removeItem(new ItemBuilder(Bossy.I5_1.getItemStack().clone()).setAmount(1).toItemStack());
                bossyManager.spawnKlejnot(player, 2);
                RPGCORE.getInstance().getCooldownManager().givePlayerKlejnoty120_130Cooldown(player.getUniqueId());
            }
        } else {
            if (bossyManager.isKlejnot120_130_1(loc)) {
                if (!bossyManager.getKlejnoty120_130()[0]) return;
                player.getInventory().addItem(new ItemBuilder(Bossy.I5_1.getItemStack().clone()).setAmount(1).toItemStack());
                bossyManager.despawnKlejnot(player, 1);
                RPGCORE.getInstance().getCooldownManager().givePlayerKlejnoty120_130Cooldown(player.getUniqueId());
            } else if (bossyManager.isKlejnot120_130_2(loc)) {
                if (!bossyManager.getKlejnoty120_130()[1]) return;
                player.getInventory().addItem(new ItemBuilder(Bossy.I5_1.getItemStack().clone()).setAmount(1).toItemStack());
                bossyManager.despawnKlejnot(player, 2);
                RPGCORE.getInstance().getCooldownManager().givePlayerKlejnoty120_130Cooldown(player.getUniqueId());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRogInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (e.getItem() == null || e.getItem().getType() != Material.HOPPER || !e.getItem().hasItemMeta() || !e.getItem().getItemMeta().hasDisplayName() ||
                !Utils.removeColor(e.getItem().getItemMeta().getDisplayName()).equals("Rog Naszych Przodkow")) return;
        if (!e.getPlayer().getLocation().getWorld().getName().equals("120-130map")) {
            e.getPlayer().sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cNie mozesz tego tutaj uzyc!"));
            return;
        }
        if (RPGCORE.getInstance().getUserManager().find(e.getPlayer().getUniqueId()).getLvl() < 120) {
            e.getPlayer().sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cPosiadasz zbyt niski poziom, zeby wykonacz ta czynnosc!"));
            return;
        }
        final Player player = e.getPlayer();
        if (!bossyManager.isKlejnoty120_130Spawned()) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cCzegos tu jeszcze brakuje..."));
            return;
        }
        player.getInventory().removeItem(new ItemBuilder(Bossy.I5_2.getItemStack().clone()).setAmount(1).toItemStack());
        bossyManager.open120_130BossGate(player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPortalEnter(final PlayerPortalEvent e) {
        if (!e.getFrom().getWorld().getName().equals("120-130map")) return;
        if (!bossyManager.isGate120_130open()) {
            e.getPlayer().performCommand("/spawn");
            e.getPlayer().sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cNiestety Brama zostala juz zamknieta!"));
            return;
        }
        e.getPlayer().sendMessage("test");
    }
}
