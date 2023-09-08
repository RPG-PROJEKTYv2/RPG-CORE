package rpg.rpgcore.dodatki.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rpg.rpgcore.dodatki.akcesoriaD.objects.AkcesoriaDodatUser;
import rpg.rpgcore.dodatki.akcesoriaP.objects.AkcesoriaPodstUser;
import rpg.rpgcore.dodatki.bony.objects.BonyUser;

import java.util.UUID;

@Getter
@Setter
public class DodatkiUser implements Cloneable {
    private final UUID uuid;
    private AkcesoriaPodstUser akcesoriaPodstawowe;
    private AkcesoriaDodatUser akcesoriaDodatkowe;
    private BonyUser bony;

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

    @Override
    public DodatkiUser clone() {
        try {
            final DodatkiUser clone = (DodatkiUser) super.clone();
            clone.akcesoriaPodstawowe = this.akcesoriaPodstawowe.clone();
            clone.akcesoriaDodatkowe = this.akcesoriaDodatkowe.clone();
            clone.bony = this.bony.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
