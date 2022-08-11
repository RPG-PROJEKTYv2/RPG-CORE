package rpg.rpgcore.mythicstick;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class MythicstickPlayerInteract implements Listener {

    private final RPGCORE rpgcore;

    public MythicstickPlayerInteract(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void mythicstickPlayerInteract(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();


        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (eventItem == null) {
                return;
            }
            if (eventItem.getType() == Material.STICK) {
                if (eventItem.getItemMeta().getDisplayName() == null) {
                    return;
                }
                if (eventItem.getItemMeta().getDisplayName().contains(Utils.format("&6&lMythic &4&lSTICK"))) {
                    player.sendMessage("mythic stick");
                }
            }
        }
    }
}
