package rpg.rpgcore.bossy;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import rpg.rpgcore.bossy.enums.Stage70_80;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BossyManager {
    @Getter
    private int boss60_70count = 0;

    @Getter
    @Setter
    private Stage70_80 stage70_80 = Stage70_80.NOT_SPAWNED;
    private final Map<UUID, Location> locationMap = new HashMap<>();



    public void incrementBoss60_70count() {
        boss60_70count++;
    }

    public void decrementBoss60_70count() {
        if (boss60_70count <= 0) return;
        boss60_70count--;
    }
}
