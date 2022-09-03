package rpg.rpgcore.ranks;

import rpg.rpgcore.ranks.types.RankType;

public class RankUser {
    private RankType rankType;

    public RankUser(RankType rankType) {
        this.rankType = rankType;
    }

    public RankType getRankType() {
        return rankType;
    }

    public void setRank(RankType rankType) {
        this.rankType = rankType;
    }

    public boolean hasRank(RankType rankType) {
        return getRankType().can(rankType);
    }

    public boolean isStaff() {
        return hasRank(RankType.JUNIORHELPER);
    }

    public boolean isHighStaff() {
        return hasRank(RankType.GM);
    }
}
