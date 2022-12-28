package rpg.rpgcore.spawn;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
@Setter
public class SpawnManager {

    public static double defaultSpawnX = Bukkit.getWorld("spawnOFFICIAL").getSpawnLocation().getX();
    public static double defaultSpawnY = Bukkit.getWorld("spawnOFFICIAL").getSpawnLocation().getY();
    public static double defaultSpawnZ = Bukkit.getWorld("spawnOFFICIAL").getSpawnLocation().getZ();

    private Location spawn;

    public Location getSpawn() {
        return this.spawn;
    }

    public void setSpawn(final Location spawn) {
        this.spawn = spawn;
    }

}
