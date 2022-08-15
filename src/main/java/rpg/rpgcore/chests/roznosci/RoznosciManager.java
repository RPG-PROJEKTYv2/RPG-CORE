package rpg.rpgcore.chests.roznosci;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.GlobalItem;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RoznosciManager {

    private final Set<Items> roznosci = Sets.newConcurrentHashSet();

    public RoznosciManager() {
        this.roznosci.add(new Items("1", 8.0, GlobalItem.getItem("I23", 1), 1));
        this.roznosci.add(new Items("2", 8.0, GlobalItem.getItem("I24", 1), 1));
        this.roznosci.add(new Items("3", 8.0, GlobalItem.getItem("I25", 1), 1));
        this.roznosci.add(new Items("4", 8.0, GlobalItem.getItem("I26", 1), 1));
        this.roznosci.add(new Items("5", 8.0, GlobalItem.getItem("I27", 1), 1));
        this.roznosci.add(new Items("6", 8.0, GlobalItem.getItem("I28", 1), 1));
        this.roznosci.add(new Items("7", 8.0, GlobalItem.getItem("I29", 1), 1)); // 56%
        this.roznosci.add(new Items("8", 3.0, GlobalItem.getItem("I23", 2), 1));
        this.roznosci.add(new Items("9", 3.0, GlobalItem.getItem("I24", 2), 1));
        this.roznosci.add(new Items("10", 3.0, GlobalItem.getItem("I25", 2), 1));
        this.roznosci.add(new Items("11", 3.0, GlobalItem.getItem("I26", 2), 1));
        this.roznosci.add(new Items("12", 3.0, GlobalItem.getItem("I27", 2), 1));
        this.roznosci.add(new Items("13", 3.0, GlobalItem.getItem("I28", 2), 1));
        this.roznosci.add(new Items("14", 3.0, GlobalItem.getItem("I29", 2), 1)); // 77%
        this.roznosci.add(new Items("15", 1.0, GlobalItem.getItem("I23", 3), 1));
        this.roznosci.add(new Items("16", 1.0, GlobalItem.getItem("I24", 3), 1));
        this.roznosci.add(new Items("17", 1.0, GlobalItem.getItem("I25", 3), 1));
        this.roznosci.add(new Items("18", 1.0, GlobalItem.getItem("I26", 3), 1));
        this.roznosci.add(new Items("19", 1.0, GlobalItem.getItem("I27", 3), 1));
        this.roznosci.add(new Items("20", 1.0, GlobalItem.getItem("I28", 3), 1));
        this.roznosci.add(new Items("21", 1.0, GlobalItem.getItem("I29", 3), 1)); // 84%
    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.roznosci) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&8&oSkrzynia okazala sie byc pusta..."));
        return null;
    }
}
