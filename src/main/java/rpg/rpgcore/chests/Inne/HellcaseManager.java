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
        this.hellcase.add(new Items("1", 12.0 , ItemHelper.createArmor("&c&lPiekielny Helm", Material.IRON_HELMET, 22, 0), 1));
        this.hellcase.add(new Items("2", 12.0 , ItemHelper.createArmor("&c&lPiekielny Napiersnik", Material.IRON_CHESTPLATE, 22, 0), 1));
        this.hellcase.add(new Items("3", 12.0 , ItemHelper.createArmor("&c&lPiekielne Nogawice", Material.IRON_LEGGINGS, 22, 0), 1));
        this.hellcase.add(new Items("4", 12.0 , ItemHelper.createArmor("&c&lPiekielne Buty", Material.IRON_BOOTS, 22, 0), 1));
        this.hellcase.add(new Items("5", 8.0, ItemHelper.createSword("&4&lPiekielny Miecz", Material.IRON_SWORD, 20, 10, true), 1));
        // AKCE - DO DOKONCZENIA
        //this.hellcase.add(new Items("7", 6.0, AkcesoriaPodsHelper.createTarcza(5.0, 5.3, 5, 5, "test tarcza"), 1));
        //this.hellcase.add(new Items("8", 25.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&7Test").toItemStack(), 1));
        // WALUTA
        this.hellcase.add(new Items("15", 4.0, GlobalItem.getHells(5),1));
        this.hellcase.add(new Items("16", 3.0, GlobalItem.getHells(10),1));
        this.hellcase.add(new Items("16", 1.0, GlobalItem.getHells(20),1));
        this.hellcase.add(new Items("16", 0.1, GlobalItem.getHells(30),1));

    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.hellcase) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
