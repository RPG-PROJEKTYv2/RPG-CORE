package rpg.rpgcore.commands.admin.drop;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DropManager {
    private static final List<UUID> isDropOn = new ArrayList<>();

    public static boolean isDropOn(UUID uuid) {
        return isDropOn.contains(uuid);
    }

    public static void addDrop(UUID uuid) {
        isDropOn.add(uuid);
    }

    public static void removeDrop(UUID uuid) {
        isDropOn.remove(uuid);
    }
}
