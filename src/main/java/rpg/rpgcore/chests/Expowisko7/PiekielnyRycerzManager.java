package rpg.rpgcore.chests.Expowisko7;

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

public class PiekielnyRycerzManager {

    private final Set<Items> przekletyrycerz = Sets.newConcurrentHashSet();

    public PiekielnyRycerzManager() {  
        this.przekletyrycerz.add(new Items("1", 0.75, new ItemBuilder(Material.STORAGE_MINECART).setName("&c&lUlepszony Naszyjnik Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("2", 0.75, new ItemBuilder(Material.WATCH).setName("&c&lUlepszony Diadem Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("3", 0.75, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&c&lUlepszony Pierscien Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("4", 0.75, new ItemBuilder(Material.ITEM_FRAME).setName("&c&lUlepszona Tarcza Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("5", 0.75, new ItemBuilder(Material.HOPPER_MINECART).setName("&c&lUlepszone Kolczyki Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("6", 5.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&c&lZwykly Naszyjnik Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("7", 5.0, new ItemBuilder(Material.WATCH).setName("&c&lZwykly Diadem Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("8", 5.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&c&lZwykly Pierscien Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("9", 5.0, new ItemBuilder(Material.ITEM_FRAME).setName("&c&lZwykly Tarcza Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("10", 5.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&c&lZwykly Kolczyki Piekielnego Rycerza").toItemStack(),1 ));
        this.przekletyrycerz.add(new Items("11", 9.0, ItemHelper.createSword("&c&lMiecz Przekletego Rycerza", Material.DIAMOND_SWORD, 32, 22,false), 1));
        this.przekletyrycerz.add(new Items("12", 12.0, ItemHelper.createArmor("&c&lHelm Przekletego Rycerza", Material.DIAMOND_HELMET, 55, 12), 1));
        this.przekletyrycerz.add(new Items("13", 12.0, ItemHelper.createArmor("&c&lZbroja Przekletego Rycerza", Material.DIAMOND_CHESTPLATE, 57, 14), 1));
        this.przekletyrycerz.add(new Items("14", 12.0, ItemHelper.createArmor("&c&lSpodnie Przekletego Rycerza", Material.DIAMOND_LEGGINGS, 60, 20), 1));
        this.przekletyrycerz.add(new Items("15", 12.0, ItemHelper.createArmor("&c&lButy Przekletego Rycerza", Material.DIAMOND_BOOTS, 55, 13), 1));

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
