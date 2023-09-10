package rpg.rpgcore.npc.mrozny_stroz.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class MroznyStrozUser implements Cloneable{
    private final UUID uuid;
    private int mission, progress;


    public MroznyStrozUser(UUID uuid) {
        this.uuid = uuid;
        this.mission = 1;
        this.progress = 0;
    }

    public MroznyStrozUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.mission = document.getInteger("mission");
        this.progress = document.getInteger("progress");
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("mission", mission)
                .append("progress", progress);
    }

    @Override
    public MroznyStrozUser clone() {
        try {
            return (MroznyStrozUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
