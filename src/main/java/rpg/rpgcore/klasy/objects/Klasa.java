package rpg.rpgcore.klasy.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.klasy.enums.KlasyMain;
import rpg.rpgcore.klasy.enums.KlasySide;

import java.util.UUID;

@Getter
@Setter
public class Klasa implements Cloneable {
    private final UUID uuid;
    private KlasyMain mainKlasa;
    private KlasySide podKlasa;
    private double bonus1, bonus2;
    private int upgrade, ksiegaProgress;
    private long cdLMB, cdRMB;
    private boolean firstReset;


    public Klasa(final UUID uuid) {
        this.uuid = uuid;
        this.mainKlasa = KlasyMain.NIE_WYBRANO;
        this.podKlasa = KlasySide.NIE_WYBRANO;
        this.bonus1 = 0;
        this.bonus2 = 0;
        this.upgrade = 0;
        this.ksiegaProgress = 0;
        this.cdLMB = 0L;
        this.cdRMB = 0L;
        this.firstReset = false;
    }

    public Klasa(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.mainKlasa = KlasyMain.getByName(document.getString("mainKlasa"));
        this.podKlasa = KlasySide.getByName(document.getString("podKlasa"));
        this.bonus1 = document.getDouble("bonus1");
        this.bonus2 = document.getDouble("bonus2");
        this.upgrade = document.getInteger("upgrade");
        this.ksiegaProgress = document.getInteger("ksiegaProgress");
        this.cdLMB = document.getLong("cdLMB");
        this.cdRMB = document.getLong("cdRMB");
        this.firstReset = (document.containsKey("firstReset") ? document.getBoolean("firstReset") : false);
    }

    public boolean hasLeftClickCooldown() {
        return System.currentTimeMillis() < cdLMB;
    }

    public boolean hasRightClickCooldown() {
        return System.currentTimeMillis() < cdRMB;
    }

    public void save() {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataKlasy(this.uuid, this));
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("mainKlasa", mainKlasa.getName())
                .append("podKlasa", podKlasa.getName())
                .append("bonus1", bonus1)
                .append("bonus2", bonus2)
                .append("upgrade", upgrade)
                .append("ksiegaProgress", ksiegaProgress)
                .append("cdLMB", cdLMB)
                .append("cdRMB", cdRMB)
                .append("firstReset", firstReset);

    }

    @Override
    public Klasa clone() {
        try {
            return (Klasa) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
