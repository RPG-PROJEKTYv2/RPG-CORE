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
        this.przekletadusza.add(new Items("1", 12.0, ItemHelper.createArmor("&7&lHelm Przekletej Duszy", Material.CHAINMAIL_HELMET, 32, 12), 1));
        this.przekletadusza.add(new Items("2", 12.0, ItemHelper.createArmor("&7&lZbroja Przekletej Duszy", Material.CHAINMAIL_CHESTPLATE, 20, 5), 1));
        this.przekletadusza.add(new Items("3", 12.0, ItemHelper.createArmor("&7&lSpodnie Przekletej Duszy", Material.CHAINMAIL_LEGGINGS, 20, 5), 1));
        this.przekletadusza.add(new Items("4", 12.0, ItemHelper.createArmor("&7&lButy Przekletej Duszy", Material.CHAINMAIL_BOOTS, 20, 5), 1));
        this.przekletadusza.add(new Items("5", 9.0, ItemHelper.createSword("&7&lMiecz Przekletej Duszy", Material.STONE_SWORD, 15, 8,false), 1));
        this.przekletadusza.add(new Items("6", 5.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&7&lZwykly Naszyjnik Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("7", 5.0, new ItemBuilder(Material.WATCH).setName("&7&lZwykly Diadem Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("8", 5.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&7&lZwykly Pierscien Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("9", 5.0, new ItemBuilder(Material.ITEM_FRAME).setName("&7&lZwykla Tarcza Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("10", 0.75, new ItemBuilder(Material.STORAGE_MINECART).setName("&7&lUlepszony Naszyjnik Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("11", 0.75, new ItemBuilder(Material.WATCH).setName("&7&lUlepszony Diadem Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("12", 0.75, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&7&lUlepszony Pierscien Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("13", 0.75, new ItemBuilder(Material.ITEM_FRAME).setName("&7&lUlepszona Tarcza Przekletej Duszy").toItemStack(),1 ));
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
