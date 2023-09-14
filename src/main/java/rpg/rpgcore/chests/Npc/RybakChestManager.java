package rpg.rpgcore.chests.Npc;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RybakChestManager {
    private final Set<Items> rybaknpc = Sets.newConcurrentHashSet();

    public RybakChestManager() {
        this.rybaknpc.add(new Items("1", 1.5, RybakItems.I5.getItemStack(), 1));

        this.rybaknpc.add(new Items("2", 2.0, GlobalItem.I10.getItemStack(), 1));
        this.rybaknpc.add(new Items("3", 2.5, GlobalItem.I_OCZYSZCZENIE.getItemStack(), 1));

        this.rybaknpc.add(new Items("4", 4.0, RybakItems.I14.getItemStack(), 1));
        this.rybaknpc.add(new Items("5", 5.0, RybakItems.I10.getItemStack(), 1));
        this.rybaknpc.add(new Items("6", 6.0, RybakItems.I9.getItemStack(), 1));
        this.rybaknpc.add(new Items("7", 7.0, RybakItems.I8.getItemStack(), 1));
        this.rybaknpc.add(new Items("8", 8.0, RybakItems.I7.getItemStack(), 1));
        this.rybaknpc.add(new Items("9", 9.0, RybakItems.I6.getItemStack(), 1));
    }


    public Items getDrawnItems(final Player player) {
        for (Items item : this.rybaknpc) {
            if (ChanceHelper.getChance(item.getChance())) {
                final Items clone = item.clone();

                if (clone.getRewardItem().isSimilar(RybakItems.I5.getItemStack())) clone.setAmount(ChanceHelper.getRandInt(1, 2));
                else if (clone.getRewardItem().isSimilar(GlobalItem.I10.getItemStack())) clone.setAmount(ChanceHelper.getRandInt(1, 2));
                else if (clone.getRewardItem().isSimilar(GlobalItem.I_OCZYSZCZENIE.getItemStack())) clone.setAmount(ChanceHelper.getRandInt(1, 2));
                else if (clone.getName().equals("4") || clone.getName().equals("5") || clone.getName().equals("6")) clone.setAmount(ChanceHelper.getRandInt(1, 2));
                else if (clone.getName().equals("7")) clone.setAmount(ChanceHelper.getRandInt(1, 3));
                else if (clone.getName().equals("8")) clone.setAmount(ChanceHelper.getRandInt(1, 4));
                else if (clone.getName().equals("9")) clone.setAmount(ChanceHelper.getRandInt(1, 5));

                clone.getRewardItem().setAmount(clone.getAmount());


                player.sendMessage(Utils.format("&2+ &fx" + clone.getAmount() + " " + clone.getRewardItem().getItemMeta().getDisplayName()));
                return clone;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
