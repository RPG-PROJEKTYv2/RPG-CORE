package rpg.rpgcore.npc.rybak.objects;

import org.bson.Document;

import java.util.UUID;

public class RybakObject {
    private final UUID uuid;
    private final RybakUser rybakUser;

    public RybakObject(UUID uuid) {
        this.uuid = uuid;
        this.rybakUser = new RybakUser(1, 0, 0, 0, 0, 0);
    }

    public RybakObject(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.rybakUser = new RybakUser(document.getInteger("mission"), document.getInteger("progress"), document.getDouble("srDef"), document.getDouble("kryt"), document.getDouble("morskieSzczescie"), document.getInteger("trueDmg"));
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
                .append("srDef", rybakUser.getSrDef())
                .append("kryt", rybakUser.getKryt())
                .append("morskieSzczescie", rybakUser.getMorskieSzczescie())
                .append("trueDmg", rybakUser.getTrueDmg());
    }
}
