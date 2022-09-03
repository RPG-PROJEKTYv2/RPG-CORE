package rpg.rpgcore.user;

import com.google.common.collect.ImmutableSet;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;

import java.util.Map;
import java.util.UUID;

public class UserManager {
    private final RPGCORE rpgcore;
    private final Map<UUID, User> usersUUIDMap;

    public UserManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.usersUUIDMap = rpgcore.getMongoManager().loadAllUsers();
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

    public String getPlayerGroup(final Player player) {
        return PlaceholderAPI.setPlaceholders(player, "%uperms_rank%");
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

    public ImmutableSet<User> getUserObjects() {
        return ImmutableSet.copyOf(usersUUIDMap.values());
    }
}
