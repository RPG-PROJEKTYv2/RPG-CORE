package rpg.rpgcore.npc.rybak.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class MlodszyRybakUser implements Cloneable{
    private final UUID uuid;
    private int mission, progress;
    private boolean done, receivedRod;
    private int dodatkowyDmg;
    private double przeszywka, srDef;


    public MlodszyRybakUser(final UUID uuid) {
        this.uuid = uuid;
        this.mission = 1;
        this.progress = 0;
        this.done = false;
        this.receivedRod = false;
        this.dodatkowyDmg = 0;
        this.przeszywka = 0.0;
        this.srDef = 0.0;
    }

    public MlodszyRybakUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.mission = document.getInteger("mission");
        this.progress = document.getInteger("progress");
        this.done = document.getBoolean("done");
        this.receivedRod = document.getBoolean("receivedRod");
        this.dodatkowyDmg = document.getInteger("dodatkowyDmg");
        this.przeszywka = document.getDouble("przeszywka");
        this.srDef = document.getDouble("srDef");
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("mission", this.mission)
                .append("progress", this.progress)
                .append("done", this.done)
                .append("receivedRod", this.receivedRod)
                .append("dodatkowyDmg", this.dodatkowyDmg)
                .append("przeszywka", this.przeszywka)
                .append("srDef", this.srDef);
    }

    @Override
    public MlodszyRybakUser clone() {
        try {
            return (MlodszyRybakUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
