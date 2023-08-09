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
        this.pozlacanyskarb.add(new Items("1", 14.0, GlobalItem.getItem("I_FRAGMENT_STALI", 1), 2));
        this.pozlacanyskarb.add(new Items("2", 12.0, GlobalItem.getItem("I_CZASTKA_MAGII", 2), 2));
        // zmianki
        this.pozlacanyskarb.add(new Items("3", 11.0, GlobalItem.getItem("I50", 1), 1));
        this.pozlacanyskarb.add(new Items("4", 11.0, GlobalItem.getItem("I50", 2), 2));
        // bao kamyk
        // bao ksiega
        this.pozlacanyskarb.add(new Items("5", 9.5, GlobalItem.getItem("I_KAMIENBAO", 1), 1));
        this.pozlacanyskarb.add(new Items("6", 8.0, GlobalItem.getItem("I_KSIEGAMAGII", 1), 1));
        // podrecznik
        // lza
        // metal
        this.pozlacanyskarb.add(new Items("7", 8.0, GlobalItem.getItem("I10", 1), 1));
        this.pozlacanyskarb.add(new Items("8", 7.5, GlobalItem.getItem("I_OCZYSZCZENIE", 1),1));

        this.pozlacanyskarb.add(new Items("9", 7.0, LesnikItems.getByItem("POTION", 1), 1));

        this.pozlacanyskarb.add(new Items("10", 5.5, GlobalItem.getItem("I_METAL", 1), 1));
        // pota do lesnika
        // fragment bona
        this.pozlacanyskarb.add(new Items("11", 0.4, GlobalItem.getItem("I_FRAGMENT_BONA", 1), 1));
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
