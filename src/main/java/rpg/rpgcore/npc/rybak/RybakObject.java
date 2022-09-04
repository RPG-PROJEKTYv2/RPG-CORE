package rpg.rpgcore.npc.rybak;

import org.bson.Document;

import java.util.UUID;

public class RybakObject {
    private final UUID uuid;
    private final RybakUser rybakUser;

    public RybakObject(UUID uuid) {
        this.uuid = uuid;
        this.rybakUser = new RybakUser(0, 0, 0, 0, 0, 0);
    }

    public RybakObject(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.rybakUser = new RybakUser(document.getInteger("mission"), document.getInteger("progress"), document.getDouble("value1"), document.getDouble("value2"), document.getDouble("value3"), document.getDouble("value4"));
    }

    public UUID getId() {
        return uuid;
    }

    public RybakUser getRybakUser() {
        return rybakUser;
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("mission", rybakUser.getMission())
                .append("progress", rybakUser.getProgress())
                .append("value1", rybakUser.getValue1())
                .append("value2", rybakUser.getValue2())
                .append("value3", rybakUser.getValue3())
                .append("value4", rybakUser.getValue4());
    }
}
