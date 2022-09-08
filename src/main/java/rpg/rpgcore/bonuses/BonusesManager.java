package rpg.rpgcore.bonuses;

import com.google.common.collect.ImmutableSet;
import rpg.rpgcore.RPGCORE;

import java.util.Map;
import java.util.UUID;

public class BonusesManager {
    private final Map<UUID, Bonuses> userMap;

    public BonusesManager(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllBonuses();
    }

    public void add(final Bonuses bonuses) {
        this.userMap.put(bonuses.getId(), bonuses);
    }

    public Bonuses find(final UUID uuid) {
        userMap.computeIfAbsent(uuid, k -> new Bonuses(uuid));
        return this.userMap.get(uuid);
    }

    public ImmutableSet<Bonuses> getBonusesObjects() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
