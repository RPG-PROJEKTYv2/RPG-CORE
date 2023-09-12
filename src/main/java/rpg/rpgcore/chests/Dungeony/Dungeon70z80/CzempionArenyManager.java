package rpg.rpgcore.chests.Dungeony.Dungeon70z80;

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

public class CzempionArenyManager {

    private final Set<Items> czempionAreny = Sets.newConcurrentHashSet();

    public CzempionArenyManager() {
        this.czempionAreny.add(new Items("1", 7.0, new ItemBuilder(Material.MINECART).setName("&6&lEnergia Czempiona Areny").toItemStack(),1 ));
        this.czempionAreny.add(new Items("2", 7.5, new ItemBuilder(Material.FIREBALL).setName("&6&lMedalion Czempiona Areny").toItemStack(),1 ));
        this.czempionAreny.add(new Items("3", 8.0 , GlobalItem.I_KAMIENBAO.getItemStack(), 1));
        this.czempionAreny.add(new Items("4", 9.5, GlobalItem.I10.getItemStack(), 1));
        this.czempionAreny.add(new Items("5", 10.0, GlobalItem.I_OCZYSZCZENIE.getItemStack(), 1));
        this.czempionAreny.add(new Items("6", 11.0, LesnikItems.POTION.getItem(), 1));
        this.czempionAreny.add(new Items("7", 12.0, ItemHelper.createSword("&6&lMiecz Czempiona Areny", Material.DIAMOND_SWORD, 42, 26, false), 1));
        this.czempionAreny.add(new Items("8", 14.0, ItemHelper.createArmor("&6&lHelm Czempiona Areny", Material.DIAMOND_HELMET, 62, 15), 1));
        this.czempionAreny.add(new Items("9", 14.0, ItemHelper.createArmor("&6&lZbroja Czempiona Areny", Material.DIAMOND_CHESTPLATE, 63, 19), 1));
        this.czempionAreny.add(new Items("10", 14.0, ItemHelper.createArmor("&6&lSpodnie Czempiona Areny", Material.DIAMOND_LEGGINGS, 65, 19), 1));
        this.czempionAreny.add(new Items("11", 14.0, ItemHelper.createArmor("&6&lButy Czempiona Areny", Material.DIAMOND_BOOTS, 67, 15), 1));
    }


    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.czempionAreny) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                final Items clone = item.clone();

                if (clone.getRewardItem().isSimilar(GlobalItem.I10.getItemStack())) clone.setAmount(ChanceHelper.getRandInt(1,3));
                else if (clone.getRewardItem().isSimilar(GlobalItem.I_KAMIENBAO.getItemStack())) clone.setAmount(ChanceHelper.getRandInt(1,3));
                else if (clone.getRewardItem().isSimilar(GlobalItem.I_OCZYSZCZENIE.getItemStack())) clone.setAmount(ChanceHelper.getRandInt(1,3));
                else if (clone.getRewardItem().isSimilar(GlobalItem.I_OCZYSZCZENIE.getItemStack())) clone.setAmount(ChanceHelper.getRandInt(1,3));
                else if (clone.getRewardItem().isSimilar(LesnikItems.POTION.getItem())) clone.setAmount(ChanceHelper.getRandInt(1,5));

                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &fx" + clone.getAmount() + " " + clone.getRewardItem().getItemMeta().getDisplayName()));

                return clone;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
