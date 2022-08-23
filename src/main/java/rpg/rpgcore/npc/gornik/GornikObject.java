package rpg.rpgcore.npc.gornik;

import org.bson.Document;

import java.util.UUID;

public class GornikObject {
    private final UUID uuid;
    private final GornikUser gornikObject;

    public GornikObject(final UUID uuid) {
        this.uuid = uuid;
        this.gornikObject = new GornikUser(0,0, 0, 0,0,0);
    }

    public GornikObject(Document document) {
        this.uuid = UUID.fromString((String) document.get("_id"));
        this.gornikObject = new GornikUser(document.getInteger("mission"), document.getInteger("progress"), document.getDouble("bonus1"), document.getDouble("bonus2"), document.getDouble("bonus3"), document.getDouble("bonus4"));
    }

    public UUID getID() {
        return uuid;
    }

    public GornikUser getGornikUser() {
        return this.gornikObject;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString()).append("mission", this.getGornikUser().getMission()).append("progress", this.getGornikUser().getProgress()).append("bonus1", this.getGornikUser().getBonus1()).append("bonus2", this.getGornikUser().getBonus2()).append("bonus3", this.getGornikUser().getBonus3()).append("bonus4", this.getGornikUser().getBonus4());
    }
}
