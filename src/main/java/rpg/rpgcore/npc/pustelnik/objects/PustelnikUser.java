package rpg.rpgcore.npc.pustelnik.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class PustelnikUser {
    private final UUID uuid;
    private int missionId;
    private int progress;

    public PustelnikUser(final UUID uuid) {
        this.uuid = uuid;
        this.missionId = 0;
        this.progress = 0;
    }

    public PustelnikUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.missionId = document.getInteger("missionId");
        this.progress = document.getInteger("progress");
    }

    public void reset() {
        this.missionId = 0;
        this.progress = 0;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("missionId", this.missionId)
                .append("progress", this.progress);
    }
}
