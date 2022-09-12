package rpg.rpgcore.chat;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class ChatUser {
    private UUID uuid;
    private boolean pingsEnabled, chestDropEnabled, niesDropEnabled, itemDropEnabled;

    public ChatUser(UUID uuid) {
        this.uuid = uuid;
        this.pingsEnabled = true;
        this.chestDropEnabled = true;
        this.niesDropEnabled = true;
        this.itemDropEnabled = true;
    }

    public ChatUser(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.pingsEnabled = document.getBoolean("pingsEnabled");
        this.chestDropEnabled = document.getBoolean("chestDropEnabled");
        this.niesDropEnabled = document.getBoolean("niesDropEnabled");
        this.itemDropEnabled = document.getBoolean("itemDropEnabled");
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("pingsEnabled", pingsEnabled)
                .append("chestDropEnabled", chestDropEnabled)
                .append("niesDropEnabled", niesDropEnabled)
                .append("itemDropEnabled", itemDropEnabled);
    }
}
