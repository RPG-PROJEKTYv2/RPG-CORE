package rpg.rpgcore.dodatki;

import com.google.common.collect.ImmutableSet;
import rpg.rpgcore.RPGCORE;

import java.util.Map;
import java.util.UUID;

public class DodatkiManager {
    private final RPGCORE rpgcore;
    private final Map<UUID, DodatkiUser> userMap;

    public DodatkiManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllDodatki();
    }

    public DodatkiUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void add(final DodatkiUser user) {
        this.userMap.put(user.getUuid(), user);
    }

    public ImmutableSet<DodatkiUser> getDodatkiUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
