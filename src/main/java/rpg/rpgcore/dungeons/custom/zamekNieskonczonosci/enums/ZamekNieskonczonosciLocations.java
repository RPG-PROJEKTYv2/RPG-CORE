package rpg.rpgcore.dungeons.custom.zamekNieskonczonosci.enums;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum ZamekNieskonczonosciLocations {
    L1("SuperAbility_1", new Location(Bukkit.getWorld("zamekNieskonczonosci"), -4.5, 17.2, 89.5, -32.5F, 40F)),
    L2("SuperAbility_2", new Location(Bukkit.getWorld("zamekNieskonczonosci"), 13.5, 17.2, 89.5, 47.5F, 40F)),
    L3("SuperAbility_3", new Location(Bukkit.getWorld("zamekNieskonczonosci"), 13.5, 17.2, 108.5, 150F, 40F)),
    L4("SuperAbility_4", new Location(Bukkit.getWorld("zamekNieskonczonosci"), -4.5, 17.2, 108.5, -140F, 40F)),
    BACK("BackLocation", new Location(Bukkit.getWorld("zamekNieskonczonosci"), 4.5, 6, 110.5, -180F, 0F));

    private final String name;
    private final Location location;

    ZamekNieskonczonosciLocations(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public static Location getLocationByName(final String name) {
        for (final ZamekNieskonczonosciLocations location : ZamekNieskonczonosciLocations.values()) {
            if (location.getName().equalsIgnoreCase(name)) {
                return location.getLocation();
            }
        }
        return null;
    }
}
