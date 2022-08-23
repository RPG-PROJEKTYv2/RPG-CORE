package rpg.rpgcore.npc.metinolog;

import org.bson.Document;

import java.util.UUID;

public class MetinologObject {
    private final UUID uuid;
    private final MetinologUser metinologObject;

    public MetinologObject(final UUID uuid) {
        this.uuid = uuid;
        this.metinologObject = new MetinologUser(0,0, 0, 0,0,0,0, 0);
    }

    public MetinologObject(Document document) {
        this.uuid = UUID.fromString((String) document.get("_id"));
        this.metinologObject = new MetinologUser(document.getInteger("postepKill"), document.getInteger("postepMisjiKill"), document.getInteger("postepGive"), document.getInteger("postepMisjiGive"), document.getDouble("value1"), document.getDouble("value2"), document.getDouble("value3"), document.getDouble("value4"));
    }

    public UUID getID() {
        return uuid;
    }

    public MetinologUser getMetinologUser() {
        return this.metinologObject;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString()).append("postepKill", this.getMetinologUser().getPostepKill()).append("postepMisjiKill", this.getMetinologUser().getPostepMisjiKill()).append("postepGive", this.getMetinologUser().getPostepGive()).append("postepMisjiGive", this.getMetinologUser().getPostepMisjiGive()).append("value1", this.getMetinologUser().getValue1()).append("value2", this.getMetinologUser().getValue2()).append("value3", this.getMetinologUser().getValue3()).append("value4", this.getMetinologUser().getValue4());
    }
}
