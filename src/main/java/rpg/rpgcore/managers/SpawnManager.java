package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SpawnManager {

    public static double defaultSpawnX = Bukkit.getWorld("world").getSpawnLocation().getX();
    public static double defaultSpawnY = Bukkit.getWorld("world").getSpawnLocation().getY();
    public static double defaultSpawnZ = Bukkit.getWorld("world").getSpawnLocation().getZ();

    private Location spawn;

    public Location getSpawn() {
        return this.spawn;
    }

    public void setSpawn(final Location spawn) {
        this.spawn = spawn;
    }

}
