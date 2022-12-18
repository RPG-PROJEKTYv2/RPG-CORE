package rpg.rpgcore.chests.Expowisko4;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PrzekletaDuszaManager {

    private final Set<Items> przekletadusza = Sets.newConcurrentHashSet();

    public PrzekletaDuszaManager() {
        this.przekletadusza.add(new Items("1", 8.0, ItemHelper.createArmor("&f&lHelm Przekletej Duszy", Material.CHAINMAIL_HELMET, 25, 0,false), 1));
        this.przekletadusza.add(new Items("2", 8.0, ItemHelper.createArmor("&f&lZbroja Przekletej Duszy", Material.CHAINMAIL_CHESTPLATE, 25, 0,false), 1));
        this.przekletadusza.add(new Items("3", 8.0, ItemHelper.createArmor("&f&lSpodnie Przekletej Duszy", Material.CHAINMAIL_LEGGINGS, 25, 0,false), 1));
        this.przekletadusza.add(new Items("4", 8.0, ItemHelper.createArmor("&f&lButy Przekletej Duszy", Material.CHAINMAIL_BOOTS, 25, 0,false), 1));
        this.przekletadusza.add(new Items("5", 6.0, ItemHelper.createSword("&f&lMiecz Przekletej Duszy", Material.STONE_SWORD, 15, 8,false), 1));
        this.przekletadusza.add(new Items("6", 2.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&f&lNaszyjnik Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("7", 2.0, new ItemBuilder(Material.WATCH).setName("&f&lDiadem Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("8", 2.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&f&lPierscien Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("9", 2.0, new ItemBuilder(Material.ITEM_FRAME).setName("&f&lTarcza Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("10", 2.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&f&lKolczyki Przekletej Duszy").toItemStack(),1 ));

    }

    public Items getDrawnItems(final Player player) {
        for (Items item : this.przekletadusza) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
