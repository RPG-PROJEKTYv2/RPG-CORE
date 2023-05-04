package rpg.rpgcore.npc.mistrz_yang.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class MistrzYangUser {
    private final UUID uuid;
    private int missionId;
    private int reqAmount;
    private int progress;

    public MistrzYangUser(final UUID uuid) {
        this.uuid = uuid;
        this.missionId = 0;
        this.reqAmount = 0;
        this.progress = 0;
    }

    public MistrzYangUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.missionId = document.getInteger("missionId");
        this.reqAmount = document.getInteger("reqAmount");
        this.progress = document.getInteger("progress");
    }

    public void incrementProgress() {
        this.progress++;
    }

    public void reset() {
        this.missionId = 0;
        this.reqAmount = 0;
        this.progress = 0;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("missionId", this.missionId)
                .append("reqAmount", this.reqAmount)
                .append("progress", this.progress);
    }
}
