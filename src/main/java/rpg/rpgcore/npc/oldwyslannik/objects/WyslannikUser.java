package rpg.rpgcore.npc.oldwyslannik.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

@Getter
@Setter
public class WyslannikUser {

    private int killMobsMission;
    private int killMobsMissionProgress;
    private int killBossMission;
    private int killBossMissionProgress;
    private int openChestMission;
    private int openChestMissionProgress;

    public WyslannikUser(final int killMobsMission, final int killMobsMissionProgress, final int killBossMission, final int killBossMissionProgress,
                         final int openChestMission, final int openChestMissionProgress) {
        this.killMobsMission = killMobsMission;
        this.killMobsMissionProgress = killMobsMissionProgress;
        this.killBossMission = killBossMission;
        this.killBossMissionProgress = killBossMissionProgress;
        this.openChestMission = openChestMission;
        this.openChestMissionProgress = openChestMissionProgress;
    }

    public WyslannikUser(final Document document) {
        this.killMobsMission = document.getInteger("killMobsMission");
        this.killMobsMissionProgress = document.getInteger("killMobsMissionProgress");
        this.killBossMission = document.getInteger("killBossMission");
        this.killBossMissionProgress = document.getInteger("killBossMissionProgress");
        this.openChestMission = document.getInteger("openChestMission");
        this.openChestMissionProgress = document.getInteger("openChestMissionProgress");
    }

}
