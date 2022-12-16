package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class WartosciowykuferManager {


    private final Set<Items> wartosciowykufer = Sets.newConcurrentHashSet();


    public WartosciowykuferManager() {
        // metale
        this.wartosciowykufer.add(new Items("1", 8.0, GlobalItem.getItem("I_METAL", 1), 1));
        // zwoje
        this.wartosciowykufer.add(new Items("4", 6.0, GlobalItem.getItem("I10", 1), 1));
        // bao
        this.wartosciowykufer.add(new Items("7", 8.0, GlobalItem.getItem("I_KAMIENBAO", 1), 1));
        // ksiega magii
        this.wartosciowykufer.add(new Items("10", 6.0, GlobalItem.getItem("I_KSIEGAMAGII", 1), 1));
        // zmianki miecz
        this.wartosciowykufer.add(new Items("13", 4.0, GlobalItem.getItem("I50",1),1));
        this.wartosciowykufer.add(new Items("14", 4.0, GlobalItem.getItem("I51",1),1));
        // zmiaki set
        // SOON...
        // wywar z kory
        this.wartosciowykufer.add(new Items("15", 4.0, LesnikItems.getByItem("POTION", 1), 1));
        // hellcase
        this.wartosciowykufer.add(new Items("16", 2.0, GlobalItem.getItem("I6",1), 1));
    }


    public Items getDrawnItems(final Player player) {
        for (Items item : this.wartosciowykufer ) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
