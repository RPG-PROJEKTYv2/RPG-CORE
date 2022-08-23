package rpg.rpgcore.npc.medyk;

import org.bson.Document;

import java.util.UUID;

public class MedykObject {
    private final UUID uuid;
    private final MedykUser medykObject;

    public MedykObject(final UUID uuid) {
        this.uuid = uuid;
        this.medykObject = new MedykUser(0,0, false);
    }

    public MedykObject(Document document) {
        this.uuid = UUID.fromString((String) document.get("_id"));
        this.medykObject = new MedykUser(document.getInteger("bonus"), document.getInteger("progress"), document.getBoolean("done"));
    }

    public UUID getID() {
        return uuid;
    }

    public MedykUser getMedykUser() {
        return this.medykObject;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString()).append("bonus", this.getMedykUser().getBonus()).append("progress", this.getMedykUser().getProgress()).append("done", this.getMedykUser().isDone());
    }
}
