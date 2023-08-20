package rpg.rpgcore.chests.Expowisko8;

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

public class MrocznaDuszaManager {

    private final Set<Items> mrocznadusza = Sets.newConcurrentHashSet();

    public MrocznaDuszaManager() {
        this.mrocznadusza.add(new Items("1", 16.0, ItemHelper.createSword("&7Miecz Mrocznej Duszy", Material.DIAMOND_SWORD, 32, 18,false), 1));
        this.mrocznadusza.add(new Items("2", 18.0, ItemHelper.createArmor("&7Helm Mrocznej Duszy", Material.IRON_HELMET, 35, 6), 1));
        this.mrocznadusza.add(new Items("3", 18.0, ItemHelper.createArmor("&7Zbroja Mrocznej Duszy", Material.DIAMOND_CHESTPLATE, 35, 7), 1));
        this.mrocznadusza.add(new Items("4", 18.0, ItemHelper.createArmor("&7Spodnie Mrocznej Duszy", Material.IRON_LEGGINGS, 35, 7), 1));
        this.mrocznadusza.add(new Items("5", 18.0, ItemHelper.createArmor("&7Buty Mrocznej Duszy", Material.DIAMOND_BOOTS, 35, 6), 1));
    }

    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.mrocznadusza) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
