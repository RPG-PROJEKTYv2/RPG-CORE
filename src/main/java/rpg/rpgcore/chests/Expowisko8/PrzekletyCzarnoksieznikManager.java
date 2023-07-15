package rpg.rpgcore.chests.Expowisko8;

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

public class PrzekletyCzarnoksieznikManager {

    private final Set<Items> przekletyczarnoksieznik = Sets.newConcurrentHashSet();

    public PrzekletyCzarnoksieznikManager() {
        this.przekletyczarnoksieznik.add(new Items("1", 0.65, new ItemBuilder(Material.STORAGE_MINECART).setName("&5&lUlepszony Naszyjnik Przekletego Czarnoksieznika").toItemStack(),1 ));
        this.przekletyczarnoksieznik.add(new Items("2", 0.65, new ItemBuilder(Material.WATCH).setName("&5&lUlepszony Diadem Przekletego Czarnoksieznika").toItemStack(),1 ));
        this.przekletyczarnoksieznik.add(new Items("3", 0.65, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&5&lUlepszony Pierscien Przekletego Czarnoksieznika").toItemStack(),1 ));
        this.przekletyczarnoksieznik.add(new Items("4", 0.65, new ItemBuilder(Material.ITEM_FRAME).setName("&5&lUlepszona Tarcza Przekletego Czarnoksieznika").toItemStack(),1 ));
        this.przekletyczarnoksieznik.add(new Items("5", 0.65, new ItemBuilder(Material.HOPPER_MINECART).setName("&5&lUlepszone Kolczyki Przekletego Czarnoksieznika").toItemStack(),1 ));
        this.przekletyczarnoksieznik.add(new Items("6", 4.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&5&lZwykly Naszyjnik Przekletego Czarnoksieznika").toItemStack(),1 ));
        this.przekletyczarnoksieznik.add(new Items("7", 4.0, new ItemBuilder(Material.WATCH).setName("&5&lZwykly Diadem Przekletego Czarnoksieznika").toItemStack(),1 ));
        this.przekletyczarnoksieznik.add(new Items("8", 4.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&5&lZwykly Pierscien Przekletego Czarnoksieznika").toItemStack(),1 ));
        this.przekletyczarnoksieznik.add(new Items("9", 4.0, new ItemBuilder(Material.ITEM_FRAME).setName("&5&lZwykla Tarcza Przekletego Czarnoksieznika").toItemStack(),1 ));
        this.przekletyczarnoksieznik.add(new Items("10", 4.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&5&lZwykle Kolczyki Przekletego Czarnoksieznika").toItemStack(),1 ));
        this.przekletyczarnoksieznik.add(new Items("11", 8.0, ItemHelper.createSword("&5&lMiecz Przekletego Czarnoksieznika", Material.DIAMOND_SWORD, 38, 23,false), 1));
        this.przekletyczarnoksieznik.add(new Items("12", 10.0, ItemHelper.createArmor("&5&lHelm Przekletego Czarnoksieznika", Material.DIAMOND_HELMET, 59, 14), 1));
        this.przekletyczarnoksieznik.add(new Items("13", 10.0, ItemHelper.createArmor("&5&lZbroja Przekletego Czarnoksieznika", Material.DIAMOND_CHESTPLATE, 60, 20), 1));
        this.przekletyczarnoksieznik.add(new Items("14", 10.0, ItemHelper.createArmor("&5&lSpodnie Przekletego Czarnoksieznika", Material.DIAMOND_LEGGINGS, 60, 15), 1));
        this.przekletyczarnoksieznik.add(new Items("15", 10.0, ItemHelper.createArmor("&5&lButy Przekletego Czarnoksieznika", Material.DIAMOND_BOOTS, 58, 13), 1));
    }

    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.przekletyczarnoksieznik) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
