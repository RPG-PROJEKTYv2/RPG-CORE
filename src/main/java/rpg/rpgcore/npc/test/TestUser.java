package rpg.rpgcore.npc.test;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class TestUser {
    //UUID, MISSION, PROGRESS MUSZA BYC ZAWSZE
    private final UUID uuid;
    private int mission;
    private int progress;
    //TU DAJESZ JAKIE BOONUSY MA DAWAC
    private int ddmg;
    private double srdmg;
    private long cooldown;

    //TO JEST KONSTRUKTOR DO ROBIENIA PRZY WEJSCIU NA SERWER
    public TestUser(UUID uuid) {
        this.uuid = uuid;
        this.mission = 1;
        this.progress = 0;
        this.ddmg = 0;
        this.srdmg = 0;
        this.cooldown = 0;
    }

    public void giveCooldown() {
        this.cooldown = new Date().getTime() + 300000L;
    }

    public boolean hasCooldown() {
        return new Date().getTime() < this.cooldown;
    }


    // TO JEST LADOWANIE USERA Z BAZY DANCYH TEZ MUSI BYC ZAWSZE!!!!!!
    public TestUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.mission = document.getInteger("mission");
        this.progress = document.getInteger("progress");
        this.ddmg = document.getInteger("ddmg");
        this.srdmg = document.getDouble("srdmg");
        this.cooldown = document.getLong("cooldown");
    }

    // TO JEST ZAPIS USERA DO BAZY DANYCH TEZ MUSI BYC ZAWSZE!!!!!
    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("mission", mission)
                .append("progress", progress)
                .append("ddmg", ddmg)
                .append("srdmg", srdmg)
                .append("cooldown", cooldown);
    }
}
