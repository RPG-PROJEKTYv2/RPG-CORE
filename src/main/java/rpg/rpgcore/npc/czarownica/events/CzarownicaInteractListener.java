package rpg.rpgcore.npc.czarownica.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

public class CzarownicaInteractListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (!e.getItem().hasItemMeta()) return;
        if (!e.getItem().getItemMeta().hasDisplayName()) return;
        if (!e.getItem().getItemMeta().getDisplayName().contains("Magiczna Receptura")) return;
        e.setCancelled(true);
        e.setUseInteractedBlock(Event.Result.DENY);
        e.setUseItemInHand(Event.Result.DENY);
        final CzarownicaUser user = RPGCORE.getInstance().getCzarownicaNPC().find(e.getPlayer().getUniqueId());
        if (user.isUnlocked()) {
            e.getPlayer().sendMessage(Utils.format("&5&lCzarownica &8>> &dOdblokowales juz Magiczna recepture!"));
            return;
        }
        user.setUnlocked(true);
        e.getPlayer().getInventory().removeItem(new ItemBuilder(e.getItem().clone()).setAmount(1).toItemStack());
        e.getPlayer().sendMessage(Utils.format("&5&lCzarownica &8>> &dPomyslnie odblokowales Magiczna recepture!"));
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataCzarownica(e.getPlayer().getUniqueId(), user));
    }
}
