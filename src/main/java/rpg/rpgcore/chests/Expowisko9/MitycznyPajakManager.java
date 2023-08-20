package rpg.rpgcore.chests.Expowisko9;

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

public class MitycznyPajakManager {

    private final Set<Items> mitycznypajak = Sets.newConcurrentHashSet();

    public MitycznyPajakManager() {
        this.mitycznypajak.add(new Items("1", 5.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&e&lKolczyki Mitycznego Pajaka").toItemStack(),1 ));
        this.mitycznypajak.add(new Items("2", 6.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&e&lNaszyjnik Mitycznego Pajaka").toItemStack(),1 ));
        this.mitycznypajak.add(new Items("3", 7.0, new ItemBuilder(Material.WATCH).setName("&e&lDiadem Mitycznego Pajaka").toItemStack(),1 ));
        this.mitycznypajak.add(new Items("4", 8.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&e&lPierscien Mitycznego Pajaka").toItemStack(),1 ));
        this.mitycznypajak.add(new Items("5", 9.0, new ItemBuilder(Material.ITEM_FRAME).setName("&e&lTarcza Mitycznego Pajaka").toItemStack(),1 ));
        this.mitycznypajak.add(new Items("6", 14.0, ItemHelper.createSword("&e&lMiecz Mitycznego Pajaka", Material.DIAMOND_SWORD, 40, 30,false), 1));
        this.mitycznypajak.add(new Items("7", 16.0, ItemHelper.createArmor("&e&lHelm Mitycznego Pajaka", Material.DIAMOND_HELMET, 65, 22), 1));
        this.mitycznypajak.add(new Items("8", 16.0, ItemHelper.createArmor("&e&lZbroja Mitycznego Pajaka", Material.DIAMOND_CHESTPLATE, 68, 15), 1));
        this.mitycznypajak.add(new Items("9", 16.0, ItemHelper.createArmor("&e&lSpodnie Mitycznego Pajaka", Material.DIAMOND_LEGGINGS, 66, 15), 1));
        this.mitycznypajak.add(new Items("10", 16.0, ItemHelper.createArmor("&e&lButy Mitycznego Pajaka", Material.DIAMOND_BOOTS, 60, 14), 1));
    }

    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.mitycznypajak) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
