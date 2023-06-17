package rpg.rpgcore.server;

import org.bson.Document;

public class ServerUser {
    private final String name;
    private final Server server;

    public ServerUser(String name) {
        this.name = name;
        this.server = new Server(0, 0L, false);
    }

    public ServerUser(Document document) {
        this.name = document.getString("_id");
        long czas;
        try {
            czas = document.getLong("czas");
        } catch (ClassCastException e) {
            czas = (long) document.getInteger("czas");
        }
        this.server = new Server(document.getInteger("dodatkowyExp"), czas, document.getBoolean("aktywny"));
    }

    public String getName() {
        return name;
    }

    public Server getServer() {
        return server;
    }

    public Document toDocument() {
        return new Document("_id", this.name).append("dodatkowyExp", this.getServer().getDodatkowyExp()).append("czas", this.getServer().getCzas()).append("aktywny", this.getServer().isAktywny());
    }
}
