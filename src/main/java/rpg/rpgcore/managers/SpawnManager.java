package rpg.rpgcore.managers;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SpawnManager {

    public static double defaultSpawnX = Bukkit.getWorld("world").getSpawnLocation().getX();
    public static double defaultSpawnY = Bukkit.getWorld("world").getSpawnLocation().getY();
    public static double defaultSpawnZ = Bukkit.getWorld("world").getSpawnLocation().getZ();

    private Location spawn;

    @Getter
    public Location getSpawn() {
        return this.spawn;
    }

    @Setter
    public void setSpawn(final Location spawn) {
        this.spawn = spawn;
    }

}
