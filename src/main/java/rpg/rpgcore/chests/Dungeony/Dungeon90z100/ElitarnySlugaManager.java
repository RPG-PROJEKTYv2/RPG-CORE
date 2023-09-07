package rpg.rpgcore.chests.Dungeony.Dungeon90z100;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ElitarnySlugaManager {

    private final Set<Items> elitarnySluga = Sets.newConcurrentHashSet();

    public ElitarnySlugaManager() {
        this.elitarnySluga.add(new Items("1", 6.0, new ItemBuilder(Material.MINECART).setName("&4&lEnergia Demona Ciemnosci").toItemStack(),1 ));
        this.elitarnySluga.add(new Items("2", 6.2, new ItemBuilder(Material.FIREBALL).setName("&4&lMedalion Demona Ciemnosci").toItemStack(),1 ));
        this.elitarnySluga.add(new Items("3", 7.0 , GlobalItem.I_KAMIENBAO.getItemStack(), ChanceHelper.getRandInt(1,5)));
        this.elitarnySluga.add(new Items("4", 8.5, GlobalItem.I10.getItemStack(), ChanceHelper.getRandInt(1,5)));
        this.elitarnySluga.add(new Items("5", 9.0, GlobalItem.I_OCZYSZCZENIE.getItemStack(), ChanceHelper.getRandInt(1,5)));
        this.elitarnySluga.add(new Items("6", 10.0, LesnikItems.POTION.getItem(), ChanceHelper.getRandInt(1,5)));
        this.elitarnySluga.add(new Items("7", 14.0, ItemHelper.createSword("&4&lMiecz Demona Ciemnosci", Material.DIAMOND_SWORD, 50, 50, false), 1));
        this.elitarnySluga.add(new Items("8", 16.0, ItemHelper.createArmor("&4&lHelm Demona Ciemnosci", Material.DIAMOND_HELMET, 75, 20), 1));
        this.elitarnySluga.add(new Items("9", 16.0, ItemHelper.createArmor("&4&lZbroja Demona Ciemnosci", Material.DIAMOND_CHESTPLATE, 75, 20), 1));
        this.elitarnySluga.add(new Items("10", 16.0, ItemHelper.createArmor("&4&lSpodnie Demona Ciemnosci", Material.DIAMOND_LEGGINGS, 75, 20), 1));
        this.elitarnySluga.add(new Items("11", 16.0, ItemHelper.createArmor("&4&lButy Demona Ciemnosci", Material.DIAMOND_BOOTS, 75, 20), 1));
    }


    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.elitarnySluga) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &fx" + item.getAmount() + " " + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
