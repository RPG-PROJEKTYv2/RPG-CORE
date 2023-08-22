package rpg.rpgcore.chests.Expowisko1;

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

public class DowodcaRozbojnikow {

    private final Set<Items> dowodcaRozbojnikow = Sets.newConcurrentHashSet();

    public DowodcaRozbojnikow() {
        this.dowodcaRozbojnikow.add(new Items("1", 6.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&c&lNaszyjnik Dowodcy Rozbojnikow").toItemStack(),1 ));
        this.dowodcaRozbojnikow.add(new Items("2", 7.0, new ItemBuilder(Material.WATCH).setName("&c&lDiadem Dowodcy Rozbojnikow").toItemStack(),1 ));
        this.dowodcaRozbojnikow.add(new Items("3", 8.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&c&lPierscien Dowodcy Rozbojnikow").toItemStack(),1 ));
        this.dowodcaRozbojnikow.add(new Items("4", 9.0, new ItemBuilder(Material.ITEM_FRAME).setName("&c&lTarcza Dowodcy Rozbojnikow").toItemStack(),1 ));
        this.dowodcaRozbojnikow.add(new Items("5", 14.0, ItemHelper.createSword("&c&lMiecz Dowodcy Rozbojnikow", Material.WOOD_SWORD, 3, 2, false), 1));
        this.dowodcaRozbojnikow.add(new Items("6", 16.0, ItemHelper.createArmor("&c&lHelm Dowodcy Rozbojnikow", Material.LEATHER_HELMET, 4, 1), 1));
        this.dowodcaRozbojnikow.add(new Items("7", 16.0, ItemHelper.createArmor("&c&lZbroja Dowodcy Rozbojnikow", Material.LEATHER_CHESTPLATE, 5, 1), 1));
        this.dowodcaRozbojnikow.add(new Items("8", 16.0, ItemHelper.createArmor("&c&lSpodnie Dowodcy Rozbojnikow", Material.LEATHER_LEGGINGS, 4, 1), 1));
        this.dowodcaRozbojnikow.add(new Items("9", 16.0, ItemHelper.createArmor("&c&lButy Dowodcy Rozbojnikow", Material.LEATHER_BOOTS, 3, 1), 1));


    }


    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.dowodcaRozbojnikow) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
