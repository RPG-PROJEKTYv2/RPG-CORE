package rpg.rpgcore.chests.Npc;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GornikChestManager {
    private final Set<Items> gorniknpc = Sets.newConcurrentHashSet();

    public GornikChestManager() {
        this.gorniknpc.add(new Items("1", 2.5, GlobalItem.I_METAL.getItemStack(), 1));
        this.gorniknpc.add(new Items("2", 3.0, GlobalItem.I10.getItemStack(), 1));
        this.gorniknpc.add(new Items("3", 3.0, GornikItems.getItem("Ruda_Runinu", 1), 5));
        this.gorniknpc.add(new Items("4", 3.2, GornikItems.getItem("Skrzynia_Gornika", 1), 3));
        this.gorniknpc.add(new Items("5", 3.5, GlobalItem.I_OCZYSZCZENIE.getItemStack(), 1));
        this.gorniknpc.add(new Items("6", 3.5, GornikItems.getItem("Ruda_Diamentu", 1), 5));
        this.gorniknpc.add(new Items("7", 4.0, GornikItems.getItem("Ruda_Runinu", 1), 3));
        this.gorniknpc.add(new Items("8", 4.0, GornikItems.getItem("Ruda_Jadeitu", 1), 5));
        this.gorniknpc.add(new Items("9", 4.0, GornikItems.getItem("Skrzynia_Gornika", 1), 2));
        this.gorniknpc.add(new Items("10", 4.5, GornikItems.getItem("Ruda_Tanzanitu", 1), 5));
        this.gorniknpc.add(new Items("11", 5.0, GornikItems.getItem("Ruda_Topazu", 1), 5));
        this.gorniknpc.add(new Items("12", 5.0, GornikItems.getItem("Ruda_Diamentu", 1), 3));
        this.gorniknpc.add(new Items("13", 5.5, GornikItems.getItem("Ruda_Zelaza", 1), 5));
        this.gorniknpc.add(new Items("14", 6.0, GornikItems.getItem("Ruda_Jadeitu", 1), 3));
        this.gorniknpc.add(new Items("15", 6.0, GornikItems.getItem("Ruda_Wegla", 1), 5));
        this.gorniknpc.add(new Items("16", 7.0, GornikItems.getItem("Ruda_Tanzanitu", 1), 3));
        this.gorniknpc.add(new Items("17", 8.0, GornikItems.getItem("Ruda_Topazu", 1), 3));
        this.gorniknpc.add(new Items("18", 9.0, GornikItems.getItem("Ruda_Zelaza", 1), 3));
        this.gorniknpc.add(new Items("19", 10.0, GornikItems.getItem("Ruda_Wegla", 1), 3));
    }


    public Items getDrawnItems(final Player player) {
        for (Items item : this.gorniknpc) {
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
