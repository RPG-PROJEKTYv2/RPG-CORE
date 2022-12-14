package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class KowalManager {

    private final Set<Items> kowal = Sets.newConcurrentHashSet();

    public KowalManager() {
        this.kowal.add(new Items("2", 35.5, GlobalItem.getItem("I_METAL", ChanceHelper.getRandInt(1, 2)), ChanceHelper.getRandInt(1, 2)));
        this.kowal.add(new Items("3", 35.5, GlobalItem.getItem("I10", ChanceHelper.getRandInt(1, 3)), ChanceHelper.getRandInt(1, 3)));
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.kowal) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }


}
