package rpg.rpgcore.npc.magazynier.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class MagazynierUser {
    private final UUID uuid;
    private int points;
    private String magazyn1, magazyn2, magazyn3, magazyn4, magazyn5;
    private boolean unlocked1, unlocked2, unlocked3, unlocked4, unlocked5, remoteCommand;
    private MagazynierMissions missions;
    private long resetTime;

    public MagazynierUser(final UUID uuid) {
        this.uuid = uuid;
        this.points = 0;
        this.magazyn1 = "";
        this.magazyn2 = "";
        this.magazyn3 = "";
        this.magazyn4 = "";
        this.magazyn5 = "";
        this.unlocked1 = true;
        this.unlocked2 = false;
        this.unlocked3 = false;
        this.unlocked4 = false;
        this.unlocked5 = false;
        this.remoteCommand = false;
        this.missions = new MagazynierMissions(0, 0, 0, 0, 0, 0, 0.0,
                false, false, false, false, false);
        this.resetTime = System.currentTimeMillis() + 86400000L;
    }

    public MagazynierUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.points = document.getInteger("points");
        this.magazyn1 = document.getString("magazyn1");
        this.magazyn2 = document.getString("magazyn2");
        this.magazyn3 = document.getString("magazyn3");
        this.magazyn4 = document.getString("magazyn4");
        this.magazyn5 = document.getString("magazyn5");
        this.unlocked1 = document.getBoolean("unlocked1");
        this.unlocked2 = document.getBoolean("unlocked2");
        this.unlocked3 = document.getBoolean("unlocked3");
        this.unlocked4 = document.getBoolean("unlocked4");
        this.unlocked5 = document.getBoolean("unlocked5");
        this.remoteCommand = document.getBoolean("remoteCommand");
        this.missions = new MagazynierMissions(document.get("missions", Document.class));
        this.resetTime = document.getLong("resetTime");
    }

    public int getUnlocked() {
        int unlocked = 0;
        if (this.unlocked1) unlocked++;
        if (this.unlocked2) unlocked++;
        if (this.unlocked3) unlocked++;
        if (this.unlocked4) unlocked++;
        if (this.unlocked5) unlocked++;
        return unlocked;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("points", this.points)
                .append("magazyn1", this.magazyn1)
                .append("magazyn2", this.magazyn2)
                .append("magazyn3", this.magazyn3)
                .append("magazyn4", this.magazyn4)
                .append("magazyn5", this.magazyn5)
                .append("unlocked1", this.unlocked1)
                .append("unlocked2", this.unlocked2)
                .append("unlocked3", this.unlocked3)
                .append("unlocked4", this.unlocked4)
                .append("unlocked5", this.unlocked5)
                .append("remoteCommand", this.remoteCommand)
                .append("missions", this.missions.toDocument())
                .append("resetTime", this.resetTime);
    }

}
