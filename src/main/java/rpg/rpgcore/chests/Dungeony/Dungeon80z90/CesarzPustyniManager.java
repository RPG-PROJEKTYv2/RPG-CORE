package rpg.rpgcore.chests.Dungeony.Dungeon80z90;

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

public class CesarzPustyniManager {

    private final Set<Items> cesarzPustyni = Sets.newConcurrentHashSet();

    public CesarzPustyniManager() {
        this.cesarzPustyni.add(new Items("1", 7.0, new ItemBuilder(Material.MINECART).setName("&4&lEnergia Cesarza Pustyni").toItemStack(),1 ));
        this.cesarzPustyni.add(new Items("2", 7.5, new ItemBuilder(Material.FIREBALL).setName("&4&lMedalion Cesarza Pustyni").toItemStack(),1 ));
        this.cesarzPustyni.add(new Items("3", 8.0 , GlobalItem.I_KAMIENBAO.getItemStack(), ChanceHelper.getRandInt(1,4)));
        this.cesarzPustyni.add(new Items("4", 9.5, GlobalItem.I10.getItemStack(), ChanceHelper.getRandInt(1,4)));
        this.cesarzPustyni.add(new Items("5", 10.0, GlobalItem.I_OCZYSZCZENIE.getItemStack(), ChanceHelper.getRandInt(1,4)));
        this.cesarzPustyni.add(new Items("6", 11.0, LesnikItems.POTION.getItem(), ChanceHelper.getRandInt(1,5)));
        this.cesarzPustyni.add(new Items("7", 12.0, ItemHelper.createSword("&4&lMiecz Cesarza Pustyni", Material.DIAMOND_SWORD, 45, 35, false), 1));
        this.cesarzPustyni.add(new Items("8", 14.0, ItemHelper.createArmor("&4&lHelm Cesarza Pustyni", Material.DIAMOND_HELMET, 70, 24), 1));
        this.cesarzPustyni.add(new Items("9", 14.0, ItemHelper.createArmor("&4&lZbroja Cesarza Pustyni", Material.DIAMOND_CHESTPLATE, 75, 18), 1));
        this.cesarzPustyni.add(new Items("10", 14.0, ItemHelper.createArmor("&4&lSpodnie Cesarza Pustyni", Material.DIAMOND_LEGGINGS, 71, 18), 1));
        this.cesarzPustyni.add(new Items("11", 14.0, ItemHelper.createArmor("&4&lButy Cesarza Pustyni", Material.DIAMOND_BOOTS, 64, 17), 1));
    }


    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.cesarzPustyni) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &fx" + item.getAmount() + " " + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
