package rpg.rpgcore.managers;

import java.util.ArrayList;
import java.util.UUID;

public class GodManager {
    private final ArrayList<UUID> godList = new ArrayList<>();

    public ArrayList<UUID> getGodList() {
        return godList;
    }

    public boolean containsPlayer(final UUID uuid) {
        return godList.contains(uuid);
    }

}
