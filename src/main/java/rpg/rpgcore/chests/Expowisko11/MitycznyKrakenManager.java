package rpg.rpgcore.chests.Expowisko11;

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

public class MitycznyKrakenManager {

    private final Set<Items> mitycznykraken = Sets.newConcurrentHashSet();

    public MitycznyKrakenManager() {
        this.mitycznykraken.add(new Items("1", 0.50, new ItemBuilder(Material.STORAGE_MINECART).setName("&b&lUlepszony Naszyjnik Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("2", 0.50, new ItemBuilder(Material.WATCH).setName("&b&lUlepszony Diadem Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("3", 0.50, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&b&lUlepszony Pierscien Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("4", 0.50, new ItemBuilder(Material.ITEM_FRAME).setName("&b&lUlepszona Tarcza Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("5", 0.50, new ItemBuilder(Material.HOPPER_MINECART).setName("&b&lUlepszone Kolczyki Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("6", 3.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&b&lZwykly Naszyjnik Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("7", 3.0, new ItemBuilder(Material.WATCH).setName("&b&lZwykly Diadem Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("8", 3.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&b&lZwykly Pierscien Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("9", 3.0, new ItemBuilder(Material.ITEM_FRAME).setName("&b&lZwykla Tarcza Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("10", 3.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&b&lZwykle Kolczyki Mitycznego Krakena").toItemStack(),1 ));
        this.mitycznykraken.add(new Items("11", 6.0, ItemHelper.createSword("&b&lMiecz Mitycznego Krakena", Material.DIAMOND_SWORD, 50, 40,false), 1));
        this.mitycznykraken.add(new Items("12", 8.0, ItemHelper.createArmor("&b&lHelm Mitycznego Krakena", Material.DIAMOND_HELMET, 75, 17), 1));
        this.mitycznykraken.add(new Items("13", 8.0, ItemHelper.createArmor("&b&lZbroja Mitycznego Krakena", Material.DIAMOND_CHESTPLATE, 78, 18), 1));
        this.mitycznykraken.add(new Items("14", 8.0, ItemHelper.createArmor("&b&lSpodnie Mitycznego Krakena", Material.DIAMOND_LEGGINGS, 78, 17), 1));
        this.mitycznykraken.add(new Items("15", 8.0, ItemHelper.createArmor("&b&lButy Mitycznego Krakena", Material.DIAMOND_BOOTS, 72, 17), 1));
    }

    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.mitycznykraken) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
