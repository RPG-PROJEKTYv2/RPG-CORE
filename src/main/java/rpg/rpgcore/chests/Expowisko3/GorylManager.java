package rpg.rpgcore.chests.Expowisko3;

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

public class GorylManager {


    private final Set<Items> goryl = Sets.newConcurrentHashSet();

    public GorylManager() {
        this.goryl.add(new Items("1", 16.0, ItemHelper.createSword("&6Miecz Goryla", Material.STONE_SWORD, 5, 3,false), 1));
        this.goryl.add(new Items("2", 18.0, ItemHelper.createArmor("&6Helm Goryla", Material.LEATHER_HELMET, 12, 2), 1));
        this.goryl.add(new Items("3", 18.0, ItemHelper.createArmor("&6Zbroja Goryla", Material.CHAINMAIL_CHESTPLATE, 11, 2), 1));
        this.goryl.add(new Items("4", 18.0, ItemHelper.createArmor("&6Spodnie Goryla", Material.LEATHER_LEGGINGS, 11, 2), 1));
        this.goryl.add(new Items("5", 18.0, ItemHelper.createArmor("&6Buty Goryla", Material.LEATHER_BOOTS, 11, 2), 1));
    }

    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.goryl) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }




}
