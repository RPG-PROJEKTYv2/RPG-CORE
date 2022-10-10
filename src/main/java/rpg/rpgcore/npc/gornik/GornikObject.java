package rpg.rpgcore.npc.gornik;

import org.bson.Document;

import java.util.UUID;

public class GornikObject {
    private final UUID uuid;
    private final GornikUser gornikUser;

    public GornikObject(final UUID uuid) {
        this.uuid = uuid;
        this.gornikUser = new GornikUser(1, 0, 0, 0, 0, false);
    }

    public GornikObject(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.gornikUser = new GornikUser(document.getInteger("mission"), document.getInteger("progress"), document.getDouble("sredniaOdpornosc"),
                document.getDouble("blokCiosu"), document.getDouble("przeszycieBloku"), document.getBoolean("dalszeDone"));
    }

    public UUID getID() {
        return this.uuid;
    }

    public GornikUser getGornikUser() {
        return this.gornikUser;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("mission", this.gornikUser.getMission())
                .append("progress", this.gornikUser.getProgress())
                .append("sredniaOdpornosc", this.gornikUser.getSredniaOdpornosc())
                .append("blokCiosu", this.gornikUser.getBlokCiosu())
                .append("przeszycieBloku", this.gornikUser.getPrzeszycieBloku())
                .append("dalszeDone", this.gornikUser.isDalszeDone());
    }
}
