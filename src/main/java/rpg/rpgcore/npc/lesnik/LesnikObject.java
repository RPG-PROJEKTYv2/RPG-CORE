package rpg.rpgcore.npc.lesnik;

import org.bson.Document;

import java.util.UUID;

public class LesnikObject {
    private final UUID uuid;
    private final LesnikUser user;

    public LesnikObject(UUID uuid) {
        this.uuid = uuid;
        this.user = new LesnikUser(1, 0, 0, 0, 0, 0);
    }

    public LesnikObject(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.user = new LesnikUser(document.getInteger("mission"), document.getInteger("progress"), document.getDouble("przeszycie"), document.getDouble("wzmKryta"), document.getDouble("defNaLudzi"), document.getLong("cooldown"));
    }

    public UUID getId() {
        return uuid;
    }

    public LesnikUser getUser() {
        return user;
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("mission", user.getMission())
                .append("progress", user.getProgress())
                .append("przeszycie", user.getPrzeszycie())
                .append("wzmKryta", user.getWzmKryta())
                .append("defNaLudzi", user.getDefNaLudzi())
                .append("cooldown", user.getCooldown());
    }
}
