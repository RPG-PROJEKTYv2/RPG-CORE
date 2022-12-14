package rpg.rpgcore.armor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ArmorEffectListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void inventoryClose(final InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        ArmorEffectsHelper.addEffectsArmor(p);
    }

}
