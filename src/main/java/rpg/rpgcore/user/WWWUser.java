package rpg.rpgcore.user;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class WWWUser implements Cloneable {
    private final UUID uuid;
    private String inventoryJSON;
    private String armorJSON;
    private String enderchestJSON;
    private String akcesoriaPodstawoweJSON;
    private String akcesoriaDodatkoweJSON;
    private String bonyJSON;
    private String userPetyJSON;
    private String activePet;
    private String magazyn1JSON, magazyn2JSON, magazyn3JSON, magazyn4JSON, magazyn5JSON;

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
        this.magazyn1JSON = "";
        this.magazyn2JSON = "";
        this.magazyn3JSON = "";
        this.magazyn4JSON = "";
        this.magazyn5JSON = "";
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
        this.magazyn1JSON = document.get("magazyny", Document.class).getString("magazyn1JSON");
        this.magazyn2JSON = document.get("magazyny", Document.class).getString("magazyn2JSON");
        this.magazyn3JSON = document.get("magazyny", Document.class).getString("magazyn3JSON");
        this.magazyn4JSON = document.get("magazyny", Document.class).getString("magazyn4JSON");
        this.magazyn5JSON = document.get("magazyny", Document.class).getString("magazyn5JSON");
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
                .append("activePet", this.activePet)
                .append("magazyny", new Document("_id", "magazyny")
                        .append("magazyn1JSON", this.magazyn1JSON)
                        .append("magazyn2JSON", this.magazyn2JSON)
                        .append("magazyn3JSON", this.magazyn3JSON)
                        .append("magazyn4JSON", this.magazyn4JSON)
                        .append("magazyn5JSON", this.magazyn5JSON)
                );
    }

    @Override
    public WWWUser clone() {
        try {
            return (WWWUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
