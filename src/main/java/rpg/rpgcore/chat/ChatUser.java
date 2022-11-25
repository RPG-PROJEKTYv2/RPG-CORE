package rpg.rpgcore.chat;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ChatUser {
    private UUID uuid;
    private boolean pingsEnabled, chestDropEnabled, niesDropEnabled, itemDropEnabled, msgEnabled;
    private List<UUID> ignoredPlayers;

    public ChatUser(UUID uuid) {
        this.uuid = uuid;
        this.pingsEnabled = true;
        this.chestDropEnabled = true;
        this.niesDropEnabled = true;
        this.itemDropEnabled = true;
        this.msgEnabled = true;
        this.ignoredPlayers = new ArrayList<>();
    }

    public ChatUser(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.pingsEnabled = document.getBoolean("pingsEnabled");
        this.chestDropEnabled = document.getBoolean("chestDropEnabled");
        this.niesDropEnabled = document.getBoolean("niesDropEnabled");
        this.itemDropEnabled = document.getBoolean("itemDropEnabled");
        this.msgEnabled = document.getBoolean("msgEnabled");
        this.ignoredPlayers = document.getList("ignoredPlayers", UUID.class);
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("pingsEnabled", pingsEnabled)
                .append("chestDropEnabled", chestDropEnabled)
                .append("niesDropEnabled", niesDropEnabled)
                .append("itemDropEnabled", itemDropEnabled)
                .append("msgEnabled", msgEnabled)
                .append("ignoredPlayers", ignoredPlayers);
    }
}
