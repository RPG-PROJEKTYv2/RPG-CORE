package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class HellcaseManager {

    private final Set<Items> hellcase = Sets.newConcurrentHashSet();

    public HellcaseManager() {
        // ZBROJA- DO DOKONCZENIA I MIECZ
        this.hellcase.add(new Items("1", 12.0 , ItemHelper.createArmor("&c&lPiekielny Helm", Material.IRON_HELMET, 34, 9), 1));
        this.hellcase.add(new Items("2", 12.0 , ItemHelper.createArmor("&c&lPiekielny Napiersnik", Material.IRON_CHESTPLATE, 26, 7), 1));
        this.hellcase.add(new Items("3", 12.0 , ItemHelper.createArmor("&c&lPiekielne Nogawice", Material.IRON_LEGGINGS, 36, 15), 1));
        this.hellcase.add(new Items("4", 12.0 , ItemHelper.createArmor("&c&lPiekielne Buty", Material.IRON_BOOTS, 33, 7), 1));
        this.hellcase.add(new Items("5", 8.0, ItemHelper.createSword("&4&lPiekielny Miecz", Material.IRON_SWORD, 40, 10, true), 1));
        // WALUTA
        this.hellcase.add(new Items("6", 4.0, GlobalItem.getHells(5),1));
        this.hellcase.add(new Items("7", 3.0, GlobalItem.getHells(10),1));
        this.hellcase.add(new Items("8", 1.0, GlobalItem.getHells(20),1));
        this.hellcase.add(new Items("9", 0.1, GlobalItem.getHells(30),1));
        // SKRZYNKA
        this.hellcase.add(new Items("10", 5.0, GlobalItem.getItem("I6",1), 2));

    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.hellcase) {
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
