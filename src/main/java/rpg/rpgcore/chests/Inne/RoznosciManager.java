package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.GlobalItems.GlobalItem;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RoznosciManager {

    private final Set<Items> roznosci = Sets.newConcurrentHashSet();

    public RoznosciManager() {
        this.roznosci.add(new Items("1", 8.0, GlobalItem.getItem("I20", 1), 1));
    }



    public Items getDrawnItems(final Player player) {
        for (Items item : this.roznosci) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
