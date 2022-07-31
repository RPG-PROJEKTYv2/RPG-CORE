package rpg.rpgcore.managers;

import rpg.rpgcore.RPGCORE;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BackupManager {

    private final RPGCORE rpgcore;

    public BackupManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final ConcurrentMap<UUID, Integer> taskMap = new ConcurrentHashMap<>();

    public void savePlayer(final UUID uuid) {
        final int task = rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, () -> rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().savePlayer(uuid)), 100L, 6000L);
        this.addToTaskMap(uuid, task);
    }


    public void addToTaskMap(final UUID uuid, final int taskId) {
        taskMap.put(uuid, taskId);
    }

    public void removeFromTaskMap(final UUID uuid) {
        taskMap.remove(uuid);
    }

    public int getTaskId(final UUID uuid) {
        return taskMap.get(uuid);
    }

}
