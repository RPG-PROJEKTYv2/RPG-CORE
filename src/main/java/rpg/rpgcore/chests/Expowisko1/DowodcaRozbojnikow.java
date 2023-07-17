package rpg.rpgcore.chests.Expowisko1;

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

public class DowodcaRozbojnikow {

    private final Set<Items> wygnaniec = Sets.newConcurrentHashSet();

    public DowodcaRozbojnikow() {
        this.wygnaniec.add(new Items("9", 9.0, ItemHelper.createSword("&c&lMiecz Dowodcy Rozbojnikow", Material.WOOD_SWORD, 3, 2, false), 1));
        this.wygnaniec.add(new Items("10", 12.0, ItemHelper.createArmor("&c&lHelm Dowodcy Rozbojnikow", Material.LEATHER_HELMET, 4, 1), 1));
        this.wygnaniec.add(new Items("11", 12.0, ItemHelper.createArmor("&c&lZbroja Dowodcy Rozbojnikow", Material.LEATHER_CHESTPLATE, 5, 1), 1));
        this.wygnaniec.add(new Items("12", 12.0, ItemHelper.createArmor("&c&lSpodnie Dowodcy Rozbojnikow", Material.LEATHER_LEGGINGS, 4, 1), 1));
        this.wygnaniec.add(new Items("13", 12.0, ItemHelper.createArmor("&c&lButy Dowodcy Rozbojnikow", Material.LEATHER_BOOTS, 3, 1), 1));


    }


    public void getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.wygnaniec) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        this.getDrawnItems(player);
    }
}
