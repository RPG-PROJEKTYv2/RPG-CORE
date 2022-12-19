package rpg.rpgcore.listanpc;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
public class NpcObject {
    private final String npcName;
    private final Location location;
    private final String npcType;
    private final int reqLvl;

    public NpcObject(final String npcName, final Location location, final String npcType, final int reqLvl) {
        this.npcName = npcName;
        this.location = location;
        this.npcType = npcType;
        this.reqLvl = reqLvl;
    }
}
