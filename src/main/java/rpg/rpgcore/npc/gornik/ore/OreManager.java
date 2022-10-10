package rpg.rpgcore.npc.gornik.ore;

import com.google.common.collect.ImmutableSet;
import rpg.rpgcore.RPGCORE;

import java.util.Map;

public class OreManager {

    private final Map<Integer, Ore> oreMap;

    public OreManager(final RPGCORE rpgcore) {
        this.oreMap = rpgcore.getMongoManager().loadAllOreLocations();
    }


    public void add(Ore ore) {
        this.oreMap.put(ore.getId(), ore);
    }

    public Ore find(int id) {
        return this.oreMap.get(id);
    }

    public boolean isOre(int id) {
        return this.oreMap.containsKey(id);
    }

    public void remove(Ore ore) {
        this.oreMap.remove(ore.getId(), ore);
    }

    public ImmutableSet<Ore> getOreLocations() {
        return ImmutableSet.copyOf(this.oreMap.values());
    }
}
