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
    private int mission;
    private double przeszywka, krytykk;
    private final Map<String, Integer> progressMap;
    private final Map<String, Boolean> completionMap;

    public DuszologUser(final UUID uuid) {
        this.uuid = uuid;
        this.mission = 1;
        this.przeszywka = 0;
        this.krytykk = 0;
        this.progressMap = new HashMap<>();
        this.completionMap = new HashMap<>();
    }

    public DuszologUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.mission = document.getInteger("mission");
        this.przeszywka = (document.containsKey("value1") ? document.getDouble("value1") : document.getDouble("przeszywka"));
        this.krytykk = (document.containsKey("value1") ? document.getDouble("value1") : document.getDouble("krytykk"));
        final Map<String, Integer> progressMap = new HashMap<>();
        final Document progressMapD = (document.containsKey("progressMap") ? document.get("progressMap", Document.class) : new Document());
        progressMapD.keySet().forEach(key -> {
            if (!key.equals("_id")) {
                progressMap.put(key, progressMapD.getInteger(key));
            }
        });
        this.progressMap = progressMap;
        final Map<String, Boolean> completionMap = new HashMap<>();
        final Document completionMapD = (document.containsKey("completionMap") ? document.get("completionMap", Document.class) : new Document());
        completionMapD.keySet().forEach(key -> {
            if (!key.equals("_id")) {
                completionMap.put(key, completionMapD.getBoolean(key));
            }
        });
        this.completionMap = completionMap;
    }

    public Document toDocument() {
        final Document progressMapD = new Document("_id", "progressMap-" + this.uuid.toString());
        progressMap.forEach(progressMapD::append);
        final Document completionMapD = new Document("_id", "completionMap-" + this.uuid.toString());
        completionMap.forEach(completionMapD::append);

        return new Document("_id", this.uuid.toString())
                .append("mission", this.mission)
                .append("przeszywka", this.przeszywka)
                .append("krytykk", this.krytykk)
                .append("progressMap", this.progressMap)
                .append("completionMap", this.completionMap);
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
