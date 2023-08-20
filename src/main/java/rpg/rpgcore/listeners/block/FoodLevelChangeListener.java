package rpg.rpgcore.listeners.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFoodLevelChange(final FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
 }
