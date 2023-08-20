package rpg.rpgcore.chests.Expowisko4;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PrzekletaDuszaManager {

    private final Set<Items> przekletadusza = Sets.newConcurrentHashSet();

    public PrzekletaDuszaManager() {
        this.przekletadusza.add(new Items("1", 6.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&7&lNaszyjnik Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("2", 7.0, new ItemBuilder(Material.WATCH).setName("&7&lDiadem Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("3", 8.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&7&lPierscien Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("4", 9.0, new ItemBuilder(Material.ITEM_FRAME).setName("&7&lTarcza Przekletej Duszy").toItemStack(),1 ));
        this.przekletadusza.add(new Items("5", 14.0, ItemHelper.createSword("&7&lMiecz Przekletej Duszy", Material.STONE_SWORD, 15, 8,false), 1));
        this.przekletadusza.add(new Items("6", 16.0, ItemHelper.createArmor("&7&lHelm Przekletej Duszy", Material.CHAINMAIL_HELMET, 23, 12), 1));
        this.przekletadusza.add(new Items("7", 16.0, ItemHelper.createArmor("&7&lZbroja Przekletej Duszy", Material.CHAINMAIL_CHESTPLATE, 25, 5), 1));
        this.przekletadusza.add(new Items("8", 16.0, ItemHelper.createArmor("&7&lSpodnie Przekletej Duszy", Material.CHAINMAIL_LEGGINGS, 21, 5), 1));
        this.przekletadusza.add(new Items("9", 16.0, ItemHelper.createArmor("&7&lButy Przekletej Duszy", Material.CHAINMAIL_BOOTS, 20, 5), 1));
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
