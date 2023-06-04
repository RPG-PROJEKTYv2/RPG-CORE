package rpg.rpgcore.npc.kolekcjoner.objects;

import org.bson.Document;

import java.util.Arrays;
import java.util.UUID;

public class KolekcjonerObject {
    private final UUID uuid;
    private final KolekcjonerUser kolekcjonerUser;

    public KolekcjonerObject(final UUID uuid) {
        this.uuid = uuid;
        this.kolekcjonerUser = new KolekcjonerUser(1, Arrays.asList(false, false, false, false, false), 0, 0,0);
    }

    public KolekcjonerObject(final Document document) {
        this.uuid = UUID.fromString((String) document.get("_id"));
        this.kolekcjonerUser = new KolekcjonerUser(document.getInteger("mission"),
                document.getList("missionProgress", Boolean.class),
                document.getInteger("szczescie"),
                document.getDouble("silnyNaLudzi"),
                document.getDouble("defNaLudzi"));
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
                .append("szczescie", this.getKolekcjonerUser().getSzczescie())
                .append("silnyNaLudzi", this.getKolekcjonerUser().getSilnyNaLudzi())
                .append("defNaLudzi", this.getKolekcjonerUser().getDefNaLudzi());
    }
}
