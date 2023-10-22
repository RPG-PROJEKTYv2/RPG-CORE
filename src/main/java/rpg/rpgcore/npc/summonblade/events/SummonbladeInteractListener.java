package rpg.rpgcore.npc.summonblade.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.summonblade.objects.SummonbladeUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public class SummonbladeInteractListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (e.getItem() == null || e.getItem().getType() != Material.BANNER) return;
        if (!e.getItem().equals(GlobalItem.KRYSZTALOWA_BARIERA.getItemStack())) return;
        e.setCancelled(true);
        e.setUseItemInHand(Event.Result.DENY);
        e.setUseInteractedBlock(Event.Result.DENY);
        final Player player = e.getPlayer();
        final SummonbladeUser user = RPGCORE.getInstance().getSummonbladeNPC().find(player.getUniqueId());

        if (user.isActivated()) {
            player.sendMessage(Utils.format("&6&lSummonblade &8>> &cPosiadasz juz aktywna &f&lKrysztalowa Bariere"));
            return;
        }
        player.getInventory().removeItem(new ItemBuilder(GlobalItem.KRYSZTALOWA_BARIERA.getItemStack().clone()).setAmount(1).toItemStack());
        user.setActivated(true);
        player.sendMessage(Utils.format("&6&lSummonblade &8>> &aAktywowano &f&lKrysztalowa Bariere"));
        Bukkit.getServer().broadcastMessage(" ");
        Bukkit.getServer().broadcastMessage(Utils.format("&6&lSummonblade &8>> &eGracz &f" + player.getName() + " &eaktywowal &f&lKrysztalowa Bariere"));
        Bukkit.getServer().broadcastMessage(" ");

        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataSummonblade(user.getUuid(), user));
    }
}
