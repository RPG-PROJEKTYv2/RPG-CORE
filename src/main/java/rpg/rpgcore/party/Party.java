package rpg.rpgcore.party;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Party {
    private String leaderName;
    private UUID leaderUUID;
    private List<UUID> members;

    public Party(String leaderName, UUID leaderUUID, List<UUID> members) {
        this.leaderName = leaderName;
        this.leaderUUID = leaderUUID;
        this.members = members;
    }
}
