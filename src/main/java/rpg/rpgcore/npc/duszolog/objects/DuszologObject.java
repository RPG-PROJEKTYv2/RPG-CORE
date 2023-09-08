package rpg.rpgcore.npc.duszolog.objects;

import org.bson.Document;

import java.util.UUID;

public class DuszologObject implements Cloneable {
    private final UUID uuid;
    private DuszologUser duszologUser;

    public DuszologObject(final UUID uuid) {
        this.uuid = uuid;
        this.duszologUser = new DuszologUser(0,0, 0, 0);
    }

    public DuszologObject(Document document) {
        this.uuid = UUID.fromString((String) document.get("_id"));
        this.duszologUser = new DuszologUser(document.getInteger("mission"), document.getInteger("progress"), document.getDouble("value1"), document.getDouble("value2"));
    }

    public UUID getID() {
        return uuid;
    }

    public DuszologUser getDuszologUser() {
        return this.duszologUser;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString()).append("mission", this.getDuszologUser().getMission()).append("progress", this.getDuszologUser().getProgress()).append("value1", this.getDuszologUser().getValue1()).append("value2", this.getDuszologUser().getValue2());
    }

    @Override
    public DuszologObject clone() {
        try {
            final DuszologObject duszologObject = (DuszologObject) super.clone();
            duszologObject.duszologUser = this.duszologUser.clone();
            return duszologObject;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
