package rpg.rpgcore.wyszkolenie.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

@Getter
@Setter
public class WyszkolenieUser {
    private final UUID uuid;
    private int punkty, totalPoints, srDmg, srDef, kryt, szczescie, blok, hp;
    private long resetCooldown;
    private final DrzewkoWyszkoleniaUser drzewkoWyszkoleniaUser;

    public WyszkolenieUser(final UUID uuid) {
        this.uuid = uuid;
        this.punkty = 0;
        this.totalPoints = 0;
        this.srDmg = 0;
        this.srDef = 0;
        this.kryt = 0;
        this.szczescie = 0;
        this.blok = 0;
        this.hp = 0;
        this.drzewkoWyszkoleniaUser = new DrzewkoWyszkoleniaUser();
    }

    public WyszkolenieUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.punkty = document.getInteger("punkty");
        this.totalPoints = document.getInteger("totalPoints");
        this.srDmg = document.getInteger("srDmg");
        this.srDef = document.getInteger("srDef");
        this.kryt = document.getInteger("kryt");
        this.szczescie = document.getInteger("szczescie");
        this.blok = document.getInteger("blok");
        this.hp = document.getInteger("hp");
        this.drzewkoWyszkoleniaUser = new DrzewkoWyszkoleniaUser(document.get("drzewkoWyszkolenia", Document.class));
    }

    public void save() {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataWyszkolenie(this.uuid, this));
    }

    public void giveCooldown() {
        this.resetCooldown = System.currentTimeMillis() + 3_600_000;
    }

    public boolean hasCooldown() {
        return this.resetCooldown >= System.currentTimeMillis();
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("punkty", this.punkty)
                .append("totalPoints", this.totalPoints)
                .append("srDmg", this.srDmg)
                .append("srDef", this.srDef)
                .append("kryt", this.kryt)
                .append("szczescie", this.szczescie)
                .append("blok", this.blok)
                .append("hp", this.hp)
                .append("drzewkoWyszkolenia", this.drzewkoWyszkoleniaUser.toDocument());
    }

}
