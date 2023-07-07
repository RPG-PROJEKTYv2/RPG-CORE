package rpg.rpgcore.old.gornik.ore;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Location;
import rpg.rpgcore.RPGCORE;

import java.util.Map;

public class OreManager {

    /*private final Map<Location, Ore> oreMap;

    public OreManager(final RPGCORE rpgcore) {
        this.oreMap = rpgcore.getMongoManager().loadAllOreLocations();
    }


    public void add(Location location, Ore ore) {
        this.oreMap.put(location, ore);
    }

    public Ore find(Location location) {
        return this.oreMap.get(location);
    }

    public Ore findById(final int id) {
        return this.oreMap.values().stream().filter(ore -> ore.getId() == id).findFirst().orElse(null);
    }

    public int getHighestId() {
        return this.oreMap.values().stream().mapToInt(Ore::getId).max().orElse(0);
    }

    public boolean isOre(Location location) {
        for (final Ore ore : this.oreMap.values()) {
            if (ore.getLocation().equals(location)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOre(final int id) {
        for (final Ore ore : this.oreMap.values()) {
            if (ore.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void remove(Ore ore) {
        this.oreMap.remove(ore.getLocation(), ore);
    }

    public ImmutableSet<Ore> getOreLocations() {
        return ImmutableSet.copyOf(this.oreMap.values());
    }*/
}
