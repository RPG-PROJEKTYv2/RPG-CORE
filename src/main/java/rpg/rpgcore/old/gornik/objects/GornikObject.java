package rpg.rpgcore.old.gornik.objects;

import org.bson.Document;

import java.util.UUID;

public class GornikObject {
    private final UUID uuid;
    private final GornikUser gornikUser;

    public GornikObject(final UUID uuid) {
        this.uuid = uuid;
        this.gornikUser = new GornikUser(1, 0, 0, 0, 0, false, false, false,
                false, false, false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false);
    }

    public GornikObject(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.gornikUser = new GornikUser(document.getInteger("mission"), document.getInteger("progress"), document.getDouble("sredniaOdpornosc"),
                document.getDouble("blokCiosu"), document.getDouble("przeszycieBloku"), document.getBoolean("dalszeDone"), document.getBoolean("d1"), document.getBoolean("d2"),
                document.getBoolean("d3_1"), document.getBoolean("d3_2"), document.getBoolean("d3_3"), document.getBoolean("d4_1"), document.getBoolean("d4_2"), document.getBoolean("d4_3"),
                document.getBoolean("d4_4"), document.getBoolean("d4_5"), document.getBoolean("d4_6"), document.getBoolean("d4_7"), document.getBoolean("d5_1"), document.getBoolean("d5_2"),
                document.getBoolean("d5_3"), document.getBoolean("d5_4"), document.getBoolean("d5_5"), document.getBoolean("d6_1"), document.getBoolean("d6_2"), document.getBoolean("d6_3"),
                document.getBoolean("d6_4"), document.getBoolean("d6_5"), document.getBoolean("d7_1"), document.getBoolean("d7_2"), document.getBoolean("d7_3"), document.getBoolean("d7_4"),
                document.getBoolean("d8_1"), document.getBoolean("d8_2"), document.getBoolean("d9_1"), document.getBoolean("d9_2"));
    }

    public UUID getID() {
        return this.uuid;
    }

    public GornikUser getGornikUser() {
        return this.gornikUser;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("mission", this.gornikUser.getMission())
                .append("progress", this.gornikUser.getProgress())
                .append("sredniaOdpornosc", this.gornikUser.getSredniaOdpornosc())
                .append("blokCiosu", this.gornikUser.getBlokCiosu())
                .append("przeszycieBloku", this.gornikUser.getPrzeszycieBloku())
                .append("dalszeDone", this.gornikUser.isDalszeDone())
                .append("d1", this.gornikUser.isD1())
                .append("d2", this.gornikUser.isD2())
                .append("d3_1", this.gornikUser.isD3_1())
                .append("d3_2", this.gornikUser.isD3_2())
                .append("d3_3", this.gornikUser.isD3_3())
                .append("d4_1", this.gornikUser.isD4_1())
                .append("d4_2", this.gornikUser.isD4_2())
                .append("d4_3", this.gornikUser.isD4_3())
                .append("d4_4", this.gornikUser.isD4_4())
                .append("d4_5", this.gornikUser.isD4_5())
                .append("d4_6", this.gornikUser.isD4_6())
                .append("d4_7", this.gornikUser.isD4_7())
                .append("d5_1", this.gornikUser.isD5_1())
                .append("d5_2", this.gornikUser.isD5_2())
                .append("d5_3", this.gornikUser.isD5_3())
                .append("d5_4", this.gornikUser.isD5_4())
                .append("d5_5", this.gornikUser.isD5_5())
                .append("d6_1", this.gornikUser.isD6_1())
                .append("d6_2", this.gornikUser.isD6_2())
                .append("d6_3", this.gornikUser.isD6_3())
                .append("d6_4", this.gornikUser.isD6_4())
                .append("d6_5", this.gornikUser.isD6_5())
                .append("d7_1", this.gornikUser.isD7_1())
                .append("d7_2", this.gornikUser.isD7_2())
                .append("d7_3", this.gornikUser.isD7_3())
                .append("d7_4", this.gornikUser.isD7_4())
                .append("d8_1", this.gornikUser.isD8_1())
                .append("d8_2", this.gornikUser.isD8_2())
                .append("d9_1", this.gornikUser.isD9_1())
                .append("d9_2", this.gornikUser.isD9_2());
    }
}
