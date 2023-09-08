package rpg.rpgcore.ranks;

import rpg.rpgcore.ranks.types.RankTypePlayer;

public class RankPlayerUser implements Cloneable {
    private RankTypePlayer rankType;
    private long time;

    public RankPlayerUser(RankTypePlayer rankType, long time) {
        this.rankType = rankType;
        this.time = time;
    }

    public RankTypePlayer getRankType() {
        return rankType;
    }

    public void setRank(RankTypePlayer rankType) {
        this.rankType = rankType;
    }

    public boolean hasRank(RankTypePlayer rankType) {
        return getRankType().can(rankType);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public RankPlayerUser clone() {
        try {
            return (RankPlayerUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
