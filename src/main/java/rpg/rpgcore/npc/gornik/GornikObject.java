package rpg.rpgcore.npc.gornik;

import org.bson.Document;

import java.util.UUID;

public class GornikObject {
    private final UUID uuid;
    private final GornikUser gornikUser;

    public GornikObject(final UUID uuid) {
        this.uuid = uuid;
        this.gornikUser = new GornikUser(0, 0, 0, 0, 0, 0);
    }

    public GornikObject(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.gornikUser = new GornikUser(document.getInteger("mission"), document.getInteger("progress"),
                document.getDouble("przebiciePancerza"), document.getDouble("szybkosc"), document.getDouble("szansaNaWzmocnienieKrytyka"), document.getDouble("sredniaOdpornosc"));
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
                .append("przebiciePancerza", this.gornikUser.getPrzebiciePancerza())
                .append("szybkosc", this.gornikUser.getSzybkosc())
                .append("szansaNaWzmocnienieKrytyka", this.gornikUser.getSzansaNaWzmocnienieKrytyka())
                .append("sredniaOdpornosc", this.gornikUser.getSredniaOdpornosc());
    }
}
