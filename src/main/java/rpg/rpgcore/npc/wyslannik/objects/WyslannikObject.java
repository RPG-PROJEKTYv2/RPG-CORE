package rpg.rpgcore.npc.wyslannik.objects;

import org.bson.Document;

import java.util.UUID;

public class WyslannikObject {

    private final UUID uuid;
    private final WyslannikUser wyslannikUser;

    public WyslannikObject(final UUID uuid) {
        this.uuid = uuid;
        this.wyslannikUser = new WyslannikUser(1, 0, 1, 0, 1, 0);
    }

    public WyslannikObject(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.wyslannikUser = new WyslannikUser(document);
    }

    public UUID getUuid() {
        return uuid;
    }

    public WyslannikUser getWyslannikUser() {
        return wyslannikUser;
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("killMobsMission", wyslannikUser.getKillMobsMission())
                .append("killMobsMissionProgress", wyslannikUser.getKillMobsMissionProgress())
                .append("killBossMission", wyslannikUser.getKillBossMission())
                .append("killBossMissionProgress", wyslannikUser.getKillBossMissionProgress())
                .append("openChestMission", wyslannikUser.getOpenChestMission())
                .append("openChestMissionProgress", wyslannikUser.getOpenChestMissionProgress());
    }
}
