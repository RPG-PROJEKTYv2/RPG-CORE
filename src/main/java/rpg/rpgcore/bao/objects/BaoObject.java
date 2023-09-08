package rpg.rpgcore.bao.objects;

import org.bson.Document;

import java.util.UUID;

public class BaoObject implements Cloneable {
    private final UUID uuid;
    private BaoUser baoUser;


    public BaoObject(final UUID uuid) {
        this.uuid = uuid;
        this.baoUser = new BaoUser("Brak Bonusu", 0, "Brak Bonusu", 0, "Brak Bonusu", 0, "Brak Bonusu", 0, "Brak Bonusu", 0);
    }


    public BaoObject(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.baoUser = new BaoUser(document.getString("bonus1"), document.getInteger("value1"), document.getString("bonus2"), document.getInteger("value2"), document.getString("bonus3"), document.getInteger("value3"), document.getString("bonus4"), document.getInteger("value4"), document.getString("bonus5"), document.getInteger("value5"));
    }

    public UUID getId() {
        return uuid;
    }

    public BaoUser getBaoUser() {
        return baoUser;
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("bonus1", baoUser.getBonus1())
                .append("value1", baoUser.getValue1())
                .append("bonus2", baoUser.getBonus2())
                .append("value2", baoUser.getValue2())
                .append("bonus3", baoUser.getBonus3())
                .append("value3", baoUser.getValue3())
                .append("bonus4", baoUser.getBonus4())
                .append("value4", baoUser.getValue4())
                .append("bonus5", baoUser.getBonus5())
                .append("value5", baoUser.getValue5());
    }

    @Override
    public BaoObject clone() {
        try {
            final BaoObject clone = (BaoObject) super.clone();
            clone.baoUser = baoUser.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
