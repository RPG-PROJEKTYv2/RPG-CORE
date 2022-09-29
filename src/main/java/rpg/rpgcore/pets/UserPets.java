package rpg.rpgcore.pets;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
public class UserPets {
    private final UUID uuid;
    private Inventory petyGui;
    private String inventoryName;

    public UserPets(UUID uuid, Inventory petyGui) {
        this.uuid = uuid;
        this.petyGui = petyGui;
        this.inventoryName = petyGui.getTitle().replaceAll("§", "&");
    }

    public UserPets(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        try {
            this.petyGui = Utils.fromBase64(document.getString("petyGui"), document.getString("inventoryName"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("petyGui", Utils.toBase64(petyGui))
                .append("inventoryName", inventoryName);
    }
}
