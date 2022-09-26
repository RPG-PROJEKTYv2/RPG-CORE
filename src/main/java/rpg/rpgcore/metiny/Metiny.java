package rpg.rpgcore.metiny;

import org.bson.Document;

public class Metiny {
    private final int id;
    private final MetinsObject metins;

    public Metiny(int id) {
        this.id = id;
        this.metins = new MetinsObject("", "", 0, 0, 0, 0);
    }

    public Metiny(Document document) {
        this.id = document.getInteger("_id");
        this.metins = new MetinsObject(document.getString("world"), document.getString("coordinates"), document.getInteger("maxhealth"), document.getInteger("health"), document.getInteger("resp"), document.getInteger("moby"));
    }

    public int getId() {
        return id;
    }

    public MetinsObject getMetins() {
        return this.metins;
    }

    public Document toDocument() {
        Document document = new Document("_id", this.id).append("world", this.getMetins().getWorld()).append("coordinates", this.getMetins().getCoordinates()).append("maxhealth", this.getMetins().getMaxhealth()).append("health", this.getMetins().getHealth()).append("resp", this.getMetins().getResp()).append("moby", this.getMetins().getMoby());
        return document;
    }
}
