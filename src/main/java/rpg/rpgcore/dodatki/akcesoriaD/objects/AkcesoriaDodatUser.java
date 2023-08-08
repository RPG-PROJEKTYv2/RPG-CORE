package rpg.rpgcore.dodatki.akcesoriaD.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

@Getter
@Setter
public class AkcesoriaDodatUser {
    private final UUID uuid;
    private ItemStack szarfa, pas, medalion, energia;

    public AkcesoriaDodatUser(final UUID uuid) {
        this.uuid = uuid;
        this.szarfa = new ItemStack(Material.AIR);
        this.pas = new ItemStack(Material.AIR);
        this.medalion = new ItemStack(Material.AIR);
        this.energia = new ItemStack(Material.AIR);
    }

    public AkcesoriaDodatUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id").replace("-akceD", ""));
        this.szarfa = Utils.deserializeItem(document.getString("szarfa"));
        this.pas = Utils.deserializeItem(document.getString("pas"));
        this.medalion = Utils.deserializeItem(document.getString("medalion"));
        this.energia = Utils.deserializeItem(document.getString("energia"));
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString() + "-akceD")
                .append("szarfa", Utils.serializeItem(this.szarfa))
                .append("pas", Utils.serializeItem(this.pas))
                .append("medalion", Utils.serializeItem(this.medalion))
                .append("energia", Utils.serializeItem(this.energia));
    }
}
