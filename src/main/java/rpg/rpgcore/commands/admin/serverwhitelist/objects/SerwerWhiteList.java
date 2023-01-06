package rpg.rpgcore.commands.admin.serverwhitelist.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SerwerWhiteList {
    private boolean enabled;
    private List<UUID> whitelisted;

    public SerwerWhiteList() {
        this.enabled = false;
        this.whitelisted = new ArrayList<>();
    }

    public SerwerWhiteList(final Document document) {
        this.enabled = document.getBoolean("enabled");
        final List<String> stringUUID = document.getList("whitelisted", String.class);
        final List<UUID> uuidList = new ArrayList<>();
        for (final String s : stringUUID) {
            uuidList.add(UUID.fromString(s));
        }
        this.whitelisted = uuidList;
    }

    public Document toDocument() {
        final List<String> stringUUID = new ArrayList<>();
        for (final UUID uuid : this.whitelisted) {
            stringUUID.add(uuid.toString());
        }
        return new Document("_id", "SerwerWhiteList").append("enabled", this.enabled).append("whitelisted", stringUUID);
    }
}
