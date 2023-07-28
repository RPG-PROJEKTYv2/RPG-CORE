package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PozlacanySkarbManager {


    private final Set<Items> pozlacanyskarb = Sets.newConcurrentHashSet();


    public PozlacanySkarbManager() {
        // fragment stali i magii
        this.pozlacanyskarb.add(new Items("1", 12.0, GlobalItem.getItem("I_FRAGMENT_STALI", 1), 2));
        this.pozlacanyskarb.add(new Items("2", 10.0, GlobalItem.getItem("I_CZASTKA_MAGII", 1), 2));
        // zmianki
        this.pozlacanyskarb.add(new Items("3", 10.0, GlobalItem.getItem("I50", 1), 1));
        this.pozlacanyskarb.add(new Items("4", 10.0, GlobalItem.getItem("I50", 1), 2));
        // bao kamyk
        this.pozlacanyskarb.add(new Items("5", 7.0, GlobalItem.getItem("I_KAMIENBAO", 1), 1));
        // bao ksiega
        this.pozlacanyskarb.add(new Items("6", 4.0, GlobalItem.getItem("I_KSIEGAMAGII", 1), 1));
        // pota do lesnika
        this.pozlacanyskarb.add(new Items("7", 3.5, LesnikItems.getByItem("POTION", 1), 1));
        // fragment bona
        this.pozlacanyskarb.add(new Items("8", 0.40, GlobalItem.getItem("I_FRAGMENT_BONA", 1), 1));
    }


    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.pozlacanyskarb ) {
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
