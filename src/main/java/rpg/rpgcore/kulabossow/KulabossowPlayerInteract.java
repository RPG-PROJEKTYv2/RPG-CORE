package rpg.rpgcore.kulabossow;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.Utils;

public class KulabossowPlayerInteract implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void kulabossowPlayerInteract(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (eventItem == null) {
                return;
            }
            if (eventItem.getType() == Material.MAGMA_CREAM) {
                if (eventItem.getItemMeta().getDisplayName() == null || eventItem.getItemMeta().getLore() == null) {
                    return;
                }
            }
        }
    }
}
