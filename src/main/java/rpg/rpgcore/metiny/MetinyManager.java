package rpg.rpgcore.metiny;

import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.ArmorStand;
import rpg.rpgcore.RPGCORE;

import java.util.HashMap;
import java.util.Map;

public class MetinyManager {
    private final Map<Integer, Metiny> metiny1;
    private final Map<Integer, ArmorStand> asMap = new HashMap<>();

    public MetinyManager(final RPGCORE rpgcore) {
        this.metiny1 = rpgcore.getMongoManager().loadMetins();
    }

    public void addAs(int id, ArmorStand armorStand) {
        this.asMap.put(id, armorStand);
    }

    public ArmorStand getAs(int id) {
        return this.asMap.get(id);
    }

    public void removeAs(int id) {
        this.asMap.remove(id);
    }

    public void add(Metiny metiny) {
        this.metiny1.put(metiny.getId(), metiny);
    }

    public Metiny find(int id) {
        return this.metiny1.get(id);
    }

    public boolean isMetin(int id) {
        return this.metiny1.containsKey(id);
    }

    public ImmutableSet<Metiny> getMetins() {
        return ImmutableSet.copyOf(this.metiny1.values());
    }

    public void remove(Metiny metiny) {
        this.metiny1.remove(metiny.getId(), metiny);
    }

}
