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
    private int openChestMission;
    private int openChestMissionProgress;

    public WyslannikUser(final UUID uuid) {
        this.uuid = uuid;
        this.killMobsMission = 1;
        this.killMobsMissionProgress = 0;
        this.killBossMission = 1;
        this.killBossMissionProgress = 0;
        this.openChestMission = 1;
        this.openChestMissionProgress = 0;
    }

    public WyslannikUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.killMobsMission = document.getInteger("killMobsMission");
        this.killMobsMissionProgress = document.getInteger("killMobsMissionProgress");
        this.killBossMission = document.getInteger("killBossMission");
        this.killBossMissionProgress = document.getInteger("killBossMissionProgress");
        this.openChestMission = document.getInteger("openChestMission");
        this.openChestMissionProgress = document.getInteger("openChestMissionProgress");
    }


    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("killMobsMission", this.killMobsMission)
                .append("killMobsMissionProgress", this.killMobsMissionProgress)
                .append("killBossMission", this.killBossMission)
                .append("killBossMissionProgress", this.killBossMissionProgress)
                .append("openChestMission", this.openChestMission)
                .append("openChestMissionProgress", this.openChestMissionProgress);
    }
}
