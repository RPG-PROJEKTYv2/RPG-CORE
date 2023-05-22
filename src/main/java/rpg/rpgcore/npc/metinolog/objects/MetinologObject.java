package rpg.rpgcore.npc.metinolog.objects;

import org.bson.Document;

import java.util.UUID;

public class MetinologObject {
    private final UUID uuid;
    private final MetinologUser metinologObject;

    public MetinologObject(final UUID uuid) {
        this.uuid = uuid;
        this.metinologObject = new MetinologUser(0,0, 0, 0,0,0,0);
    }

    public MetinologObject(Document document) {
        this.uuid = UUID.fromString((String) document.get("_id"));
        this.metinologObject = new MetinologUser(document.getInteger("postepKill"), document.getInteger("postepMisjiKill"), document.getInteger("postepGive"), document.getInteger("postepMisjiGive"), document.getDouble("przeszywka"), document.getDouble("srOdpo"), document.getInteger("dodatkowedmg"));
    }

    public UUID getID() {
        return uuid;
    }

    public MetinologUser getMetinologUser() {
        return this.metinologObject;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("postepKill", this.getMetinologUser().getPostepKill())
                .append("postepMisjiKill", this.getMetinologUser().getPostepMisjiKill())
                .append("postepGive", this.getMetinologUser().getPostepGive())
                .append("postepMisjiGive", this.getMetinologUser().getPostepMisjiGive())
                .append("przeszywka", this.getMetinologUser().getPrzeszycie())
                .append("srOdpo", this.getMetinologUser().getSrOdpo())
                .append("dodatkowedmg", this.getMetinologUser().getDodatkowedmg());
    }
}
