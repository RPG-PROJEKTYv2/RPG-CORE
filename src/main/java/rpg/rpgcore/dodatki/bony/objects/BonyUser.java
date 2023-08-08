package rpg.rpgcore.dodatki.bony.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

@Getter
@Setter
public class BonyUser {
    private final UUID uuid;
    private ItemStack dmg5, dmg10, dmg15, def5, def10, def15, kryt5, kryt10, kryt15, wzmkryt10, blok20, przeszywka20, predkosc25, predkosc50, hp10, hp20, hp35, dmgMetiny;

    public BonyUser(final UUID uuid) {
        this.uuid = uuid;
        this.dmg5 = new ItemStack(Material.AIR);
        this.dmg10 = new ItemStack(Material.AIR);
        this.dmg15 = new ItemStack(Material.AIR);
        this.def5 = new ItemStack(Material.AIR);
        this.def10 = new ItemStack(Material.AIR);
        this.def15 = new ItemStack(Material.AIR);
        this.kryt5 = new ItemStack(Material.AIR);
        this.kryt10 = new ItemStack(Material.AIR);
        this.kryt15 = new ItemStack(Material.AIR);
        this.wzmkryt10 = new ItemStack(Material.AIR);
        this.blok20 = new ItemStack(Material.AIR);
        this.przeszywka20 = new ItemStack(Material.AIR);
        this.predkosc25 = new ItemStack(Material.AIR);
        this.predkosc50 = new ItemStack(Material.AIR);
        this.hp10 = new ItemStack(Material.AIR);
        this.hp20 = new ItemStack(Material.AIR);
        this.hp35 = new ItemStack(Material.AIR);
        this.dmgMetiny = new ItemStack(Material.AIR);
    }

    public BonyUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id").replace("-bony", ""));
        this.dmg5 = Utils.deserializeItem(document.getString("dmg5"));
        this.dmg10 = Utils.deserializeItem(document.getString("dmg10"));
        this.dmg15 = Utils.deserializeItem(document.getString("dmg15"));
        this.def5 = Utils.deserializeItem(document.getString("def5"));
        this.def10 = Utils.deserializeItem(document.getString("def10"));
        this.def15 = Utils.deserializeItem(document.getString("def15"));
        this.kryt5 = Utils.deserializeItem(document.getString("kryt5"));
        this.kryt10 = Utils.deserializeItem(document.getString("kryt10"));
        this.kryt15 = Utils.deserializeItem(document.getString("kryt15"));
        this.wzmkryt10 = Utils.deserializeItem(document.getString("wzmkryt10"));
        this.blok20 = Utils.deserializeItem(document.getString("blok20"));
        this.przeszywka20 = Utils.deserializeItem(document.getString("przeszywka20"));
        this.predkosc25 = Utils.deserializeItem(document.getString("predkosc25"));
        this.predkosc50 = Utils.deserializeItem(document.getString("predkosc50"));
        this.hp10 = Utils.deserializeItem(document.getString("hp10"));
        this.hp20 = Utils.deserializeItem(document.getString("hp20"));
        this.hp35 = Utils.deserializeItem(document.getString("hp35"));
        this.dmgMetiny = Utils.deserializeItem(document.getString("dmgMetiny"));
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString() + "-bony")
                .append("dmg5", Utils.serializeItem(this.dmg5))
                .append("dmg10", Utils.serializeItem(this.dmg10))
                .append("dmg15", Utils.serializeItem(this.dmg15))
                .append("def5", Utils.serializeItem(this.def5))
                .append("def10", Utils.serializeItem(this.def10))
                .append("def15", Utils.serializeItem(this.def15))
                .append("kryt5", Utils.serializeItem(this.kryt5))
                .append("kryt10", Utils.serializeItem(this.kryt10))
                .append("kryt15", Utils.serializeItem(this.kryt15))
                .append("wzmkryt10", Utils.serializeItem(this.wzmkryt10))
                .append("blok20", Utils.serializeItem(this.blok20))
                .append("przeszywka20", Utils.serializeItem(this.przeszywka20))
                .append("predkosc25", Utils.serializeItem(this.predkosc25))
                .append("predkosc50", Utils.serializeItem(this.predkosc50))
                .append("hp10", Utils.serializeItem(this.hp10))
                .append("hp20", Utils.serializeItem(this.hp20))
                .append("hp35", Utils.serializeItem(this.hp35))
                .append("dmgMetiny", Utils.serializeItem(this.dmgMetiny));
    }
}
