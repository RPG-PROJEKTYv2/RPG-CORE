package rpg.rpgcore.chests.Expowisko13;

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

public class StarozytnySmoczyCesarzManager {
    private final Set<Items> starozytnycesarz = Sets.newConcurrentHashSet();

    public StarozytnySmoczyCesarzManager() {
        this.starozytnycesarz.add(new Items("1", 5.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&5&lKolczyki Smoczego Cesarza").toItemStack(), 1));
        this.starozytnycesarz.add(new Items("2", 6.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&5&lNaszyjnik Smoczego Cesarza").toItemStack(), 1));
        this.starozytnycesarz.add(new Items("3", 7.0, new ItemBuilder(Material.WATCH).setName("&5&lDiadem Smoczego Cesarza").toItemStack(), 1));
        this.starozytnycesarz.add(new Items("4", 8.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&5&lPierscien Smoczego Cesarza").toItemStack(), 1));
        this.starozytnycesarz.add(new Items("5", 9.0, new ItemBuilder(Material.ITEM_FRAME).setName("&5&lTarcza Smoczego Cesarza").toItemStack(), 1));
        this.starozytnycesarz.add(new Items("6", 14.0, ItemHelper.createSword("&5&lMiecz Smoczego Cesarza", Material.DIAMOND_SWORD, 80, 50, false), 1));
        this.starozytnycesarz.add(new Items("7", 16.0, ItemHelper.createArmor("&5&lHelm Smoczego Cesarza", Material.DIAMOND_HELMET, 95, 20), 1));
        this.starozytnycesarz.add(new Items("8", 16.0, ItemHelper.createArmor("&5&lZbroja Smoczego Cesarza", Material.DIAMOND_CHESTPLATE, 99, 20), 1));
        this.starozytnycesarz.add(new Items("9", 16.0, ItemHelper.createArmor("&5&lSpodnie Smoczego Cesarza", Material.DIAMOND_LEGGINGS, 99, 20), 1));
        this.starozytnycesarz.add(new Items("10", 16.0, ItemHelper.createArmor("&5&lButy Smoczego Cesarza", Material.DIAMOND_BOOTS, 95, 20), 1));
    }

    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.starozytnycesarz) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
