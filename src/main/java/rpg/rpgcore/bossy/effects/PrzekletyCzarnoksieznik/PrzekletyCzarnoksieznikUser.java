package rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik;


import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class PrzekletyCzarnoksieznikUser implements Cloneable {
    private final UUID uuid;
    private int defMOB;
    private int dmgMOB;

    public PrzekletyCzarnoksieznikUser(final UUID uuid) {
        this.uuid = uuid;
        this.defMOB = 0;
        this.dmgMOB = 0;
    }
    public UUID getUuid() {
        return uuid;
    }

    public PrzekletyCzarnoksieznikUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.defMOB = document.getInteger("defMOB");
        this.dmgMOB = document.getInteger("dmgMOB");
    }
    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("defMOB", defMOB)
                .append("dmgMOB", dmgMOB);
    }

    @Override
    public PrzekletyCzarnoksieznikUser clone() {
        try {
            return (PrzekletyCzarnoksieznikUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
