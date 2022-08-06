package rpg.rpgcore.metiny;

import com.google.common.collect.ImmutableSet;
import rpg.rpgcore.RPGCORE;

import java.util.Map;

public class MetinyManager {
    private final Map<Integer, Metiny> metiny1;
    private final RPGCORE rpgcore;

    public MetinyManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.metiny1 = rpgcore.getMongoManager().loadMetins();
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
