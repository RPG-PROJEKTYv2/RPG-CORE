package rpg.rpgcore.party;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PartyInvite {
    private final Party party;
    private final UUID invitedUuid;

    public PartyInvite(Party party, UUID invitedUuid) {
        this.party = party;
        this.invitedUuid = invitedUuid;
    }
}
