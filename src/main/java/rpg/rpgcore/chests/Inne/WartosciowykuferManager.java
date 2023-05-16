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

public class WartosciowykuferManager {


    private final Set<Items> wartosciowykufer = Sets.newConcurrentHashSet();


    public WartosciowykuferManager() {
        // metale, zwoje, oczyszczenia
        this.wartosciowykufer.add(new Items("1", 8.0, GlobalItem.getItem("I_METAL", 1), 1));
        this.wartosciowykufer.add(new Items("2", 16.0, GlobalItem.getItem("I10", 1), 1));
        this.wartosciowykufer.add(new Items("3", 12.0, GlobalItem.getItem("I_OCZYSZCZENIE", 1), 1));
        // bao, ksiega magii
        this.wartosciowykufer.add(new Items("4", 8.0, GlobalItem.getItem("I_KAMIENBAO", 1), 1));
        this.wartosciowykufer.add(new Items("5", 4.0, GlobalItem.getItem("I_KSIEGAMAGII", 1), 1));
        // zmianki miecz i set
        this.wartosciowykufer.add(new Items("6", 5.0, GlobalItem.getItem("I50",1),1));
        this.wartosciowykufer.add(new Items("7", 5.0, GlobalItem.getItem("I51",1),1));
        // wywar z kory
        this.wartosciowykufer.add(new Items("8", 6.0, LesnikItems.getByItem("POTION", 1), 1));
        // hellcase i dwa kufry
        this.wartosciowykufer.add(new Items("9", 8.0, GlobalItem.getItem("I6",1), 1));
        this.wartosciowykufer.add(new Items("10", 3.0, GlobalItem.getItem("I1", 1), 2));
        // BONY 15
        this.wartosciowykufer.add(new Items("11", 0.005, BonType.SREDNIE_15.getBon().clone(), 1));
        this.wartosciowykufer.add(new Items("12", 0.005, BonType.DEFENSYWA_15.getBon().clone(), 1));
        this.wartosciowykufer.add(new Items("13", 0.005, BonType.KRYTYK_15.getBon().clone(), 1));
    }


    public Items getDrawnItems(final Player player) {
        for (Items item : this.wartosciowykufer ) {
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
