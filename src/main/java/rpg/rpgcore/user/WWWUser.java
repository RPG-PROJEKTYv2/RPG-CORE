package rpg.rpgcore.user;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class WWWUser {
    private final UUID uuid;
    private String inventoryJSON;
    private String armorJSON;
    private String enderchestJSON;
    private String akcesoriaPodstawoweJSON;
    private String akcesoriaDodatkoweJSON;
    private String bonyJSON;
    private String userPetyJSON;
    private String activePet;

    public WWWUser(final UUID uuid) {
        this.uuid = uuid;
        this.inventoryJSON = "";
        this.armorJSON = "";
        this.enderchestJSON = "";
        this.akcesoriaPodstawoweJSON = "";
        this.akcesoriaDodatkoweJSON = "";
        this.bonyJSON = "";
        this.userPetyJSON = "";
        this.activePet = "";
    }

    public WWWUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.inventoryJSON = document.getString("inventoryJSON");
        this.armorJSON = document.getString("armorJSON");
        this.enderchestJSON = document.getString("enderchestJSON");
        this.akcesoriaPodstawoweJSON = document.getString("akcesoriaPodstawoweJSON");
        this.akcesoriaDodatkoweJSON = document.getString("akcesoriaDodatkoweJSON");
        this.bonyJSON = document.getString("bonyJSON");
        this.userPetyJSON = document.getString("userPetyJSON");
        this.activePet = document.getString("activePet");
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("inventoryJSON", this.inventoryJSON)
                .append("armorJSON", this.armorJSON)
                .append("enderchestJSON", this.enderchestJSON)
                .append("akcesoriaPodstawoweJSON", this.akcesoriaPodstawoweJSON)
                .append("akcesoriaDodatkoweJSON", this.akcesoriaDodatkoweJSON)
                .append("bonyJSON", this.bonyJSON)
                .append("userPetyJSON", this.userPetyJSON)
                .append("activePet", this.activePet);
    }
}
