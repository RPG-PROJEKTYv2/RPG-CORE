package rpg.rpgcore.listeners.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeatherChange(final WeatherChangeEvent e) {
        e.setCancelled(true);
        e.getWorld().setStorm(false);
        e.getWorld().setWeatherDuration(0);
        e.getWorld().setThundering(false);
        e.getWorld().setThunderDuration(0);
    }
}
