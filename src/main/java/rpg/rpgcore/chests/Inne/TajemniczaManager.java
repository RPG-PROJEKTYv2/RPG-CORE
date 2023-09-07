package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TajemniczaManager {


    private final Set<Items> tajemnicza = Sets.newConcurrentHashSet();



    public TajemniczaManager() {
        this.tajemnicza.add(new Items("1", 0.06, GlobalItem.getItem("I_FRAGMENT_BONA", 1), 2));
        this.tajemnicza.add(new Items("2", 1.0, SkrzynkiOther.getItem("I1", 1),1));
        this.tajemnicza.add(new Items("3", 2.0, GlobalItem.I_KAMIENBAO.getItemStack(), 1));
        this.tajemnicza.add(new Items("4", 4.0, GlobalItem.getItem("I10", 2), 2));
        this.tajemnicza.add(new Items("5", 4.0, GlobalItem.getItem("I_OCZYSZCZENIE", 2),2));
        this.tajemnicza.add(new Items("6", 4.2, SkrzynkiOther.I4.getItemStack(), 1));
        this.tajemnicza.add(new Items("7", 6.0, GlobalItem.getItem("I50", 1), 1));
        this.tajemnicza.add(new Items("8", 7.0, GlobalItem.getItem("I_FRAGMENT_STALI", 2), 2));
        this.tajemnicza.add(new Items("9", 7.0, LesnikItems.POTION.getItem(), 1));
        // set I
        this.tajemnicza.add(new Items("10", 10.0, ItemHelper.createArmor("&3Tajemniczy Helm", Material.IRON_HELMET, ChanceHelper.getRandInt(3, 6), ChanceHelper.getRandInt(1, 2)), 1));
        this.tajemnicza.add(new Items("11", 10.0, ItemHelper.createArmor("&3Tajemnicza Klata", Material.IRON_CHESTPLATE, ChanceHelper.getRandInt(4, 7), ChanceHelper.getRandInt(1, 2)), 1));
        this.tajemnicza.add(new Items("12", 10.0, ItemHelper.createArmor("&3Tajemnicze Spodnie", Material.IRON_LEGGINGS, ChanceHelper.getRandInt(3, 6), ChanceHelper.getRandInt(1, 2)), 1));
        this.tajemnicza.add(new Items("13", 10.0, ItemHelper.createArmor("&3Tajemnicze Buty", Material.IRON_BOOTS, ChanceHelper.getRandInt(3, 5), ChanceHelper.getRandInt(1, 2)), 1));
    }


    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.tajemnicza) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                item.getRewardItem().setAmount(item.getAmount());
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &fx" + item.getAmount() + " " + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
