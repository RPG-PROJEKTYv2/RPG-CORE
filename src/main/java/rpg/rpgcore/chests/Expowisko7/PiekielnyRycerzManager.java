package rpg.rpgcore.chests.Expowisko7;

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

public class PiekielnyRycerzManager {

    private final Set<Items> przekletyrycerz = Sets.newConcurrentHashSet();

    public PiekielnyRycerzManager() {
        this.przekletyrycerz.add(new Items("1", 7.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&c&lKolczyki Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("2", 8.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&c&lNaszyjnik Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("3", 9.0, new ItemBuilder(Material.WATCH).setName("&c&lDiadem Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("4", 10.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&c&lPierscien Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("5", 11.0, new ItemBuilder(Material.ITEM_FRAME).setName("&c&lTarcza Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("6", 15.0, ItemHelper.createSword("&c&lMiecz Piekielnego Rycerza", Material.DIAMOND_SWORD, 32, 22,false), 1));
        this.przekletyrycerz.add(new Items("7", 17.0, ItemHelper.createArmor("&c&lHelm Piekielnego Rycerza", Material.DIAMOND_HELMET, 55, 12), 1));
        this.przekletyrycerz.add(new Items("8", 17.0, ItemHelper.createArmor("&c&lZbroja Piekielnego Rycerza", Material.DIAMOND_CHESTPLATE, 57, 14), 1));
        this.przekletyrycerz.add(new Items("9", 17.0, ItemHelper.createArmor("&c&lSpodnie Piekielnego Rycerza", Material.DIAMOND_LEGGINGS, 60, 16), 1));
        this.przekletyrycerz.add(new Items("10", 17.0, ItemHelper.createArmor("&c&lButy Piekielnego Rycerza", Material.DIAMOND_BOOTS, 55, 13), 1));

    }

    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.przekletyrycerz) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
