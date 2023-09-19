package rpg.rpgcore.managers;

import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserSaveManager {

    private final RPGCORE rpgcore;

    public UserSaveManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final ConcurrentMap<UUID, Integer> taskMap = new ConcurrentHashMap<>();

    public void savePlayer(final Player player, final UUID uuid) {
        final int task = rpgcore.getServer().getScheduler().scheduleAsyncRepeatingTask(rpgcore, () -> rpgcore.getMongoManager().savePlayer(player, uuid), 100L, 6000L);
        this.addToTaskMap(uuid, task);
    }


    public void addToTaskMap(final UUID uuid, final int taskId) {
        taskMap.put(uuid, taskId);
    }

    public void removeFromTaskMap(final UUID uuid) {
        taskMap.remove(uuid);
    }

    public int getTaskId(final UUID uuid) {
        if (!taskMap.containsKey(uuid)) return -1;
        return taskMap.get(uuid);
    }

}
