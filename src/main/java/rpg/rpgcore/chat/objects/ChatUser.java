package rpg.rpgcore.chat.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ChatUser implements Cloneable {
    private UUID uuid;
    private boolean pingsEnabled, chestDropEnabled, niesDropEnabled, itemDropEnabled, msgEnabled, joinMessageEnabled, quitMessageEnabled, dmgHologramsVisable, databaseMessageEnabled,
    boss1_10;
    private List<UUID> ignoredPlayers;

    public ChatUser(UUID uuid) {
        this.uuid = uuid;
        this.pingsEnabled = true;
        this.chestDropEnabled = true;
        this.niesDropEnabled = true;
        this.itemDropEnabled = true;
        this.msgEnabled = true;
        this.ignoredPlayers = new ArrayList<>();
        this.joinMessageEnabled = true;
        this.quitMessageEnabled = true;
        this.dmgHologramsVisable = true;
        this.databaseMessageEnabled = true;
        this.boss1_10 = true;
    }

    public ChatUser(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.pingsEnabled = document.getBoolean("pingsEnabled");
        this.chestDropEnabled = document.getBoolean("chestDropEnabled");
        this.niesDropEnabled = document.getBoolean("niesDropEnabled");
        this.itemDropEnabled = document.getBoolean("itemDropEnabled");
        this.msgEnabled = document.getBoolean("msgEnabled");
        this.ignoredPlayers = document.getList("ignoredPlayers", UUID.class);
        this.joinMessageEnabled = document.getBoolean("joinMessageEnabled");
        this.quitMessageEnabled = document.getBoolean("quitMessageEnabled");
        this.dmgHologramsVisable = document.getBoolean("dmgHologramsVisable");
        this.databaseMessageEnabled = (document.containsKey("databaseMessageEnabled") ? document.getBoolean("databaseMessageEnabled") : true);
        this.boss1_10 = (document.containsKey("boss1_10") ? document.getBoolean("boss1_10") : true);
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("pingsEnabled", pingsEnabled)
                .append("chestDropEnabled", chestDropEnabled)
                .append("niesDropEnabled", niesDropEnabled)
                .append("itemDropEnabled", itemDropEnabled)
                .append("msgEnabled", msgEnabled)
                .append("ignoredPlayers", ignoredPlayers)
                .append("joinMessageEnabled", joinMessageEnabled)
                .append("quitMessageEnabled", quitMessageEnabled)
                .append("dmgHologramsVisable", dmgHologramsVisable)
                .append("databaseMessageEnabled", databaseMessageEnabled)
                .append("boss1_10", boss1_10);
    }

    @Override
    public ChatUser clone() {
        try {
            return (ChatUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
