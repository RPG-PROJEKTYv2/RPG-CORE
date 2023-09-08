package rpg.rpgcore.dodatki.akcesoriaP.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

@Getter
@Setter
public class AkcesoriaPodstUser implements Cloneable {
    private final UUID uuid;
    private ItemStack tarcza, naszyjnik, kolczyki, pierscien, diadem;

    public AkcesoriaPodstUser(final UUID uuid) {
        this.uuid = uuid;
        this.tarcza = new ItemStack(Material.AIR);
        this.naszyjnik = new ItemStack(Material.AIR);
        this.kolczyki = new ItemStack(Material.AIR);
        this.pierscien = new ItemStack(Material.AIR);
        this.diadem = new ItemStack(Material.AIR);
    }

    public AkcesoriaPodstUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id").replace("-akceP", ""));
        this.tarcza = Utils.deserializeItem(document.getString("tarcza"));
        this.naszyjnik = Utils.deserializeItem(document.getString("naszyjnik"));
        this.kolczyki = Utils.deserializeItem(document.getString("kolczyki"));
        this.pierscien = Utils.deserializeItem(document.getString("pierscien"));
        this.diadem = Utils.deserializeItem(document.getString("diadem"));
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString() + "-akceP")
                .append("tarcza", Utils.serializeItem(this.tarcza))
                .append("naszyjnik", Utils.serializeItem(this.naszyjnik))
                .append("kolczyki", Utils.serializeItem(this.kolczyki))
                .append("pierscien", Utils.serializeItem(this.pierscien))
                .append("diadem", Utils.serializeItem(this.diadem));
    }

    @Override
    public AkcesoriaPodstUser clone() {
        try {
            return (AkcesoriaPodstUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
