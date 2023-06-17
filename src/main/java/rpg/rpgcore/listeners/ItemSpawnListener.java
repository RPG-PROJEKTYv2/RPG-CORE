package rpg.rpgcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.admin.drop.DropManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

public class ItemSpawnListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemSpawn(final ItemSpawnEvent e) {
        if (!e.getEntity().getItemStack().hasItemMeta() || !e.getEntity().getItemStack().getItemMeta().hasDisplayName()) {
            e.getEntity().setCustomName(Utils.format("&7" + e.getEntity().getItemStack().getAmount() + "x " + e.getEntity().getItemStack().getType().toString()));
        } else {
            e.getEntity().setCustomName(Utils.format("&7" + e.getEntity().getItemStack().getAmount() + "x " + e.getEntity().getItemStack().getItemMeta().getDisplayName()));
        }
        e.getEntity().setCustomNameVisible(true);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemDrop(final PlayerDropItemEvent e) {
        e.setCancelled(true);
        final User user = RPGCORE.getInstance().getUserManager().find(e.getPlayer().getUniqueId());
        if (user == null) return;
        if (user.getRankUser().isHighStaff() && DropManager.isDropOn(e.getPlayer().getUniqueId())) {
            e.setCancelled(false);
        }
    }
}
