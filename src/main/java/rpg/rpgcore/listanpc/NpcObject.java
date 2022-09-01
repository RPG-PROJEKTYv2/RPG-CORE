package rpg.rpgcore.listanpc;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
public class NpcObject {
    private String npcName;
    private Location location;
    private String npcType;

    public NpcObject(String npcName, Location location, String npcType) {
        this.npcName = npcName;
        this.location = location;
        this.npcType = npcType;
    }
}
