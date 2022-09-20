package rpg.rpgcore.npc.lesnik;

import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;

import java.util.Map;
import java.util.UUID;

public class LesnikNPC {
    private final RPGCORE rpgcore;
    private final Map<UUID, LesnikObject> userMap;

    public LesnikNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllLesnik();
    }

    public void openLesnikGUI(final Player player) {

    }


    public LesnikObject find(final UUID uuid) {
        userMap.computeIfAbsent(uuid, k -> new LesnikObject(uuid));
        return userMap.get(uuid);
    }

    public void add(final LesnikObject lesnikObject) {
        userMap.put(lesnikObject.getId(), lesnikObject);
    }

    public ImmutableSet<LesnikObject> getLesnikObjects() {
        return ImmutableSet.copyOf(userMap.values());
    }
}
