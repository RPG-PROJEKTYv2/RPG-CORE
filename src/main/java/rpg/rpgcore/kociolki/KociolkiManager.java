package rpg.rpgcore.kociolki;

import com.google.common.collect.ImmutableSet;
import rpg.rpgcore.RPGCORE;

import java.util.Map;
import java.util.UUID;

public class KociolkiManager {
    private final Map<UUID, KociolkiUser> userMap;

    public KociolkiManager(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllKociolki();
    }

    public KociolkiUser find(final UUID uuid) {
        return userMap.get(uuid);
    }

    public void add(final KociolkiUser kociolkiUser) {
        userMap.put(kociolkiUser.getUuid(), kociolkiUser);
    }

    public ImmutableSet<KociolkiUser> getKociolkiUsers() {
        return ImmutableSet.copyOf(userMap.values());
    }
}
