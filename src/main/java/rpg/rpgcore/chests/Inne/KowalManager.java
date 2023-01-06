package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class KowalManager {

    private final Set<Items> kowal = Sets.newConcurrentHashSet();

    public KowalManager() {
        // metale
        this.kowal.add(new Items("1", 4.0, GlobalItem.getItem("I_METAL", 1), 1));
        this.kowal.add(new Items("2", 3.0, GlobalItem.getItem("I_METAL", 2), 2));
        this.kowal.add(new Items("3", 2.0, GlobalItem.getItem("I_METAL", 3), 3));
        // zwoje
        this.kowal.add(new Items("4", 16.0, GlobalItem.getItem("I10", 1), 1));
        this.kowal.add(new Items("5", 12.0, GlobalItem.getItem("I10", 2), 2));
        this.kowal.add(new Items("6", 8.0, GlobalItem.getItem("I10", 3), 3));
        // oczyszczenie
        this.kowal.add(new Items("7", 8.0, GlobalItem.getItem("I_OCZYSZCZENIE", 1), 1));
        this.kowal.add(new Items("7", 5.0, GlobalItem.getItem("I_OCZYSZCZENIE", 2),2));
        this.kowal.add(new Items("7", 2.5, GlobalItem.getItem("I_OCZYSZCZENIE", 3), 3));
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
