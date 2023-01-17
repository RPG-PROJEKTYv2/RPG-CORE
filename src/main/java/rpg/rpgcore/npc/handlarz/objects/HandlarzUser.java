package rpg.rpgcore.npc.handlarz.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class HandlarzUser {
    private final UUID uuid;
    private int sprzedane;
    private boolean black_market;

    private int item1, item2, item3, item4, item5, item6;
    private int black_item1, black_item2, black_item3, black_item4, black_item5, black_item6;
    private long nextReset;

    public HandlarzUser(UUID uuid) {
        this.uuid = uuid;
        this.sprzedane = 0;
        this.black_market = false;
        this.item1 = 0;
        this.item2 = 0;
        this.item3 = 0;
        this.item4 = 0;
        this.item5 = 0;
        this.item6 = 0;
        this.black_item1 = 0;
        this.black_item2 = 0;
        this.black_item3 = 0;
        this.black_item4 = 0;
        this.black_item5 = 0;
        this.black_item6 = 0;
        this.nextReset = System.currentTimeMillis() + 86400000;
    }

    public HandlarzUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.sprzedane = document.getInteger("sprzedane");
        this.black_market = document.getBoolean("black_market");
        this.item1 = document.getInteger("item1");
        this.item2 = document.getInteger("item2");
        this.item3 = document.getInteger("item3");
        this.item4 = document.getInteger("item4");
        this.item5 = document.getInteger("item5");
        this.item6 = document.getInteger("item6");
        this.black_item1 = document.getInteger("black_item1");
        this.black_item2 = document.getInteger("black_item2");
        this.black_item3 = document.getInteger("black_item3");
        this.black_item4 = document.getInteger("black_item4");
        this.black_item5 = document.getInteger("black_item5");
        this.black_item6 = document.getInteger("black_item6");
        this.nextReset = document.getLong("nextReset");
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("sprzedane", this.sprzedane)
                .append("black_market", this.black_market)
                .append("item1", this.item1)
                .append("item2", this.item2)
                .append("item3", this.item3)
                .append("item4", this.item4)
                .append("item5", this.item5)
                .append("item6", this.item6)
                .append("black_item1", this.black_item1)
                .append("black_item2", this.black_item2)
                .append("black_item3", this.black_item3)
                .append("black_item4", this.black_item4)
                .append("black_item5", this.black_item5)
                .append("black_item6", this.black_item6)
                .append("nextReset", this.nextReset);
    }
}
