package rpg.rpgcore.npc.duszolog.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class DuszologUser implements Cloneable {
    private final UUID uuid;
    private double przeszywka, krytyk;
    private final Map<String, Integer> progressMap;
    private final Map<String, Boolean> completionMap;

    public DuszologUser(final UUID uuid) {
        this.uuid = uuid;
        this.przeszywka = 0;
        this.krytyk = 0;
        this.progressMap = new HashMap<>();
        this.completionMap = new HashMap<>();
    }

    public DuszologUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.przeszywka = (document.containsKey("value1") ? document.getDouble("value1") : document.getDouble("przeszywka"));
        this.krytyk = (document.containsKey("value1") ? document.getDouble("value1") : document.getDouble("krytykk"));
        final Map<String, Integer> progressMap = new HashMap<>();
        final Document progressMapD = (document.containsKey("progressMap") ? document.get("progressMap", Document.class) : new Document());
        progressMapD.keySet().forEach(key -> {
            if (!key.equals("_id")) {
                progressMap.put(key.replaceAll("_", " ").replaceAll("Lvl", "Lvl."), progressMapD.getInteger(key));
            }
        });
        this.progressMap = progressMap;
        final Map<String, Boolean> completionMap = new HashMap<>();
        final Document completionMapD = (document.containsKey("completionMap") ? document.get("completionMap", Document.class) : new Document());
        completionMapD.keySet().forEach(key -> {
            if (!key.equals("_id")) {
                completionMap.put(key.replaceAll("_", " ").replaceAll("Lvl", "Lvl."), completionMapD.getBoolean(key));
            }
        });
        this.completionMap = completionMap;
    }

    public void incrementProgress(final String entityName) {
        if (progressMap.containsKey(entityName)) {
            progressMap.replace(entityName, progressMap.get(entityName) + 1);
        } else {
            progressMap.put(entityName, 1);
        }
    }

    public Document toDocument() {
        final Document progressMapD = new Document("_id", "progressMap-" + this.uuid.toString());
        progressMap.forEach((key, value) -> progressMapD.append(key.replaceAll(" ", "_").replaceAll("Lvl\\.", "Lvl"), value));
        final Document completionMapD = new Document("_id", "completionMap-" + this.uuid.toString());
        completionMap.forEach((key, value) -> completionMapD.append(key.replaceAll(" ", "_").replaceAll("Lvl\\.", "Lvl"), value));

        return new Document("_id", this.uuid.toString())
                .append("przeszywka", this.przeszywka)
                .append("krytykk", this.krytyk)
                .append("progressMap", progressMapD)
                .append("completionMap", completionMapD);
    }

    @Override
    public DuszologUser clone() {
        try {
            return (DuszologUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
