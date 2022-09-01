package rpg.rpgcore.npc.przyrodnik;

import org.bson.Document;

import java.util.UUID;

public class PrzyrodnikObject {
    private final UUID uuid;
    private final PrzyrodnikUser user;

    public PrzyrodnikObject(final UUID uuid) {
        this.uuid = uuid;
        this.user = new PrzyrodnikUser(0, 0, 0, 0);
    }

    public PrzyrodnikObject(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.user = new PrzyrodnikUser(document.getInteger("mission"), document.getInteger("progress"), document.getDouble("dmg"), document.getDouble("def"));
    }

    public PrzyrodnikUser getUser() {
        return user;
    }

    public UUID getId() {
        return this.uuid;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("mission", this.user.getMission())
                .append("progress", this.user.getProgress())
                .append("dmg", this.user.getDmg())
                .append("def", this.user.getDef());
    }
}
