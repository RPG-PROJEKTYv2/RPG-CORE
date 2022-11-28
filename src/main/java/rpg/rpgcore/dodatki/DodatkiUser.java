package rpg.rpgcore.dodatki;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rpg.rpgcore.dodatki.akcesoriaD.objects.AkcesoriaDodatUser;
import rpg.rpgcore.dodatki.akcesoriaP.objects.AkcesoriaPodstUser;
import rpg.rpgcore.dodatki.bony.objects.BonyUser;

import java.util.UUID;

@Getter
@Setter
public class DodatkiUser {
    private final UUID uuid;
    private final AkcesoriaPodstUser akcesoriaPodstawowe;
    private final AkcesoriaDodatUser akcesoriaDodatkowe;
    private final BonyUser bony;

    public DodatkiUser(final UUID uuid) {
        this.uuid = uuid;
        this.akcesoriaPodstawowe = new AkcesoriaPodstUser(uuid);
        this.akcesoriaDodatkowe = new AkcesoriaDodatUser(uuid);
        this.bony = new BonyUser(uuid);
    }

    public DodatkiUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.akcesoriaPodstawowe = new AkcesoriaPodstUser(document.get("akcesoriaPodstawowe", Document.class));
        this.akcesoriaDodatkowe = new AkcesoriaDodatUser(document.get("akcesoriaDodatkowe", Document.class));
        this.bony = new BonyUser(document.get("bony", Document.class));
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("akcesoriaPodstawowe", this.akcesoriaPodstawowe.toDocument())
                .append("akcesoriaDodatkowe", this.akcesoriaDodatkowe.toDocument())
                .append("bony", this.bony.toDocument());
    }
}
