package rpg.rpgcore.npc.kolekcjoner;

import org.bson.Document;

import java.util.UUID;

public class KolekcjonerObject {
    private final UUID uuid;
    private final KolekcjonerUser kolekcjonerUser;

    public KolekcjonerObject(final UUID uuid) {
        this.uuid = uuid;
        this.kolekcjonerUser = new KolekcjonerUser(0,0, 0, 0,0);
    }

    public KolekcjonerObject(final Document document) {
        this.uuid = UUID.fromString((String) document.get("_id"));
        this.kolekcjonerUser = new KolekcjonerUser(document.getInteger("mission"),
                document.getInteger("missionProgress"),
                document.getDouble("value1"),
                document.getDouble("value2"),
                document.getDouble("value3"));
    }

    public UUID getID() {
        return uuid;
    }

    public KolekcjonerUser getKolekcjonerUser() {
        return this.kolekcjonerUser;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("mission", this.getKolekcjonerUser().getMission())
                .append("missionProgress", this.getKolekcjonerUser().getMissionProgress())
                .append("value1", this.getKolekcjonerUser().getValue1())
                .append("value2", this.getKolekcjonerUser().getValue2())
                .append("value3", this.getKolekcjonerUser().getValue3());
    }
}
