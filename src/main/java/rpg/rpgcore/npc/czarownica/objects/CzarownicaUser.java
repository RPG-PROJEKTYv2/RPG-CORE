package rpg.rpgcore.npc.czarownica.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.LinkedHashMap;
import java.util.UUID;

@Getter
@Setter
public class CzarownicaUser {
    private final UUID uuid;
    private int mission;
    private LinkedHashMap<ItemStack, Integer> progressMap;
    private boolean isUnlocked;

    public CzarownicaUser(final UUID uuid) {
        this.uuid = uuid;
        this.mission = 1;
        this.progressMap = new LinkedHashMap<>();
        this.isUnlocked = false;
    }

    public CzarownicaUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.mission = document.getInteger("mission");
        this.progressMap = new LinkedHashMap<>();
        final Document progressMap = document.get("progressMap", Document.class);
        for (final String key : progressMap.keySet()) {
            if (key.equals("_id")) continue;
            this.progressMap.put(Utils.deserializeItem(key), progressMap.getInteger(key));
        }
        this.isUnlocked = false;
    }

    public void incrementProgress(final ItemStack item) {
        this.progressMap.replace(item, this.progressMap.getOrDefault(item, 0) + 1);
    }

    public void resetProgress() {
        this.progressMap.clear();
    }

    public void save() {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataCzarownica(this.uuid, this));
    }


    public Document toDocument() {
        final Document document = new Document();
        document.append("_id", this.uuid.toString());
        document.append("mission", this.mission);
        final Document progressMap = new Document("_id", this.uuid.toString() + "-progressMap");
        for (final ItemStack item : this.progressMap.keySet()) {
            progressMap.append(Utils.serializeItem(item), this.progressMap.get(item));
        }
        document.append("progressMap", progressMap);
        return document;
    }
}
