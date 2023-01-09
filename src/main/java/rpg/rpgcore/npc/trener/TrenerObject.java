package rpg.rpgcore.npc.trener;

import org.bson.Document;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class TrenerObject {
    private final UUID uuid;
    private final TrenerUser trenerUser;

    public TrenerObject(final UUID uuid) {
        this.uuid = uuid;
        this.trenerUser = new TrenerUser(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public TrenerObject(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.trenerUser = new TrenerUser(document.getInteger("points"), document.getDouble("sredniDmg"),
                document.getDouble("sredniDef"), document.getDouble("blokCiosu"), document.getInteger("szczescie"),
                document.getDouble("silnyNaLudzi"), document.getDouble("defNaLudzi"), document.getDouble("kryt"), document.getInteger("dodatkoweHp"), document.getLong("cooldown"));
    }

    public UUID getId() {
        return this.uuid;
    }

    public TrenerUser getTrenerUser() {
        return trenerUser;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("points", this.trenerUser.getPoints())
                .append("sredniDmg", this.trenerUser.getSredniDmg())
                .append("sredniDef", this.trenerUser.getSredniDef())
                .append("blokCiosu", this.trenerUser.getBlokCiosu())
                .append("szczescie", this.trenerUser.getSzczescie())
                .append("silnyNaLudzi", this.trenerUser.getSilnyNaLudzi())
                .append("defNaLudzi", this.trenerUser.getDefNaLudzi())
                .append("kryt", this.trenerUser.getKryt())
                .append("dodatkoweHp", this.trenerUser.getDodatkoweHp())
                .append("cooldown", this.trenerUser.getCooldown());
    }

    public void giveCooldown() {
        this.trenerUser.setCooldown(System.currentTimeMillis() + 300_000L);
    }

    public boolean hasCooldown() {
        return this.trenerUser.getCooldown() > System.currentTimeMillis();
    }

    public String getCooldown() {
        return Utils.durationToString(this.trenerUser.getCooldown() - System.currentTimeMillis(), true);
    }
}
