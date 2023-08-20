package rpg.rpgcore.chests.Expowisko2;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GoblinManager {

    private final Set<Items> goblin = Sets.newConcurrentHashSet();

    public GoblinManager() {
        this.goblin.add(new Items("1", 16.0, ItemHelper.createSword("&2Miecz Goblina", Material.WOOD_SWORD, 3, 2,false), 1));
        this.goblin.add(new Items("2", 18.0, ItemHelper.createArmor("&2Helm Goblina", Material.LEATHER_HELMET, 8, 2), 1));
        this.goblin.add(new Items("3", 18.0, ItemHelper.createArmor("&2Zbroja Goblina", Material.LEATHER_CHESTPLATE, 7, 1), 1));
        this.goblin.add(new Items("4", 18.0, ItemHelper.createArmor("&2Spodnie Goblina", Material.LEATHER_LEGGINGS, 7, 1), 1));
        this.goblin.add(new Items("5", 18.0, ItemHelper.createArmor("&2Buty Goblina", Material.LEATHER_BOOTS, 5, 1), 1));
    }


    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.goblin) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
