package rpg.rpgcore.bossy.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bossy.BossyManager;
import rpg.rpgcore.bossy.enums.Stage70_80;
import rpg.rpgcore.commands.admin.restart.RestartManager;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;

import java.util.UUID;

public class PrzekletyCzarnoksieznikListener implements Listener {

    private final BossyManager bossyManager = RPGCORE.getInstance().getBossyManager();


    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(final EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof ArmorStand)) return;
        if (!e.getEntity().getWorld().getName().equals("70-80map")) return;
        e.setCancelled(true);
        e.setDamage(0);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) return;
        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getType() != Material.ENDER_PORTAL_FRAME) return;

        final Location blockLoc = e.getClickedBlock().getLocation();
        if (!bossyManager.isLocationPlace70_80(blockLoc)) return;

        final Player player = e.getPlayer();

        if (RPGCORE.getInstance().getCooldownManager().hasSerce70_80Cooldown(player.getUniqueId())) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cMusisz jeszcze chwile odczekac..."));
            return;
        }

        final ItemStack item = e.getItem();
        if (item == null || item.getType() != Material.MAGMA_CREAM || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || !Utils.removeColor(item.getItemMeta().getDisplayName()).equals("Przeklete Serce")) return;
        if (RestartManager.restart.isRestarting()) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            player.sendMessage(Utils.format("&8&l[&4&lRESTART&8&l] &cNie mozesz tego zrobic, poniewaz aktualnie trwa restart serwera!"));
            return;
        }
        if (!bossyManager.canBeSpawned70_80()) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cW tym momencie nie mozesz tego zrobic!"));
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cPrawdopodobnie boss pojawil sie juz gdzies na mapie!"));
            return;
        }
        if (bossyManager.isLocationPlaceUsed70_80(blockLoc)) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cTo miejsce jest juz zajete!"));
            return;
        }
        player.getInventory().removeItem(new ItemBuilder(Bossy.I2.getItemStack().clone()).setAmount(1).toItemStack().clone());
        player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &aPomyslnie wlozyles/-as &cPrzeklete Serce&a!"));
        final ArmorStand as = bossyManager.spawnArmorStandOnPlace70_80(blockLoc);
        bossyManager.addLocation70_80(player.getUniqueId(), as);
        bossyManager.incrementStage70_80();
        RPGCORE.getInstance().getCooldownManager().givePlayerSerce70_80Cooldown(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onArmorStandInteract(final PlayerInteractAtEntityEvent e) {
        if (!(e.getRightClicked() instanceof ArmorStand)) return;
        final ArmorStand as = (ArmorStand) e.getRightClicked();
        if (!as.getWorld().getName().equals("70-80map")) return;

        if (!bossyManager.isArmorStand70_80(as.getEntityId())) return;
        e.setCancelled(true);
        final Player player = e.getPlayer();
        if (bossyManager.getStage70_80() == Stage70_80.SPAWNING || bossyManager.getStage70_80() == Stage70_80.SPAWNED) return;
        if (RPGCORE.getInstance().getCooldownManager().hasSerce70_80Cooldown(player.getUniqueId())) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cMusisz jeszcze chwile odczekac..."));
            return;
        }
        final UUID uuidFromLoc = bossyManager.getLocation70_80Map().get(as);
        if (uuidFromLoc == null || !uuidFromLoc.equals(player.getUniqueId())) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cTo serce nie nalezy do Ciebie!"));
            return;
        }
        if (uuidFromLoc.equals(player.getUniqueId())) {
            bossyManager.returnSingle70_80(player, as);
            RPGCORE.getInstance().getCooldownManager().givePlayerSerce70_80Cooldown(player.getUniqueId());
        }
    }
}
