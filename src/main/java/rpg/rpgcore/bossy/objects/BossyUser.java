package rpg.rpgcore.bossy.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Location;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.DoubleUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BossyUser {
    private int mobsCount100_110;
    private int odlamki110_120;
    private List<Location> rdzen110_120Locations;

    public BossyUser() {
        mobsCount100_110 = 0;
        odlamki110_120 = 0;
        rdzen110_120Locations = new ArrayList<>();
    }

    public BossyUser(Document document) {
        mobsCount100_110 = document.getInteger("mobsCount100_110");
        odlamki110_120 = document.getInteger("odlamki110_120");
        rdzen110_120Locations = locationsFromString(document.getList("rdzen110_120Locations", String.class));
    }

    public void incrementMobsCount100_110() {
        mobsCount100_110++;
    }

    public void resetMobsCount100_110() {
        mobsCount100_110 = 0;
    }

    public void incrementOdlamki110_120() {
        odlamki110_120++;
    }

    public void resetOdlamki110_120() {
        odlamki110_120 = 0;
    }

    public void addRdzen110_120Location(final Location location) {
        rdzen110_120Locations.add(location);
    }

    public void removeRdzen110_120Location(final Location location) {
        rdzen110_120Locations.remove(location);
    }

    public boolean isRdzen110_120Location(final Location location) {
        return rdzen110_120Locations.contains(location);
    }

    public List<Location> locationsFromString(final List<String> stringList) {
        final List<Location> locations = new ArrayList<>();
        for (final String string : stringList) {
            final String[] split = string.split(";");
            locations.add(new Location(RPGCORE.getInstance().getServer().getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3])));
        }
        return locations;
    }

    public List<String> locationsToString(final List<Location> locationList) {
        final List<String> stringList = new ArrayList<>();
        for (final Location location : locationList) {
            stringList.add(location.getWorld().getName() + ";" + DoubleUtils.round(location.getX(), 0) + ";" + DoubleUtils.round(location.getY(), 0) + ";" + DoubleUtils.round(location.getZ(), 0));
        }
        return stringList;
    }


    public void save() {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataBossy());
    }

    public Document toDocument() {
        return new Document("_id", "bossy")
                .append("mobsCount100_110", mobsCount100_110)
                .append("odlamki110_120", odlamki110_120)
                .append("rdzen110_120Locations", locationsToString(rdzen110_120Locations));
    }
}
