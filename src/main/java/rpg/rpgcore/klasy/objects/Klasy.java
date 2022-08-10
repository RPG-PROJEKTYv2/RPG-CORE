package rpg.rpgcore.klasy.objects;

import org.bson.Document;

import java.util.ArrayList;
import java.util.UUID;

public class Klasy {
    private final UUID uuid;
    private final KlasaUser klasaUser;

    public Klasy(UUID uuid) {
        this.uuid = uuid;
        this.klasaUser = new KlasaUser("", 0,0, "", 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public Klasy(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.klasaUser = new KlasaUser(document.getString("name"), document.getInteger("lvl"), document.getInteger("exp"), document.getString("mission"), document.getInteger("missionProgress"), document.getInteger("globalPoints"), document.getInteger("points"), document.getDouble("value1"), document.getDouble("value2"), document.getDouble("value3"), document.getDouble("value4"), document.getDouble("value5"));
    }

    public UUID getId() {
        return this.uuid;
    }

    public KlasaUser getKlasaUser() {
        if (this.klasaUser == null) {
            return new KlasaUser("", 0,0, "", 0, 0, 0, 0, 0, 0, 0, 0);
        }
        return this.klasaUser;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString()).append("name", this.klasaUser.getName()).append("lvl", this.klasaUser.getLvl()).append("exp", this.klasaUser.getExp()).append("mission", this.klasaUser.getMission()).append("missionProgress", this.klasaUser.getMissionProgress()).append("globalPoints", this.getKlasaUser().getGlobalPoints()).append("points", this.klasaUser.getPoints()).append("value1", this.klasaUser.getValue1()).append("value2", this.klasaUser.getValue2()).append("value3", this.klasaUser.getValue3()).append("value4", this.klasaUser.getValue4()).append("value5", this.klasaUser.getValue5());
    }
}
