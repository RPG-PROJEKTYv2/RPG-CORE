package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class SurowceManager {

    private final Set<Items> surowce = Sets.newConcurrentHashSet();


    public SurowceManager() {
        // materialy
        // SZANSA NA 3
        surowce.add(new Items("1", 1.5, GlobalItem.getItem("I15", 3), 3));
        surowce.add(new Items("2", 1.5, GlobalItem.getItem("I16", 3), 3));
        surowce.add(new Items("3", 1.5, GlobalItem.getItem("I17", 3), 3));
        surowce.add(new Items("4", 1.5, GlobalItem.getItem("I18", 3), 3));
        surowce.add(new Items("5", 2.0, GlobalItem.getItem("I12", 3), 3));
        surowce.add(new Items("6", 2.0, GlobalItem.getItem("I13", 3), 3));
        surowce.add(new Items("7", 2.0, GlobalItem.getItem("I14", 3), 3));
        // SZANSA NA 2
        surowce.add(new Items("8", 4.0, GlobalItem.getItem("I18", 2), 2));
        surowce.add(new Items("9", 4.0, GlobalItem.getItem("I16", 2), 2));
        surowce.add(new Items("10", 4.0, GlobalItem.getItem("I17", 2), 2));
        surowce.add(new Items("11", 5.0, GlobalItem.getItem("I14", 2), 2));
        surowce.add(new Items("12", 5.0, GlobalItem.getItem("I15", 2), 2));
        surowce.add(new Items("13", 6.0, GlobalItem.getItem("I12", 2), 2));
        surowce.add(new Items("14", 6.0, GlobalItem.getItem("I13", 2), 2));
        // SZANSA NA 1
        surowce.add(new Items("15", 11.5, GlobalItem.getItem("I18", 1), 1));
        surowce.add(new Items("16", 12.0, GlobalItem.getItem("I16", 1), 1));
        surowce.add(new Items("17", 12.0, GlobalItem.getItem("I17", 1), 1));
        surowce.add(new Items("18", 14.0, GlobalItem.getItem("I14", 1), 1));
        surowce.add(new Items("19", 14.0, GlobalItem.getItem("I15", 1), 1));
        surowce.add(new Items("20", 16.0, GlobalItem.getItem("I12", 1), 1));
        surowce.add(new Items("21", 16.0, GlobalItem.getItem("I13", 1), 1));
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
