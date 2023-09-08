package rpg.rpgcore.npc.rybak.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class StaruszekUser implements Cloneable {
    private final UUID uuid;
    private int mission, progress, paliwo;
    private boolean done, receivedRod;
    private double srDef;


    public StaruszekUser(final UUID uuid) {
        this.uuid = uuid;
        this.mission = 1;
        this.progress = 0;
        this.paliwo = 0;
        this.srDef = 0;
        this.done = false;
        this.receivedRod = false;
    }

    public StaruszekUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.mission = document.getInteger("mission");
        this.progress = document.getInteger("progress");
        this.paliwo = document.getInteger("paliwo");
        this.done = document.getBoolean("done");
        this.receivedRod = (document.containsKey("receivedRod") ? document.getBoolean("receivedRod") : false);
        this.srDef = (document.containsKey("srDef") ? document.getDouble("srDef") : 0);
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("mission", this.mission)
                .append("progress", this.progress)
                .append("paliwo", this.paliwo)
                .append("done", this.done)
                .append("receivedRod", this.receivedRod)
                .append("srDef", this.srDef);
    }


    @Override
    public StaruszekUser clone() {
        try {
            return (StaruszekUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
