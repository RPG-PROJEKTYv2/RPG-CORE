package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PozlacanykuferManager {


    private final Set<Items> pozlacanykufer = Sets.newConcurrentHashSet();


    public PozlacanykuferManager() {
        // BONY 15
        this.pozlacanykufer.add(new Items("1", 0.005, BonType.SREDNIE_15.getBon().clone(), 1));
        this.pozlacanykufer.add(new Items("2", 0.005, BonType.DEFENSYWA_15.getBon().clone(), 1));
        this.pozlacanykufer.add(new Items("3", 0.005, BonType.KRYTYK_15.getBon().clone(), 1));
        // metale, zwoje, oczyszczenia
        this.pozlacanykufer.add(new Items("4", 8.0, GlobalItem.getItem("I_METAL", 1), 1));
        this.pozlacanykufer.add(new Items("5", 16.0, GlobalItem.getItem("I10", 1), 1));
        this.pozlacanykufer.add(new Items("6", 12.0, GlobalItem.getItem("I_OCZYSZCZENIE", 1), 1));
        // bao, ksiega magii
        this.pozlacanykufer.add(new Items("7", 8.0, GlobalItem.getItem("I_KAMIENBAO", 1), 1));
        this.pozlacanykufer.add(new Items("8", 4.0, GlobalItem.getItem("I_KSIEGAMAGII", 1), 1));
        // zmianka
        this.pozlacanykufer.add(new Items("9", 7.0, GlobalItem.getItem("I50",1),1));
        // wywar z kory
        this.pozlacanykufer.add(new Items("11", 6.0, LesnikItems.getByItem("POTION", 1), 1));
        // hellcase i dwa kufry
        this.pozlacanykufer.add(new Items("12", 8.0, GlobalItem.getItem("I6",1), 1));
        this.pozlacanykufer.add(new Items("13", 3.0, GlobalItem.getItem("I1", 1), 2));
    }


    public Items getDrawnItems(final Player player) {
        for (Items item : this.pozlacanykufer ) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                item.getRewardItem().setAmount(item.getAmount());
                player.sendMessage(Utils.format("&2+ &fx" + item.getAmount() + " " + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
