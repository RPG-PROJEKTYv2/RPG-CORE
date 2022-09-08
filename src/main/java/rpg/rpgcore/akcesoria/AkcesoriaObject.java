package rpg.rpgcore.akcesoria;

import org.bson.Document;

import java.util.UUID;

public class AkcesoriaObject {
    private final AkcesoriaUser akcesoriaUser;
    private final UUID uuid;

    public AkcesoriaObject(UUID uuid) {
        this.uuid = uuid;
        this.akcesoriaUser = new AkcesoriaUser("", "", "", "", "", "", "");
    }

    public AkcesoriaObject(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.akcesoriaUser = new AkcesoriaUser(document.getString("tarcza"), document.getString("medalion"), document.getString("pas"), document.getString("kolczyki"), document.getString("sygnet"), document.getString("energia"), document.getString("zegarek"));
    }


    public UUID getId() {
        return this.uuid;
    }

    public AkcesoriaUser getAkcesoriaUser() {
        return this.akcesoriaUser;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("tarcza", getAkcesoriaUser().getTarcza())
                .append("medalion", getAkcesoriaUser().getMedalion())
                .append("pas", getAkcesoriaUser().getPas())
                .append("kolczyki", getAkcesoriaUser().getKolczyki())
                .append("sygnet", getAkcesoriaUser().getSygnet())
                .append("energia", getAkcesoriaUser().getEnergia())
                .append("zegarek", getAkcesoriaUser().getZegarek());
    }
}
