package rpg.rpgcore.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;


public class BookInteractListener implements Listener {
    private final RPGCORE rpgcore;

    public BookInteractListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        final ItemStack eventItem = e.getItem();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (eventItem == null) {
            return;
        }
        if (eventItem.getType() == Material.BOOK_AND_QUILL && eventItem.getItemMeta().hasDisplayName()) {
            e.setCancelled(true);
            e.getPlayer().closeInventory();
        }
    }
}
