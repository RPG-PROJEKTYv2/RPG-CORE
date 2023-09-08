package rpg.rpgcore.npc.gornik.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

@Getter
@Setter
public class GornikUser implements Cloneable {
    private final UUID uuid;
    private int mission, progress;
    private double silnyNaLudzi, defNaMoby;
    private long timeLeft, maxTimeLeft;
    private long freePass, pickaxeAbility;

    public GornikUser(final UUID uuid) {
        this.uuid = uuid;
        this.mission = 1;
        this.progress = 0;
        this.silnyNaLudzi = 0;
        this.defNaMoby = 0;
        this.timeLeft = 0;
        this.maxTimeLeft = 15 * 60 * 1000;
        this.freePass = System.currentTimeMillis();
        this.pickaxeAbility = -1;
    }

    public GornikUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.mission = document.getInteger("mission");
        this.progress = document.getInteger("progress");
        this.silnyNaLudzi = document.getDouble("silnyNaLudzi");
        this.defNaMoby = document.getDouble("defNaMoby");
        this.timeLeft = document.getLong("timeLeft");
        this.maxTimeLeft = document.getLong("maxTimeLeft");
        this.freePass = document.getLong("freePass");
        this.pickaxeAbility = document.getLong("pickaxeAbility");
    }

    public void save() {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataGornik(this.getUuid(),this));
    }

    public void giveFreePassCooldown() {
        this.freePass = System.currentTimeMillis() + 4 * 60 * 60 * 1000;
    }
    public void activatePickaxeAbility() {
        this.pickaxeAbility = System.currentTimeMillis() + 60 * 1000;
    }

    public boolean isPickaxeAbilityActive() {
        return this.pickaxeAbility > System.currentTimeMillis();
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("mission", mission)
                .append("progress", progress)
                .append("silnyNaLudzi", silnyNaLudzi)
                .append("defNaMoby", defNaMoby)
                .append("timeLeft", timeLeft)
                .append("maxTimeLeft", maxTimeLeft)
                .append("freePass", freePass)
                .append("pickaxeAbility", pickaxeAbility);
    }

    @Override
    public GornikUser clone() {
        try {
            return (GornikUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
