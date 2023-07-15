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

public class CiezkaSkrzyniaKowalaManager {

    private final Set<Items> kowal = Sets.newConcurrentHashSet();

    public CiezkaSkrzyniaKowalaManager() {
        // metale
        this.kowal.add(new Items("1", 4.0, GlobalItem.getItem("I_METAL", 1), 1));
        this.kowal.add(new Items("2", 3.0, GlobalItem.getItem("I_METAL", 1), 2));
        this.kowal.add(new Items("3", 2.0, GlobalItem.getItem("I_METAL", 1), 3));
        // zwoje
        this.kowal.add(new Items("4", 16.0, GlobalItem.getItem("I10", 1), 1));
        this.kowal.add(new Items("5", 12.0, GlobalItem.getItem("I10", 1), 2));
        this.kowal.add(new Items("6", 8.0, GlobalItem.getItem("I10", 1), 3));
        // oczyszczenie
        this.kowal.add(new Items("7", 8.0, GlobalItem.getItem("I_OCZYSZCZENIE", 1), 1));
        this.kowal.add(new Items("8", 5.0, GlobalItem.getItem("I_OCZYSZCZENIE", 1),2));
        this.kowal.add(new Items("9", 2.5, GlobalItem.getItem("I_OCZYSZCZENIE", 1), 3));
        // skrzynki
        this.kowal.add(new Items("10", 1.0, GlobalItem.getItem("I1", 1),1));
        this.kowal.add(new Items("11", 0.05, GlobalItem.getItem("I6", 1),1));
    }

    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.kowal) {
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
