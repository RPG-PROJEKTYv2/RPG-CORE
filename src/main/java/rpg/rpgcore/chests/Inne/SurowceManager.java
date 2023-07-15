package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class SurowceManager {

    private final Set<Items> surowce = Sets.newConcurrentHashSet();


    public SurowceManager() {
        // kufer
        surowce.add(new Items("1", 0.01, GlobalItem.getItem("I1", 1), 1));
        // tajemnicza
        surowce.add(new Items("23", 0.5, GlobalItem.getItem("I4", 1), 1));
        // materialy
        // SZANSA NA 3
        surowce.add(new Items("19", 1.5, GlobalItem.getItem("I15", 1), 3));
        surowce.add(new Items("20", 1.5, GlobalItem.getItem("I16", 1), 3));
        surowce.add(new Items("21", 1.5, GlobalItem.getItem("I17", 1), 3));
        surowce.add(new Items("22", 1.5, GlobalItem.getItem("I18", 1), 3));
        surowce.add(new Items("16", 2.0, GlobalItem.getItem("I12", 1), 3));
        surowce.add(new Items("17", 2.0, GlobalItem.getItem("I13", 1), 3));
        surowce.add(new Items("18", 2.0, GlobalItem.getItem("I14", 1), 3));
        // SZANSA NA 2
        surowce.add(new Items("15", 4.0, GlobalItem.getItem("I18", 1), 2));
        surowce.add(new Items("13", 4.0, GlobalItem.getItem("I16", 1), 2));
        surowce.add(new Items("14", 4.0, GlobalItem.getItem("I17", 1), 2));
        surowce.add(new Items("11", 5.0, GlobalItem.getItem("I14", 1), 2));
        surowce.add(new Items("12", 5.0, GlobalItem.getItem("I15", 1), 2));
        surowce.add(new Items("9", 6.0, GlobalItem.getItem("I12", 1), 2));
        surowce.add(new Items("10", 6.0, GlobalItem.getItem("I13", 1), 2));
        // SZANSA NA 1
        surowce.add(new Items("8", 11.5, GlobalItem.getItem("I18", 1), 1));
        surowce.add(new Items("6", 12.0, GlobalItem.getItem("I16", 1), 1));
        surowce.add(new Items("7", 12.0, GlobalItem.getItem("I17", 1), 1));
        surowce.add(new Items("4", 14.0, GlobalItem.getItem("I14", 1), 1));
        surowce.add(new Items("5", 14.0, GlobalItem.getItem("I15", 1), 1));
        surowce.add(new Items("2", 16.0, GlobalItem.getItem("I12", 1), 1));
        surowce.add(new Items("3", 16.0, GlobalItem.getItem("I13", 1), 1));
    }




    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.surowce) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                item.getRewardItem().setAmount(item.getAmount());
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &fx" + item.getAmount() + " " + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
