package rpg.rpgcore.managers;

import org.bukkit.Location;

public class SpawnManager {

    private Location spawn;

    public Location getSpawn() {
        return this.spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }
}
