package rpg.rpgcore.tasks;

import org.bukkit.World;
import rpg.rpgcore.RPGCORE;

public class WeatherTask implements Runnable{
    public WeatherTask(final RPGCORE rpgcore) {
        rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, this, 0L, 20L);
    }

    @Override
    public void run() {
        for (final World world : RPGCORE.getInstance().getServer().getWorlds()) {
            world.setStorm(false);
            world.setWeatherDuration(0);
            world.setThundering(false);
            world.setThunderDuration(0);
        }
    }
}
