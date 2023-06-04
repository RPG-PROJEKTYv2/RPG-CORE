package rpg.rpgcore.npc.lowca.objects;

import org.bson.Document;

import java.util.UUID;

public class LowcaObject {
    private final UUID uuid;
    private final LowcaUser lowcaUser;

    public LowcaObject(final UUID uuid) {
        this.uuid = uuid;
        this.lowcaUser = new LowcaUser(1, 0, 0, 0, 0);
    }

    public LowcaObject(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.lowcaUser = new LowcaUser(document.getInteger("mission"), document.getInteger("progress"), document.getInteger("szczescie"), document.getInteger("szybkosc"), document.getInteger("dodatkoweDmg"));
    }

    public UUID getId() {
        return this.uuid;
    }

    public LowcaUser getLowcaUser() {
        return this.lowcaUser;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("mission", this.lowcaUser.getMission())
                .append("progress", this.lowcaUser.getProgress())
                .append("szczescie", this.lowcaUser.getSzczescie())
                .append("szybkosc", this.lowcaUser.getSzybkosc())
                .append("dodatkoweDmg", this.lowcaUser.getDodatkoweDmg());
    }

}
