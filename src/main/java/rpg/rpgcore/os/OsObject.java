package rpg.rpgcore.os;

import org.bson.Document;

import java.util.UUID;

public class OsObject {
    private final UUID uuid;
    private final OsUser osUser;

    public OsObject(final UUID uuid) {
        this.uuid = uuid;
        this.osUser = new OsUser(0,0, 0, 0,0L,0,
                0, 0, 0, 0, 0 ,
                0,0,0,0,0,0,0,0,0);
    }

    public OsObject(final Document document) {
        this.uuid = UUID.fromString((String) document.get("_id"));
        this.osUser = new OsUser(
                document.getInteger("playerKills"),
                document.getInteger("playerKillsProgress"),
                document.getInteger("mobKills"),
                document.getInteger("mobKillsProgress"),
                document.getLong("timeSpent"),
                document.getInteger("timeSpentProgress"),
                document.getInteger("minedBlocks"),
                document.getInteger("minedBlocksProgress"),
                document.getInteger("fishedItems"),
                document.getInteger("fishedItemsProgress"),
                document.getInteger("openedChests"),
                document.getInteger("openedChestsProgress"),
                document.getInteger("positiveUpgrades"),
                document.getInteger("positiveUpgradesProgress"),
                document.getInteger("pickedUpNies"),
                document.getInteger("pickedUpNiesProgress"),
                document.getInteger("destroyedMetins"),
                document.getInteger("destroyedMetinsProgress"),
                document.getInteger("minedTrees"),
                document.getInteger("minedTreesProogress"));
    }

    public UUID getID() {
        return uuid;
    }

    public OsUser getOsUser() {
        return this.osUser;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("playerKills", this.getOsUser().getPlayerKills())
                .append("playerKillsProgress", this.getOsUser().getPlayerKillsProgress())
                .append("mobKills", this.getOsUser().getMobKills())
                .append("mobKillsProgress", this.getOsUser().getMobKillsProgress())
                .append("timeSpent", this.getOsUser().getTimeSpent())
                .append("timeSpentProgress", this.getOsUser().getTimeSpentProgress())
                .append("minedBlocks", this.getOsUser().getMinedBlocks())
                .append("minedBlocksProgress", this.getOsUser().getMinedBlocksProgress())
                .append("fishedItems", this.getOsUser().getFishedItems())
                .append("fishedItemsProgress", this.getOsUser().getFishedItemsProgress())
                .append("openedChests", this.getOsUser().getOpenedChests())
                .append("openedChestsProgress", this.getOsUser().getOpenedChestsProgress())
                .append("positiveUpgrades", this.getOsUser().getPositiveUpgrades())
                .append("positiveUpgradesProgress", this.getOsUser().getPositiveUpgradesProgress())
                .append("pickedUpNies", this.getOsUser().getPickedUpNies())
                .append("pickedUpNiesProgress", this.getOsUser().getPickedUpNiesProgress())
                .append("destroyedMetins", this.getOsUser().getDestroyedMetins())
                .append("destroyedMetinsProgress", this.getOsUser().getDestroyedMetinsProgress())
                .append("minedTrees", this.getOsUser().getMinedTrees())
                .append("minedTreesProogress", this.getOsUser().getMinedTreesProogress());
    }
}
