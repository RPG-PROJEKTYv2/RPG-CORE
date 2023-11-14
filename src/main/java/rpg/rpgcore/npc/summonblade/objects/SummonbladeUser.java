package rpg.rpgcore.npc.summonblade.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class SummonbladeUser implements Cloneable {
    private final UUID uuid;
    private int boss1_10progress, boss10_20progress, boss20_30progress, boss30_40progress, boss40_50progress,
            boss60_70progress, boss70_80progress, boss80_90progress, boss90_100progress, boss100_110progress;
    private boolean activated;

    public SummonbladeUser(final UUID uuid) {
        this.uuid = uuid;
        this.boss1_10progress = 0;
        this.boss10_20progress = 0;
        this.boss20_30progress = 0;
        this.boss30_40progress = 0;
        this.boss40_50progress = 0;
        this.boss60_70progress = 0;
        this.boss70_80progress = 0;
        this.boss80_90progress = 0;
        this.boss90_100progress = 0;
        this.boss100_110progress = 0;
        this.activated = false;
    }

    public SummonbladeUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.boss1_10progress = document.getInteger("boss1_10progress");
        this.boss10_20progress = document.getInteger("boss10_20progress");
        this.boss20_30progress = document.getInteger("boss20_30progress");
        this.boss30_40progress = document.getInteger("boss30_40progress");
        this.boss40_50progress = document.getInteger("boss40_50progress");
        this.boss60_70progress = document.getInteger("boss60_70progress");
        this.boss70_80progress = document.getInteger("boss70_80progress");
        this.boss80_90progress = document.getInteger("boss80_90progress");
        this.boss90_100progress = document.getInteger("boss90_100progress");
        this.boss100_110progress = document.getInteger("boss100_110progress");
        this.activated = document.getBoolean("activated");
    }

    public void incrementBoss1_10progress() {
        this.boss1_10progress++;
    }

    public void incrementBoss10_20progress() {
        this.boss10_20progress++;
    }

    public void incrementBoss20_30progress() {
        this.boss20_30progress++;
    }

    public void incrementBoss30_40progress() {
        this.boss30_40progress++;
    }

    public void incrementBoss40_50progress() {
        this.boss40_50progress++;
    }

    public void incrementBoss60_70progress() {
        this.boss60_70progress++;
    }

    public void incrementBoss70_80progress() {
        this.boss70_80progress++;
    }

    public void incrementBoss80_90progress() {
        this.boss80_90progress++;
    }

    public void incrementBoss90_100progress() {
        this.boss90_100progress++;
    }

    public void incrementBoss100_110progress() {
        this.boss100_110progress++;
    }

    public boolean isDone() {
        return this.boss1_10progress >= 32 &&
                this.boss10_20progress >= 28 &&
                this.boss20_30progress >= 24 &&
                this.boss30_40progress >= 20 &&
                this.boss40_50progress >= 16 &&
                this.boss60_70progress >= 12 &&
                this.boss70_80progress >= 4 &&
                this.boss80_90progress >= 2 &&
                this.boss90_100progress >= 1 &&
                this.boss100_110progress >= 1;
    }

    public void reset() {
        this.boss1_10progress -= 32;
        this.boss10_20progress -= 28;
        this.boss20_30progress -= 24;
        this.boss30_40progress -= 20;
        this.boss40_50progress -= 16;
        this.boss60_70progress -= 12;
        this.boss70_80progress -= 4;
        this.boss80_90progress -= 2;
        this.boss90_100progress -= 1;
        this.boss100_110progress -= 1;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("boss1_10progress", this.boss1_10progress)
                .append("boss10_20progress", this.boss10_20progress)
                .append("boss20_30progress", this.boss20_30progress)
                .append("boss30_40progress", this.boss30_40progress)
                .append("boss40_50progress", this.boss40_50progress)
                .append("boss60_70progress", this.boss60_70progress)
                .append("boss70_80progress", this.boss70_80progress)
                .append("boss80_90progress", this.boss80_90progress)
                .append("boss90_100progress", this.boss90_100progress)
                .append("boss100_110progress", this.boss100_110progress)
                .append("activated", this.activated);
    }

    @Override
    public SummonbladeUser clone() {
        try {
            return (SummonbladeUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
