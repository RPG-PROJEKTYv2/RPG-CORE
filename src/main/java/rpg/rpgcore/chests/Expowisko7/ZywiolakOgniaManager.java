package rpg.rpgcore.chests.Expowisko7;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ZywiolakOgniaManager {

    private final Set<Items> zywiolak = Sets.newConcurrentHashSet();

    public ZywiolakOgniaManager() {
        this.zywiolak.add(new Items("1", 18.0, ItemHelper.createArmor("&4Helm Zywiolaka Ognia", Material.IRON_HELMET, 29, 5), 1));
        this.zywiolak.add(new Items("2", 18.0, ItemHelper.createArmor("&4Zbroja Zywiolaka Ognia", Material.IRON_CHESTPLATE, 34, 7), 1));
        this.zywiolak.add(new Items("3", 18.0, ItemHelper.createArmor("&4Spodnie Zywiolaka Ognia", Material.IRON_LEGGINGS, 32, 6), 1));
        this.zywiolak.add(new Items("4", 18.0, ItemHelper.createArmor("&4Buty Zywiolaka Ognia", Material.IRON_BOOTS, 31, 5), 1));
        this.zywiolak.add(new Items("5", 16.0, ItemHelper.createSword("&4Miecz Zywiolaka Ognia", Material.IRON_SWORD, 26, 15,false), 1));
    }

    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.zywiolak) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
