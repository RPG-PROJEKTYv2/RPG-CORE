package rpg.rpgcore.dodatki.bony.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class BonyUser {
    private final UUID uuid;
    private String dmg5, dmg10, dmg15, def5, def10, def15, kryt5, kryt10, kryt15, wzmkryt10, blok20, przeszywka20, predkosc25, predkosc50, hp10, hp20, hp35, dmgMetiny;

    public BonyUser(final UUID uuid) {
        this.uuid = uuid;
        this.dmg5 = "";
        this.dmg10 = "";
        this.dmg15 = "";
        this.def5 = "";
        this.def10 = "";
        this.def15 = "";
        this.kryt5 = "";
        this.kryt10 = "";
        this.kryt15 = "";
        this.wzmkryt10 = "";
        this.blok20 = "";
        this.przeszywka20 = "";
        this.predkosc25 = "";
        this.predkosc50 = "";
        this.hp10 = "";
        this.hp20 = "";
        this.hp35 = "";
        this.dmgMetiny = "";
    }

    public BonyUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id").replace("-bony", ""));
        this.dmg5 = document.getString("dmg5");
        this.dmg10 = document.getString("dmg10");
        this.dmg15 = document.getString("dmg15");
        this.def5 = document.getString("def5");
        this.def10 = document.getString("def10");
        this.def15 = document.getString("def15");
        this.kryt5 = document.getString("kryt5");
        this.kryt10 = document.getString("kryt10");
        this.kryt15 = document.getString("kryt15");
        this.wzmkryt10 = document.getString("wzmkryt10");
        this.blok20 = document.getString("blok20");
        this.przeszywka20 = document.getString("przeszywka20");
        this.predkosc25 = document.getString("predkosc25");
        this.predkosc50 = document.getString("predkosc50");
        this.hp10 = document.getString("hp10");
        this.hp20 = document.getString("hp20");
        this.hp35 = document.getString("hp35");
        this.dmgMetiny = document.getString("dmgMetiny");
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString() + "-bony")
                .append("dmg5", this.dmg5)
                .append("dmg10", this.dmg10)
                .append("dmg15", this.dmg15)
                .append("def5", this.def5)
                .append("def10", this.def10)
                .append("def15", this.def15)
                .append("kryt5", this.kryt5)
                .append("kryt10", this.kryt10)
                .append("kryt15", this.kryt15)
                .append("wzmkryt10", this.wzmkryt10)
                .append("blok20", this.blok20)
                .append("przeszywka20", this.przeszywka20)
                .append("predkosc25", this.predkosc25)
                .append("predkosc50", this.predkosc50)
                .append("hp10", this.hp10)
                .append("hp20", this.hp20)
                .append("hp35", this.hp35)
                .append("dmgMetiny", this.dmgMetiny);
    }
}
