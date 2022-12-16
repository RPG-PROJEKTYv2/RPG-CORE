package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class SurowceManager {

    private final Set<Items> surowce = Sets.newConcurrentHashSet();


    public SurowceManager() {
        // kufer
        surowce.add(new Items("1", 1.0, GlobalItem.getItem("I1", 1), 1));
        // materialy
        // SZANSA NA 1
        surowce.add(new Items("3", 20.0, GlobalItem.getItem("I12", 1), 1));
        surowce.add(new Items("4", 20.0, GlobalItem.getItem("I13", 1), 1));
        surowce.add(new Items("5", 18.0, GlobalItem.getItem("I14", 1), 1));
        surowce.add(new Items("6", 18.0, GlobalItem.getItem("I15", 1), 1));
        surowce.add(new Items("7", 16.0, GlobalItem.getItem("I16", 1), 1));
        surowce.add(new Items("8", 16.0, GlobalItem.getItem("I17", 1), 1));
        surowce.add(new Items("9", 14.5, GlobalItem.getItem("I18", 1), 1));
        // SZANSA NA 2
        surowce.add(new Items("3", 10.0, GlobalItem.getItem("I12", 2), 2));
        surowce.add(new Items("4", 10.0, GlobalItem.getItem("I13", 2), 2));
        surowce.add(new Items("5", 8.0, GlobalItem.getItem("I14", 2), 2));
        surowce.add(new Items("6", 8.0, GlobalItem.getItem("I15", 2), 2));
        surowce.add(new Items("7", 6.0, GlobalItem.getItem("I16", 2), 2));
        surowce.add(new Items("8", 6.0, GlobalItem.getItem("I17", 2), 2));
        surowce.add(new Items("9", 4.5, GlobalItem.getItem("I18", 2), 2));
        // SZANSA NA 3
        surowce.add(new Items("3", 5.0, GlobalItem.getItem("I12", 1), 1));
        surowce.add(new Items("4", 5.0, GlobalItem.getItem("I13", 1), 1));
        surowce.add(new Items("5", 4.0, GlobalItem.getItem("I14", 1), 1));
        surowce.add(new Items("6", 4.0, GlobalItem.getItem("I15", 1), 1));
        surowce.add(new Items("7", 3.0, GlobalItem.getItem("I16", 1), 1));
        surowce.add(new Items("8", 3.0, GlobalItem.getItem("I17", 1), 1));
        surowce.add(new Items("9", 2.5, GlobalItem.getItem("I18", 1), 1));
        // ZWOJ KOWALA , METAL
        surowce.add(new Items("9", 1.5, GlobalItem.getItem("I_METAL", 1), 1));
        surowce.add(new Items("9", 0.5, GlobalItem.getItem("I10", 1), 1));
    }




    public Items getDrawnItems(final Player player) {
        for (Items item : this.surowce) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
