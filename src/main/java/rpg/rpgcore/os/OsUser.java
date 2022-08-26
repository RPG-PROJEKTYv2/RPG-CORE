package rpg.rpgcore.os;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OsUser {
    private int playerKills, playerKillsProgress, mobKills, mobKillsProgress, timeSpentProgress, minedBlocks, minedBlocksProgress, fishedItems, fishedItemsProgress, openedChests, openedChestsProgress,
            positiveUpgrades, positiveUpgradesProgress, pickedUpNies, pickedUpNiesProgress, destroyedMetins, destroyedMetinsProgress, minedTrees, minedTreesProogress;
    private long timeSpent;

    public OsUser(final int playerKills, final int playerKillsProgress, final int mobKills, final int mobKillsProgress, final long timeSpent,
                  final int timeSpentProgress, final int minedBlocks, final int minedBlocksProgress, final int fishedItems, final int fishedItemsProgress,
                  final int openedChests, final int openedChestsProgress, final int positiveUpgrades, final int positiveUpgradesProgress, final int pickedUpNies,
                  final int pickedUpNiesProgress, final int destroyedMetins, final int destroyedMetinsProgress, final int minedTrees, final int minedTreesProogress) {
        this.playerKills = playerKills;
        this.playerKillsProgress = playerKillsProgress;
        this.mobKills = mobKills;
        this.mobKillsProgress = mobKillsProgress;
        this.timeSpent = timeSpent;
        this.timeSpentProgress = timeSpentProgress;
        this.minedBlocks = minedBlocks;
        this.minedBlocksProgress = minedBlocksProgress;
        this.fishedItems = fishedItems;
        this.fishedItemsProgress = fishedItemsProgress;
        this.openedChests = openedChests;
        this.openedChestsProgress = openedChestsProgress;
        this.positiveUpgrades = positiveUpgrades;
        this.positiveUpgradesProgress = positiveUpgradesProgress;
        this.pickedUpNies = pickedUpNies;
        this.pickedUpNiesProgress = pickedUpNiesProgress;
        this.destroyedMetins = destroyedMetins;
        this.destroyedMetinsProgress = destroyedMetinsProgress;
        this.minedTrees = minedTrees;
        this.minedTreesProogress = minedTreesProogress;
    }
}
