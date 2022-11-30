package rpg.rpgcore.commands.player.profile;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import rpg.rpgcore.utils.Utils;

public class ProfileInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }

        if (Utils.removeColor(e.getClickedInventory().getTitle()).contains("Profil ")) {
            e.setCancelled(true);
        }
    }
}
