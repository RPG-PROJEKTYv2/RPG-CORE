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
    private int mission, progress;
    private double srDef, kryt, blok;
    private final List<Integer> clickedArmorStands;

    public RybakUser(final UUID uuid) {
        this.uuid = uuid;
        this.mission = 0;
        this.progress = 0;
        this.srDef = 0;
        this.kryt = 0;
        this.blok = 0;
        this.clickedArmorStands = new ArrayList<>();
    }

    public RybakUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.mission = document.getInteger("mission");
        this.progress = document.getInteger("progress");
        this.srDef = document.getDouble("srDef");
        this.kryt = document.getDouble("kryt");
        this.blok = document.getDouble("blok");
        this.clickedArmorStands = document.getList("clickedArmorStands", Integer.class);
    }

    public void save() {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataRybak(this.uuid, this));
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("mission", this.mission)
                .append("progress", this.progress)
                .append("srDef", this.srDef)
                .append("kryt", this.kryt)
                .append("blok", this.blok)
                .append("clickedArmorStands", this.clickedArmorStands);
    }
}
