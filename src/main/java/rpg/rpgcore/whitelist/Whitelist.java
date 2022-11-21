package rpg.rpgcore.whitelist;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Whitelist {
    private final String id = "whitelist";
    private final List<UUID> whitelist;

    public Whitelist(final List<UUID> whitelist) {
        this.whitelist = whitelist;
    }

    public Document toDocument() {
        return new Document("_id", this.id).append("users", this.whitelist);
    }
}
