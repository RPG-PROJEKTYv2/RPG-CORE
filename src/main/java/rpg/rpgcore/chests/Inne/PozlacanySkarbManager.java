package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PozlacanySkarbManager {


    private final Set<Items> pozlacanyskarb = Sets.newConcurrentHashSet();


    public PozlacanySkarbManager() {
        this.pozlacanyskarb.add(new Items("1", 0.4, GlobalItem.getItem("I_FRAGMENT_BONA", 1), 1));
        this.pozlacanyskarb.add(new Items("2", 2.5, SkrzynkiOther.getItem("I1", 1),2));
        this.pozlacanyskarb.add(new Items("3", 7.0, LesnikItems.getByItem("POTION", 1), 1));
        this.pozlacanyskarb.add(new Items("4", 9.0, GlobalItem.getItem("I_KAMIENBAO", 1), 1));
        this.pozlacanyskarb.add(new Items("5", 10.0, GlobalItem.getItem("I_KSIEGAMAGII", 1), 1));
        this.pozlacanyskarb.add(new Items("6", 11.0, GlobalItem.getItem("I_METAL", 1), 1));
        this.pozlacanyskarb.add(new Items("7", 13.0, GlobalItem.getItem("I_OCZYSZCZENIE", 1),1));
        this.pozlacanyskarb.add(new Items("8", 13.0, GlobalItem.getItem("I10", 1), 1));
        this.pozlacanyskarb.add(new Items("9", 13.5, GlobalItem.getItem("I50", 1), 2));
        this.pozlacanyskarb.add(new Items("10", 15.5, GlobalItem.getItem("I_CZASTKA_MAGII", 2), 2));
        this.pozlacanyskarb.add(new Items("11", 16.5, GlobalItem.getItem("I_FRAGMENT_STALI", 1), 2));
    }


    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.pozlacanyskarb ) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &fx" + item.getAmount() + " " + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
