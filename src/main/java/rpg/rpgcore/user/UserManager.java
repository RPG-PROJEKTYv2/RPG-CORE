package rpg.rpgcore.user;

import com.google.common.collect.ImmutableSet;
import rpg.rpgcore.RPGCORE;

import java.util.Map;
import java.util.UUID;

public class UserManager {
    private final Map<UUID, User> usersUUIDMap;
    private final Map<UUID, WWWUser> wwwUserMap;

    public UserManager(final RPGCORE rpgcore) {
        this.usersUUIDMap = rpgcore.getMongoManager().loadAllUsers();
        this.wwwUserMap = rpgcore.getMongoManager().loadAllWWWUsers();
    }

    public boolean isUser(final UUID uniqueId) {
        return this.usersUUIDMap.containsKey(uniqueId);
    }



    public boolean isUserName(final String name) {
        for (final Map.Entry<UUID, User> entry : this.usersUUIDMap.entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public User find(final UUID uuid) {
        return usersUUIDMap.get(uuid);
    }

    public User find(final String name) {
        for (Map.Entry<UUID, User> entry : this.usersUUIDMap.entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void add(final User user) {
        usersUUIDMap.put(user.getId(), user);
    }

    public void remove(final User user) {
        usersUUIDMap.remove(user.getId());
    }

    public void addWWWUser(final WWWUser user) {
        wwwUserMap.put(user.getUuid(), user);
    }

    public WWWUser findWWWUser(final UUID uuid) {
        return wwwUserMap.get(uuid);
    }

    public ImmutableSet<WWWUser> getWWWUsers() {
        return ImmutableSet.copyOf(wwwUserMap.values());
    }

    public ImmutableSet<User> getUserObjects() {
        return ImmutableSet.copyOf(usersUUIDMap.values());
    }
}
