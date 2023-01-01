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
        surowce.add(new Items("2", 20.0, GlobalItem.getItem("I12", 1), 1));
        surowce.add(new Items("3", 20.0, GlobalItem.getItem("I13", 1), 1));
        surowce.add(new Items("4", 18.0, GlobalItem.getItem("I14", 1), 1));
        surowce.add(new Items("5", 18.0, GlobalItem.getItem("I15", 1), 1));
        surowce.add(new Items("6", 16.0, GlobalItem.getItem("I16", 1), 1));
        surowce.add(new Items("7", 16.0, GlobalItem.getItem("I17", 1), 1));
        surowce.add(new Items("8", 14.5, GlobalItem.getItem("I18", 1), 1));
        // SZANSA NA 2
        surowce.add(new Items("9", 10.0, GlobalItem.getItem("I12", 2), 2));
        surowce.add(new Items("10", 10.0, GlobalItem.getItem("I13", 2), 2));
        surowce.add(new Items("11", 8.0, GlobalItem.getItem("I14", 2), 2));
        surowce.add(new Items("12", 8.0, GlobalItem.getItem("I15", 2), 2));
        surowce.add(new Items("13", 6.0, GlobalItem.getItem("I16", 2), 2));
        surowce.add(new Items("14", 6.0, GlobalItem.getItem("I17", 2), 2));
        surowce.add(new Items("15", 4.5, GlobalItem.getItem("I18", 2), 2));
        // SZANSA NA 3
        surowce.add(new Items("16", 5.0, GlobalItem.getItem("I12", 3), 3));
        surowce.add(new Items("17", 5.0, GlobalItem.getItem("I13", 3), 3));
        surowce.add(new Items("18", 4.0, GlobalItem.getItem("I14", 3), 3));
        surowce.add(new Items("19", 4.0, GlobalItem.getItem("I15", 3), 3));
        surowce.add(new Items("20", 3.0, GlobalItem.getItem("I16", 3), 3));
        surowce.add(new Items("21", 3.0, GlobalItem.getItem("I17", 3), 3));
        surowce.add(new Items("22", 2.5, GlobalItem.getItem("I18", 3), 3));
        // tajemnicza
        surowce.add(new Items("23", 1.5, GlobalItem.getItem("I4", 1), 1));
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
