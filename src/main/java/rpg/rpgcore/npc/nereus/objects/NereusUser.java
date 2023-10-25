package rpg.rpgcore.npc.nereus.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

@Getter
@Setter
public class NereusUser {
    private final UUID uuid;
    private boolean dialog;
    private ItemStack potegi, wiecznosci, starozytnosci, przodkow;


    public NereusUser(final UUID uuid) {
        this.uuid = uuid;
        this.dialog = false;
        this.potegi = new ItemStack(Material.AIR);
        this.wiecznosci = new ItemStack(Material.AIR);
        this.starozytnosci = new ItemStack(Material.AIR);
        this.przodkow = new ItemStack(Material.AIR);
    }

    public NereusUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.dialog = document.getBoolean("dialog");
        this.potegi = Utils.deserializeItem(document.getString("potegi"));
        this.wiecznosci = Utils.deserializeItem(document.getString("wiecznosci"));
        this.starozytnosci = Utils.deserializeItem(document.getString("starozytnosci"));
        this.przodkow = Utils.deserializeItem(document.getString("przodkow"));
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("dialog", this.dialog)
                .append("potegi", Utils.serializeItem(this.potegi))
                .append("wiecznosci", Utils.serializeItem(this.wiecznosci))
                .append("starozytnosci", Utils.serializeItem(this.starozytnosci))
                .append("przodkow", Utils.serializeItem(this.przodkow));
    }
}
