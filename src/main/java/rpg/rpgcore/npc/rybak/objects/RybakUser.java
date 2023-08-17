package rpg.rpgcore.npc.rybak.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rpg.rpgcore.RPGCORE;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RybakUser {
    private final UUID uuid;
    private final StaruszekUser staruszekUser;
    private double podwojnyDrop;
    private int lvlWedki, expWedki, wylowioneRyby;
    private final List<Integer> clickedArmorStands;
    private boolean dialog;

    public RybakUser(final UUID uuid) {
        this.uuid = uuid;
        this.staruszekUser = new StaruszekUser(uuid);
        this.podwojnyDrop = 0;
        this.lvlWedki = 1;
        this.expWedki = 0;
        this.wylowioneRyby = 0;
        this.clickedArmorStands = new ArrayList<>();
        this.dialog = false;
    }

    public RybakUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        if (document.containsKey("staruszekUser")) this.staruszekUser = new StaruszekUser(document.get("staruszekUser", Document.class));
        else this.staruszekUser = new StaruszekUser(this.uuid);
        this.podwojnyDrop = (document.containsKey("podwojnyDrop") ? document.getDouble("podwojnyDrop") : 0);
        this.lvlWedki = (document.containsKey("lvlWedki") ? document.getInteger("lvlWedki") : 1);
        this.expWedki = (document.containsKey("expWedki") ? document.getInteger("expWedki") : 0);
        this.wylowioneRyby = (document.containsKey("wylowioneRyby") ? document.getInteger("wylowioneRyby") : 0);
        this.clickedArmorStands = document.getList("clickedArmorStands", Integer.class);
        this.dialog = (document.containsKey("dialog") ? document.getBoolean("dialog") : false);
    }

    public void save() {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(this.uuid, this));
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("staruszekUser", this.staruszekUser.toDocument())
                .append("podwojnyDrop", this.podwojnyDrop)
                .append("lvlWedki", this.lvlWedki)
                .append("expWedki", this.expWedki)
                .append("wylowioneRyby", this.wylowioneRyby)
                .append("clickedArmorStands", this.clickedArmorStands)
                .append("dialog", this.dialog);
    }
}
