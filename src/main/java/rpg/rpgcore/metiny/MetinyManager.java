package rpg.rpgcore.metiny;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Location;
import rpg.rpgcore.RPGCORE;

import java.util.Map;

public class MetinyManager {
    private final Map<Location, Metiny> metiny1;

    public MetinyManager(final RPGCORE rpgcore) {
        this.metiny1 = rpgcore.getMongoManager().loadMetins();
    }

    public void add(Metiny metiny) {
        this.metiny1.put(metiny.getLocation(), metiny);
    }

    public Metiny find(final Location location) {
        return this.metiny1.get(location);
    }

    public Metiny find(final int id) {
        for (Metiny metiny : this.metiny1.values()) {
            if (metiny.getId() == id) {
                return metiny;
            }
        }
        return null;
    }

    public boolean isMetin(final Location location) {
        return this.metiny1.containsKey(location);
    }

    public boolean isMetin(final int id) {
        for (Metiny metiny : this.metiny1.values()) {
            if (metiny.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public ImmutableSet<Metiny> getMetins() {
        return ImmutableSet.copyOf(this.metiny1.values());
    }

    public void remove(Metiny metiny) {
        this.metiny1.remove(metiny.getLocation(), metiny);
    }

}
