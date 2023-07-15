package rpg.rpgcore.chests.Expowisko4;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PrzekletaDuszaManager {

    private final Set<Items> przekletadusza = Sets.newConcurrentHashSet();

    public PrzekletaDuszaManager() {
        this.przekletadusza.add(new Items("1", 0.75, new ItemBuilder(Material.STORAGE_MINECART).setName("&7&lUlepszony Naszyjnik Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("2", 0.75, new ItemBuilder(Material.WATCH).setName("&7&lUlepszony Diadem Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("3", 0.75, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&7&lUlepszony Pierscien Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("4", 0.75, new ItemBuilder(Material.ITEM_FRAME).setName("&7&lUlepszona Tarcza Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("5", 5.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&7&lZwykly Naszyjnik Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("6", 5.0, new ItemBuilder(Material.WATCH).setName("&7&lZwykly Diadem Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("7", 5.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&7&lZwykly Pierscien Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("8", 5.0, new ItemBuilder(Material.ITEM_FRAME).setName("&7&lZwykla Tarcza Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("9", 9.0, ItemHelper.createSword("&7&lMiecz Przekletej Duszy", Material.STONE_SWORD, 15, 8,false), 1));
        this.przekletadusza.add(new Items("10", 12.0, ItemHelper.createArmor("&7&lHelm Przekletej Duszy", Material.CHAINMAIL_HELMET, 23, 12), 1));
        this.przekletadusza.add(new Items("11", 12.0, ItemHelper.createArmor("&7&lZbroja Przekletej Duszy", Material.CHAINMAIL_CHESTPLATE, 25, 5), 1));
        this.przekletadusza.add(new Items("12", 12.0, ItemHelper.createArmor("&7&lSpodnie Przekletej Duszy", Material.CHAINMAIL_LEGGINGS, 21, 5), 1));
        this.przekletadusza.add(new Items("13", 12.0, ItemHelper.createArmor("&7&lButy Przekletej Duszy", Material.CHAINMAIL_BOOTS, 20, 5), 1));
    }

    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.przekletadusza) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
