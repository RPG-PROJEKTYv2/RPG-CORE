package rpg.rpgcore.chests.Expowisko5;

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

public class TrytonManager {

    private final Set<Items> tryton = Sets.newConcurrentHashSet();

    public TrytonManager() {
        this.tryton.add(new Items("1", 8.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&5&lNaszyjnik Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("2", 9.0, new ItemBuilder(Material.WATCH).setName("&5&lDiadem Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("3", 10.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&5&lPierscien Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("4", 11.0, new ItemBuilder(Material.ITEM_FRAME).setName("&5&lTarcza Trytona").toItemStack(),1 ));
        this.tryton.add(new Items("5", 15.0, ItemHelper.createSword("&6&lMiecz Trytona", Material.IRON_SWORD, 23, 15,false), 1));
        this.tryton.add(new Items("6", 17.0, ItemHelper.createArmor("&6&lHelm Trytona", Material.IRON_HELMET, 35, 9), 1));
        this.tryton.add(new Items("7", 17.0, ItemHelper.createArmor("&6&lZbroja Trytona", Material.IRON_CHESTPLATE, 30, 7), 1));
        this.tryton.add(new Items("8", 17.0, ItemHelper.createArmor("&6&lSpodnie Trytona", Material.IRON_LEGGINGS, 34, 15), 1));
        this.tryton.add(new Items("9", 17.0, ItemHelper.createArmor("&6&lButy Trytona", Material.IRON_BOOTS, 33, 8), 1));

    }

    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.tryton) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
