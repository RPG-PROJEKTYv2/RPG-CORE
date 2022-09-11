package rpg.rpgcore.commands.admin.teleport;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import rpg.rpgcore.RPGCORE;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class TeleportManager {

    private final RPGCORE rpgcore;
    private final HashMap<UUID, Location> beforeTeleportLocation = new HashMap<>();

    public TeleportManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public void setBeforeTeleportLocation(final UUID uuid, final Location loc) {
        if (!(this.beforeTeleportLocation.containsKey(uuid))) {
            this.beforeTeleportLocation.put(uuid, loc);
            return;
        }
        this.beforeTeleportLocation.replace(uuid, loc);
    }

    public Location getBeforeTeleportLocation(final UUID uuid) {
        return this.beforeTeleportLocation.get(uuid);
    }

}
