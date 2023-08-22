package rpg.rpgcore.npc.wyslannik.objects;

import lombok.Getter;
import lombok.Setter;

import org.bson.Document;
import java.util.UUID;

@Getter
@Setter
public class WyslannikUser {
    private final UUID uuid;
    private int killMobsMission;
    private int killMobsMissionProgress;
    private int killBossMission;
    private int killBossMissionProgress;
    private int sredniDMG;
    private int sredniDEF;

    public WyslannikUser(final UUID uuid) {
        this.uuid = uuid;
        this.killMobsMission = 1;
        this.killMobsMissionProgress = 0;
        this.killBossMission = 1;
        this.killBossMissionProgress = 0;
        this.sredniDMG = 0;
        this.sredniDEF = 0;
    }
    public UUID getUuid() {return uuid;}

    public WyslannikUser(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.killMobsMission = document.getInteger("killMobsMission");
        this.killMobsMissionProgress = document.getInteger("killMobsMissionProgress");
        this.killBossMission = document.getInteger("killBossMission");
        this.killBossMissionProgress = document.getInteger("killBossMissionProgress");
        this.sredniDMG = document.getInteger("sredniDMG");
        this.sredniDEF = document.getInteger("sredniDEF");
    }
    public org.bson.Document toDocument() {
        return new org.bson.Document("_id", this.uuid.toString())
                .append("killMobsMission", this.getKillMobsMission())
                .append("killMobsMissionProgress", this.getKillMobsMissionProgress())
                .append("killBossMission", this.getKillBossMission())
                .append("killBossMissionProgress", this.getKillBossMissionProgress())
                .append("sredniDMG", this.getSredniDMG())
                .append("sredniDEF", this.getSredniDEF());
    }
}
