package rpg.rpgcore.npc.medrzec.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

@Getter
@Setter
public class MedrzecUser {
    private final UUID uuid;
    private int bonus;

    public MedrzecUser(final UUID uuid) {
        this.uuid = uuid;
        this.bonus = 0;
    }

    public MedrzecUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.bonus = document.getInteger("bonus");
    }

    public void save() {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMedrzec(this.uuid, this));
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("bonus", this.bonus);
    }
}
